package com.pfe.cvcreator.repositories;

import com.pfe.cvcreator.entities.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICvRepository extends JpaRepository<Cv,Long> {
}
