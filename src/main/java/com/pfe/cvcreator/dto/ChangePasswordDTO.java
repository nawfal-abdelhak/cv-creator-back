package com.pfe.cvcreator.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    Long id;
    String oldPassword;
    String newPassword;
}
