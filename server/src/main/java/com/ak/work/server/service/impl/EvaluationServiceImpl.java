package com.ak.work.server.service.impl;

import com.ak.work.server.entity.Evaluation;
import com.ak.work.server.entity.SolutionHistory;
import com.ak.work.server.repository.EvaluationRepository;
import com.ak.work.server.repository.ProblemRepository;
import com.ak.work.server.repository.SolutionRepository;
import com.ak.work.server.repository.UserRepository;
import com.ak.work.server.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private static final String EVALUATION_WITH_USER =
            "select row_, column_, count(1) as evaluation from solution_history" +
                    " where problem_id = ? and user_id = ? and value_ = 1 group by (row_, column_)";

    private static final String EVALUATION_WITHOUT_USER =
            "select row_, column_, count(1) as evaluation from solution_history" +
                    " where problem_id = ?  and value_ = 1 group by (row_, column_)";

    private static final ResultSetExtractor<List<Evaluation>> EVALUATION_EXTRACTOR =
            resultSet -> {
                List<Evaluation> evaluations = new ArrayList<>();

                while (resultSet.next()) {
                    Evaluation evaluation = new Evaluation();

                    evaluation.setRow(resultSet.getInt("row_"));
                    evaluation.setColumn(resultSet.getInt("column_"));
                    evaluation.setScore(resultSet.getInt("evaluation"));

                    evaluations.add(evaluation);
                }
                return evaluations;
            };

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SolutionHistory save(SolutionHistory solutionHistory) {
        return evaluationRepository.save(solutionHistory);
    }

    @Override
    public void update(SolutionHistory solutionHistory, int oldRow, int oldColumn) {
        evaluationRepository.updateSolutionHistory(
                solutionHistory.getRow(),
                solutionHistory.getColumn(),
                solutionHistory.getValue(),
                solutionHistory.getProblem(),
                solutionHistory.getUser(),
                oldRow,
                oldColumn);
    }

    @Override
    public int[][] getSolutionMatrix(Integer problemId, Integer expertId) {
        if (!problemRepository.existsById(problemId) ||
                (expertId != null && !userRepository.existsById(expertId))) {
            return new int[0][0];
        }

        List<Evaluation> evaluations = findEvaluations(problemId, expertId);
        int N = solutionRepository.getMaxOrderByProblemId(problemId);

        //fill matrix by zeros
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(matrix[i], 0);
        }

        for (Evaluation evaluation : evaluations) {
            int i = evaluation.getRow();
            int j = evaluation.getColumn();

            matrix[i][j] = evaluation.getScore();
        }

        return matrix;
    }

    private List<Evaluation> findEvaluations(Integer problemId, Integer expertId) {
        boolean isNull = expertId == null;
        Object[] objects = new Object[isNull ? 1 : 2];

        if (isNull) {
            objects[0] = problemId;
        } else {
            objects[0] = problemId;
            objects[1] = expertId;
        }

        return jdbcTemplate.query(
                expertId != null ? EVALUATION_WITH_USER : EVALUATION_WITHOUT_USER,
                //inject values instead of '?' in query
                objects,
                //extract result
                EVALUATION_EXTRACTOR);
    }
}
