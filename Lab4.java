import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Lab4 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    static class HashTable {
        public ArrayList<LinkedList<String>> listSubstring = new ArrayList<LinkedList<String>>();
        
        public HashTable(int y, String p) {
            for (int i = 0; i < y; i++) {
                listSubstring.add(new LinkedList<String>());
            }
        }

        public void addKata(String kata, int index) {
            LinkedList<String> linkedListkata = listSubstring.get(index);
            if (linkedListkata.size() == 0 || !linkedListkata.contains(kata)) {
                linkedListkata.addLast(kata);

            }
        }
    }

    //method untuk menghitung pangkat (x^i)mody
    //inspired from https://www.geeksforgeeks.org/
    public static int pangkatxmody(int pangkat, int x, int y, int nilaiSubstr) {
        BigInteger substrBig = BigInteger.valueOf(nilaiSubstr);
        BigInteger result = new BigInteger("1");
        BigInteger nilaiy = BigInteger.valueOf(y);
        BigInteger nilaix = BigInteger.valueOf(x % y);

        while (pangkat > 0) {
            if (pangkat % 2 != 0) {
                result = (result.multiply(nilaix)).mod(nilaiy);
            }
            pangkat = pangkat / 2;
            nilaix = (nilaix.multiply(nilaix)).mod(nilaiy);

        }
        result = result.mod(nilaiy);

        int nilaiPerKata = (result.multiply(substrBig)).mod(nilaiy).intValue();

        return nilaiPerKata;
    }
//untuk mencari nilai F(S) dari tiap substr
    public static int cariNilai(String kata, int x, int y) {
        int hasil = 0;

        for (int i = 0; i < kata.length(); i++) {
            int nilaiSubstr = ((int) kata.charAt(i) - 96) % y;
            int nilaiPerKata = pangkatxmody(i, x, y, nilaiSubstr);
            hasil += nilaiPerKata;

        }

        hasil = hasil % y;
        return hasil;
    }
//untuk menghitung banyak kombinasi
    public static int combinationCounter(int n) {
        int hasil = n * (n - 1) / 2;
        return hasil;
    }

    public static void main(String[] args) {
        int x = in.nextInt();
        int y = in.nextInt();
        String p = in.next();

        HashTable table = new HashTable(y, p);

        for (int i = 0; i < p.length(); i++) {
            for (int j = i + 1; j <= p.length(); j++) {
                String substr = p.substring(i, j);
                int index = cariNilai(substr, x, y);
                table.addKata(substr, index);
            }
        }

        int banyakKemungkinan = 0;

        for (int i = 0; i < y; i++) {
            LinkedList<String> linkedListKata = table.listSubstring.get(i);
            if (linkedListKata.size() < 2) {
                continue;
            }

            banyakKemungkinan += combinationCounter(linkedListKata.size());
        }
        out.println(banyakKemungkinan);
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