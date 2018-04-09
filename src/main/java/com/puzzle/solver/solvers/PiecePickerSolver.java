package com.puzzle.solver.solvers;

import com.puzzle.solver.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PiecePickerSolver {
    protected int width;
    protected int height;
    protected ShapeSet shapeSet;

    protected static int callCount = 0;

    protected void increment(SolutionState state) {
        SimpleIterativeSolver.callCount++;
//        if (SimpleIterativeSolver.callCount < 20)
//            state.printState(shapeSet);
//
//        if (SimpleIterativeSolver.callCount % 10 == 1) {
//            state.printState(shapeSet);
//        }
    }

    private class FreeLocation {
        protected int x;
        protected int y;
        protected Element el;

        public FreeLocation(int x, int y, Element e) {
            this.x = x;
            this.y = y;
            this.el = e;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Element getEl() {
            return el;
        }
    }

    /*
        Pre-requisite:  the given board is valid

        Returns:  the next free location on the board and returns the
        type of element that needs to fit in the location to ensure
        that the board remains valid.
        i.e., if the free location is surrounded by white squares, the
        location must be green otherwise the board would be invalid.
     */
    protected FreeLocation nextFreeLocation(Board board) {
        FreeLocation freeLocation = null;
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                if (board.getElements()[y][x] == Element.Empty) {
                    Element el = Element.Empty;
                    // check adjacent pieces on the board are alternative element types as follows:
                    //
                    // Empty can be next to anything
                    // Green can only be adjacent to White or Empty
                    // White can only be adjacent to Green or Empty
                    //
                    if (x > 0 && board.getElements()[y][x - 1] != Element.Empty) {
                        el = board.getElements()[y][x - 1];
                    } else if (x < (board.getWidth() - 1) && board.getElements()[y][x + 1] != Element.Empty) {
                        el = board.getElements()[y][x + 1];
                    } else if (y > 0 && board.getElements()[y - 1][x] != Element.Empty) {
                        el = board.getElements()[y-1][x];
                    } else if (y < (board.getHeight() - 1) && board.getElements()[y + 1][x] != Element.Empty) {
                        el = board.getElements()[y+1][x];
                    }

                    if (el == Element.Green)
                        el = Element.White;
                    else if (el == Element.White)
                        el = Element.Green;
                    return new FreeLocation(x, y, el);
                }
            }
        }
        return freeLocation;
    }


    /*
        given a free location, find the list of shapes (including rotations) that could potentially
        fill the free location
     */
    protected List<PlacedShape> validPlaceableShapes(SolutionState state, FreeLocation location) {

        List<PlacedShape> placedShapes = new ArrayList<>();

        for(String rsId : state.getRemainingShapes()) {
            Shape shape = this.shapeSet.getShape(rsId);
            for(Rotation r: Rotation.values()) {
                ShapeRotation shapeRotation = shape.getShapeRotation(r);

                if (shapeRotation.getHeight() < (state.getBoard().getHeight() - location.getY() + 1)) {
                    Element[][] elements = shapeRotation.elements;


                    int x = 0;
                    boolean found = false;
                    while(!found && x < shapeRotation.getWidth()) {
                        if (elements[0][x] == Element.Empty) {
                            x++;
                        }
                        else {
                            if ((location.getX() - x) < 0 || (location.getEl() != Element.Empty && elements[0][x] != location.getEl())) {
                                // no fit
                                found = true;
                            }
                            else {
                                // could be a match.
                                if ((shapeRotation.getWidth() - x) > (state.getBoard().getWidth() - location.getX()))
                                    found = true;
                                else {
                                    found = true;
                                    placedShapes.add(new PlacedShape(location.getX() - x, location.getY(), rsId, r));
                                }
                            }
                        }
                    }
                }
            }
        }

        return placedShapes;
    }

    public PiecePickerSolver(int width, int height, ShapeSet shapeSet) {
        this.width = width;
        this.height = height;
        this.shapeSet = shapeSet;
    }


    private void solve(SolutionState currentState, List<SolutionState> solutions) {
        if (currentState.hasUnplaceShapes()) {
            FreeLocation loc = nextFreeLocation(currentState.getBoard());
            if (loc != null) {
                List<PlacedShape> placeableShapes = validPlaceableShapes(currentState, loc);
                for (PlacedShape ps : placeableShapes) {
                    SolutionState nextState = currentState.copy();
                    Shape shape = shapeSet.getShape(ps.getName());
                    if (nextState.placeShape(ps.getX(), ps.getY(), shape, ps.getRotation())) {
                        increment(nextState);
                        solve(nextState, solutions);
                    }
                }
            }
        }
        else {
            Date endTime = new Date();
            currentState.setEndTime(endTime);
            solutions.add(currentState);
            currentState.printState(shapeSet);
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
