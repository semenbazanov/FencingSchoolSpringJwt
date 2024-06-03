package com.semenbazanov.fencingschoolfxspring.model;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrainerSchedule {
    private long id;

    private LocalTime mondayStart;

    private LocalTime mondayEnd;

    private LocalTime tuesdayStart;

    private LocalTime tuesdayEnd;

    private LocalTime wednesdayStart;

    private LocalTime wednesdayEnd;

    private LocalTime thursdayStart;

    private LocalTime thursdayEnd;

    private LocalTime fridayStart;

    private LocalTime fridayEnd;

    private LocalTime saturdayStart;

    private LocalTime saturdayEnd;

    private LocalTime sundayStart;

    private LocalTime sundayEnd;

    private Trainer trainer;

    public TrainerSchedule() {
    }

    public List<TrainerScheduleItem> get() {
        List<TrainerScheduleItem> result = new ArrayList<>();
        String[] dayWeek = new String[]{"monday", "tuesday", "wednesday", "thursday",
                "friday", "saturday", "sunday"};
        for (String day : dayWeek) {
            try {
                Field start = this.getClass().getDeclaredField(day + "Start");
                Field end = this.getClass().getDeclaredField(day + "End");
                if (start.get(this) != null && end.get(this) != null) {
                    TrainerScheduleItem item = new TrainerScheduleItem();
                    item.setEngDay(day);
                    item.setStart((LocalTime) start.get(this));
                    item.setEnd((LocalTime) end.get(this));
                    result.add(item);
                }
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        }
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalTime getMondayStart() {
        return mondayStart;
    }

    public void setMondayStart(LocalTime mondayStart) {
        this.mondayStart = mondayStart;
    }

    public LocalTime getMondayEnd() {
        return mondayEnd;
    }

    public void setMondayEnd(LocalTime mondayEnd) {
        this.mondayEnd = mondayEnd;
    }

    public LocalTime getTuesdayStart() {
        return tuesdayStart;
    }

    public void setTuesdayStart(LocalTime tuesdayStart) {
        this.tuesdayStart = tuesdayStart;
    }

    public LocalTime getTuesdayEnd() {
        return tuesdayEnd;
    }

    public void setTuesdayEnd(LocalTime tuesdayEnd) {
        this.tuesdayEnd = tuesdayEnd;
    }

    public LocalTime getWednesdayStart() {
        return wednesdayStart;
    }

    public void setWednesdayStart(LocalTime wednesdayStart) {
        this.wednesdayStart = wednesdayStart;
    }

    public LocalTime getWednesdayEnd() {
        return wednesdayEnd;
    }

    public void setWednesdayEnd(LocalTime wednesdayEnd) {
        this.wednesdayEnd = wednesdayEnd;
    }

    public LocalTime getThursdayStart() {
        return thursdayStart;
    }

    public void setThursdayStart(LocalTime thursdayStart) {
        this.thursdayStart = thursdayStart;
    }

    public LocalTime getThursdayEnd() {
        return thursdayEnd;
    }

    public void setThursdayEnd(LocalTime thursdayEnd) {
        this.thursdayEnd = thursdayEnd;
    }

    public LocalTime getFridayStart() {
        return fridayStart;
    }

    public void setFridayStart(LocalTime fridayStart) {
        this.fridayStart = fridayStart;
    }

    public LocalTime getFridayEnd() {
        return fridayEnd;
    }

    public void setFridayEnd(LocalTime fridayEnd) {
        this.fridayEnd = fridayEnd;
    }

    public LocalTime getSaturdayStart() {
        return saturdayStart;
    }

    public void setSaturdayStart(LocalTime saturdayStart) {
        this.saturdayStart = saturdayStart;
    }

    public LocalTime getSaturdayEnd() {
        return saturdayEnd;
    }

    public void setSaturdayEnd(LocalTime saturdayEnd) {
        this.saturdayEnd = saturdayEnd;
    }

    public LocalTime getSundayStart() {
        return sundayStart;
    }

    public void setSundayStart(LocalTime sundayStart) {
        this.sundayStart = sundayStart;
    }

    public LocalTime getSundayEnd() {
        return sundayEnd;
    }

    public void setSundayEnd(LocalTime sundayEnd) {
        this.sundayEnd = sundayEnd;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainerSchedule that = (TrainerSchedule) o;
        return id == that.id && Objects.equals(mondayStart, that.mondayStart)
                && Objects.equals(mondayEnd, that.mondayEnd) && Objects.equals(tuesdayStart, that.tuesdayStart)
                && Objects.equals(tuesdayEnd, that.tuesdayEnd) && Objects.equals(wednesdayStart, that.wednesdayStart)
                && Objects.equals(wednesdayEnd, that.wednesdayEnd) && Objects.equals(thursdayStart, that.thursdayStart)
                && Objects.equals(thursdayEnd, that.thursdayEnd) && Objects.equals(fridayStart, that.fridayStart)
                && Objects.equals(fridayEnd, that.fridayEnd) && Objects.equals(saturdayStart, that.saturdayStart)
                && Objects.equals(saturdayEnd, that.saturdayEnd) && Objects.equals(sundayStart, that.sundayStart)
                && Objects.equals(sundayEnd, that.sundayEnd) && Objects.equals(trainer, that.trainer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mondayStart, mondayEnd, tuesdayStart, tuesdayEnd, wednesdayStart,
                wednesdayEnd, thursdayStart, thursdayEnd, fridayStart, fridayEnd, saturdayStart,
                saturdayEnd, sundayStart, sundayEnd, trainer);
    }

    @Override
    public String toString() {
        return "TrainerSchedule{" +
                "id=" + id +
                ", mondayStart=" + mondayStart +
                ", mondayEnd=" + mondayEnd +
                ", tuesdayStart=" + tuesdayStart +
                ", tuesdayEnd=" + tuesdayEnd +
                ", wednesdayStart=" + wednesdayStart +
                ", wednesdayEnd=" + wednesdayEnd +
                ", thursdayStart=" + thursdayStart +
                ", thursdayEnd=" + thursdayEnd +
                ", fridayStart=" + fridayStart +
                ", fridayEnd=" + fridayEnd +
                ", saturdayStart=" + saturdayStart +
                ", saturdayEnd=" + saturdayEnd +
                ", sundayStart=" + sundayStart +
                ", sundayEnd=" + sundayEnd +
                ", trainer=" + trainer +
                '}';
    }
}
