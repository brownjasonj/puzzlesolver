package com.puzzle.solver.solvers;

import com.puzzle.solver.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleIterativeSolver {
    protected int width;
    protected int height;
    protected ShapeSet shapeSet;

    protected static int callCount = 0;

    protected void increment(SolutionState state) {
        SimpleIterativeSolver.callCount++;

        if (SimpleIterativeSolver.callCount % 1000 == 1) {
            state.printState(shapeSet);
        }
    }

    public SimpleIterativeSolver(int width, int height, ShapeSet shapeSet) {
        this.width = width;
        this.height = height;
        this.shapeSet = shapeSet;
    }

    private void solve(SolutionState currentState, List<SolutionState> solutions) {
        if (currentState.hasUnplaceShapes()) {
            String shapeName = currentState.nextRemainingShape();
            Shape shape = this.shapeSet.getShape(shapeName);
            for(int x = 0; x < currentState.getBoard().getWidth(); x++) {
                for (int y = 0; y < currentState.getBoard().getHeight(); y++) {
                    SolutionState nextState = currentState.copy();
                    if (nextState.placeShape(x, y, shape, Rotation.North)) {
                        increment(nextState);
                        solve(nextState, solutions);
                    }
//                    // else try rotating the shape in the same location.
//                    nextState = currentState.copy();
//                    if (nextState.placeShape(x, y, shape, Rotation.East)) {
//                        increment(nextState);
//                        solve(nextState, solutions);
//                    }
//
//                    nextState = currentState.copy();
//                    if (nextState.placeShape(x, y, shape, Rotation.South)) {
//                        increment(nextState);
//                        solve(nextState, solutions);
//                    }
//
//                    nextState = currentState.copy();
//                    if (nextState.placeShape(x, y, shape, Rotation.West)) {
//                        increment(nextState);
//                        solve(nextState, solutions);
//                    }
                }
            }
        }
        else {
            Date endTime = new Date();
            currentState.setEndTime(endTime);
            solutions.add(currentState);
            System.out.println("Solution found @" + endTime + " total found : " + solutions.size());
        }
    }

    public List<SolutionState> solve() {
        SolutionState startState = new SolutionState(new Board(this.width, this.height), this.shapeSet.getAllShapes());
        List<SolutionState> solutions = new ArrayList<>();

       solve(startState, solutions);

       return solutions;
    }
}
