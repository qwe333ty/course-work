package com.ak.work.server.service.impl;

import com.ak.work.server.entity.Problem;
import com.ak.work.server.entity.Solution;
import com.ak.work.server.repository.ProblemRepository;
import com.ak.work.server.service.ProblemService;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public Problem save(Problem problem) {
        return problemRepository.save(problem);
    }

    @Override
    public List<Problem> findProblems(Integer managerId) {
        @Cleanup EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Problem> query = builder.createQuery(Problem.class);
        Root<Problem> root = query.from(Problem.class);

        if (managerId != null) {
            Predicate predicate = builder.equal(root.get("manager").get("id"), managerId);
            query.where(predicate);
        }

        return em.createQuery(query).getResultList();
    }

    @Override
    public void delete(Integer problemId) {
        deleteSolutionsByProblemId(problemId);
        deleteRelatedSolutionData(problemId);
        problemRepository.deleteById(problemId);
    }

    @Override
    public Boolean exists(Integer problemId) {
        return problemRepository.existsById(problemId);
    }

    private void deleteSolutionsByProblemId(Integer problemId) {
        @Cleanup EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaDelete<Solution> delete = builder.createCriteriaDelete(Solution.class);
        Root<Solution> root = delete.from(Solution.class);

        Predicate predicate = builder.equal(root.get("problem").get("id"), problemId);
        em.createQuery(delete.where(predicate)).executeUpdate();
        em.getTransaction().commit();
    }

    private void deleteRelatedSolutionData(Integer problemId) {
        String table_name = jdbcTemplate.queryForObject("select ps_table_name from problem_mapping " +
                "where problem_id = ?", new Object[]{problemId}, String.class);
        jdbcTemplate.execute(String.format("drop table %s", table_name));
        jdbcTemplate.update("delete from problem_mapping where problem_id = ?", problemId);

    }
}
