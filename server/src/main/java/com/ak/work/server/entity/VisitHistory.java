package com.ak.work.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "visit_history")
@AllArgsConstructor
@NoArgsConstructor
public class VisitHistory {

    @Id
    @GeneratedValue(generator = "visit_history_id_sequence")
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "date_time")
    private Timestamp visitTime;
}
