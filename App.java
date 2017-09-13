package ru.kazan.icl;

import gs_producing_web_service.GetCountryRequest;
import gs_producing_web_service.ObjectFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        try{

            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage soapMsg = factory.createMessage();
            SOAPPart part = soapMsg.getSOAPPart();

            SOAPEnvelope envelope = part.getEnvelope();
            SOAPHeader header = envelope.getHeader();
            SOAPBody body = envelope.getBody();
            header.addTextNode("Training Details");

            ObjectFactory objectFactory = new ObjectFactory();

            GetCountryRequest getCountryRequest = new GetCountryRequest();
            getCountryRequest.setName("USA");

            JAXBElement<GetCountryRequest> requestElement = objectFactory.getCountryRequestJAXBElement(getCountryRequest);

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Marshaller marshaller = JAXBContext.newInstance(GetCountryRequest.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            marshaller.marshal(getCountryRequest, document);
            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
            soapMessage.getSOAPBody().addDocument(document);
            ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
            soapMessage.writeTo(outputStream);
            String output = new String(outputStream.toByteArray());
            System.out.println(output);

            SOAPBodyElement element1 = body.addBodyElement(envelope.createName("JAVA", "training", "http://JitendraZaa.com/blog"));
            element1.addChildElement("Spring").addTextNode("Training on Spring 3.0");

            soapMsg.writeTo(System.out);
            FileOutputStream fOut = new FileOutputStream("SoapMessage.xml");
            soapMsg.writeTo(fOut);

            System.out.println();
            System.out.println("SOAP msg created");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static Element JAXBElementToDomElement(GetCountryRequest element) {

        try {
            JAXBContext jc = JAXBContext.newInstance(new Class[]{
                    GetCountryRequest.class});
            Marshaller um = jc.createMarshaller();
            StringWriter sw = new StringWriter();

            um.marshal(element, sw);
            InputStream is = new ByteArrayInputStream(sw.toString().getBytes());
            Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            return xmlDocument.getDocumentElement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
