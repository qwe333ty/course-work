package com.ak.work.server.service.impl;

import com.ak.work.server.entity.Solution;
import com.ak.work.server.repository.SolutionRepository;
import com.ak.work.server.service.SolutionService;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private SolutionRepository repository;

    @Override
    public Solution save(Solution solution) {
        return repository.save(solution);
    }

    @Transactional
    @Override
    public List<Solution> saveAll(List<Solution> solutions, Integer problemId) {
        Spliterator<Solution> spliterator = repository.saveAll(solutions).spliterator();
        return StreamSupport.stream(spliterator, false).collect(Collectors.toList());
    }

    @Override
    public List<Solution> findSolutions(Integer expertId, Integer problemId, Boolean all, Boolean isRow, Integer row, Boolean inverse) {
        @Cleanup EntityManager em = entityManagerFactory.createEntityManager();

        if (all != null && !all) {
            if (isRow != null) {

                if (isRow) {

                    if (row != null) {

                        if (inverse) {
                            return findRatedSolutionColumns(em, expertId, problemId, row);
                        } else {
                            return findNotRatedSolutionColumns(em, expertId, problemId, row);
                        }

                    } else {
                        return Collections.emptyList();
                    }

                } else {

                    if (inverse) {
                        return findRatedSolutionRows(em, expertId, problemId);
                    } else {
                        return findNotRatedSolutionRows(em, expertId, problemId);
                    }

                }

            } else {
                return Collections.emptyList();
            }
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Solution> query = builder.createQuery(Solution.class);
        Root<Solution> root = query.from(Solution.class);

        List<Predicate> predicates = new ArrayList<>();

        if (problemId != null) {
            Predicate predicate = builder.equal(root.get("problem").get("id"), problemId);
            predicates.add(predicate);
        }

        if (!predicates.isEmpty()) {
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }

        return em.createQuery(query).getResultList();
    }

    @SuppressWarnings("unchecked")
    private List<Solution> findNotRatedSolutionRows(EntityManager em, Integer expertId, Integer problemId) {
        return em.createNativeQuery(
                "select *\n" +
                        "from solution \n" +
                        "where (solution_order not in (" +
                        "select row_ from solution_history " +
                        "where problem_id = :problemId and user_id = :userId and value_ = 1)) " +
                        "and problem_id = :problemId", Solution.class)
                .setParameter("problemId", problemId)
                .setParameter("userId", expertId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    private List<Solution> findNotRatedSolutionColumns(EntityManager em, Integer expertId, Integer problemId, Integer column) {
        return em.createNativeQuery(
                "select *\n" +
                        "from solution \n" +
                        "where (solution_order not in (" +
                        "select column_ from solution_history " +
                        "where problem_id = :problemId and user_id = :userId and row_ = :rw)) " +
                        "and problem_id = :problemId and solution_order != :rw", Solution.class)
                .setParameter("problemId", problemId)
                .setParameter("userId", expertId)
                .setParameter("rw", column)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    private List<Solution> findRatedSolutionRows(EntityManager em, Integer expertId, Integer problemId) {
        return em.createNativeQuery(
                "select *\n" +
                        "from solution \n" +
                        "where (solution_order in (" +
                        "select row_ from solution_history " +
                        "where problem_id = :problemId and user_id = :userId and value_ = 1)) " +
                        "and problem_id = :problemId", Solution.class)
                .setParameter("problemId", problemId)
                .setParameter("userId", expertId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    private List<Solution> findRatedSolutionColumns(EntityManager em, Integer expertId, Integer problemId, Integer column) {
        return em.createNativeQuery(
                "select *\n" +
                        "from solution \n" +
                        "where (solution_order in (" +
                        "select column_ from solution_history " +
                        "where problem_id = :problemId and user_id = :userId and row_ = :rw)) " +
                        "and problem_id = :problemId and solution_order != :rw", Solution.class)
                .setParameter("problemId", problemId)
                .setParameter("userId", expertId)
                .setParameter("rw", column)
                .getResultList();
    }
}
