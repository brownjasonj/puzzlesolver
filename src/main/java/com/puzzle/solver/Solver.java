package com.puzzle.solver;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    protected int width;
    protected int height;
    protected ShapeSet shapeSet;

    public Solver(int width, int height, ShapeSet shapeSet) {
        this.width = width;
        this.height = height;
        this.shapeSet = shapeSet;
    }

    private void solve(SolutionState currentState, List<SolutionState> solutions) {
        if (currentState.hasUnplaceShapes()) {
            String shapeName = currentState.nextRemainingShape();
            Shape shape = this.shapeSet.getShape(shapeName);
            for(int x = 0; x < currentState.board.getWidth(); x++) {
                for (int y = 0; y < currentState.board.getHeight(); y++) {
                    SolutionState nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.North)) {
                        solve(nextState, solutions);
                    }
                    // else try rotating the shape in the same location.
                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.East)) {
                        solve(nextState, solutions);
                    }

                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.South)) {
                        solve(nextState, solutions);
                    }

                    nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.West)) {
                        solve(nextState, solutions);
                    }
                }
            }
        }
        else {
            solutions.add(currentState);
        }
    }

    public List<SolutionState> solve() {
        SolutionState startState = new SolutionState(new Board(this.width, this.height), this.shapeSet.getAllShapes());
        List<SolutionState> solutions = new ArrayList<>();

       solve(startState, solutions);

       return solutions;
    }
}
