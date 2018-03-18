package ru.gotoqa.Constants;

/**
 * @author Muflikhunov Roman
 */
public class Constants {

    public static final String URLSITE = "https://shop.harvard.com/search/site/java";
    public static final String FILEPATH = "D:\\JAVA\\Java_SRC\\ISBNProject\\src\\main\\resources\\report.txt";
    public static final String MONEYCODE = "$";
    public static final String STATUSMESSAGE = "OK";
    public static final String ADDTOCART = "Add to Cart";
    public static final String ADDTOWISHLIST = "Add to Wish List";
    public static final int COUNTELEMENT = 10;
    public static final int STATUSCODE200 = 200;


    //userAgent
    public static final String USERAGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0.2 Safari/604.4.7";



    private Constants() {
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }
}