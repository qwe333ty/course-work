package com.ak.work.client.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SolutionProducer extends Producer {

    @Value("${api.urn.solution}")
    private String solutionPath;
}
