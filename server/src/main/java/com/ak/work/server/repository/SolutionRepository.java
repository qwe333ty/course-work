package com.ak.work.server.repository;

import com.ak.work.server.entity.Solution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends CrudRepository<Solution, Integer> {

    @Query(value = "select (max(solution_order) + 1) as max from solution " +
            "where problem_id = :problemId", nativeQuery = true)
    int getMaxOrderByProblemId(@Param("problemId") int problemId);
}
