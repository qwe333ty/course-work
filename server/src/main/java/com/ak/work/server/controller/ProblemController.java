package com.ak.work.server.controller;

import com.ak.work.server.entity.Problem;
import com.ak.work.server.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.urn}/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public ResponseEntity<List<Problem>> getProblems(
            @RequestParam(value = "managerId", required = false) Integer managerId,
            @RequestParam(value = "resolved", required = false) Boolean resolved) {
        List<Problem> problems = problemService.findProblems(managerId, resolved);
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Problem> saveProblem(@RequestBody Problem problem) {
        return ResponseEntity.ok(problemService.save(problem));
    }

    @DeleteMapping("/{id}")
    public void deleteProblem(@PathVariable(name = "id") Integer id) {
        problemService.delete(id);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable(name = "id") Integer problemId) {
        return ResponseEntity.ok(problemService.exists(problemId));
    }
}
