package bank.parsing.sax;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bank.domain.Account;

public class BankHandler extends DefaultHandler {

	private Stack<String> tagStack = new Stack<String>();

	private Account account = new Account();

	private List<Account> accountList = new ArrayList<Account>();

	public List<Account> getAccountList() {
		return accountList;
	}

	@Override
	public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
		tagStack.push(tag);
		if (tag.equals("b:account")) 
			account = new Account();
		else if (attributes.getLength() == 1){
			String strId = attributes.getValue(0);
			AccountSetter.ID.parse(account, strId);
		}
	}

	@Override
	public void endElement(String uri, String localName, String tag) throws SAXException {
		if (tag.equals("b:account")) {
			accountList.add(account);
			account = null;
		}
		tagStack.pop();
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		try {
			String convertedTagName = tagStack.peek().substring(2).toUpperCase();
			AccountSetter accountSetter = AccountSetter.valueOf(convertedTagName);
			accountSetter.parse(account, new String(ch, start, length));
		} catch (IllegalArgumentException e) {
			// Well, if we are here, then we don't have AccountSetter for
			// a given tag, so we do nothing, it's OK
		}
	}

}
