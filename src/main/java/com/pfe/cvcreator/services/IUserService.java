package com.pfe.cvcreator.services;

import com.pfe.cvcreator.dto.ChangePasswordDTO;
import com.pfe.cvcreator.dto.RegisterDTO;
import com.pfe.cvcreator.entities.User;

public interface IUserService {
    User addUser(RegisterDTO user);
    Boolean changePassword(ChangePasswordDTO changePassword);
}
