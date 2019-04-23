package com.ak.work.server.service.impl;

import com.ak.work.server.entity.Solution;
import com.ak.work.server.repository.SolutionRepository;
import com.ak.work.server.service.SolutionService;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    @Override
    public List<Solution> saveAll(List<Solution> solutions) {
        Spliterator<Solution> spliterator = repository.saveAll(solutions).spliterator();
        return StreamSupport.stream(spliterator, false).collect(Collectors.toList());
    }

    @Override
    public List<Solution> findSolutions(Integer expertId, Integer problemId) {
        @Cleanup EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Solution> query = builder.createQuery(Solution.class);
        Root<Solution> root = query.from(Solution.class);

        List<Predicate> predicates = new ArrayList<>();
        if (expertId != null) {
            Predicate predicate = builder.equal(root.get("expert").get("id"), expertId);
            predicates.add(predicate);
        }

        if (problemId != null) {
            Predicate predicate = builder.equal(root.get("problem").get("id"), problemId);
            predicates.add(predicate);
        }

        if (!predicates.isEmpty()) {
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }

        return em.createQuery(query).getResultList();
    }
}
