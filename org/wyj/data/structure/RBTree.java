package org.wyj.data.structure;

import java.util.Arrays;
import java.util.LinkedList;

// 红黑树 
public class RBTree<K extends Comparable<K>, V> {

    // 颜色标识
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // 树中持有根节点的引用
    private Node root;

    // 树中元素的个数
    private int size;

    // 判断节点的颜色是不是红色
    private boolean isRed(Node node){ return node.isColor(); }

    // 判断节点的颜色是不是黑色
    private boolean isBlack(Node node){ return node.isColor() == BLACK; }

    // 向树中添加元素
    public void add(K key, V value){
        Node newNode = new Node(key, value, RED, null, null, null);
        if(root == null){
            root = newNode;
            root.setColor(BLACK);
            size++;
            return;
        }
        Node curNode = root;
        Node parent = null;
        int cmp;
        while (curNode != null){
            parent = curNode;
            cmp = newNode.getKey().compareTo(curNode.getKey());
            if(cmp < 0){
                curNode = curNode.left;
            }else if(cmp > 0){
                curNode = curNode.right;
            }else{
                curNode.setValue(value);
                return;
            }
        }
        if(newNode.getKey().compareTo(parent.getKey()) < 0){
            parent.setLeft(newNode);
        }else{
            parent.setRight(newNode);
        }
        newNode.setParent(parent);
        size++;
        adjust(newNode);
    }

    // 新增元素后调整红黑树
    private void adjust(Node node){
        Node parent = node.getParent();
        // 如果节点的父节点是null，代表节点是根节点，将根节点设置为红色，然后退出
        if(parent == null){
            node.setColor(BLACK);
            return;
        }
        // 如果节点的父节点是黑色，不需要调整，直接退出
        if(isBlack(parent)){ return; }
        // 如果节点的父节点是红色，那么父节点一定还有父节点，因为红黑树中根节点是黑色，红色节点最高只能到第二层
        Node gParent = parent.getParent();
        // 获取叔节点
        Node uncle;
        if(isEquals(gParent.getLeft(), parent)){
            // 父节点是祖父节点的左子节点
            uncle = gParent.getRight();
            if(uncle == null || isBlack(uncle)) {
                if(isEquals(parent.getLeft(), node)){
                    // 当前节点是父节点的左子节点
                    parent.setColor(BLACK);
                    gParent.setColor(RED);
                    rightRotate(parent);
                }else{
                    // 当前节点是父节点的右子节点
                    leftRotate(node);
                    node.setColor(BLACK);
                    gParent.setColor(RED);
                    rightRotate(node);
                }
            }
        }else{
            // 父节点是祖父节点的右子节点
            uncle = gParent.getLeft();
            if(uncle == null || isBlack(uncle)) {
                if (isEquals(parent.getLeft(), node)) {
                    // 当前节点是父节点的左子节点
                    rightRotate(node);
                    node.setColor(BLACK);
                    gParent.setColor(RED);
                    leftRotate(node);
                } else {
                    // 当前节点是父节点的右子节点
                    parent.setColor(BLACK);
                    gParent.setColor(RED);
                    leftRotate(parent);
                }
            }
        }
        // 父叔双红的情况
        if(uncle != null && isRed(uncle)){
            parent.setColor(BLACK);
            uncle.setColor(BLACK);
            gParent.setColor(RED);
            adjust(gParent);
        }
    }

