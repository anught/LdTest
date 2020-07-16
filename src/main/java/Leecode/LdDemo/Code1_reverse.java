package Leecode.LdDemo;

/*
 给定一个字符数组 或者 字符串数组， 输入一个int值point；从数组的point处进行翻转。例如
输入 ['a','b','c','d','e','f']     point=4
输出 ['e','f','a','b','c','d']  
 * */

public class Code1_reverse {
	public static void main(String[] args) {
		String[] input = { "1", "2", "3", "4", "5", "6", "7" };
		fun1(input, 3);
		// turnOver(input, 2, 6);
		for (int idx = 0; idx < input.length; idx++) {
			System.out.print(input[idx]);
		}

	}

	public static void fun1(String[] input, int point) {
		int inputLen = input.length;
		if (point < 0 || point > inputLen) {
			System.out.println("point out of inputStringList range");
			return;
		}

		turnOver(input, 0, point);
		for (int idx = 0; idx < input.length; idx++) {
			System.out.print(input[idx]);
		}
		System.out.println();
		turnOver(input, point + 1, inputLen - 1);
		for (int idx = 0; idx < input.length; idx++) {
			System.out.print(input[idx]);
		}
		System.out.println();
		turnOver(input, 0, inputLen - 1);

		for (int idx = 0; idx < input.length; idx++) {
			System.out.print(input[idx]);
		}
		System.out.println();
	}

	public static void turnOver(String[] input, int start, int end) {
		if (start > end || end > input.length || start < 0) {
			System.out.println("why");
			return;
		}
		String tmp = null;
		for (int i = start; i <= (end + start) / 2; i++) {
			tmp = input[i];
			input[i] = input[end - i + start];
			input[end - i + start] = tmp;
		}
	}

}
