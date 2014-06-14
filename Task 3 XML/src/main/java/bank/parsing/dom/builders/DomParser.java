package bank.parsing.dom.builders;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bank.domain.Account;
import bank.parsing.dom.AbstractFromElementBuilder;

public class DomParser extends AbstractFromElementBuilder<List<Account>> {
	private static final String[] NESTED_TAGS = { "b:bank", "b:accounts", "b:account" };

	private final Document document;

	public DomParser(Document document) {
		this.document = document;
	}

	private NodeList getAccountsNodeList() {
		int i = 0;
		NodeList nodeList;
		nodeList = document.getElementsByTagName(NESTED_TAGS[i++]);
		for (; i < NESTED_TAGS.length;) {
			element = (Element) nodeList.item(0);
			nodeList = element.getElementsByTagName(NESTED_TAGS[i++]);
		}
		return nodeList;
	}

	@Override
	public List<Account> build() {
		NodeList nodeList = getAccountsNodeList();
		int size = nodeList.getLength();
		List<Account> accountList = new ArrayList<Account>(size);
		for (int i = 0; i < size; i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			AccountBuilder accountBuilder = new AccountBuilder(node);
			accountList.add(accountBuilder.build());
		}
		return accountList;
	}

}
