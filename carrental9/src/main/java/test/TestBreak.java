package test;

public class TestBreak {

	public static void main(String[] args) {
		boolean b = false;
		checkParams: {
			if (b)
				break checkParams;
			b = true;
			if (b)
				break checkParams;
			System.out.println("befor return");
			return;
				
		

		}
		System.out.println("after return, lal");

	}

}
