package com.pfe.cvcreator.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String lastName;
    private String email;
    private List<String> role = new ArrayList<String>();
}
