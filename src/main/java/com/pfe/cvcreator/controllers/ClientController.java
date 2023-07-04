package com.pfe.cvcreator.controllers;

import com.pfe.cvcreator.dto.ChangePasswordDTO;
import com.pfe.cvcreator.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@CrossOrigin("*")
public class ClientController {
    @Autowired
    private IUserService userService;

    @GetMapping("/hello")
    public  ResponseEntity<String> getCart(){

        return ResponseEntity.ok("hello");
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePassword){
        Boolean changed = userService.changePassword(changePassword);
        if(!changed)
            return ResponseEntity.badRequest().body("Failed To Change Password");

        return ResponseEntity.ok().body("Password Changed");
    }
}
