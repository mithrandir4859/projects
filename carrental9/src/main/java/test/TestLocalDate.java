package test;


public class TestLocalDate {

	public static void main(String[] args){
		Class<? extends Object> klass = Object.class;
		System.out.println(klass);
		System.out.println(klass.getSimpleName());
		
		String actionName = klass.getSimpleName();
		actionName = String.valueOf(actionName.charAt(0)).toLowerCase() + actionName.substring(1);
		System.out.println(actionName);
	}

}
