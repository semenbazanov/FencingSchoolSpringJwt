package com.semenbazanov.service;

import com.semenbazanov.model.Trainer;
import com.semenbazanov.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setTrainerRepository(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(Trainer trainer) {
        try {
            trainer.setPassword(this.passwordEncoder.encode(trainer.getPassword()));
            this.trainerRepository.save(trainer);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Trainer already added");
        }
    }

    @Override
    public List<Trainer> get() {
        return this.trainerRepository.findAll();
    }

    @Override
    public Trainer get(long id) {
        return this.trainerRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Trainer doesn't exists"));
    }

    @Override
    public Trainer update(Trainer trainer) {
        Trainer oldTrainer = this.get(trainer.getId());
        oldTrainer.setName(trainer.getName());
        oldTrainer.setSurname(trainer.getSurname());
        oldTrainer.setPatronymic(trainer.getPatronymic());
        oldTrainer.setExperience(trainer.getExperience());
        try {
            this.trainerRepository.save(oldTrainer);
            return oldTrainer;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Trainer already exists");
        }
    }

    @Override
    public Trainer delete(long id) {
        Trainer trainer = this.get(id);
        this.trainerRepository.deleteById(id);
        return trainer;
    }
}
