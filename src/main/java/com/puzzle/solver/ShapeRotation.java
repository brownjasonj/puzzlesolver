package com.puzzle.solver;


public class ShapeRotation {
    public Element[][] elements;
    public int width;
    public int height;
    public Rotation rotation;

    ShapeRotation(int width, int height, Rotation rotation, Element[][] elements) {
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.elements = new Element[height][width];
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                this.elements[y][x] = elements[y][x];
    }

    public Element[][] getElements() {
        return elements;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rotation getRotation() {
        return rotation;
    }
}
