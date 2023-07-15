package com.pfe.cvcreator.dto;

import com.pfe.cvcreator.utils.Env;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class UserDTO {
    private Long id;
    private String username;
    private List<String> role = new ArrayList<String>();

    private String last_name;
    private String mail;
    private String image;
    private String address;
    private String phone_number;
    private String birth_place;
    private Date birth_date;
    private boolean subbed;

    public String getImage() {
        return Env.getUrlImages()+image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
