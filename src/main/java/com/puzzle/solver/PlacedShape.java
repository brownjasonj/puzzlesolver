package com.puzzle.solver;


public class PlacedShape {
    protected String name;
    protected Rotation rotation;
    protected int x;
    protected int y;

    public PlacedShape(int x, int y, String name, Rotation rotation) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.rotation = rotation;
    }

    public String getName() {
        return name;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
