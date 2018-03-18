package ru.gotoqa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Muflikhunov Roman
 */
public class TempParsing {
    @Test
    void test1() throws IOException {
        Document doc = Jsoup.connect("https://shop.harvard.com/search/site/java")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0.2 Safari/604.4.7")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();



/*        // get all links
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            // get the value from href attribute
            System.out.println("\nlink : " + link.attr("href"));
            System.out.println("text : " + link.text());

        }*/

        //ISBN
        Elements links = doc.select("div.abaproduct-details > span:matches(\\d{13})");
        for (Element link : links) {
            // get the value
            String replace = link.text().replace("ISBN-13: ", "");
            System.out.println(replace);
        }


        ArrayList<String> elements = new ArrayList<String>();
        for (int i = 0; i < links.size(); i++) {
            elements.add(links.get(i).html());
        }
        System.out.println(elements);




        //div.abaproduct-image > a > img
        //


/*
        //All data loop
        Elements bookPage = document.select("div.abaproduct-details");
        for (Element element : bookPage) {
            System.out.println(element.text());
        }*/





    }


}