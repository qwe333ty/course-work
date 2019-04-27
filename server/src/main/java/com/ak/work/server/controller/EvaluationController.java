package com.ak.work.server.controller;

import com.ak.work.server.entity.SolutionHistory;
import com.ak.work.server.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.urn}/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<SolutionHistory> saveHistory(
            @RequestBody SolutionHistory solutionHistory) {
        return ResponseEntity.ok(evaluationService.save(solutionHistory));
    }

    @PutMapping
    public ResponseEntity<SolutionHistory> updateHistory(
            @RequestBody SolutionHistory solutionHistory,
            @RequestParam(name = "oldRow") Integer oldRow,
            @RequestParam(name = "oldColumn") Integer oldColumn) {
        evaluationService.update(solutionHistory, oldRow, oldColumn);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{problemId}/solutionMatrix")
    public ResponseEntity<int[][]> getSolutionMatrixByProblem(
            @PathVariable(name = "problemId") Integer problemId,
            @RequestParam(name = "userId", required = false) Integer userId) {
        return ResponseEntity.ok(evaluationService.getSolutionMatrix(problemId, userId));
    }
}
