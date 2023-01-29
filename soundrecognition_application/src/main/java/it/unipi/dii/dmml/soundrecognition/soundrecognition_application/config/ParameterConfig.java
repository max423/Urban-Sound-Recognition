package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static String getDbname() {return dbname;}

    public static String getAdminUsername() {
        return adminUsername;
    }

    public static String getAdminPassword() {
        return adminPassword;
    }


    public static void saveToXML() {
        String xml = "src/main/resources/config/config.xml";
        Document dom;
        Element e = null;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("it.unipi.dii.dmml.soundrecognition.soundrecognition_application.ParamConfig");

            // create data elements and place them under root
            e = dom.createElement("ip");
            e.appendChild(dom.createTextNode(ip));
            rootEle.appendChild(e);

            e = dom.createElement("port");
            e.appendChild(dom.createTextNode(port));
            rootEle.appendChild(e);

            e = dom.createElement("username");
            e.appendChild(dom.createTextNode(username));
            rootEle.appendChild(e);

            e = dom.createElement("dbname");
            e.appendChild(dom.createTextNode(dbname));
            rootEle.appendChild(e);

            e = dom.createElement("adminUsername");
            e.appendChild(dom.createTextNode(adminUsername));
            rootEle.appendChild(e);

            e = dom.createElement("adminPassword");
            e.appendChild(dom.createTextNode(adminPassword));
            rootEle.appendChild(e);

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(xml)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }
}





