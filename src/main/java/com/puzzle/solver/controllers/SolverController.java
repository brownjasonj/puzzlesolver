package com.puzzle.solver.controllers;

import com.puzzle.solver.SolutionState;
import com.puzzle.solver.Solver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolverController {
    @RequestMapping(method = { RequestMethod.GET }, value = { "/help" }, produces = "application/json")
    public String help() {

        return "Help";
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/solve" }, produces = "application/json")
    public SolutionState solve() {

        return Solver.solve();
    }
}
