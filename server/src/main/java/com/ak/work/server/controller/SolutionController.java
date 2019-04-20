package com.ak.work.server.controller;

import com.ak.work.server.entity.Solution;
import com.ak.work.server.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.urn}/solution")
public class SolutionController {

    @Autowired
    private SolutionService service;

    @GetMapping
    public ResponseEntity<List<Solution>> getSolutions(@RequestParam(name = "expertId", required = false) Integer expertId) {
        List<Solution> solutions = service.findSolutions(expertId);
        return new ResponseEntity<>(solutions, HttpStatus.OK);
    }
}
