package com.pfe.cvcreator.repositories;

import com.pfe.cvcreator.entities.Cv;
import com.pfe.cvcreator.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User  findUserByUsername(String username);
    @Query("SELECT cv FROM User u JOIN u.cvs cv WHERE u.id = :userId ")
    List<Cv> findCvsByUserId(@Param("userId") Long userId);

    @Query("SELECT u, COUNT(c) FROM User u LEFT JOIN u.cvs c GROUP BY u")
    List<Object[]> getUsersWithCVCount();
}
