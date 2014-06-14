package bank.parsing.dom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import bank.parsing.dom.builders.Builder;

public abstract class AbstractFromElementBuilder<T> implements Builder<T> {

	protected Element element;
	
	public AbstractFromElementBuilder(){
		
	}

	public AbstractFromElementBuilder(Element element) {
		this.element = element;
	}

	public AbstractFromElementBuilder(Node node) {
		this((Element) node);
	}

	protected String getTextContent(String tag) {
		return getSubElement(tag).getTextContent();
	}

	protected Element getSubElement(String tag) {
		return (Element) element.getElementsByTagName(tag).item(0);
	}

}
