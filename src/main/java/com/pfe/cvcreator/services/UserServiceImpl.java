package com.pfe.cvcreator.services;

import com.pfe.cvcreator.config.EmailCfg;
import com.pfe.cvcreator.dto.ChangePasswordDTO;
import com.pfe.cvcreator.dto.CvDTO;
import com.pfe.cvcreator.dto.RegisterDTO;

import com.pfe.cvcreator.entities.*;

import com.pfe.cvcreator.repositories.*;
import com.pfe.cvcreator.utils.FileHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.nio.file.Paths.get;


@Transactional
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICompRepository compRepository;

    @Autowired
    private IExeprienceRepository exeprienceRepository;

    @Autowired
    private IFromationRepository fromationRepository;

    @Autowired
    private IIneterestRepository ineterestRepository;

    @Autowired
    private ILangueRepository langueRepository;



    @Autowired
    private ICvRepository cvRepository;

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



    @Override
    public Boolean saveCv(CvDTO cvDTO1, MultipartFile image,Long id) throws IOException {
        Optional<User> user = userRepository.findById(id);
        user.get().setAddress(cvDTO1.getPers_info().getAddress());
        user.get().setMail(cvDTO1.getPers_info().getMail());
        user.get().setBirth_date(cvDTO1.getPers_info().getBirth_date());
        user.get().setBirth_place(cvDTO1.getPers_info().getBirth_place());
        user.get().setPhone_number(cvDTO1.getPers_info().getPhone_number());
        user.get().setImage(FileHandler.uploadFile(image));
        userRepository.save(user.get());

        Cv  cv = new Cv();
        cv.setUser(user.get());
        cv.setTitle(cvDTO1.getTitle());
        Cv cv1 = cvRepository.save(cv);


        for (Comp comp1 :  cvDTO1.getComps()) {
            Comp comp = new Comp();
            comp.setName(comp1.getName());
            comp.setValue(comp1.getValue());
            comp.setCv(cv1);
            compRepository.save(comp);
        }



        for (Experience exp :  cvDTO1.getExperiences()) {
            Experience experience = new Experience();
            experience.setName(exp.getName());
            experience.setCity(exp.getCity());
            experience.setDescription(exp.getDescription());
            experience.setHire(exp.getHire());
            experience.setEnd_date(exp.getEnd_date());
            experience.setStart_date(exp.getStart_date());
            experience.setCv(cv1);
            exeprienceRepository.save(experience);
        }

        for (Formation form :  cvDTO1.getFormations()) {
            Formation formation = new Formation();
            formation.setName(form.getName());
            formation.setCity(form.getCity());
            formation.setDescription(form.getDescription());
            formation.setSchool(form.getSchool());
            formation.setEnd_date(form.getEnd_date());
            formation.setStart_date(form.getStart_date());
            formation.setCv(cv1);
            fromationRepository.save(formation);
        }



        for (Interest interest :  cvDTO1.getInterests()) {
            Interest inte = new Interest();
            inte.setName(interest.getName());

            inte.setCv(cv1);
            ineterestRepository.save(inte);
        }


        for (Langue langue :  cvDTO1.getLangs()) {
            Langue lang = new Langue();
            lang.setName(langue.getName());
            lang.setValue(langue.getValue());
            lang.setCv(cv1);
            langueRepository.save(lang);
        }


        return true;
    }

    @Override
    public List<Cv> getCvs(long id)  {
        List<Cv> userCvs = userRepository.findCvsByUserId(id);
        return  userCvs;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User subsribe(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.get().setSubbed(true);
        return userRepository.save(user.get());
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Object[]> getUsersWithCVCount() {
        return  userRepository.getUsersWithCVCount();
    }

    @Override
    public Boolean edtiCv(CvDTO cvDTO1, MultipartFile image, Long userId, Long cvId) throws IOException {
        Optional<User> user = userRepository.findById(userId);
        user.get().setAddress(cvDTO1.getPers_info().getAddress());
        user.get().setMail(cvDTO1.getPers_info().getMail());
        user.get().setBirth_date(cvDTO1.getPers_info().getBirth_date());
        user.get().setBirth_place(cvDTO1.getPers_info().getBirth_place());
        user.get().setPhone_number(cvDTO1.getPers_info().getPhone_number());
        user.get().setImage(FileHandler.uploadFile(image));
        userRepository.save(user.get());

        Cv  cv = cvRepository.findById(cvId).get();
        cv.setUser(user.get());
        cv.setTitle(cvDTO1.getTitle());
        Cv cv1 = cvRepository.save(cv);


        for (Comp comp1 :  cvDTO1.getComps()) {
            Comp comp = new Comp();
            comp.setName(comp1.getName());
            comp.setValue(comp1.getValue());
            comp.setCv(cv1);
            compRepository.save(comp);
        }



        for (Experience exp :  cvDTO1.getExperiences()) {
            Experience experience = new Experience();
            experience.setName(exp.getName());
            experience.setCity(exp.getCity());
            experience.setDescription(exp.getDescription());
            experience.setHire(exp.getHire());
            experience.setEnd_date(exp.getEnd_date());
            experience.setStart_date(exp.getStart_date());
            experience.setCv(cv1);
            exeprienceRepository.save(experience);
        }

        for (Formation form :  cvDTO1.getFormations()) {
            Formation formation = new Formation();
            formation.setName(form.getName());
            formation.setCity(form.getCity());
            formation.setDescription(form.getDescription());
            formation.setSchool(form.getSchool());
            formation.setEnd_date(form.getEnd_date());
            formation.setStart_date(form.getStart_date());
            formation.setCv(cv1);
            fromationRepository.save(formation);
        }



        for (Interest interest :  cvDTO1.getInterests()) {
            Interest inte = new Interest();
            inte.setName(interest.getName());

            inte.setCv(cv1);
            ineterestRepository.save(inte);
        }


        for (Langue langue :  cvDTO1.getLangs()) {
            Langue lang = new Langue();
            lang.setName(langue.getName());
            lang.setValue(langue.getValue());
            lang.setCv(cv1);
            langueRepository.save(lang);
        }


        return true;
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }


}
