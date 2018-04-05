package com.puzzle.solver;

import javax.lang.model.util.Elements;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class ShapeSet {
    private static HashMap<UUID, Shape> shapes;

    static {
        shapes = new HashMap<>();

        Shape smallla = new Shape("Small L A");
        ShapeSet.shapes.put(smallla.getId(), smallla);

        Element[][] smalllaElements = new Element[2][2];
        smalllaElements[0][0] = Element.White;
        smalllaElements[0][1] = Element.Empty;
        smalllaElements[1][0] = Element.Green;
        smalllaElements[1][1] = Element.White;
        smallla.addRotation(2, 2, Rotation.North, smalllaElements);

        // Clockwise rotation of 90 degrees
        // [0][0] -> [1][0]
        // [0][1] -> [0][0]
        // [1][0] -> [1][1]
        // [1][1] -> [0][1]
        //
        smalllaElements[0][0] = Element.Empty;
        smalllaElements[0][1] = Element.White;
        smalllaElements[1][0] = Element.White;
        smalllaElements[1][1] = Element.Green;
        smallla.addRotation(2, 2, Rotation.East, smalllaElements);

        smalllaElements[0][0] = Element.White;
        smalllaElements[0][1] = Element.Green;
        smalllaElements[1][0] = Element.Empty;
        smalllaElements[1][1] = Element.White;
        smallla.addRotation(2, 2, Rotation.South, smalllaElements);

        smalllaElements[0][0] = Element.Green;
        smalllaElements[0][1] = Element.White;
        smalllaElements[1][0] = Element.White;
        smalllaElements[1][1] = Element.Empty;
        smallla.addRotation(2, 2, Rotation.West, smalllaElements);


        Shape smalllb = new Shape("Small L B");
        ShapeSet.shapes.put(smalllb.getId(), smalllb);

        Element[][] smalllbElements = new Element[2][2];

        smalllbElements[0][0] = Element.Green;
        smalllbElements[0][1] = Element.Empty;
        smalllbElements[1][0] = Element.White;
        smalllbElements[1][1] = Element.Green;
        smalllb.addRotation(2, 2, Rotation.North, smalllbElements);

        // Clockwise rotation of 90 degrees
        // [0][0] -> [1][0]
        // [0][1] -> [0][0]
        // [1][0] -> [1][1]
        // [1][1] -> [0][1]
        //
        smalllbElements[0][0] = Element.Empty;
        smalllbElements[0][1] = Element.Green;
        smalllbElements[1][0] = Element.Green;
        smalllbElements[1][1] = Element.White;
        smalllb.addRotation(2, 2, Rotation.East, smalllbElements);

        smalllbElements[0][0] = Element.Green;
        smalllbElements[0][1] = Element.White;
        smalllbElements[1][0] = Element.Empty;
        smalllbElements[1][1] = Element.Green;
        smalllb.addRotation(2, 2, Rotation.South, smalllbElements);

        smalllbElements[0][0] = Element.White;
        smalllbElements[0][1] = Element.Green;
        smalllbElements[1][0] = Element.Green;
        smalllbElements[1][1] = Element.Empty;
        smalllb.addRotation(2, 2, Rotation.West, smalllbElements);


    }

    public static Shape getShape(UUID id) {
        if (shapes.containsKey(id)) {
            return shapes.get(id);
        }
        else
            return null;
    }

    public static Set<UUID> getAllShapes() {
        return shapes.keySet();
    }
}