    /*
    左旋：在左旋过程中，只有当前节点cur和父节点p一定是存在的，其它节点存不存在都可以。
    左旋分为3步：
      gp             gp                  gp             gp
      |              |                   |              |
      p              p                  node            node
     / \            / \                   \            / \
   bro node       bro lx  node         p  rx          p   rx
       / \                 \          / \            / \
      lx rx                rx        bro lx        bro lx
     */
    private void leftRotate(Node node){
        Node p = node.getParent();
        // 第一步：将node节点的左子节点挂到父节点的右子结点上，如果node节点的左子结点为空，
        // 那么这会断开父节点指向node节点的链接
        p.setRight(node.getLeft());
        if(node.getLeft() != null){
            node.getLeft().setParent(p);
        }
        // 第二步：设置当前节点的父节点。注意，p节点的父节点在第二步的最后应该设置为null，因为第三步的存在，这一步可以
        // 省略
        Node gParent = p.getParent();
        node.setParent(gParent);
        if(gParent == null){
            root = node;
        }else{
            if(isEquals(gParent.getLeft(), p)){
                gParent.setLeft(node);
            }else{
                gParent.setRight(node);
            }
        }
        // 第三步：将当前节点之前的父节点变成当前节点的左子节点
        p.setParent(node);
        node.setLeft(p);
    }

    /*
    右旋：在右旋过程中，只有当前节点cur和父节点p一定是存在的，其它节点存不存在都可以
       gp                 gp                gp                 gp
       |                  |                 |                  |
       p                  p                node               node
      / \                / \               /                  / \
   node  bro       node rx  bro           lx    p            lx  p
    / \             /                          / \              / \
   lx rx           lx                        rx  bro          rx  bro
     */
    private void rightRotate(Node node){
        Node p = node.getParent();
        // 第一步：将当前节点的右子节点设置为父节点的左子节点，当前节点的右子节点如果为空，这一步会断开
        // 父节点指向当前节点的链接
        p.setLeft(node.getRight());
        if(node.getRight() != null){
            node.getRight().setParent(p);
        }
        // 第二步：设置当前节点的父节点
        Node gParent = p.getParent();
        node.setParent(gParent);
        if(gParent == null){
            root = node;
        }else{
            if(isEquals(gParent.getLeft(), p)){
                gParent.setLeft(node);
            }else{
                gParent.setRight(node);
            }
        }
        // 第三步：将当前节点之前的父节点设置为当前节点的右子结点
        p.setParent(node);
        node.setRight(p);
    }

    // 判断两个节点的键是否相等
    private boolean isEquals(Node node1, Node node2){
        if(node1 == null && node2 == null){
            return true;
        }else if(node1 == null || node2 == null){
            return false;
        }else{
            return node1.getKey().compareTo(node2.getKey()) == 0;
        }
    }

    // 查找某个子树下的最小节点
    private Node minNode(Node node){
        if(node == null){
            return null;
        }
        while (node.left != null){
            node = node.left;
        }
        return node;
    }

    // 查找某个子树下的最大节点
    private Node maxNode(Node node){
        if(node == null){
            return null;
        }
        while (node.right != null){
            node = node.right;
        }
        return node;
    }

    // 返回当前节点的后继节点，也就是当前节点右子树中的最小值
    private Node successor(Node node){
        return minNode(node.getRight());
    }

    // 返回当前节点的前驱节点，也就是当前节点左子树中的最大值
    private Node predecessor(Node node){
        return maxNode(node.getLeft());
    }

    // 根据键取值
    public V get(K key){
        Node node = getNode(root, key);
        return node != null ? node.getValue() : null;
    }

    // 删除树中的某个元素
    public V remove(K key){
        // 如果要删除的键不存在，直接返回
        Node curNode = getNode(root, key);
        if(curNode == null){
            return null;
        }
        V oldValue = curNode.getValue();
        remove(curNode);
        return oldValue;
    }

