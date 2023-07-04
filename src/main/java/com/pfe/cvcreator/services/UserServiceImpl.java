package com.pfe.cvcreator.services;

import com.pfe.cvcreator.config.EmailCfg;
import com.pfe.cvcreator.dto.ChangePasswordDTO;
import com.pfe.cvcreator.dto.RegisterDTO;

import com.pfe.cvcreator.entities.*;

import com.pfe.cvcreator.repositories.IRoleRepository;
import com.pfe.cvcreator.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.nio.file.Paths.get;


@Transactional
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public User addUser(RegisterDTO registerDTO) {

        User user = modelMapper.map(registerDTO,User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().clear();
        for (String role : registerDTO.getRole()){
            Role role1 = roleRepository.findByRole(role);
            user.getRoles().add(role1);
        }
        User u =  userRepository.save(user);
       /* SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(this.emailCfg.getUsername());
        mailMessage.setTo(u.getEmail());
        mailMessage.setSubject("welcome");
        mailMessage.setText("your UserName is : "+u.getUsername()+" you password is : "+registerDTO.getPassword());
        javaMailSender.send(mailMessage);*/
        return u;
    }





    @Override
    public Boolean changePassword(ChangePasswordDTO changePassword) {
        Optional<User> user = userRepository.findById(changePassword.getId());
        Boolean changed = false;
        if (user.isPresent()) {

            if ( passwordEncoder.matches(changePassword.getOldPassword(),user.get().getPassword())){
                user.get().setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
                userRepository.save(user.get());
                changed=true;
            }
        }
        return changed;
    }


}
