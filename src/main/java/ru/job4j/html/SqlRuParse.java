package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements ru.job4j.grabber.Parse {
    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> postList = new ArrayList<>();
        for (int j = 1; j < 6; j++) {
            String url = link + j;
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements row = doc.select(".postslisttopic");
            Elements dates = doc.select("td.altCol:nth-last-child(odd)");
            try {
                for (int i = 0; i < row.size(); i++) {
                    Element href = row.get(i).child(0);
                    String postLink = href.attr("href");
                    String postName = href.text();
                    LocalDateTime dateTime = dateTimeParser.parse(dates.get(i).text());
                    Post post = new Post(postName, postLink, dateTime);
                    postList.add(post);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("end of elements");
            }
        }
        return postList;
    }

    @Override
    public Post detail(String link) {
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements desc = doc.select("td.msgBody");
        String text = desc.toString();
        Elements header = doc.select("td.messageHeader");
        String name = header.get(0).text();
        Elements date = doc.select("td.msgFooter");
        String d = date.get(0).text();
        String dt = d.substring(0, d.indexOf(" ["));
        LocalDateTime dateTime = dateTimeParser.parse(dt);
        return new Post(name, text, link, dateTime);
    }

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse(new SqlRuDateTimeParser());
        Post post = sqlRuParse.detail("https://www.sql.ru/forum/1336939/java-razrabotchik-moskva-udalyonka-200-300-gross");
        System.out.println(post);
        String link = "https://www.sql.ru/forum/job-offers/";
        List<Post> list = sqlRuParse.list(link);
        list.forEach(System.out::println);
    }
}



