package com.puzzle.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SolutionState {
    protected List<PlacedShape> placedShapes;
    protected List<String> remainingShapes;
    protected Board board;

    protected SolutionState(Board board) {
        this.board = board;
        this.placedShapes = new ArrayList<>();
        this.remainingShapes = new ArrayList<>();
    }

    SolutionState(Board board, Set<String> shapeSet) {
        this(board);
        for(String name : shapeSet)
            this.remainingShapes.add(name);
    }

    private SolutionState(SolutionState toBeCopied) {
        this(toBeCopied.getBoard().copy());
        for(PlacedShape ps : toBeCopied.placedShapes)
            this.placedShapes.add(ps);
        for(String rs: toBeCopied.remainingShapes)
            this.remainingShapes.add(rs);
    }

    public List<String> getRemainingShapes() {
        return remainingShapes;
    }

    public Board getBoard() {
        return board;
    }

    public List<PlacedShape> getPlacedShapes() {
        return placedShapes;
    }

    public SolutionState copy() {
        return new SolutionState(this);
    }

    public boolean hasUnplaceShapes() {
        return !this.remainingShapes.isEmpty();
    }

    public String nextRemainingShape() {
        if (this.remainingShapes.isEmpty())
            return null;
        else
            return this.remainingShapes.get(0);
    }

    boolean placeShape(int x, int y, Shape shape, Rotation rotation) {

        // check that shape can be placed in the x, y given
        int shapeWidth = shape.getWidth(rotation);
        int shapeHeight = shape.getHeight(rotation);
        Element[][] shapeElements = shape.getElements(rotation);

        System.out.println("Trying to place " + shape.getName() + " at " + x + "," + y + " with rotation " + rotation);

        // check that the shape doesn't overlap edges of board
        if ((x + shapeWidth > board.getWidth())
                || (y + shapeHeight > board.getHeight())) {
            return false;
        }

        // check it doesn't overlap any shape already placed on board
        // and that placing the shape doesn't create a solution where two adjacent elements have the same value
        for (int j = y; j < (y + shapeHeight); j++) {
            for(int i = x; i < (x + shapeWidth); i++) {
                Element shapeElement = shapeElements[j - y][i - x];
                if (this.board.getElements()[j][i] != Element.Empty &&  shapeElement != Element.Empty) {
                    System.out.print("board = " + this.board.getElements()[j][i] + " shape = " + shapeElements[j - y][i - x]);
                    return false;
                }

                // check adjacent pieces on the board are alternative element types as follows:
                //
                // Empty can be next to anything
                // Green can only be adjacent to White or Empty
                // White can only be adjacent to Green or Empty
                //
                if (i > 0) {
                    if (this.board.getElements()[j][i-1] != Element.Empty &&  this.board.getElements()[j][i-1] == shapeElement)
                        return false;
                }
                if (i < (board.getWidth() - 1)) {
                    if (this.board.getElements()[j][i+1] != Element.Empty &&  this.board.getElements()[j][i+1] == shapeElement)
                        return false;
                }
                if (j > 0) {
                    if (this.board.getElements()[j-1][i] != Element.Empty &&  this.board.getElements()[j-1][i] == shapeElement)
                        return false;
                }
                if (j < (board.getHeight() - 1)) {
                    if (this.board.getElements()[j+1][i] != Element.Empty &&  this.board.getElements()[j+1][i] == shapeElement)
                        return false;
                }
            }
        }

        // getting here means that the shape can be placed on the board without overlapping the edge
        // of the board or another shape and without creating a solution where two adjacent elements have the same value

        // ok it is a valid placement of the shape.  Add the shape placement to the list and add
        // the shape elements to the board
        placedShapes.add(new PlacedShape(x, y, shape.getName(), rotation));
        for(int i = x; i < (x + shapeWidth); i++) {
            for (int j = y; j < (y + shapeHeight); j++) {
                // if shapeElement is Empty, then don't copy to the board!
                Element copyElement = shapeElements[j - y][i - x];
                if (copyElement != Element.Empty)
                    this.board.getElements()[j][i] = copyElement;
            }
        }

        this.remainingShapes.remove(shape.getName());

        return true;
    }

    @Override
    public String toString() {
        for(PlacedShape ps : placedShapes) {
            System.out.println("id: " + ps.name.toString() + " x: " + ps.x + " y: " + ps.y + " rotation: " + ps.rotation);
        }
        return super.toString();
    }
}
