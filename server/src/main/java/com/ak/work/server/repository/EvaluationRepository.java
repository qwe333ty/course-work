package com.ak.work.server.repository;

import com.ak.work.server.entity.SolutionHistory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends CrudRepository<SolutionHistory, Integer> {

    @Modifying
    @Query(value = "update solution_history set row_ = ?, column_ = ?, value_ = ? " +
            "where problem_id = ? and user_id = ? and row_ = ? and column_ = ?", nativeQuery = true)
    void updateSolutionHistory(int newRow, int newColumn, int newValue,
                               int problemId, int userId, int oldRow, int oldColumn);

}
