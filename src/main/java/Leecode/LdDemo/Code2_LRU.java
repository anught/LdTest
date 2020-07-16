package Leecode.LdDemo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 LRU（The Least Recently Used，最近最久未使用算法）是一种常见的缓存算法，在很多分布式缓存系统
 （如Redis, Memcached）中都有广泛使用。

LRU算法的思想是：如果一个数据在最近一段时间没有被访问到，那么可以认为在将来它被访问的可能性也很小。
因此，当空间满时，最久没有访问的数据最先被置换（淘汰）。

LRU算法的描述： 设计一种缓存结构，该结构在构造时确定大小，假设大小为 K，并有两个功能：

1. set(key,value)：将记录(key,value)插入该结构。当缓存满时，将最久未使用的数据置换掉。
2. get(key)：返回key对应的value值。

实现：最朴素的思想就是用数组+时间戳的方式，不过这样做效率较低。
因此，我们可以用双向链表（LinkedList）+哈希表（HashMap）实现（链表用来表示位置，哈希表用来存储和查找），
在Java里有对应的数据结构LinkedHashMap。
 * */
public class Code2_LRU {

	public static void ByLinkedHashMap() {
		LinkedHashMap<String, String> accessOrderedMap = new LinkedHashMap<String, String>(16, 0.75F, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
				// 实现自定义删除策略，否则行为就和普遍 Map 没有区别
				return size() > 3;
			}
		};
		accessOrderedMap.put("Project1", "Valhalla");
		accessOrderedMap.put("Project2", "Panama");
		accessOrderedMap.put("Project3", "Loom");
		accessOrderedMap.forEach((k, v) -> System.out.println(k + ":" + v));
		// 模拟访问
		accessOrderedMap.get("Project2");
		accessOrderedMap.get("Project1");
		accessOrderedMap.get("Project3");
		System.out.println("Iterate over should be not affected:");
		accessOrderedMap.forEach((k, v) -> System.out.println(k + ":" + v));
		// 触发删除
		accessOrderedMap.put("Project4", "Mission Control");
		System.out.println("Oldest entry should be removed:");
		// 遍历顺序不变
		accessOrderedMap.forEach((k, v) -> System.out.println(k + ":" + v));
	}

	public static void main(String[] args) throws Exception {
		LRUCache<String, String> lc = new LRUCache<String, String>(4);
		lc.put("a", "a");
		lc.put("b", "b");
		lc.put("c", "c");
		lc.put("d", "d");
		lc.put("e", "e");
		lc.put("f", "f");

		lc.get("d");

		lc.get("e");
		lc.put("a", "a");
		lc.printAllNode();
		// System.out.println(lc);
	}

}

class LRUCache<K, V> {
	private int size = 0;
	private int capacity = 0;
	private HashMap<K, Node> map = new HashMap<K, Node>();
	private Node head = null;
	private Node tail = null;

	class Node<K, V> {
		V value = null;
		K key = null;
		Node pro = null;
		Node next = null;

		public Node(K k, V v) {
			this.value = v;
			this.key = k;
		}

		@Override
		public String toString() {
			return " " + value;
		}
	}

	public LRUCache(int cap) throws Exception {
		if (cap <= 0) {
			throw new Exception("err cap");
		}
		this.capacity = cap;
	}

	public void printAllNode() {
		if (head == null) {
			System.out.print(" ");
		}
		System.out.print(head.key + " ");
		Node tmp = head;
		while (tmp.next != null) {
			System.out.print(tmp.next.key + " ");
			tmp = tmp.next;
		}
		System.out.println("");
	}

	public void put(K key, V value) {

		if (map.get(key) != null) {
			map.get(key).value = value;// 更新数值
			refreshNode2Tail(map.get(key));// 将访问过的node放到链表末尾
		} else {
			Node node = new Node(key, value);
			if (size >= capacity) {
				map.remove(head.key);
				removeHead();
			} else {
				size++;
			}
			add2Tail(node);
			map.put(key, node);
		}
	}

	public V get(K key) {
		Node node = map.get(key);
		if (node == null) {
			return null;
		} else {
			refreshNode2Tail(node);
			return (V) node.value;
		}
	}

	public void removeHead() {
		if (head == null) {
			return;
		}

		Node nodeNext = head.next;
		if (nodeNext == null) { // 说明只有一个
			head = null;
		} else {
			nodeNext.pro = null;
			head = nodeNext;
		}

	}

	public void add2Tail(Node node) {
		if (node == null || head == null) {
			head = node;
			tail = head;
			return;
		}

		node.next = null;
		node.pro = tail;
		tail.next = node;
		tail = node;

	}

	public void refreshNode2Tail(Node node) {
		if (node == null) {
			return;
		}

		if (node.next == null) { // 此node已经是tail了
			return;
		} else if (node.pro == null) { // 此node 是 head
			Node nodeNext = node.next;
			if (nodeNext == null) {
				return;
			}
			nodeNext.pro = null;
			head = nodeNext;

		} else {
			Node nodePro = node.pro;
			Node nodeNext = node.next;
			nodePro.next = nodeNext;
			nodeNext.pro = nodePro;
		}

		node.next = null;
		node.pro = tail;
		tail.next = node;
		tail = node;

	}

	public String toString() {
		return " head:" + head.key + "\n tail:" + tail.key + "\n" + map.toString();
	}
}
