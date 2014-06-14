package parsing;

import java.io.FileNotFoundException;

public class Demo {

	public static void main(String[] args) throws FileNotFoundException {
		Text t = new Text("text0.txt");
		System.out.println(t.getSortedWords('d'));
	}

}
