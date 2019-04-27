package com.ak.work.server.controller;

import com.ak.work.server.entity.Solution;
import com.ak.work.server.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.urn}/solution")
public class SolutionController {

    @Autowired
    private SolutionService service;

    @GetMapping
    public ResponseEntity<List<Solution>> getSolutions(
            @RequestParam(name = "expertId", required = false) Integer expertId,
            @RequestParam(name = "problemId", required = false) Integer problemId,
            @RequestParam(name = "all", required = false) Boolean all,
            @RequestParam(name = "isRow", required = false) Boolean isRow,
            @RequestParam(name = "row", required = false) Integer row,
            @RequestParam(name = "inverse", required = false, defaultValue = "false") Boolean inverse) {
        List<Solution> solutions = service.findSolutions(expertId, problemId, all, isRow, row, inverse);
        return new ResponseEntity<>(solutions, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<List<Solution>> saveAllSolutions(
            @RequestBody List<Solution> solutions,
            @RequestParam(name = "problemId") Integer problemId) {
        return ResponseEntity.ok(service.saveAll(solutions, problemId));
    }
}
