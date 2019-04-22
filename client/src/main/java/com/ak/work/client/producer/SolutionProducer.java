package com.ak.work.client.producer;

import com.ak.work.client.entity.Solution;
import com.ak.work.client.util.URIUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class SolutionProducer extends Producer {

    @Value("${api.urn.solution}")
    private String solutionPath;

    public List<Solution> findSolutions(Integer expertId, Integer problemId) {
        List<URIUtils.QueryParam> params = new ArrayList<>();
        if (expertId != null) {
            params.add(new URIUtils.QueryParam("expertId", new Object[]{expertId}));
        }
        if (problemId != null) {
            params.add(new URIUtils.QueryParam("problemId", new Object[]{problemId}));
        }

        URI uri = getUriWithPathsAndParams(params, solutionPath);

        return getObjectList(uri, HttpMethod.GET, null, Solution.class);
    }

    public List<Solution> saveAll(List<Solution> solutions) {
        URI uri = getUriWithPaths(null, solutionPath, "/save");
        return getObjectList(uri, HttpMethod.POST, solutions, Solution.class);
    }
}
