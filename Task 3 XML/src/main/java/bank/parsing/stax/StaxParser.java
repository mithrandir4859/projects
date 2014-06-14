package bank.parsing.stax;

import static javax.xml.stream.XMLStreamConstants.ATTRIBUTE;
import static javax.xml.stream.XMLStreamConstants.CHARACTERS;
import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import bank.domain.Account;
import bank.parsing.sax.AccountSetter;

public class StaxParser {

	private Stack<String> tagStack = new Stack<String>();

	private Account account = new Account();

	private List<Account> accountList = new ArrayList<Account>();

	private XMLStreamReader reader;

	public StaxParser(XMLStreamReader reader) {
		super();
		this.reader = reader;
	}

	public List<Account> getAccountList() {
		return accountList;
	}

	public void parse() throws XMLStreamException, FileNotFoundException {

		while (reader.hasNext()) {
			int event = reader.next();

			switch (event) {
			case START_ELEMENT:
				String tag = reader.getLocalName();

				if (tag.equals("depositor")) {
					String strId = reader.getAttributeValue(0);
					AccountSetter.ID.parse(account, strId);
				}

				tagStack.push(tag);
				if (tag.equals("account"))
					account = new Account();
				break;

			case CHARACTERS:
				try {
					String convertedTagName = tagStack.peek().toUpperCase();
					AccountSetter accountSetter = AccountSetter.valueOf(convertedTagName);
					accountSetter.parse(account, reader.getText().trim());
				} catch (IllegalArgumentException e) {
					// Well, if we are here, then we don't have AccountSetter
					// for a given tag, so we do nothing, it's OK
				}
				break;

			case END_ELEMENT:
				if (reader.getLocalName().equals("account")) {
					accountList.add(account);
					account = null;
				}
				tagStack.pop();
				break;

			case ATTRIBUTE:
				// String strId = reader.getAttributeValue(0);
				// AccountSetter.ID.parse(account, strId);
				// System.out.println(account.getDepositor());
				break;
			}

		}

	}
}
