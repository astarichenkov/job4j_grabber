package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class SqlRuDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        List<String> months = Arrays.asList("янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек");
        String[] arr = parse.replace(",", "").split(" ");

        if (parse.contains("сегодня") || parse.contains("вчера")) {
            LocalDateTime ldt = LocalDateTime.now();
            String[] tmp = new String[4];
            if (parse.contains("сегодня")) {
                tmp[0] = (String.valueOf(ldt.getDayOfMonth()));
            } else {
                tmp[0] = (String.valueOf(ldt.getDayOfMonth() - 1));
            }
            tmp[1] = ("0" + ldt.getMonthValue()).substring(0, 2);
            tmp[2] = String.valueOf(ldt.getYear()).substring(2);
            tmp[3] = arr[1];
            arr = tmp;
        } else {
            for (int i = 0; i < months.size(); i++) {
                if (parse.contains(months.get(i))) {
                        arr[1] = arr[1].replace(months.get(i), String.valueOf(i + 1));
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (arr[i].length() < 2) {
                arr[i] = "0" + arr[i];
            }
        }
        String newDate = arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3];
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MM yy HH:mm");
        return LocalDateTime.parse(newDate, dateFormat);
    }
}