    // 删除树中的某个节点
    private void remove(Node curNode){
        // 第1种情况：如果当前节点有两个子结点，找到当前节点的后继节点，用后继节点的值来代替当前节点的值，
        // 然后删除后继节点
        if(curNode.getLeft() != null && curNode.getRight() != null){
            Node successor = successor(curNode);
            curNode.setKey(successor.getKey());
            curNode.setValue(successor.getValue());
            // 后继节点变成了当前节点，删除后继节点
            curNode = successor;
        }
        // 执行正式的删除逻辑，删除倒数第一层或倒数第二层的节点
        // 获取被删除节点的子结点
        Node replacement = curNode.getLeft() != null ? curNode.getLeft() : curNode.getRight();
        // 第2种情况：被删除的节点只有一个红色子结点，那么被删除的节点一定是黑色，子结点一定是红色，
        // 使用子结点的值代替被删除节点的值，然后删除子结点
        if(replacement != null && replacement.isColor() == RED){
            curNode.setKey(replacement.getKey());
            curNode.setValue(replacement.getValue());
            if(isEquals(curNode.getLeft(), replacement)){
                curNode.setLeft(null);
            } else {
                curNode.setRight(null);
            }
            replacement.setParent(null);
            size--;
            return;
        }
        Node p = curNode.getParent();
        // 第3种情况：被删除的节点没有子结点并且也没有父节点，那么被删除的节点就是根节点，把被删除的节点置为空即可
        if(p == null){
            root = null;
            size--;
            return;
        }
        // 第4种情况：被删除节点没有子结点并且被删除的节点是红色，直接删除被删除节点即可
        if(isRed(curNode)){
            if(isEquals(p.getLeft(), curNode)){
                p.setLeft(null);
            } else {
                p.setRight(null);
            }
            curNode.setParent(null);
            size--;
            return;
        }
        // 第5种情况：被删除的节点没有子结点并且被删除的节点是黑色
        if(curNode.getLeft() == null && curNode.getRight() == null && isBlack(curNode)){
            // 在删除前对树进行调整
            adjustAfterRemove(curNode);
            // 执行删除逻辑
            Node parent = curNode.getParent();
            if(isEquals(parent.getLeft(), curNode)){
                parent.setLeft(null);
            }else{
                parent.setRight(null);
            }
            size--;
        }
    }

