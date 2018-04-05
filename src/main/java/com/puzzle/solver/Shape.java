package com.puzzle.solver;

import java.util.HashMap;
import java.util.UUID;

public class Shape {
    private HashMap<Rotation, ShapeRotation> shapes;
    private String name;

    public Shape(String name) {
        this.name = name;
        this.shapes = new HashMap<>();
    }

    public void addRotation(int width, int height, Rotation rotation, Element[][] elementGrid) {
        this.shapes.put(rotation, new ShapeRotation(width, height, rotation, elementGrid));
    }

    public void addRotation(ShapeRotation shapeRotation) {
        if (!this.shapes.containsKey(shapeRotation.rotation)) {
            this.shapes.put(shapeRotation.rotation, shapeRotation);
        }
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
