package ru.gotoqa;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.gotoqa.models.ISBNService;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static ru.gotoqa.Constants.Constants.*;

/**
 * @author Muflikhunov Roman
 */

public class ParsingHtmlSoupRqSetUserAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParsingHtmlSoupRqSetUserAgent.class);
    public static Document doc;
    public static Connection.Response response;
    public static ISBNService bean;
    private static ClassPathXmlApplicationContext contextdb = new ClassPathXmlApplicationContext("db.xml");
    NamedParameterJdbcTemplate nqu = new NamedParameterJdbcTemplate(contextdb.getBean(DataSource.class));


    @BeforeAll
    public static void AccessSetup() throws IOException {
        //Set User Agent + get connect to page
        doc = Jsoup.connect(URLSITE)
                .userAgent(USERAGENT)
                .timeout(5000)
                .cookie("cookiename", "roman")
                .cookie("anothercookie", "gotoqa")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();

        //get response
        response = Jsoup.connect(URLSITE).followRedirects(false).execute();

        //Soap request
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"application-config.xml"});
        bean = context.getBean(ISBNService.class);
    }

    @Test
    @DisplayName("Test ID = 1. Check the Status Code.")
    public void statusCodeTest() {
        int sc = response.statusCode();
        Assertions.assertEquals(STATUSCODE200, sc);
        LOGGER.trace("Status Code: {}", sc);
    }

    @Test
    @DisplayName("Test ID = 2. Check the Status Message.")
    public void statusMessageTest() {
        String sm = response.statusMessage();
        Assertions.assertEquals(STATUSMESSAGE, sm);
        LOGGER.trace("Status Message: {}", sm);
    }

    @Test
    @DisplayName("Test ID = 3. Check the ISBN use webservice & create report TXT file.")
    public void checkIsbnWebServiceTest() {
        Elements links = doc.select("div.abaproduct-details > span:matches(\\d{13})");
        Assertions.assertEquals(COUNTELEMENT, links.size());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILEPATH))){
        for (Element link : links) {
            String replace = link.text().replace("ISBN-13: ", "");
            boolean validISBN13 = bean.getISBNServiceSoap().isValidISBN13(replace);
            Assertions.assertTrue(validISBN13);
            LOGGER.trace("Checking book ISBN = " + replace + ". Web service return: " + validISBN13);
            bw.write("Checking book ISBN = " + replace + ". Web service return: " + validISBN13);
            bw.newLine();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Test ID = 4. Check the Title Page.")
    public void checkTitlePageTest() {
        String title = doc.title();
        Assertions.assertEquals("Search | Harvard Book Store", title);
        LOGGER.trace("Title: {}", title);
    }

    @Test
    @DisplayName("Test ID = 5. Check the Book name.")
    public void checkBookNameTest() {
        Elements bookName = doc.select("#block-system-main > div > ol > li > h3");
        Assertions.assertEquals(COUNTELEMENT, bookName.size());
        for (Element link2 : bookName) {
            String replace2 = link2.text();
            LOGGER.trace("Book name: {}", replace2);
            Assertions.assertNotNull(replace2, "No book title.");
            Assertions.assertTrue(replace2.contains("Java"));
            //Assertions.assertTrue(replace2.contains("Paperback"));
        }
    }

    @Test
    @DisplayName("Test ID = 6. Check the Book price.")
    public void checkBookPriceTest() {
        Elements bookPrice = doc.select("div.abaproduct-details > h3");
        Assertions.assertEquals(COUNTELEMENT, bookPrice.size());
        for (Element link3 : bookPrice) {
            String replace3 = link3.text();
            LOGGER.trace("Book price: {}", replace3);
            Assertions.assertNotNull(replace3);
            Assertions.assertTrue(replace3.contains(MONEYCODE));
        }
    }

    @Test
    @DisplayName("Test ID = 7. Check the Book Author.")
    public void ckeckBookAuthorTest() {
        //get the bookAuthor. all authors css (div.abaproduct-details > p > a)
        Elements bookAuthor = doc.select("div.abaproduct-details > p");
        Assertions.assertEquals(COUNTELEMENT, bookAuthor.size());
        for (Element link4 : bookAuthor) {
            String replace4 = link4.text();
            LOGGER.trace("Book Author: {}", replace4);
            Assertions.assertNotNull(replace4);
        }
    }

    @Test
    @DisplayName("Test ID = 8. Check the Enter Terms.")
    public void checkEnterTermsTest() {
        Elements enterTermsInput = doc.select("#edit-keys");
        Assertions.assertNotNull(enterTermsInput, "Element enterTermsInput is empty");
        LOGGER.trace("Enter Terms: {}", enterTermsInput);
    }

    @Test
    @DisplayName("Test ID = 9. Check the Sort Results element.")
    public void checkSortResultElementTest() {
        Elements sortResults = doc.select("#edit-fsort");
        Assertions.assertNotNull(sortResults, "Element Sort Results is empty");
    }

    @Test
    @DisplayName("Test ID = 10. Check the Search Results element.")
    public void checkSearchResultElementTest() {
        Elements searchResults = doc.select("#block-system-main > div > h2");
        Assertions.assertNotNull(searchResults, "Element Search Results is empty");
    }

    @Test
    @DisplayName("Test ID = 11. Check the Add Cart button.")
    public void checktAddCartButtonTest() {
        Elements addCart = doc.select("[id^=edit-add-to-cart]");
        Assertions.assertNotNull(addCart, "Nu such Element: Add to Cart");
        Assertions.assertEquals(COUNTELEMENT, addCart.size());
        for (Element link5 : addCart) {
            String replace5 = link5.text();
            System.out.println(replace5);
            Assertions.assertNotNull(replace5);
            Assertions.assertEquals(ADDTOCART, replace5);
        }
    }

    @Test
    @DisplayName("Test ID = 12. Check the Wish List button.")
    public void checktWishListButtonTest() {
        Elements addWishList = doc.select("[id^=edit-add-to-w]");
        Assertions.assertNotNull(addWishList, "Nu such Element: Add to Wish List");
        Assertions.assertEquals(COUNTELEMENT, addWishList.size());
        for (Element link : addWishList) {
            String replace = link.text();
            System.out.println(replace);
            Assertions.assertNotNull(replace);
            Assertions.assertEquals(ADDTOWISHLIST, replace);
        }
    }

    @Test
    @DisplayName("Test ID = 13. Check the Availability element.")
    public void checktAvailabilityElementTest() {
        Elements availability = doc.select("div.abaproduct-details > span:nth-child(6)");
        Assertions.assertNotNull(availability, "Nu such Element: Availability");
        Assertions.assertEquals(COUNTELEMENT, availability.size());
        for (Element link : availability) {
            String replace = link.text();
            System.out.println(replace);
            Assertions.assertNotNull(replace);
            Assertions.assertTrue(replace.contains("Availability"));
        }
    }

    @Test
    @DisplayName("Test ID = 14. Check the Published element.")
    public void checktPublishedTest() {
        Elements published = doc.select("div.abaproduct-details > span:nth-child(8)");
        Assertions.assertNotNull(published, "Nu such Element: Published");
        Assertions.assertEquals(COUNTELEMENT, published.size());
        for (Element link : published) {
            String replace = link.text();
            System.out.println(replace);
            Assertions.assertNotNull(replace);
            Assertions.assertTrue(replace.contains("Published"));
        }
    }

    @Test
    @DisplayName("Test ID = 15. Check the img element.")
    public void checkImgElementTest() {
        Elements published = doc.select("div.abaproduct-image > a > img");
        Assertions.assertNotNull(published, "Nu such Element: img");
        Assertions.assertEquals(COUNTELEMENT, published.size());
        for (Element link : published) {
            System.out.println(link);
            Assertions.assertNotNull(link);
        }
    }

    @Test
    @DisplayName("Test ID = 16. Check the all element.")
    public void checkAllElementTest() {
        Elements published = doc.select("#block-system-main > div > ol > li");
        Assertions.assertNotNull(published, "Nu such Element");
        Assertions.assertEquals(COUNTELEMENT, published.size());
        for (Element link : published) {
            String replace = link.text();
            System.out.println(replace);
            Assertions.assertNotNull(replace);
        }
    }

}