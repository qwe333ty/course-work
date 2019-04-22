package com.ak.work.server.service;

import com.ak.work.server.entity.Problem;

import java.util.List;

public interface ProblemService {
    Problem save(Problem problem);

    List<Problem> findProblems(Integer managerId);

    void delete(Integer problemId);
}
