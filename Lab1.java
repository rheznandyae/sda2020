import java.io.*;
import java.util.*;

// colaborator: Haidar Labib , Johanes Jason, Naufal Adi Wijanarko, Affan Mirza chairy, Faris Sayidinarechan Ardhafa, Bintang Samudro

public class Lab1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {

        int l = in.nextInt();
        int n = in.nextInt();
        int q = in.nextInt();
//array tembok
        int[] arr = new int[l];
//array Map temboknya
        int[] arrMapTembok = new int[l+1];

        for(int i = 0; i < n ; i++){
            int a = in.nextInt();
            int b = in.nextInt();
            int k = in.nextInt();

            arrMapTembok[a-1] += k;
            arrMapTembok[b] -= k;
        }
//pembuatan tembok
        int countbatu = 0;
        for(int i = 0 ; i < l ; i++){
            countbatu += arrMapTembok[i];
            arr[i] += countbatu;
        }
        
// array jawaban
        int[] min = new int[l];
        int[] max = new int[l];
// Menghitung awal air
        for(int index = 0; index < l ; index++){
            if(index == 0){
                min[index] = index+1;
                continue;
            }
            if(arr[index] >= arr[index-1]){
                min[index] = min[index-1];
            }
            if(arr[index] < arr[index-1]){
                min[index] = index+1;
            }
        }
// Menghitung akhir air
        for(int index = l-1; index >= 0 ; index--){
            if(index == l-1){
                max[index] = l;
                continue;
            }
            if(arr[index+1] <= arr[index]){
                max[index] = max[index+1];
            }
            if(arr[index+1] > arr[index] ){
                max[index] = index + 1;
            }
        }

        for(int i = 0 ; i < q ; i++){
            int inputWater = in.nextInt();
            out.println("" + min[inputWater-1] + " " + max[inputWater-1]);
        }
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