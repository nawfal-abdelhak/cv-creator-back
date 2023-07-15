package com.pfe.cvcreator.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 5, message = "*Your username must have at least 5 characters")
    @NotEmpty(message = "*Please provide an user name")
    @Column(unique = true)
    private String username;

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    @Email
    @Column(unique = true)
    private String mail;

    private String image;

    private boolean subbed;

    private String last_name;
    private String profile_title;
    private String address;
    private String phone_number;
    private String city;
    private Date birth_date;
    private String birth_place;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Cv> cvs;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<Role>();




}
