import models.encryption.Function;

public class Tester {
	private static char c;
	public static void main(String[]args) {
//		System.out.println(c == '\u0000');
		String text = "abc";
		for (int i = 0; i < 10; i++) {
			System.out.println(Function.getInstance().hash(text));
		}
	}
}
