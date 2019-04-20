package com.ak.work.server.service;

import com.ak.work.server.entity.Solution;

import java.util.List;

public interface SolutionService {
    Solution save(Solution solution);

    List<Solution> findSolutions(Integer expertId);
}
