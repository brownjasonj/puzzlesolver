package com.puzzle.solver;

import java.util.HashMap;
import java.util.Set;

public class ShapeSet {
    private HashMap<String, Shape> shapes;

    public ShapeSet() {
        this.shapes = new HashMap<>();
    }

    public void addShape(Shape shape) {
        this.shapes.put(shape.getName(), shape);
    }

    public Shape getShape(String name) {
        if (shapes.containsKey(name)) {
            return shapes.get(name);
        }
        else
            return null;
    }

    public Set<String> getAllShapes() {
        return shapes.keySet();
    }
}

