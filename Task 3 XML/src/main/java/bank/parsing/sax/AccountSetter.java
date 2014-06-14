package bank.parsing.sax;

import java.math.BigDecimal;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import bank.domain.Account;
import bank.domain.AccountType;
import bank.domain.Person;

public enum AccountSetter{

	NUMBER {
		@Override
		public void parse(Account account, String strNumber) {
			account.setNumber(Long.parseLong(strNumber));
		}
	},
	TYPE {
		@Override
		public void parse(Account account, String strAccountType) {
			strAccountType = strAccountType.toUpperCase().replaceAll(" ", "_");
			AccountType accountType = AccountType.valueOf(strAccountType);
			account.setAccountType(accountType);
		}
	},
	BALANCE {
		@Override
		public void parse(Account account, String strBalance) {
			BigDecimal balance = new BigDecimal(strBalance);
			account.setBalance(balance);
		}
	},
	RATE {
		@Override
		public void parse(Account account, String strRate) {
			BigDecimal rate = new BigDecimal(strRate);
			account.setRate(rate);
		}
	},
	DATESTART {
		@Override
		public void parse(Account account, String strDateStart) {
			account.setDateStart(FORMATTER.parseLocalDate(strDateStart));
		}
	},
	DATEEND {
		@Override
		public void parse(Account account, String strDateEnd) {
			account.setDateEnd(FORMATTER.parseLocalDate(strDateEnd));
		}
	},FIRSTNAME {
		@Override
		public void parse(Account account, String firstname) {
			Person person = account.getDepositor();
			person.setFirstname(firstname);
		}
	},
	LASTNAME {
		@Override
		public void parse(Account account, String lastname) {
			Person person = account.getDepositor();
			person.setLastname(lastname);
		}
	},
	SSN {
		@Override
		public void parse(Account account, String ssn) {
			Person person = account.getDepositor();
			person.setSsn(ssn);
		}
	},
	ID {
		@Override
		public void parse(Account account, String strId) {
			Person person = new Person();
			person.setId(Integer.parseInt(strId));
			account.setDepositor(person);
		}
	};

	private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

	public abstract void parse(Account account, String parameter);

}
