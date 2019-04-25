package com.ak.work.client.producer;

import com.ak.work.client.entity.Problem;
import com.ak.work.client.util.URIUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Component
public class ProblemProducer extends Producer {

    @Value("${api.urn.problem}")
    private String problemPath;

    public List<Problem> findProblems(Integer managerId) {
        URIUtils.QueryParam queryParam = new URIUtils.QueryParam(
                "managerId",
                new Object[]{managerId});
        URI uri = getUriWithPathsAndParams(Collections.singletonList(queryParam), problemPath);

        return getObjectList(uri, HttpMethod.GET, null, Problem.class);
    }

    public Problem save(Problem problem) {
        URI uri = getUriWithPaths(null, problemPath, "/save");
        return getOneObject(uri, HttpMethod.POST, problem, Problem.class);
    }

    public void deleteProblem(Integer problemId) {
        URI uri = getUriWithPaths(new Object[]{problemId}, problemPath, "{id}");
        getOneObject(uri, HttpMethod.DELETE, null, Problem.class);
    }

    public Boolean checkIfExists(Integer problemId) {
        URI uri = getUriWithPaths(new Object[]{problemId}, problemPath, "{id}", "exists");
        return getOneObject(uri, HttpMethod.GET, null, Boolean.class);
    }

    public int[][] getSolutionMatrixByProblemId(Integer problemId) {
        return getSolutionMatrixByProblemId(problemId);
    }
}
