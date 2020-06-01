import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

public class Main {
    public static void main(String[] args) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse("lab.xml");
            XPathFactory pathFactory = XPathFactory.newInstance();
            XPath xpath = pathFactory.newXPath();

            ArrayList<ArrayList> name = getMotherNameByNumberHospitalRoom(doc, xpath, "1");
            System.out.println("В первой палате лежат:");
            for (int i=0;i < name.get(0).size();i++)
                System.out.println(name.get(0).get(i)+ " " + name.get(1).get(i));
            System.out.println();

            ArrayList<ArrayList> child = getChildByMotherName(doc, xpath, "Ivanova");
            System.out.println("Дети Ивановой:");
            for (int i=0;i < child.get(0).size();i++)
                System.out.println(child.get(0).get(i)+ " " + child.get(1).get(i));
            System.out.println();


        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ArrayList> getMotherNameByNumberHospitalRoom(Document doc, XPath xpath, String number) {
        ArrayList<String> surname = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<ArrayList> fullName = new ArrayList<>();
        try {
            XPathExpression xExp = xpath.compile(
                    "//mother[number_hospital_room='"+number+"']/full_name/surname/text()");
            XPathExpression xExp1 = xpath.compile(
                    "//mother[number_hospital_room='"+number+"']/full_name/name/text()");
            NodeList surnameNode = (NodeList) xExp.evaluate(doc, XPathConstants.NODESET);
            NodeList nameNode = (NodeList) xExp1.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < surnameNode.getLength(); i++) {
                surname.add(surnameNode.item(i).getTextContent());
                name.add(nameNode.item(i).getTextContent());
            }
            fullName.add(surname);
            fullName.add(name);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return fullName;
    }

    public static ArrayList<ArrayList> getChildByMotherName(Document doc, XPath xpath, String surname) {
        ArrayList<String> birthday = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<ArrayList> fullName = new ArrayList<>();
        try {
            XPathExpression xExp = xpath.compile(
                    "//mother[full_name[surname='"+surname+"']]/childs/nameChild/text()");
            XPathExpression xExp1 = xpath.compile(
                    "//mother[full_name[surname='"+surname+"']]/childs/birthdayChild/text()");
            NodeList nameNode = (NodeList) xExp.evaluate(doc, XPathConstants.NODESET);
            NodeList birthdayNode = (NodeList) xExp1.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nameNode.getLength(); i++) {
                name.add(nameNode.item(i).getTextContent());
                birthday.add(birthdayNode.item(i).getTextContent());
            }
            fullName.add(name);
            fullName.add(birthday);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return fullName;
    }
}