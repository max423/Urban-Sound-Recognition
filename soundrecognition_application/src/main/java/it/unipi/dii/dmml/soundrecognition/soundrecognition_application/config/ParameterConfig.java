package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.config;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ParameterConfig {

    // Configuration Parameters
    private static String ip;
    private static String port;
    private static String username;
    private static String dbname;
    private static String adminUsername;
    private static String adminPassword;

    static {

        try {
            // creating a constructor of file class and
            // parsing an XML file
            File file = new File("src/main/resources/config/config.xml");

            // Defines a factory API that enables
            // applications to obtain a parser that produces
            // DOM object trees from XML documents.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // we are creating an object of builder to parse
            // the xml file.
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

			/*here normalize method Puts all Text nodes in
			the full depth of the sub-tree underneath this
			Node, including attribute nodes, into a "normal"
			form where only structure separates
			Text nodes, i.e., there are neither adjacent
			Text nodes nor empty Text nodes. */
            doc.getDocumentElement().normalize();

            ip = doc.getElementsByTagName("ip").item(0).getTextContent();
            port = doc.getElementsByTagName("port").item(0).getTextContent();
            username = doc.getElementsByTagName("username").item(0).getTextContent();
            dbname = doc.getElementsByTagName("dbname").item(0).getTextContent();
            adminUsername = doc.getElementsByTagName("adminUsername").item(0).getTextContent();
            adminPassword = doc.getElementsByTagName("adminPassword").item(0).getTextContent();
        }

        // This exception block catches all the exception
        // raised.
        // For example if we try to access a element by a
        // TagName that is not there in the XML etc.
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getIp() {
        return ip;
    }

    public static String getPort() {
        return port;
    }

    public static String getUsername() {
        return username;
    }

    public static String getDbname() {
        return dbname;
    }

    public static String getAdminUsername() {
        return adminUsername;
    }

    public static String getAdminPassword() {
        return adminPassword;
    }

}





