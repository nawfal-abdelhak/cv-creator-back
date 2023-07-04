package com.pfe.cvcreator.entities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Data
public class Comp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    private Cv cv;
}
