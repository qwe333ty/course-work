package com.ak.work.server.repository;

import com.ak.work.server.entity.Problem;
import org.springframework.data.repository.CrudRepository;

public interface ProblemRepository extends CrudRepository<Problem, Integer> {
}
