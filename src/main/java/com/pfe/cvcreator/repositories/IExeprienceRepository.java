package com.pfe.cvcreator.repositories;

import com.pfe.cvcreator.entities.Experience;
import com.pfe.cvcreator.entities.Langue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExeprienceRepository extends JpaRepository<Experience,Long> {
}
