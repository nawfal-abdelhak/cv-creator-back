package com.pfe.cvcreator.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cv")
    private List<Formation> formations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cv")
    private List<Experience> experiences;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cv")
    private List<Comp> comps;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cv")
    private List<Langue> langs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cv")
    private List<Interest> CIs ;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
