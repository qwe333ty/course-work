package com.ak.work.server.service.impl;

import com.ak.work.server.entity.Problem;
import com.ak.work.server.repository.ProblemRepository;
import com.ak.work.server.service.ProblemService;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ProblemRepository repository;

    @Override
    public Problem save(Problem problem) {
        return repository.save(problem);
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
}
