package bank.parsing.dom.builders;

import bank.domain.Person;
import bank.parsing.dom.AbstractFromElementBuilder;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PersonBuilder extends AbstractFromElementBuilder<Person> {

	public PersonBuilder(Element element) {
		super(element);
	}

	public PersonBuilder(Node node) {
		super(node);
	}

	@Override
	public Person build() {
		Person person = new Person();
		person.setFirstname(getTextContent("b:firstname"));
		person.setLastname(getTextContent("b:lastname"));
		person.setSsn(getTextContent("b:ssn"));
		person.setId(Integer.parseInt(element.getAttribute("id")));
		return person;
	}

}
