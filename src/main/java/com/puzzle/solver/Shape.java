package com.puzzle.solver;

import java.util.HashMap;
import java.util.UUID;

public class Shape {
    private HashMap<Rotation, ShapeRotation> shapes;
    private UUID id;
    private String name;

    private class ShapeRotation {
        public Element[][] elements;
        public int width;
        public int height;
        public Rotation rotation;

        ShapeRotation(int width, int height, Rotation rotation, Element[][] elements) {
            this.width = width;
            this.height = height;
            this.rotation = rotation;
            this.elements = new Element[width][height];
            for(int x = 0; x < width; x++)
                for(int y = 0; y < height; y++)
                    this.elements[x][y] = elements[x][y];
        }
    }

    Shape(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.shapes = new HashMap<>();
    }

    public void addRotation(int width, int height, Rotation rotation, Element[][] elementGrid) {
        this.shapes.put(rotation, new ShapeRotation(width, height, rotation, elementGrid));
    }

    public int getWidth(Rotation rotation) {
        if (shapes.containsKey(rotation))
            return shapes.get(rotation).width;
        else
            return -1;
    }

    public int getHeight(Rotation rotation) {
        if (shapes.containsKey(rotation))
            return shapes.get(rotation).height;
        else
            return -1;
    }

    public UUID getId() { return this.id; }

    public String getName() {
        return name;
    }

    public Element[][] getElements(Rotation rotation) {
        if (shapes.containsKey(rotation))
            return shapes.get(rotation).elements;
        else
            return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
