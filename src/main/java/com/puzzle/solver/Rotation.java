package com.puzzle.solver;

public enum Rotation {
    North (0,0),
    East (1,90),
    South (2,180),
    West (3, 270);

    private final int index;
    private final int degrees;

    Rotation(int index, int degrees) {
        this.index = index;
        this.degrees = degrees;
    }

    public int getIndex() {
        return this.index;
    }

    public Rotation rotate() {
        switch (this) {
            case North:
                return East;
            case East:
                return South;
            case South:
                return West;
            case West:
                return North;
        }
        return North;
    }

}
