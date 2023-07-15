package com.pfe.cvcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfe.cvcreator.dto.ChangePasswordDTO;
import com.pfe.cvcreator.dto.CvDTO;
import com.pfe.cvcreator.dto.UserDTO;
import com.pfe.cvcreator.entities.Cv;
import com.pfe.cvcreator.entities.User;
import com.pfe.cvcreator.services.IUserService;
import com.pfe.cvcreator.utils.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@CrossOrigin("*")
public class ClientController {
    @Autowired
    private  ModelMapper modelMapper;

    @Autowired
    private IUserService userService;

    @GetMapping("/hello")
    public  ResponseEntity<String> getCart(){

        return ResponseEntity.ok("hello");
    }
    @GetMapping("/getCvs/{id}")
    public  ResponseEntity<?> getCvs(@PathVariable Long id){

        List<Cv> cvs = userService.getCvs(id);
        return ResponseEntity.ok(cvs);
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePassword){
        Boolean changed = userService.changePassword(changePassword);
        if(!changed)
            return ResponseEntity.badRequest().body("Failed To Change Password");

        return ResponseEntity.ok().body("Password Changed");
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addCv(@RequestParam("cv") String  cvDTO,@RequestParam("image") MultipartFile image,@PathVariable Long id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CvDTO cvDTO1 = objectMapper.readValue(cvDTO,CvDTO.class);

        Boolean added = userService.saveCv(cvDTO1,image,id);

        if (added)
            return ResponseEntity.ok().body("cv created");
         return ResponseEntity.badRequest().body("cv Not created");
    }

    @PostMapping("/editCv/{user_id}/{cv_id}")
    public ResponseEntity<?> addCv(@RequestParam("cv") String  cvDTO,@RequestParam("image") MultipartFile image,@PathVariable Long user_id,@PathVariable Long cv_id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CvDTO cvDTO1 = objectMapper.readValue(cvDTO,CvDTO.class);

        Boolean added = userService.edtiCv(cvDTO1,image,user_id,cv_id);

        if (added)
            return ResponseEntity.ok().body("cv created");
        return ResponseEntity.badRequest().body("cv Not created");
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {

        User user = userService.getUser(id);

        UserDTO userdto = Mapper.convertUserToUserDTO(user);
        return ResponseEntity.ok().body(userdto);
    }

    @GetMapping("/subscribe/{id}")
    public ResponseEntity<?> subscribe(@PathVariable Long id)  {

        User user = userService.subsribe(id);

        UserDTO userdto = Mapper.convertUserToUserDTO(user);
        return ResponseEntity.ok().body(userdto);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers()  {

        List<User> users = userService.getUsers();

        List<UserDTO> usersDTO= users.stream().map(user -> modelMapper.map(user,UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(usersDTO);
    }

    @GetMapping("/getUsersWithCVCount")
    public ResponseEntity<?> getUsersWithCVCount()  {

        List<Object[]> usersWithCVCount = userService.getUsersWithCVCount();


        return ResponseEntity.ok().body(usersWithCVCount);
    }


    @GetMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)  {

        boolean added  = userService.deleteUser(id);

        if (added)
            return ResponseEntity.ok().body("user deleted");
        return ResponseEntity.badRequest().body("user not");

    }





}
