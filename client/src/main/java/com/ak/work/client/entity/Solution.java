package com.ak.work.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solution {

    private int id;

    private User expert;

    private Problem problem;

    private double rating;

    private String header;
}
