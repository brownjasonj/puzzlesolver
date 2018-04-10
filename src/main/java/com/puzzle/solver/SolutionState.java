package com.puzzle.solver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class SolutionState {
    protected List<PlacedShape> placedShapes;
    protected List<String> remainingShapes;
    protected Board board;
    protected Date startTime;
    protected Date endTime;
    protected int duration;

    protected SolutionState(Board board) {
        this.board = board;
        this.placedShapes = new ArrayList<>();
        this.remainingShapes = new ArrayList<>();
        this.startTime = new Date();
    }

    public SolutionState(Board board, Set<String> shapeSet) {
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
        this.startTime = toBeCopied.startTime;
    }

    public List<String> getRemainingShapes() {
        return remainingShapes;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return duration;
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

    public boolean placeShape(int x, int y, Shape shape, Rotation rotation) {

        // check that shape can be placed in the x, y given
        int shapeWidth = shape.getWidth(rotation);
        int shapeHeight = shape.getHeight(rotation);
        Element[][] shapeElements = shape.getElements(rotation);


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
                    // System.out.print("board = " + this.board.getElements()[j][i] + " shape = " + shapeElements[j - y][i - x]);
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

    public void printState(ShapeSet shapeSet) {
        int boardWidth = this.board.getWidth();
        int boardHeight = this.board.getHeight();
        String[][] state = new String[boardHeight][boardWidth];
        for(int i = 0; i < boardHeight; i++) {
            for(int j = 0; j < boardWidth; j++)
                state[i][j] = " ";
        }

        for(PlacedShape ps : placedShapes) {
            int x = ps.x;
            int y = ps.y;
            Shape shape = shapeSet.getShape(ps.name);
            ShapeRotation shapeRotation = shape.getShapeRotation(ps.rotation);
            for(int i = x; i < (x + shapeRotation.getWidth()); i++) {
                for (int j = y; j < (y + shapeRotation.getHeight()); j++) {
                    // if shapeElement is Empty, then don't copy to the board!
                    Element copyElement = shapeRotation.getElements()[j - y][i - x];
                    if (copyElement != Element.Empty)
                        state[j][i] = shape.getLabel();
                }
            }
        }

        System.out.println("*****************************************");
        for(int y = 0; y < boardHeight; y++) {
            System.out.print("| ");
            for (int x = 0; x < boardWidth; x++) {
                    System.out.print(state[y][x] + " | ");
            }
            System.out.println("");
        }
        System.out.println("*****************************************");
        System.out.println();

    }
}
