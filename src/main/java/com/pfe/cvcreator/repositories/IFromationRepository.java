package com.pfe.cvcreator.repositories;

import com.pfe.cvcreator.entities.Formation;
import com.pfe.cvcreator.entities.Langue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFromationRepository extends JpaRepository<Formation,Long> {
}
