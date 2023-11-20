package me.lagggpixel.platformerTutorial.logging;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.logging.Level;

public class Logger {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void log(Level level, String message) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.now(), TimeZone.getDefault().toZoneId());

        System.out.println("[" + dtf.format(dateTime) + " " + level.toString() + "]: " + message);
    }
}
