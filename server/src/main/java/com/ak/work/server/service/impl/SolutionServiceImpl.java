package com.ak.work.server.service.impl;

import com.ak.work.server.entity.Solution;
import com.ak.work.server.repository.SolutionRepository;
import com.ak.work.server.service.SolutionService;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private static final String CREATE_TABLE_FORMAT =
            "create table problem_solutions_%d (\n" +
                    "    id integer not null,\n" +
                    "    value_ smallint not null\n" +
                    ")";

    private static final String INSERT_INTO_PS_TABLE_FORMAT =
            "insert into problem_solutions_%d (id, value_) values (?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        solutions = StreamSupport.stream(spliterator, false).collect(Collectors.toList());

        Integer table_id = jdbcTemplate.queryForObject(
                "select nextval('problem_solutions_table_number_sequence')", Integer.class);
        jdbcTemplate.execute(String.format(CREATE_TABLE_FORMAT, table_id));

        int size = solutions.size();
        List<Object[]> values = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    values.add(new Object[]{((i * size) + j + 1), -1});
                } else {
                    values.add(new Object[]{((i * size) + j + 1), 0});
                }
            }
        }

        jdbcTemplate.batchUpdate(String.format(INSERT_INTO_PS_TABLE_FORMAT, table_id), values);
        jdbcTemplate.update(
                "insert into problem_mapping (problem_id, ps_table_name) values (?, ?)",
                problemId, String.format("problem_solutions_%d", table_id));

        return solutions;
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
