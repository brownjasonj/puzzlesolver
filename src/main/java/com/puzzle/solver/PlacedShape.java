package com.puzzle.solver;

import java.util.UUID;

public class PlacedShape {
    protected UUID id;
    protected Rotation rotation;
    protected int x;
    protected int y;

    PlacedShape(int x, int y, UUID id, Rotation rotation) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.rotation = rotation;
    }

    public UUID getId() {
        return id;
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
