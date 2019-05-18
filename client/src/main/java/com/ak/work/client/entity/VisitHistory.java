package com.ak.work.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitHistory {

    private Integer id;

    private Timestamp visitTime;
}
