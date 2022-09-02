package com.example.itsquad.repository;

import com.example.itsquad.domain.Squad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadRepository extends JpaRepository<Squad, Long> {

}
