package com.ak.work.client.producer;

import com.ak.work.client.entity.Solution;
import com.ak.work.client.entity.SolutionHistory;
import com.ak.work.client.util.URIUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SolutionProducer extends Producer {

    @Value("${api.urn.solution}")
    private String solutionPath;

    @Value("${api.urn.evaluation}")
    private String evaluation;

    public List<Solution> findSolutions(Integer expertId, Integer problemId, Boolean all, Boolean isRow, Integer row, Boolean inverse) {
        List<URIUtils.QueryParam> params = new ArrayList<>();
        if (expertId != null) {
            params.add(new URIUtils.QueryParam("expertId", new Object[]{expertId}));
        }
        if (problemId != null) {
            params.add(new URIUtils.QueryParam("problemId", new Object[]{problemId}));
        }
        if (all != null) {
            params.add(new URIUtils.QueryParam("all", new Object[]{all}));
        }
        if (isRow != null) {
            params.add(new URIUtils.QueryParam("isRow", new Object[]{isRow}));
        }
        if (row != null) {
            params.add(new URIUtils.QueryParam("row", new Object[]{row}));
        }
        if (inverse != null) {
            params.add(new URIUtils.QueryParam("inverse", new Object[]{inverse}));
        }

        URI uri = getUriWithPathsAndParams(params, solutionPath);

        return getObjectList(uri, HttpMethod.GET, null, Solution.class);
    }

    public List<Solution> saveAll(List<Solution> solutions, Integer problemId) {
        URI uri = getUriWithPathsAndParams(
                Collections.singletonList(new URIUtils.QueryParam("problemId", new Object[]{problemId})),
                solutionPath, "/save");
        return getObjectList(uri, HttpMethod.POST, solutions, Solution.class);
    }

    public void saveSolutionHistory(SolutionHistory solutionHistory) {
        URI uri = getClearUri(evaluation);
        getOneObject(uri, HttpMethod.POST, solutionHistory, SolutionHistory.class);
    }

    public void updateSolutionHistory(SolutionHistory solutionHistory, Integer oldRow, Integer oldColumn) {
        List<URIUtils.QueryParam> params = new ArrayList<>();
        if (oldColumn != null) {
            params.add(new URIUtils.QueryParam("oldColumn", new Object[]{oldColumn}));
        }
        if (oldRow != null) {
            params.add(new URIUtils.QueryParam("oldRow", new Object[]{oldRow}));
        }

        URI uri = getUriWithPathsAndParams(params, evaluation);
        getOneObject(uri, HttpMethod.PUT, solutionHistory, SolutionHistory.class);
    }
}