    // 当被删除的节点是一个黑色节点并且没有子结点时，需要对树进行调整
    private void adjustAfterRemove(Node curNode) {
        Node p = curNode.getParent();
        Node bro;
        if(isEquals(p.getLeft(), curNode)){
            // 被删除节点是父节点的左子结点
            bro = p.getRight();
            // 第一种情况：
            // 如果兄弟节点是红色并且有子结点，兄弟节点变为黑色，父节点变为红色，以兄弟节点为核心，进行左旋
            // ，此时父节点变为兄弟节点的右子结点。左旋之后，重新获取当前节点的兄弟节点，在随后进行调整
            if(isRed(bro) && (bro.getLeft() != null || bro.getRight() != null)){
                bro.setColor(BLACK);
                p.setColor(RED);
                leftRotate(bro);
                bro = p.getRight();
            }
            if(isBlack(bro)){
                // 第二种情况：如果兄弟节点是黑色并且只有一个红色左子结点，左子节点变黑，兄弟节点变红，
                // 以左子结点为核心进行右旋。重新获取当前节点的兄弟节点，在随后进行处理
                if(bro.getLeft() != null && bro.getLeft().isColor() == RED && bro.getRight() == null){
                    bro.getLeft().setColor(BLACK);
                    bro.setColor(RED);
                    rightRotate(bro.getLeft());
                    bro = p.getRight();
                }
                // 第三种情况：如果兄弟节点有一个右子节点是红色，兄弟节点变红，父节点
                // 变黑，右子结点变黑，以兄弟节点为核心，进行左旋。完成。（不需要判断兄弟节点有
                // 两个子结点的情况，因为它们的处理方式和兄弟节点只有一个右子结点的处理方式是
                // 一致的）
                if(bro.getRight() != null && bro.getRight().isColor() == RED){
                    bro.getRight().setColor(BLACK);
                    bro.setColor(RED);
                    bro.getParent().setColor(BLACK);
                    leftRotate(bro);
                    return;
                }
                if((bro.getLeft() == null || bro.getLeft().isColor() == BLACK)
                        && (bro.getRight() == null || bro.getRight().isColor() == BLACK)){
                    if(bro.getParent().isColor() == RED){
                        // 第四种情况：如果兄弟节点是黑色并且没有子结点，父节点是红色，父节点变黑，兄弟节点变红
                        bro.setColor(RED);
                        bro.getParent().setColor(BLACK);
                    } else {
                        // 第五种情况：如果兄弟节点是黑色并且没有子结点，或者有两个黑色子结点，父节点是黑色，
                        // 兄弟节点变红，随后，如果父节点不是根节点，以父节点为当前节点，递归调用本方法，继续
                        // 调整
                        bro.setColor(RED);
                        if(bro.getParent() != root){
                            adjustAfterRemove(bro.getParent());
                        }
                    }
                }
            }
        } else {
            // 被删除节点是父节点的右子结点
            bro = p.getLeft();
            // 第一种情况：
            // 如果兄弟节点是红色并且有子结点，兄弟节点变为黑色，父节点变为红色，以兄弟节点为核心，进行右旋
            // ，此时父节点变为兄弟节点的右子结点。右旋之后，重新获取当前节点的兄弟节点，在随后进行调整
            if(isRed(bro) && (bro.getLeft() != null || bro.getRight() != null)){
                bro.setColor(BLACK);
                p.setColor(RED);
                rightRotate(bro);
                bro = p.getLeft();
            }
            if(isBlack(bro)){
                // 第二种情况：如果兄弟节点是黑色并且只有一个红色右子结点，右子节点变黑，兄弟节点变红，
                // 以右子结点为核心进行左旋。重新获取当前节点的兄弟节点，在随后进行处理
                if(bro.getRight() != null && bro.getRight().isColor() == RED && bro.getLeft() == null){
                    bro.getRight().setColor(BLACK);
                    bro.setColor(RED);
                    leftRotate(bro.getRight());
                    bro = p.getLeft();
                }
                // 第三种情况：如果兄弟节点有一个左子节点是红色，兄弟节点变红，父节点
                // 变黑，左子结点变黑，以兄弟节点为核心，进行右旋。完成。（不需要判断兄弟节点有
                // 两个子结点的情况，因为它们的处理方式和兄弟节点只有一个右子结点的处理方式是
                // 一致的）
                if(bro.getLeft() != null && bro.getLeft().isColor() == RED){
                    bro.getLeft().setColor(BLACK);
                    bro.setColor(RED);
                    bro.getParent().setColor(BLACK);
                    rightRotate(bro);
                    return;
                }
                if((bro.getLeft() == null || bro.getLeft().isColor() == BLACK)
                        && (bro.getRight() == null || bro.getRight().isColor() == BLACK)){
                    if(bro.getParent().isColor() == RED){
                        // 第四种情况：如果兄弟节点是黑色并且没有子结点，父节点是红色，父节点变黑，兄弟节点变红
                        bro.setColor(RED);
                        bro.getParent().setColor(BLACK);
                    } else {
                        // 第五种情况：如果兄弟节点是黑色并且没有子结点，或者有两个黑色子结点，父节点是黑色，
                        // 兄弟节点变红，随后，如果父节点不是根节点，以父节点为当前节点，递归调用本方法，继续
                        // 调整
                        bro.setColor(RED);
                        if(bro.getParent() != root){
                            adjustAfterRemove(bro.getParent());
                        }
                    }
                }
            }
        }
        // 最终将根节点设置为黑色
        root.setColor(BLACK);
    }



    // 根据键取某个子树下的节点
    private Node getNode(Node node, K key){
        int cmp;
        while (node != null){
            cmp = key.compareTo(node.getKey());
            if(cmp < 0){
                node = node.left;
            }else if(cmp > 0){
                node = node.right;
            }else{
                return node;
            }
        }
        return null;
    }

