package com.pfe.cvcreator.services;

import com.pfe.cvcreator.dto.ChangePasswordDTO;
import com.pfe.cvcreator.dto.CvDTO;
import com.pfe.cvcreator.dto.RegisterDTO;
import com.pfe.cvcreator.entities.Cv;
import com.pfe.cvcreator.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    User addUser(RegisterDTO user);
    Boolean changePassword(ChangePasswordDTO changePassword);



    Boolean saveCv(CvDTO cvDTO1, MultipartFile image,Long id) throws IOException;

    List<Cv> getCvs(long id);

    User getUser(Long id);

    User subsribe(Long id);

    List<User> getUsers();

    List<Object[]> getUsersWithCVCount();

    Boolean edtiCv(CvDTO cvDTO1, MultipartFile image, Long userId, Long cvId) throws IOException;

    boolean deleteUser(Long id);
}
