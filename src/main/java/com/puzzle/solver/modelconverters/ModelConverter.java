package com.puzzle.solver.modelconverters;

import com.puzzle.solver.Shape;
import com.puzzle.solver.ShapeSet;
import com.puzzle.solver.model.ShapeModel;

public class ModelConverter {
    public static ShapeSet convertToShapeSet(ShapeModel[] shapeModels) {
        ShapeSet shapeSet = new ShapeSet();
        for(ShapeModel sm : shapeModels) {
            System.out.println("Shape " + sm.name);
            Shape shape = new Shape(sm.name, sm.dimensions.width, sm.dimensions.height, sm.elements);
            shapeSet.addShape(shape);
        }
        return shapeSet;
    }
}
