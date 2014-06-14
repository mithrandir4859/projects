package bank.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import bank.domain.Account;
import bank.parsing.dom.builders.DomParser;
import bank.parsing.sax.BankHandler;
import bank.parsing.stax.StaxParser;
import bank.validation.XmlValidation;

public class Demo {
	private static final String XML_FILE = "xml/bank.xml";
	private static final String XSD_FILE = "xml/deposit.xsd";

	public List<Account> domParsing() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(XML_FILE);
		return new DomParser(document).build();
	}

	public List<Account> saxParsing() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		BankHandler handler = new BankHandler();
		saxParser.parse(XML_FILE, handler);
		return handler.getAccountList();
	}

	public List<Account> staxParsing() throws FileNotFoundException, XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(XML_FILE));
		StaxParser staxParser = new StaxParser(reader);
		staxParser.parse();
		return staxParser.getAccountList();
	}

	@Test
	public void testResultsTheSame() throws Exception {
		List<Account> domList, saxList, staxList;
		domList = domParsing();
		saxList = saxParsing();
		staxList = staxParsing();
		assertEquals(domList, saxList);
		assertEquals(staxList, domList);
	}

	@Test
	public void testValidation() {
		assertTrue(XmlValidation.validateXMLSchema(XSD_FILE, XML_FILE));
	}

	@Test
	public void show() throws Exception {
		for (Account account : domParsing())
			System.out.println(account);
	}

}
