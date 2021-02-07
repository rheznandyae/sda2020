import java.io.*;
import java.util.*;

//colaborate with Affan Mirza , Faris Sayidinarechan Ardhafa , Naufal Adi W. , Haidar Labib B.
// insert Method inspired from https://www.geeksforgeeks.org/avl-tree-set-1-insertion/

public class Lab3 { 

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        Tree stockTree = new Tree();
        Tree distanceTree = new Tree();
        int q = in.nextInt();

        for(int i = 1 ; i <= q ; i++){
            String query = in.next();

            if(query.equals("INSERT")){
                String nama = in.next();
                int stok = in.nextInt();
                int jarak = in.nextInt();

                stockTree.insert(nama, stok);
                distanceTree.insert(nama, jarak);

            }else if(query.equals("STOK_MINIMAL")){
                int stockMin = in.nextInt();
                out.println(stockTree.countMinimal(stockMin));
                
            }else if(query.equals("JARAK_MAKSIMAL")){
                int jarakMax = in.nextInt();
                out.println(distanceTree.countMaximal(jarakMax));

            }else if(query.equals("TOKO_STOK")){
                int stok = in.nextInt();
                if(stockTree.search(stok) != null){
                    out.println("true");
                }else{
                    out.println("false");
                }
                

            }else if(query.equals("TOKO_JARAK")){
                int jarak = in.nextInt();
                if(distanceTree.search(jarak) != null){
                    out.println("true");
                }else{
                    out.println("false");
                }
            }
        }
        
        out.flush();
    }
    
    // taken from https://codeforces.com/submissions/Petr
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}

class Tree {
    TreeNode root;   

    public void insert(String storeName, int value) {
        root = insertNode(root, storeName, value);
    }    

    private TreeNode insertNode(TreeNode node, String storeName, int value) {
        // TODO: implement insert node

        if(node == null){
            node = new TreeNode(storeName, value, null, null);
            return node;
        }else if(value < node.value){
            node.left = insertNode(node.left, storeName, value);
            node.setLeftCount(node.getLeftCount()+1);
            if(getBalance(node) > 1){
                if(value < node.left.value){
                    node = leftRotate(node);
                }else{
                    node = doubleleftRotate(node);
                }
            }
            
        }else if(value > node.value){
            node.right = insertNode(node.right, storeName, value);
            node.setRightCount(node.getRightCount()+1);
            if( getBalance(node) > 1){
                if(value > node.right.value){
                    node = rightRotate(node);
                }else{
                    node = doublerightRotate(node);
                }
            }
            
        }
        
        node.setHeight(max(height(node.left), height(node.right)) + 1);
        
        return node;
    }    

    private TreeNode rightRotate(TreeNode y) { 
        // TODO: implement right rotation

        TreeNode k1 = y.right;
        y.right = k1.left;
        k1.left = y;

        y.setHeight(Math.max(height(y.left), height(y.right)) + 1);
        k1.setHeight(Math.max(height(k1.left), height(k1.right)) + 1);

        y.setRightCount(k1.getLeftCount());
        k1.setLeftCount(y.getTotalCount());


        return k1;
    } 

    private TreeNode doublerightRotate(TreeNode y) { 
        // TODO: implement right rotation

        y.right = leftRotate(y.right);
        return rightRotate(y);
    } 
  
    private TreeNode leftRotate(TreeNode x) { 
        // TODO: implement left rotation

        TreeNode k1 = x.left;
        x.left = k1.right;
        k1.right = x;

        x.setHeight(Math.max(height(x.left), height(x.right)) + 1);
        k1.setHeight(Math.max(height(k1.left), height(k1.right)) + 1);

        x.setLeftCount(k1.getRightCount());
        k1.setRightCount(x.getTotalCount());
        return k1;
    }
    private TreeNode doubleleftRotate(TreeNode x) { 
        // TODO: implement left rotation
        x.left = rightRotate(x.left);
        return leftRotate(x);
    }

    public TreeNode search(int value) {
        // TODO: implement search node

        TreeNode nodeSekarang = root;
        while(nodeSekarang != null ){
            if(nodeSekarang.value > value){
                nodeSekarang = nodeSekarang.left;
            }else if(nodeSekarang.value < value){
                nodeSekarang = nodeSekarang.right;
            }else{
                return nodeSekarang;
            }
        }

        return null;
    }

    public boolean exists(int value) {
        return search(value) != null;
    } 

    public int countMinimal(int min) {
        return this.root.countMinimal(min);
    }

    public int countMaximal(int max) {
        return this.root.countMaximal(max);
    }

    // Utility function to get height of node
    private int height(TreeNode n) { 
        return n == null ? 0 : n.height;
    }
    
    // Utility function to get max between two values
    private int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 

    // Utility function to get balance factor of node
    private int getBalance(TreeNode N) { 
        if (N == null) 
            return 0; 
  
        return Math.abs(height(N.left) - height(N.right)) ; 
    }
}

class TreeNode {
    String storeName;
    int value;
    TreeNode left;
    TreeNode right;
    int leftCount;
    int rightCount;
    int height;
    
    public TreeNode(String storeName, int value, TreeNode left, TreeNode right){
        this.left = left;
        this.right = right;
        this.storeName = storeName;
        this.value = value;
        this.height = 1;


    }

    public int countMinimal(int min) {
        // TODO: get count of nodes with at least value min recursively

        if(this.value < min){
            if(this.right != null){
                return this.right.countMinimal(min);
            }else{
                return 0;
            } 
        }else{
            if(this.left != null){
                return this.left.countMinimal(min) + this.getRightCount() + 1 ;
            }else{
                return this.getRightCount() + 1;
            }
            
        }
    }

    public int countMaximal(int max) {
        // TODO: get count of nodes with at most value max recursively


        if(this.value > max){
            if(this.left != null){
                return this.left.countMaximal(max);
            }else{
                return 0;
            }
        }else{
            if(this.right != null){
                return this.right.countMaximal(max) + this.getLeftCount() + 1;
            }else{
                return this.getLeftCount() + 1;
            }
            
        }

    }

    public int getTotalCount() {
        return this.leftCount + this.rightCount + 1;
    }

    public int getLeftCount() {
        return this.leftCount;
    }

    public int getRightCount() {
        return this.rightCount;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setLeftCount(int count) {
        this.leftCount = count;
    }

    public void setRightCount(int count) {
        this.rightCount = count;
    }
}
