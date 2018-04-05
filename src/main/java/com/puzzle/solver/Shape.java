package com.puzzle.solver;

import java.util.HashMap;

public class Shape {
    private HashMap<Rotation, ShapeRotation> shapes;
    private String name;

    public Shape(String name) {
        this.name = name;
        this.shapes = new HashMap<>();
    }

    public Shape(String name, int width, int height, Element[][] elements) {
        this(name);
        this.addRotation(width, height, Rotation.North, elements);
        Element[][] east = this.rotateArray90clockwise(elements);
        this.addRotation(height, width, Rotation.East, east);
        Element[][] south = this.rotateArray90clockwise(east);
        this.addRotation(width, height, Rotation.South, south);
        Element[][] west = this.rotateArray90clockwise(south);
        this.addRotation(height, width, Rotation.West, west);
    }

    protected Element[][] rotateArray90clockwise(Element[][] array){

        Element[][] target = new Element[array[0].length][array.length];

        for (int i = 0; i < target.length; i++) {
            for (int j = 0; j < target[i].length; j++) {
                target[i][j] = array[(target[i].length - 1) - j][i];
            }
        }

        return target;
    }

    protected void addRotation(int width, int height, Rotation rotation, Element[][] elementGrid) {
        this.shapes.put(rotation, new ShapeRotation(width, height, rotation, elementGrid));
    }

    protected void addRotation(ShapeRotation shapeRotation) {
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
