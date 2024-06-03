package com.semenbazanov.util;

import java.time.LocalTime;

public class Control {
    public static boolean isOverlapping(LocalTime start1, LocalTime end1,
                                        LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}
