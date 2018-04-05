package com.puzzle.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SolutionState {
    protected List<PlacedShape> placedShapes;
    protected List<UUID> remainingShapes;
    protected Board board;

    protected SolutionState() {
        this.board = Board.getBoard();
        this.placedShapes = new ArrayList<>();
        this.remainingShapes = new ArrayList<>();
    }

    SolutionState(Set<UUID> shapeSet) {
        this();
        for(UUID id : shapeSet)
            this.remainingShapes.add(id);
    }

    private SolutionState(SolutionState toBeCopied) {
        this();
        for(PlacedShape ps : toBeCopied.placedShapes)
            this.placedShapes.add(ps);
        for(UUID rs: toBeCopied.remainingShapes)
            this.remainingShapes.add(rs);
        this.board = toBeCopied.board.copy();
    }

    public List<UUID> getRemainingShapes() {
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

    public UUID nextRemainingShape() {
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
        for(int i = x; i < (x + shapeWidth); i++) {
            for (int j = y; j < (y + shapeHeight); j++) {
                Element shapeElement = shapeElements[i - x][j - y];
                if (this.board.getElements()[i][j] != Element.Empty &&  shapeElement != Element.Empty) {
                    System.out.print("board = " + this.board.getElements()[i][j] + " shape = " + shapeElements[i - x][j - y]);
                    return false;
                }

                // check adjacent pieces on the board are alternative element types as follows:
                //
                // Empty can be next to anything
                // Green can only be adjacent to White or Empty
                // White can only be adjacent to Green or Empty
                //
                if (i > 0) {
                    if (this.board.getElements()[i-1][j] != Element.Empty &&  this.board.getElements()[i-1][j] == shapeElement)
                        return false;
                }
                if (i < (board.getWidth() - 1)) {
                    if (this.board.getElements()[i+1][j] != Element.Empty &&  this.board.getElements()[i+1][j] == shapeElement)
                        return false;
                }
                if (j > 0) {
                    if (this.board.getElements()[i][j-1] != Element.Empty &&  this.board.getElements()[i][j-1] == shapeElement)
                        return false;
                }
                if (j < (board.getHeight() - 1)) {
                    if (this.board.getElements()[i][j+1] != Element.Empty &&  this.board.getElements()[i][j+1] == shapeElement)
                        return false;
                }
            }
        }

        // getting here means that the shape can be placed on the board without overlapping the edge
        // of the board or another shape and without creating a solution where two adjacent elements have the same value

        // ok it is a valid placement of the shape.  Add the shape placement to the list and add
        // the shape elements to the board
        placedShapes.add(new PlacedShape(x, y, shape.getId(), rotation));
        for(int i = x; i < (x + shapeWidth); i++) {
            for (int j = y; j < (y + shapeHeight); j++) {
                // if shapeElement is Empty, then don't copy to the board!
                Element copyElement = shapeElements[i - x][j - y];
                if (copyElement != Element.Empty)
                    this.board.getElements()[i][j] = copyElement;
            }
        }

        this.remainingShapes.remove(shape.getId());

        return true;
    }

    @Override
    public String toString() {
        for(PlacedShape ps : placedShapes) {
            System.out.println("id: " + ps.id.toString() + " x: " + ps.x + " y: " + ps.y + " rotation: " + ps.rotation);
        }
        return super.toString();
    }
}