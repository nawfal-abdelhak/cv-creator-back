package com.pfe.cvcreator.repositories;

import com.pfe.cvcreator.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User  findUserByUsername(String username);
}
