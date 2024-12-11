public class Main {
	public static void main(String[] args) {
		CustomStringBuilder csb = new CustomStringBuilder("Hello");

		csb.append(" World"); // Hello World
		System.out.println(csb);

		csb.append("!"); // Hello World!
		System.out.println(csb);

		csb.undo(); // Hello World
		System.out.println(csb);

		csb.insert(5, " Beautiful"); // Hello Beautiful World
		System.out.println(csb);

		csb.reverse(); // dlroW lufituaeB olleH
		System.out.println(csb);

		System.out.println(csb.indexOf("World")); // 6
	}
}