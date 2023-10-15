package pro.sky.telegrambot;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Runner {
    public static void main(String[] args) throws IOException {
        System.out.println("LocalDateTime.now().toLocalDate() = " + LocalDateTime.now().toLocalDate());

        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("dateTime = " + dateTime);

    }


}

