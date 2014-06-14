package bank.parsing.dom.builders;

import java.math.BigDecimal;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import bank.domain.Account;
import bank.domain.AccountType;
import bank.domain.Person;
import bank.parsing.dom.AbstractFromElementBuilder;

public class AccountBuilder extends AbstractFromElementBuilder<Account> {

	public AccountBuilder(Element element) {
		super(element);
	}

	public AccountBuilder(Node node) {
		super(node);
	}

	@Override
	public Account build() {
		Account account = new Account();
		{
			String strNumber = getTextContent("b:number");
			account.setNumber(Long.parseLong(strNumber));
		}
		{
			String strAccountType = getTextContent("b:type");
			strAccountType = strAccountType.toUpperCase().replaceAll(" ", "_");
			AccountType accountType = AccountType.valueOf(strAccountType);
			account.setAccountType(accountType);
		}
		{
			PersonBuilder personBuilder = new PersonBuilder(getSubElement("b:depositor"));
			Person person = personBuilder.build();
			account.setDepositor(person);
		}
		{
			String strBalance = getTextContent("b:balance");
			BigDecimal balance = new BigDecimal(strBalance);
			account.setBalance(balance);
		}
		{
			String strRate = getTextContent("b:rate");
			BigDecimal rate = new BigDecimal(strRate);
			account.setRate(rate);
		}
		{
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
			String strDateStart = getTextContent("b:dateStart");
			account.setDateStart(formatter.parseLocalDate(strDateStart));
			String strDateEnd = getTextContent("b:dateEnd");
			account.setDateEnd(formatter.parseLocalDate(strDateEnd));
		}
		return account;
	}
}
