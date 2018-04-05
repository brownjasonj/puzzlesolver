package com.puzzle.solver.modelconverters;

import com.puzzle.solver.Shape;
import com.puzzle.solver.ShapeSet;
import com.puzzle.solver.model.RotationModel;
import com.puzzle.solver.model.ShapeModel;

public class ModelConverter {
    public static ShapeSet convertToShapeSet(ShapeModel[] shapeModels) {
        ShapeSet shapeSet = new ShapeSet();
        for(ShapeModel sm : shapeModels) {
            Shape shape = new Shape(sm.name);
            for(RotationModel rm : sm.rotations) {
                shape.addRotation(rm.dimensions.width, rm.dimensions.height, rm.rotation, rm.elements);
            }
            shapeSet.addShape(shape);
        }
        return shapeSet;
    }
}
