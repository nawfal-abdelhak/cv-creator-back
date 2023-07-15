package com.pfe.cvcreator.dto;

import com.pfe.cvcreator.entities.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CvDTO {
    private Long id;
    private UserDTO pers_info;
    private String title;
    private List<Formation> formations = new ArrayList<>();
    private List<Experience> experiences = new ArrayList<>();
    private List<Comp> comps = new ArrayList<>();
    private List<Langue> langs = new ArrayList<>();
    private List<Interest> interests = new ArrayList<>();
}
