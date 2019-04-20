package com.ak.work.server.controller;

import com.ak.work.server.entity.Problem;
import com.ak.work.server.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.urn}/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public ResponseEntity<List<Problem>> getProblems(@RequestParam(value = "managerId", required = false) Integer managerId) {
        List<Problem> problems = problemService.findProblems(managerId);
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }
}
