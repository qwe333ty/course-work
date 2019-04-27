package com.ak.work.server.service;

import com.ak.work.server.entity.SolutionHistory;


public interface EvaluationService {

    SolutionHistory save(SolutionHistory solutionHistory);

    void update(SolutionHistory solutionHistory, int oldRow, int oldColumn);

    int[][] getSolutionMatrix(Integer problemId, Integer expertId);
}
