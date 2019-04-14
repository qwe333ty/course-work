package com.ak.work.server.service.impl;

import com.ak.work.server.entity.Problem;
import com.ak.work.server.repository.ProblemRepository;
import com.ak.work.server.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemRepository repository;

    @Override
    public Problem save(Problem problem) {
        return repository.save(problem);
    }
}
