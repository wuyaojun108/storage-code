package org.wyj.data.structure;

import java.util.Scanner;

/**
 * 测试红黑树的功能 
 */
public class RBTreeTest {

    public static void main(String[] args) {
        RBTree<Integer, String> tree = new RBTree<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("========功能菜单=========");
            System.out.println("1.添加元素");
            System.out.println("2.移除元素");
            System.out.println("请输入：");
            String str = scanner.next();
            if ("1".equals(str)) {
                System.out.println("请输入要添加的键（int类型）：");
                String key = scanner.next();
                addEle(tree, Integer.parseInt(key), null);
            } else if ("2".equals(str)) {
                System.out.println("请输入要删除的键：");
                String key = scanner.next();
                delEle(tree, Integer.parseInt(key));
            } else if("exit".equals(str)){
                scanner.close();
                System.exit(0);
            }
        }
    }

    // 删除树中的某个元素并且打印树
    public static <K extends Comparable<K>, V> void delEle(RBTree<K, V> tree, K key) {
        tree.remove(key);
        tree.printTree();
    }

    // 向树中添加元素并且打印树结构
    public static <K extends Comparable<K>, V> void addEle(RBTree<K, V> tree, K key, V value) {
        tree.add(key, value);
        tree.printTree();
    }


}
