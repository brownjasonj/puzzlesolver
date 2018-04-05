package com.puzzle.solver;

import java.util.ArrayList;

public class Board {
    protected int width = 3;
    protected int height = 2;


    protected Element board[][];

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Element[height][width];
        this.reset();
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
                board[j][i] = Element.Empty;
            }
        }
    }

    public Board copy() {
        Board newBoard = new Board(this.width, this.height);
        for(int i = 0; i < this.getWidth(); i++)
            for(int j = 0; j < this.getHeight(); j++)
                newBoard.board[j][i] = this.board[j][i];
        return newBoard;
    }
}
