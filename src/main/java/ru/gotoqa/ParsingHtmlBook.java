package ru.gotoqa;

import org.junit.jupiter.api.Test;
import ru.gotoqa.models.ISBNService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Muflikhunov Roman
 */

public class ParsingHtmlBook {

        @Test
        void test1() throws IOException {
        String url = "https://shop.harvard.com/search/site/java";
        Document document = null;
        if (!Jsoup.connect(url).get().equals(null)){
                 document = Jsoup.connect(url).get();
        }


/*        //Parsing all info from page
        String bookPage = document.select("div.abaproduct-details").text();
        System.out.println("ISBN: " + bookPage);*/

        //String bookIsbn = document.select("li.search-result:nth-child(1) > div:nth-child(3) > span:nth-child(4)").text();

        //get ISBN num first book
        String firstBookIsbn = document.select("#block-system-main > div > ol > li:nth-child(1) > div.abaproduct-details > span:nth-child(4)").text().replace("ISBN-13: ", "");
        System.out.println(firstBookIsbn);

        //Soap request
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application-config.xml"});
        ISBNService bean = context.getBean(ISBNService.class);

        boolean validISBN13 = bean.getISBNServiceSoap().isValidISBN13(firstBookIsbn);
        System.out.println("Проверка книги показала: " +validISBN13);
    }


}
