import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class CityHandler extends DefaultHandler {
    private StringBuilder data;
    private String city;
    private String street;
    private String house;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data = new StringBuilder();
        if ("city".equals(qName)) {
            city = attributes.getValue("size");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("city".equals(qName)) {
            System.out.println("City: " + city);
        } else if ("street".equals(qName)) {
            street = data.toString();
            System.out.println("Street: " + street);
        } else if ("house".equals(qName)) {
            house = data.toString();
            System.out.println("House: " + house);
        }
    }
}

public class CityXmlGenerator {

    public static void main(String[] args) {
        generateXml();
        parseXml();
    }

    private static void generateXml() {
        try (FileWriter writer = new FileWriter("city.xml")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<city size=\"big\">\n");
            writer.write("    <street>Mir Street</street>\n");
            writer.write("    <house>15</house>\n");
            writer.write("</city>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseXml() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            CityHandler handler = new CityHandler();
            saxParser.parse(new File("city.xml"), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
