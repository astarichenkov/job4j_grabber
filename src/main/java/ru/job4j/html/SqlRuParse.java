package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        String site = "https://www.sql.ru/forum/job-offers/";
        for (int j = 1; j < 6; j++) {
            String url = site + j;
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            Elements dates = doc.select("td.altCol:nth-last-child(odd)");
            try {
                for (int i = 0; i < row.size(); i++) {
                    System.out.println(dates.get(i).text());
                    Element href = row.get(i).child(0);
                    System.out.println(href.attr("href"));
                    System.out.println(href.text());
                    System.out.println();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("end of elements");
            }
        }
    }

    private static void getDescriptionAndDate(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements desc = doc.select("td.msgBody");

        Elements date = doc.select("td.msgFooter");
        String d = date.get(0).text();
        String dt = d.substring(0, d.indexOf(" ["));
        SqlRuDateTimeParser timeParser = new SqlRuDateTimeParser();
        LocalDateTime dateTime = timeParser.parse(dt);
    }
}


