package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
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