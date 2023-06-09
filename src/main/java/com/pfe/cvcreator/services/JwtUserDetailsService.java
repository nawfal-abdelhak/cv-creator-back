package com.pfe.cvcreator.services;



import com.pfe.cvcreator.entities.User;
import com.pfe.cvcreator.repositories.IUserRepository;
import com.pfe.cvcreator.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);
        System.out.println(user.getRoles().size());
        if (user==null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new MyUserPrincipal(user);
    }
}
