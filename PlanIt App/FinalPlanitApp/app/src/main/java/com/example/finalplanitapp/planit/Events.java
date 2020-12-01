package com.example.finalplanitapp.planit;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.main;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
// Refer to TSP with one vehicle

public class Events {
  static {
    System.loadLibrary("jniortools");
  }

  static class DataModel {
    public final long[][] distanceMatrix = {
    		{0, 538, 449207, 2955342, 3418959, 4386457},
    		{921, 0, 448587, 2956549, 3420166, 4387664},
    		{449672, 449584, 0, 2929649, 3330932, 4361058},
    		{2951815, 2953079, 2930314, 0, 618203, 1577847},
    		{3413585, 3414849, 3329506, 617636, 0, 972863},
    		{4381927, 4383191, 4360885, 1559624, 971037, 0}
    		// td: google distance mat. resp. w weights
    };
    public final int numVehicle = 1;
    public final int startLocation = 0;
  }

  static void printSolution(
	RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
	  // Since no add. weights, costs result in the same route
	  String route = "";
	  System.out.println("Your route is: ");
	  long routeDistance = 0;
	  long index = routing.start(0);
	  while (!routing.isEnd(index)) {
      route += manager.indexToNode(index) + "\n";
      long previousIndex = index;
      index = solution.value(routing.nextVar(index));
      routeDistance += routing.getArcCostForVehicle(previousIndex, index, 0);
    }
    route += manager.indexToNode(routing.end(0));
    System.out.println(route + "\n");
    // td: change to metric
    System.out.println("Distance in miles: " + routeDistance);
  }

  public static void main(String[] args) throws Exception {
    final DataModel data = new DataModel();
    RoutingIndexManager manager = new RoutingIndexManager(data.distanceMatrix.length, data.numVehicle, data.startLocation);
    RoutingModel routing = new RoutingModel(manager);

    // Create and register a transit callback
    final int transitCallbackIndex = routing.registerTransitCallback((long fromIndex, long toIndex) -> {
          // Convert from routing variable Index to user NodeIndex
          int fromNode = manager.indexToNode(fromIndex);
          int toNode = manager.indexToNode(toIndex);
          return data.distanceMatrix[fromNode][toNode];
        });

    // Defines cost of travel between locations (cost of edge)
    routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

    // PATH_CHEAPEST_ARC creates a baselines route and ensures a place already in the route is not revisited
    // Except for the startLocation and ending location (represented by 0)
    RoutingSearchParameters searchParameters =
        main.defaultRoutingSearchParameters()
            .toBuilder()
            .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
            .build();

    Assignment solution = routing.solveWithParameters(searchParameters);
    printSolution(routing, manager, solution);
  }
}
