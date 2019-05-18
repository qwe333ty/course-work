package com.ak.work.server.repository;

import com.ak.work.server.entity.VisitHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitHistoryRepository extends CrudRepository<VisitHistory, Integer> {
    List<VisitHistory> findAllByUser_Id(Integer userId);
}
