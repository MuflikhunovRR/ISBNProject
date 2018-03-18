package ru.gotoqa.models;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * DataFlex Web Service to validate ISBN numbers.
 *
 * This class was generated by Apache CXF 3.2.1
 * 2018-03-17T21:50:43.914+03:00
 * Generated source version: 3.2.1
 * 
 */
@WebServiceClient(name = "ISBNService", 
                  wsdlLocation = "http://webservices.daehosting.com/services/isbnservice.wso?wsdl",
                  targetNamespace = "http://webservices.daehosting.com/ISBN") 
public class ISBNService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://webservices.daehosting.com/ISBN", "ISBNService");
    public final static QName ISBNServiceSoap = new QName("http://webservices.daehosting.com/ISBN", "ISBNServiceSoap");
    public final static QName ISBNServiceSoap12 = new QName("http://webservices.daehosting.com/ISBN", "ISBNServiceSoap12");
    static {
        URL url = null;
        try {
            url = new URL("http://webservices.daehosting.com/services/isbnservice.wso?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ISBNService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://webservices.daehosting.com/services/isbnservice.wso?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ISBNService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ISBNService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ISBNService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public ISBNService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ISBNService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ISBNService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns ISBNServiceSoapType
     */
    @WebEndpoint(name = "ISBNServiceSoap")
    public ISBNServiceSoapType getISBNServiceSoap() {
        return super.getPort(ISBNServiceSoap, ISBNServiceSoapType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ISBNServiceSoapType
     */
    @WebEndpoint(name = "ISBNServiceSoap")
    public ISBNServiceSoapType getISBNServiceSoap(WebServiceFeature... features) {
        return super.getPort(ISBNServiceSoap, ISBNServiceSoapType.class, features);
    }


    /**
     *
     * @return
     *     returns ISBNServiceSoapType
     */
    @WebEndpoint(name = "ISBNServiceSoap12")
    public ISBNServiceSoapType getISBNServiceSoap12() {
        return super.getPort(ISBNServiceSoap12, ISBNServiceSoapType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ISBNServiceSoapType
     */
    @WebEndpoint(name = "ISBNServiceSoap12")
    public ISBNServiceSoapType getISBNServiceSoap12(WebServiceFeature... features) {
        return super.getPort(ISBNServiceSoap12, ISBNServiceSoapType.class, features);
    }

}
