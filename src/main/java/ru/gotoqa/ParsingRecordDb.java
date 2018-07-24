package ru.gotoqa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.gotoqa.models.ISBNService;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.HashMap;

import static ru.gotoqa.Constants.Constants.*;

/**
 * @author Muflikhunov Roman
 */

public class ParsingRecordDb {
    public static ISBNService bean;

    public static void main(String[] args) throws IOException {

        //https://try.jsoup.org/
        Document doc = Jsoup.parse(new File(FILEPATHHTML), "utf-8");
        Elements inputElements = doc.select("ol > li");
        int update = 0;

        for (Element inputElement : inputElements) {
            String author = inputElement.select("div.abaproduct-details > p").text().replace("By ", "");
            String price = inputElement.select("div.abaproduct-details > h3").text();
            String isbn = inputElement.select("div.abaproduct-details > span:nth-child(4)").text().replace("ISBN-13: ", "");
            String title = inputElement.select("h3.title").text().replace("Java: ", "");
            String availability = inputElement.select("div.abaproduct-details > span:nth-child(6)").text().replace("Availability: ", "");
            String published = inputElement.select("div.abaproduct-details > span:nth-child(8)").text().replace("Published: ", "");
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Price: " + price);
            System.out.println("ISBN: " + isbn);
            System.out.println("Availability: " +availability);
            System.out.println("Published: " +published);
            System.out.println();

            //Soap request
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"application-config.xml"});
            bean = context.getBean(ISBNService.class);
            boolean isbnCheck = bean.getISBNServiceSoap().isValidISBN13(isbn);
            System.out.println(isbnCheck);

            ClassPathXmlApplicationContext contextdb = new ClassPathXmlApplicationContext("db.xml");
            NamedParameterJdbcTemplate nquOracle = new NamedParameterJdbcTemplate(contextdb.getBean(DataSource.class));

            // define query arguments ISBN_Check
            Object[] params = new Object[] {title, author, price, isbn, availability, published, isbnCheck};

            // define SQL types of the arguments
            int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN };

            HashMap<String, Object> param = new HashMap<>();
            param.put("title", title);
            param.put("author", author);
            param.put("price", price);
            param.put("isbn", isbn);
            param.put("availability", availability);
            param.put("published", published);
            param.put("isbnCheck", isbnCheck);
            String insertSql = "INSERT INTO ISBN_CATALOG (Title, Author, Price, ISBN, AvailabilityData, Published, ISBN_Check) VALUES (:title, :author, :price, :isbn, :availability, :published, :isbnCheck)";

            // execute insert + count number of records / records processed by the executed query
            nquOracle.update(insertSql, param);
            update ++;
        }
        System.out.println(update + " records inserted.");
    }

}