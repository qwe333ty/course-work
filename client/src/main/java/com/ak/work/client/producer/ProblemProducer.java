package com.ak.work.client.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProblemProducer extends Producer {

    @Value("${api.urn.problem}")
    private String problemPath;


}
