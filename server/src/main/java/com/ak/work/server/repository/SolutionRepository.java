package com.ak.work.server.repository;

import com.ak.work.server.entity.Solution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends CrudRepository<Solution, Integer> {
}
