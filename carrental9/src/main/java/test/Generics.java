package test;

import java.util.Arrays;
import java.util.List;

public class Generics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] str = new String[]{"lalala"};
		Object[] obj = str;
//		obj[0] = new Integer(20);
		List<? extends String> la = Arrays.asList(str);

	}

}
