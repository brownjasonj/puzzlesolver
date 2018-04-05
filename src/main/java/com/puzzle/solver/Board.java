package com.puzzle.solver;

import java.util.ArrayList;

public class Board {
    private static ArrayList<Board> freeBoards = new ArrayList<Board>();
    private static ArrayList<Board> usedBoards = new ArrayList<Board>();

    protected int width = 3;
    protected int height = 2;


    protected Element board[][];

    private Board() {
        this.board = new Element[this.width][this.height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Element[][] getElements() {
        return this.board;
    }

    private void reset() {
        for(int i = 0; i < this.width; i++) {
            for(int j = 0; j < this.height; j++) {
                board[i][j] = Element.Empty;
            }
        }
    }

    public Board copy() {
        Board newBoard = Board.getBoard();
        for(int i = 0; i < this.getWidth(); i++)
            for(int j = 0; j < this.getHeight(); j++)
                newBoard.board[i][j] = this.board[i][j];
        return newBoard;
    }

    public static Board getBoard() {
        if (freeBoards.isEmpty()) {
            Board newBoard = new Board();
            newBoard.reset();
            usedBoards.add(newBoard);
            return newBoard;
        }
        else {
            Board board = freeBoards.get(0);
            freeBoards.remove(0);
            usedBoards.add(board);
            board.reset();
            return board;
        }
    }
}
