package Leecode.LdDemo;

import java.util.HashMap;

/**
 * leecode Longest Consecutive
 */

public class Code3_LongestConsecutiveSequence {
	public static int longestConsecutiveSequence(int[] nums) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int len = 0;
		for (int num : nums) {
			int preLen = map.containsKey(num - 1) ? map.get(num - 1) : 0;
			int posLen = map.containsKey(num + 1) ? map.get(num + 1) : 0;
			int all = posLen + preLen + 1;
			map.put(num - preLen, all);
			map.put(num + posLen, all);
			len = Math.max(len, all);
		}

		System.out.println(map);
		return len;
	}

	public static void main(String[] args) {
		int[] nums = { 1, 2, 3, 4, 5, 6 };
		System.out.println(longestConsecutiveSequence(nums));
	}
}
