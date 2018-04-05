package com.puzzle.solver;

import java.util.UUID;

public class Solver {

    private static SolutionState solve(SolutionState currentState) {
        if (currentState.hasUnplaceShapes()) {
            UUID shapeId = currentState.nextRemainingShape();
            Shape shape = ShapeSet.getShape(shapeId);
            for(int x = 0; x < currentState.board.getWidth(); x++) {
                for (int y = 0; y < currentState.board.getHeight(); y++) {
                    SolutionState nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.North)) {
                        SolutionState completedPath = Solver.solve(nextState);
                        if (completedPath != null)
                            return completedPath;
                    }
                    // else try rotating the shape in the same location.
                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.East)) {
                        SolutionState completedPath = Solver.solve(nextState);
                        if (completedPath != null)
                            return completedPath;
                    }

                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.South)) {
                        SolutionState completedPath = Solver.solve(nextState);
                        if (completedPath != null)
                            return completedPath;
                    }

                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.West)) {
                        SolutionState completedPath = Solver.solve(nextState);
                        if (completedPath != null)
                            return completedPath;
                    }
                }
            }
            // iterated through all shapes without finding a path
            // return null to indicated to the caller that this path failed to place the remaining shapes from given state
            return null;
        }
        else {
            return currentState;
        }
    }

    public static SolutionState solve() {
        SolutionState startState = new SolutionState(ShapeSet.getAllShapes());

        SolutionState finalState = solve(startState);

        if (finalState != null) {
            System.out.println("Solution found");
            System.out.println(finalState.toString());
            return finalState;
        }
        else {
            System.out.println("No Solution found");
            return null;
        }
    }
}
