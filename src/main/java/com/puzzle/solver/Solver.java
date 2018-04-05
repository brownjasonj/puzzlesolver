package com.puzzle.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Solver {

    private static void solve(SolutionState currentState, List<SolutionState> solutions) {
        if (currentState.hasUnplaceShapes()) {
            UUID shapeId = currentState.nextRemainingShape();
            Shape shape = ShapeSet.getShape(shapeId);
            for(int x = 0; x < currentState.board.getWidth(); x++) {
                for (int y = 0; y < currentState.board.getHeight(); y++) {
                    SolutionState nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.North)) {
                        Solver.solve(nextState, solutions);
                    }
                    // else try rotating the shape in the same location.
                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.East)) {
                        Solver.solve(nextState, solutions);
                    }

                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.South)) {
                        Solver.solve(nextState, solutions);
                    }

                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.West)) {
                        Solver.solve(nextState, solutions);
                    }
                }
            }
        }
        else {
            solutions.add(currentState);
        }
    }

    public static List<SolutionState> solve() {
        SolutionState startState = new SolutionState(ShapeSet.getAllShapes());
        List<SolutionState> solutions = new ArrayList<>();

       solve(startState, solutions);

       return solutions;
    }
}
