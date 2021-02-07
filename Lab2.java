import java.io.*;
import java.util.*;

// colaborate : Affan Mirza chairy , Naufal Adi W.
class Node 
{ 
    Character data; 
    Node next;
    Node previous;
    Node(Character huruf) 
    { 
        data = huruf; 
        next = null;
        previous = null;
    }
    Node(){
        data = null; 
        next = null;
        previous = null;
    } 
} 
  
class DoubleLinkedList {

    Node head = new Node();
    Node pointer1 = new Node();
    Node pointer2 = new Node();
  
    public void push(char data){ 
        Node new_Node = new Node(data); 
        new_Node.next = head.next; 
        head.next = new_Node;
        if(new_Node.next != null){
            new_Node.next.previous = new_Node;
        }

        pointer1.next = head.next;
        pointer2.next = head.next;
    }

    public void kanan1(){
        pointer1.previous = pointer1.next;
        pointer1.next = pointer1.next.next;
        
    }
    public void kanan2(){
        pointer2.previous = pointer2.next;
        pointer2.next = pointer2.next.next;
   
    }
    public void kiri1(){
        pointer1.next = pointer1.previous;
        pointer1.previous = pointer1.previous.previous;
        
    }
    public void kiri2(){
        pointer2.next = pointer2.previous;
        pointer2.previous = pointer2.previous.previous;
        
    }
    public void hapus1(){
        boolean flag = false;
        Node yangDiHapus = pointer1.previous;
        if(pointer1.previous == pointer2.previous || pointer1.next == pointer2.next){
                flag = true;
        }
        if(pointer1.next != null){
            pointer1.next.previous = pointer1.previous.previous;
            if(pointer1.previous.previous != null){
                pointer1.previous.previous.next = pointer1.next;
            }else{
                head.next = pointer1.next;
            }
            if(flag){
                pointer2.previous = pointer1.previous.previous;
                pointer2.next = pointer1.next;
            }
            pointer1.previous = pointer1.previous.previous;
        }else{
            if(pointer1.previous.previous != null){
                pointer1.previous.previous.next = pointer1.next;
            }else{
                head.next = pointer1.next;
            }
            if(flag){
                pointer2.previous = pointer1.previous.previous;
                pointer2.next = pointer1.next;
            }
            pointer1.previous = pointer1.previous.previous;
        }
        if(pointer2.next == yangDiHapus){
            pointer2.next = pointer1.next;
        }
        if(pointer2.previous == yangDiHapus){
            pointer2.previous = pointer1.previous;
        }
    }
    public void hapus2(){
        boolean flag = false;
        Node yangDiHapus = pointer2.previous;
        if(pointer1.previous == pointer2.previous || pointer1.next == pointer2.next){
            flag = true;
        }
        if(pointer2.next != null){
            pointer2.next.previous = pointer2.previous.previous;
            if(pointer2.previous.previous != null){
                pointer2.previous.previous.next = pointer2.next;
            }else{
                head.next = pointer2.next;
            }
            if(flag){
                pointer1.previous = pointer2.previous.previous;
                pointer1.next = pointer2.next;
            }
            pointer2.previous = pointer2.previous.previous;
        }else{
            if(pointer2.previous.previous != null){
                pointer2.previous.previous.next = pointer2.next;
            }else{
                head.next = pointer2.next;
            }
            if(flag){
                pointer1.previous = pointer2.previous.previous;
                pointer1.next = pointer2.next;
            }
            pointer2.previous = pointer2.previous.previous;
        }

        if(pointer1.next == yangDiHapus){
            pointer1.next = pointer2.next;
        }
        if(pointer1.previous == yangDiHapus){
            pointer1.previous = pointer2.previous;
        }
    }
    
    public void tulis1(char huruf){
        Node huruf_baru = new Node(huruf);

        if(pointer1.previous != null){
            if(pointer1.next != null){
                pointer1.previous.next = huruf_baru;
                pointer1.next.previous = huruf_baru;
                huruf_baru.next = pointer1.next;
                huruf_baru.previous = pointer1.previous;
            }else{
                pointer1.previous.next = huruf_baru;
                huruf_baru.next = null;
                huruf_baru.previous = pointer1.previous;
            }
        }else{
            head.next = huruf_baru;
            if(pointer1.next != null){
                pointer1.next.previous = huruf_baru;
                huruf_baru.next = pointer1.next;
            }
        }
        if(pointer1.previous == pointer2.previous){
            pointer2.previous = huruf_baru;
        }
        pointer1.previous = huruf_baru;
    }
    public void tulis2(char huruf){
        Node huruf_baru = new Node(huruf);

        if(pointer2.previous != null){
            if(pointer2.next != null){
                pointer2.previous.next = huruf_baru;
                pointer2.next.previous = huruf_baru;
                huruf_baru.next = pointer2.next;
                huruf_baru.previous = pointer2.previous;
            }else{
                pointer2.previous.next = huruf_baru;
                huruf_baru.next = null;
                huruf_baru.previous = pointer2.previous;
            }
        }else{
            head.next = huruf_baru;
            if(pointer2.next != null){
                pointer2.next.previous = huruf_baru;
                huruf_baru.next = pointer2.next;
            }
        }
        if(pointer1.previous == pointer2.previous){
            pointer1.previous = huruf_baru;
        }
        pointer2.previous = huruf_baru;
    }

    public void swap(){ 
        char temp = pointer1.previous.data;
        pointer1.previous.data = pointer2.previous.data;
        pointer2.previous.data = temp;
    }

    public String printjawaban(){
        Node nodeData = head.next;
        StringBuilder str = new StringBuilder();
        while(nodeData != null){
            str.append(nodeData.data);
            nodeData = nodeData.next;
        }
        return str.toString();
    }
}


public class Lab2{
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {

        DoubleLinkedList lstkata = new DoubleLinkedList();

        String kata = in.next();
        for(int i = kata.length()-1 ; i >= 0 ; i--){
            lstkata.push(kata.charAt(i));
        }
        
        int loop = in.nextInt();
        for(int i = 0 ; i < loop ; i++){
            String query = in.next();
            if(query.equals("GESER_KANAN")){
                String pointer = in.next();
                if(pointer.equals("1")){
                    lstkata.kanan1();
                }else{
                    lstkata.kanan2();
                }
                continue;
            }
            if(query.equals("GESER_KIRI")){
                String pointer = in.next();
                if(pointer.equals("1")){
                    lstkata.kiri1();
                }else{
                    lstkata.kiri2();
                }
                continue;
            }
            if(query.equals("TULIS")){
                String pointer = in.next();
                if(pointer.equals("1")){
                    Character huruf = in.next().charAt(0);
                    lstkata.tulis1(huruf);
                }else{
                    Character huruf = in.next().charAt(0);
                    lstkata.tulis2(huruf);
                }
                continue;
            }
            if(query.equals("HAPUS")){
                String pointer = in.next();
                if(pointer.equals("1")){
                    lstkata.hapus1();
                }else{
                    lstkata.hapus2();
                }
                continue;
            }
            if(query.equals("SWAP")){
                lstkata.swap();
                continue;
            }
        }
        out.print(lstkata.printjawaban());
        out.close();
    }

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
