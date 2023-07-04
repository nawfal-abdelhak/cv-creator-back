package com.pfe.cvcreator.utils;

import com.pfe.cvcreator.dto.UserDTO;
import com.pfe.cvcreator.entities.Role;
import com.pfe.cvcreator.entities.User;
import org.modelmapper.ModelMapper;

public class Mapper {


     private  static ModelMapper modelmapper = new ModelMapper();
    public static UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = modelmapper.map(user,UserDTO.class);
        for(Role role : user.getRoles()){
            userDTO.getRole().add(role.getRole());
        }
        return userDTO;
    }
}