    // 获取树的深度，在这里使用递归的方式来获取树的深度，也可以使用深度遍历的方式来获取树的深度
    public int getTreeDepth(Node root) {
        return root == null ? 0 : (1 + Math.max(getTreeDepth(root.getLeft()), getTreeDepth(root.getRight())));
    }

    // 获取树的最大深度
    public int getMaxDepth(){
        int depth = 0;
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() != 0){
            depth++;
            int size = queue.size();
            for(int i = 0; i < size; i++){
                Node node = queue.poll();
                if(node != null){
                    if(node.getLeft() != null) queue.offer(node.getLeft());
                    if(node.getRight() != null) queue.offer(node.getRight());
                }
            }
        }
        return depth;
    }

    // 获取树的最小深度
    public int getMinDepth(){
        int depth = 0;
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() != 0){
            depth++;
            int size = queue.size();
            for(int i = 0; i < size; i++){
                Node node = queue.poll();
                if(node != null){
                    if(node.getLeft() == null || node.getRight() == null) return depth;
                    else if(node.getLeft() != null) queue.offer(node.getLeft());
                    else if(node.getRight() != null) queue.offer(node.getRight());
                }
            }
        }
        return depth;
    }

    /**
     *    打印树的图形结构。
     */
    public void printTree(){
        // 判断根节点是否为空
        if(root == null) {
            System.out.println("EMPTY");
            return;
        }
        // 获取树的深度，树的深度就是二维数组的高度
        int treeDepth = getMaxDepth();
        // 获取最后一层的元素个数。最后一层的元素个数是：2^(treeDepth-1)
        int eleNumOfLastLevel = (int) Math.pow(2, (treeDepth - 1));
        // 获取二维数组的宽度
        int arrayWidth = eleNumOfLastLevel + (eleNumOfLastLevel - 1);

        // 声明并初始化二维数组
        String [][] store = new String[treeDepth][arrayWidth];
        for (String[] strings : store) {
            Arrays.fill(strings, "     ");
        }

        writeArray(root, 0, arrayWidth/ 2, store, treeDepth);
        for (String[] strings : store) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }

    private void writeArray(Node curNode, int rowIndex, int columnIndex, String[][] res, int treeDepth) {
        int gap = (int) Math.pow(2, (treeDepth - (rowIndex + 2)));
        if(treeDepth == rowIndex + 1) gap = 0;

        // 先将当前节点保存到二维数组中
        res[rowIndex][columnIndex] = curNode.getKey() + "-" + (curNode.isColor() ? "R" : "B") + "";

        // 对左儿子进行判断，若有左儿子，则记录相应的左儿子的值
        if (curNode.getLeft() != null) {
            writeArray(curNode.getLeft(), rowIndex + 1
                    , columnIndex - gap, res, treeDepth);
        }

        // 对右儿子进行判断，若有右儿子，则记录相应的右儿子的值
        if (curNode.getRight() != null) {
            writeArray(curNode.getRight(), rowIndex + 1
                    , columnIndex + gap, res, treeDepth);
        }

    }





    @Override
    public String toString() {
        return "RBTree{" +
                "root=" + root +
                ", size=" + size +
                '}';
    }

    // 红黑树中每一个节点的数据结构
    private class Node{
        private K key;
        private V value;
        private boolean color;
        private Node parent;
        private Node left;
        private Node right;

        private Node(){ }

        public Node(K key, V value, boolean color, Node parent, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public K getKey() { return key; }

        public void setKey(K key) { this.key = key; }

        public V getValue() { return value; }

        public void setValue(V value) { this.value = value; }

        public boolean isColor() { return color; }

        public void setColor(boolean color) { this.color = color; }

        public Node getParent() { return parent; }

        public void setParent(Node parent) { this.parent = parent; }

        public Node getLeft() { return left; }

        public void setLeft(Node left) { this.left = left; }

        public Node getRight() { return right; }

        public void setRight(Node right) { this.right = right; }

        public String getColorString(boolean color){ return color?"RED":"BLACK"; }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", color=" + getColorString(color) +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}
