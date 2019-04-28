package com.ak.work.server.service;

import com.ak.work.server.entity.Solution;

import java.util.List;

public interface SolutionService {
    Solution save(Solution solution);

    List<Solution> saveAll(List<Solution> solutions, Integer problemId);

    List<Solution> findSolutions(Integer expertId, Integer problemId, Boolean all, Boolean isRow, Integer row, Boolean inverse);
}
