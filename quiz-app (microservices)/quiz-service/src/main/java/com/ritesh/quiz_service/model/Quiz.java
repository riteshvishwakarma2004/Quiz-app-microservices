package com.ritesh.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @ElementCollection     //use this when there are multiple elements but this elements are not objects(which has multiple attributes)
    private List<Integer> questionIDs;

}
