package com.puzzle.solver.controllers;

import com.puzzle.solver.*;
import com.puzzle.solver.model.Problem;
import com.puzzle.solver.modelconverters.ModelConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class SolverController {
    @RequestMapping(method = { RequestMethod.GET }, value = { "/help" }, produces = "application/json")
    public String help() {

        return "Help";
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/solve" }, produces = "application/json")
    public List<SolutionState> solve() {
        ShapeSet shapeSet = new ShapeSet();

        Shape smallla = new Shape("Small L A");
        shapeSet.addShape(smallla);

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
        shapeSet.addShape(smalllb);

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

        Solver solver = new Solver(3, 2, shapeSet);
        return solver.solve();
    }

    @RequestMapping(method = { RequestMethod.POST }, value = { "/solveproblem" }, produces = "application/json")
    public List<SolutionState> solver(@RequestBody Problem problem) {
        ShapeSet shapeSet = ModelConverter.convertToShapeSet(problem.shapes);
        Solver solver = new Solver(problem.dimensions.width, problem.dimensions.height, shapeSet);
        return solver.solve();
    }

}
