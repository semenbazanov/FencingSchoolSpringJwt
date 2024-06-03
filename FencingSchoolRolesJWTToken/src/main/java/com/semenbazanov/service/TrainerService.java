package com.semenbazanov.service;

import com.semenbazanov.model.Trainer;

import java.util.List;

public interface TrainerService {
    void add(Trainer trainer);

    List<Trainer> get();

    Trainer get(long id);

    Trainer update(Trainer trainer);

    Trainer delete(long id);
}
