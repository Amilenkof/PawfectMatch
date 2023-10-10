package pro.sky.telegrambot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {
    public static void main(String[] args) {

//        Pattern pattern = Pattern.compile("^(.*?),(.*?),(.*?),(.*?)$");
//        String s = "Иванов,Иван,mail@mail.ru,+79271234567";
//        Matcher matcher = pattern.matcher(s);
//        String group1 = matcher.group(1);
//        String group2 = matcher.group(2);
//        String group3 = matcher.group(3);
//        String group4 = matcher.group(4);
//
//        System.out.println("group1 = " + group1);
//        System.out.println("group2 = " + group2);
//        System.out.println("group3 = " + group3);
//        System.out.println("group4 = " + group4);

        String message = "Иванов,Иван,mail@mail.ru,+79271234567";
        String regex = "^(.*?),(.*?),(.*?),(.*?)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String lastName = matcher.group(1);
            String firstName = matcher.group(2);
            String email = matcher.group(3);
            String phoneNumber = matcher.group(4);

            System.out.println("Last Name: " + lastName);
            System.out.println("First Name: " + firstName);
            System.out.println("Email: " + email);
            System.out.println("Phone Number: " + phoneNumber);
    }}}
