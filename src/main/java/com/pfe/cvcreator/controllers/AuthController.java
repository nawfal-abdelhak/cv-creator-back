package com.pfe.cvcreator.controllers;

import com.pfe.cvcreator.dto.JwtRequest;
import com.pfe.cvcreator.dto.JwtResponse;
import com.pfe.cvcreator.dto.RegisterDTO;
import com.pfe.cvcreator.dto.UserDTO;
import com.pfe.cvcreator.entities.User;
import com.pfe.cvcreator.security.MyUserPrincipal;
import com.pfe.cvcreator.services.IUserService;
import com.pfe.cvcreator.utils.Mapper;
import com.pfe.cvcreator.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/LogIn",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final MyUserPrincipal userDetails = (MyUserPrincipal) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        UserDTO userdto = Mapper.convertUserToUserDTO(userDetails.getUser());
        return ResponseEntity.ok(new JwtResponse(token,userdto));
    }
    @PostMapping(value = "/signUp",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody RegisterDTO user) throws Exception {
        user.getRole().add("CLIENT");
            User client1=userService.addUser(user);
            UserDetails userDetails=new MyUserPrincipal(client1);
            final String token = jwtTokenUtil.generateToken(userDetails);
        UserDTO userdto = Mapper.convertUserToUserDTO(client1);
            return ResponseEntity.ok(new JwtResponse(token,userdto));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
