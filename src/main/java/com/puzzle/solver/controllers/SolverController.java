package com.puzzle.solver.controllers;

import com.puzzle.solver.*;
import com.puzzle.solver.model.Problem;
import com.puzzle.solver.modelconverters.ModelConverter;
import com.puzzle.solver.solvers.PiecePickerSolver;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SolverController {
    @RequestMapping(method = { RequestMethod.GET }, value = { "/help" }, produces = "application/json")
    public String help() {
        return "Help";
    }


    @RequestMapping(method = { RequestMethod.POST }, value = { "/solveproblem" }, produces = "application/json")
    public List<SolutionState> solver2(@RequestBody Problem problem) {
         ShapeSet shapeSet = ModelConverter.convertToShapeSet(problem.shapes);
        PiecePickerSolver solver = new PiecePickerSolver(problem.dimensions.width, problem.dimensions.height, shapeSet);
        return solver.solve();
    }
}
