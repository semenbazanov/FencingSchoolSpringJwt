package com.semenbazanov.repository;

import com.semenbazanov.model.TrainerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Long> {
}
