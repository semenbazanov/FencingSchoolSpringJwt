package com.semenbazanov.service;

import com.semenbazanov.model.Apprentice;
import com.semenbazanov.model.Trainer;
import com.semenbazanov.model.TrainerSchedule;
import com.semenbazanov.model.Training;
import com.semenbazanov.repository.TrainingRepository;
import com.semenbazanov.util.Control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    private TrainingRepository trainingRepository;

    private TrainerService trainerService;

    private ApprenticeService apprenticeService;

    private TrainerScheduleService trainerScheduleService;

    @Autowired
    public void setTrainingRepository(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setApprenticeService(ApprenticeService apprenticeService) {
        this.apprenticeService = apprenticeService;
    }

    @Autowired
    public void setTrainerScheduleService(TrainerScheduleService trainerScheduleService) {
        this.trainerScheduleService = trainerScheduleService;
    }

    private boolean is3Overlap(List<Training> trainingsToday, LocalTime start) {
        int count = 0;
        for (Training training : trainingsToday) {
            boolean overlapping = Control.isOverlapping(training.getTimeStart(),
                    training.getTimeStart().plusMinutes(90),
                    start, start.plusMinutes(90));
            if (overlapping) {
                count++;
            }
        }
        return count >= 3;
    }

    private boolean is10Counting(List<Training> trainingsToday, LocalTime start) {
        int count = 0;
        for (Training training : trainingsToday) {
            boolean overlapping = Control.isOverlapping(training.getTimeStart(),
                    training.getTimeStart().plusMinutes(90),
                    start, start.plusMinutes(90));
            if (overlapping) {
                count++;
            }
        }
        return count >= 10;
    }

    @Override
    public Training add(long trainerId, long apprenticeId, Training training) {
        Trainer trainer = this.trainerService.get(trainerId);
        Apprentice apprentice = this.apprenticeService.get(apprenticeId);

        List<Training> trainingsToday = this.trainingRepository.findAllByDateAndTrainer(training.getDate(), trainer);
        System.out.println("Тренировки сегодня: " + trainingsToday);
        //Тренер одновременно не может принимать более 3 учеников
        if (this.is3Overlap(trainingsToday, training.getTimeStart())) {
            throw new IllegalArgumentException("Невозможно добавить больше 3 тренировок одновременно");
        }

        //Не больше 10 человек в зале одновременно
        List<Training> trainings = this.trainingRepository.findAllByDateAndNumberGym(training.getDate(), training.getNumberGym());
        boolean counting = this.is10Counting(trainings, training.getTimeStart());
        if (counting) {
            throw new IllegalArgumentException("В зале уже есть 10 учеников");
        }

        TrainerSchedule trainerSchedule = this.trainerScheduleService.get(trainerId);
        //Тренер не ведет прием в нерабочие дни - проверка
        LocalDate trainingDate = training.getDate();
        boolean date = trainerSchedule.getDate(trainingDate.getDayOfWeek().name().toLowerCase());
        if (!date){
            throw new IllegalArgumentException("Trainer is not available at the specified day");
        }

        //Тренер не ведет прием в нерабочее время - проверка
        LocalTime[] time = trainerSchedule.getTime(training.getDate().getDayOfWeek().name().toLowerCase());
        LocalTime start = time[0];
        LocalTime end = time[1];
        LocalTime trainingTimeStart = training.getTimeStart();
        if (!((trainingTimeStart.isAfter(start) || trainingTimeStart.equals(start)) && trainingTimeStart.isBefore(end))){
            throw new IllegalArgumentException("Trainer is not available at the specified time");
        }

        training.setApprentice(apprentice);
        training.setTrainer(trainer);

        try {
            this.trainingRepository.save(training);
            return training;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Тренировка уже существует");
        }
    }

    @Override
    public Training get(long id) {
        return this.trainingRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Training doesn't exist"));
    }

    @Override
    public List<Training> getByTrainerId(long trainerId) {
        return this.trainingRepository.getTrainingByTrainerId(trainerId);
    }

    @Override
    public List<Training> getByApprenticeId(long apprenticeId) {
        return this.trainingRepository.getTrainingByApprenticeId(apprenticeId);
    }

    @Override
    public Training delete(long id) {
        Training training = this.get(id);
        this.trainingRepository.deleteById(id);
        return training;
    }

    @Override
    public List<LocalTime> getFreeTimes(long trainerId, LocalDate date) {
        Trainer trainer = this.trainerService.get(trainerId);
        List<Training> trainings = this.trainingRepository.findAllByDateAndTrainer(date, trainer);
        List<LocalTime> timeSlots = new ArrayList<>();

        TrainerSchedule schedule = this.trainerScheduleService.get(trainerId);
        String dayWeek = date.getDayOfWeek().name().toLowerCase();
        LocalTime[] time = schedule.getTime(dayWeek);
        LocalTime start = time[0];
        LocalTime end = time[1];
        end = end.minusMinutes(90);
        while (!start.isAfter(end)) {
            if (!is3Overlap(trainings, start) && !is10Counting(trainings, start)) {
                timeSlots.add(start);
            }
            start = start.plusMinutes(30);
        }
        return timeSlots;
    }

    @Override
    public List<LocalDate> getFreeDates(long trainerId) {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        List<LocalDate> freeDates = new ArrayList<>();

        try {
            TrainerSchedule schedule = this.trainerScheduleService.get(trainerId);
            for (int day = currentDay; day <= currentDate.lengthOfMonth(); day++) {
                LocalDate date = LocalDate.of(currentYear, currentMonth, day);
                String dayOfWeek = date.getDayOfWeek().name().toLowerCase();

                if (schedule.getDate(dayOfWeek)) {
                    List<LocalTime> freeTimes = this.getFreeTimes(trainerId, date);
                    if (!freeTimes.isEmpty()) {
                        freeDates.add(date);
                    }
                }
            }
        } catch (IllegalArgumentException ignored) {
        }
        return freeDates;
    }
}
