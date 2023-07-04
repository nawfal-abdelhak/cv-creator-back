package com.pfe.cvcreator.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    private Cv cv;
}
