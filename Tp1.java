import java.io.*;
import java.util.*;

// colaborate : Raihan Rabbanii Rifaii, Muhammad Ikhsan Asa Pambayun , Naufal Adi W. , Affan Mirza Chairy, Faris Ardhaffa, Haidar Labib B. , Faris Muzhaffar

class Tp1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static int[] ladang;
    public static HashMap<String, int[]> mapKeranjang;
    public static int n; // banyak ladang
    public static int m; // banyak keranjang
    public static ArrayList<Object[]> arrKeranjang = new ArrayList<Object[]>();

    public static void query(String[] task) {

        if (task[1].equals("ADD")) {
            if (!mapKeranjang.containsKey(task[2])) {
                int[] badData = new int[] { Integer.parseInt(task[3]), Integer.parseInt(task[4]) };
                int angka = keranjangTerbaik(badData);
                int[] data = new int[] { Integer.parseInt(task[3]), Integer.parseInt(task[4]), angka };
                mapKeranjang.put(task[2], data);

                m += 1;
            }
        }
        if (task[1].equals("SELL")) {
            if (mapKeranjang.containsKey(task[2])) {
                mapKeranjang.remove(task[2]);
                m -= 1;
            }
        }
        if (task[1].equals("UPDATE")) {
            if (mapKeranjang.containsKey(task[2])) {
                int[] badData = new int[] { Integer.parseInt(task[3]), Integer.parseInt(task[4]) };
                int angka = keranjangTerbaik(badData);
                int[] val = new int[] { Integer.parseInt(task[3]), Integer.parseInt(task[4]), angka };
                mapKeranjang.replace(task[2], val);
            }
        }
        if (task[1].equals("RENAME")) {
            if (mapKeranjang.containsKey(task[2]) && !mapKeranjang.containsKey(task[3])) {
                int[] val = mapKeranjang.get(task[2]);
                mapKeranjang.remove(task[2]);
                mapKeranjang.put(task[3], val);
            }
        }
    }

    // memilih nilai terbaik dari keranjang
    public static int keranjangTerbaik(int[] arr) {
        int[][] matriksLadang = new int[n][n];

        int maxPanen = 0;

        for (int i = 0; i < n; i++) {

            int maksKeranjang = arr[0] + (i * arr[1]);

            for (int j = 0; j < n; j++) {
                int panen = 0;
                if (i == 0) {
                    if (j == 0) {
                        panen = ladang[0];
                        if (panen <= maksKeranjang) {
                            matriksLadang[j][i] = panen;
                        } else {
                            matriksLadang[j][i] = maksKeranjang;
                        }
                    } else {
                        panen = matriksLadang[j - 1][i] + ladang[j];
                        if (panen <= maksKeranjang) {
                            matriksLadang[j][i] = panen;
                        } else {
                            matriksLadang[j][i] = maksKeranjang;
                        }
                    }
                } else if (j < i) {
                    matriksLadang[j][i] = 0;

                } else {
                    int temp1 = 0;

                    panen = matriksLadang[j - 1][i] + ladang[j];
                    if (panen <= maksKeranjang) {
                        temp1 = panen;
                    } else {
                        temp1 = maksKeranjang;
                    }

                    int temp2 = matriksLadang[j - 1][i - 1];

                    matriksLadang[j][i] = Math.max(temp1, temp2);
                }
            }

            if (maxPanen < matriksLadang[n - 1][i]) {
                maxPanen = matriksLadang[n - 1][i];
            }
        }
        return maxPanen;
    }

    // Modified Quicksort Algorithm inspired from www.Geeksforgeek.com
    public static int partition(ArrayList<Object[]> arr, int low, int high) {
        int pivot = (int) arr.get(high)[1];
        String name = (String) arr.get(high)[0];
        int i = (low - 1);
        for (int j = low; j < high; j++) {

            if ((int) arr.get(j)[1] > pivot) {
                i++;

                Object[] temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
            if ((int) arr.get(j)[1] == pivot) {
                String current = (String) arr.get(j)[0];
                if (current.compareTo(name) < 0) {
                    i++;

                    Object[] temp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, temp);
                }
            }
        }

        Object[] temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);
        return i + 1;
    }

    public static void sort(ArrayList<Object[]> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) throws IOException {
        Tp1.n = in.nextInt();
        ladang = new int[n];

        for (int i = 0; i < n; i++) {
            ladang[i] = in.nextInt();
        }

        m = in.nextInt(); // banyak keranjang

        mapKeranjang = new HashMap<String, int[]>();

        for (int i = 0; i < m; i++) {
            String s = in.next();
            int[] data = new int[3];
            data[0] = in.nextInt();
            data[1] = in.nextInt();
            data[2] = 0;

            int hasil = keranjangTerbaik(data);
            data[2] = hasil;
            mapKeranjang.put(s, data);
        }
        Queue<String> customerQueue = new LinkedList<String>();
        Queue<String> taskPerDay = new LinkedList<String>();
        Queue<String> customerPerDay = new LinkedList<String>();

        int h = in.nextInt(); // lama ladang di panen

        for (String namaKeranjang : mapKeranjang.keySet()) {
            Object[] isi = new Object[] { namaKeranjang, mapKeranjang.get(namaKeranjang)[2] };
            arrKeranjang.add(isi);
        }

        sort(arrKeranjang, 0, m - 1);

        out.println("Hari ke-1:");
        out.println("Hasil Panen");
        for (int i = 0; i < m; i++) {
            out.println("" + arrKeranjang.get(i)[0] + " " + arrKeranjang.get(i)[1]);
        }
        out.println(" ");
        // Loop hari kedua s/d seterusnya
        for (int i = 0; i < h - 1; i++) {
            String izuri = in.reader.readLine();
            if (izuri.equals("")) {
                izuri = in.reader.readLine();
                izuri = "IZURI " + izuri;
            }
            if (i == 0) {
                izuri = "IZURI " + izuri;
            }

            int y = in.nextInt();
            for (int j = 0; j < y; j++) {
                String customer = in.reader.readLine();
                customerQueue.add(customer);
            }

            int o = in.nextInt();

            for (int j = 0; j < o; j++) {
                taskPerDay.add(customerQueue.poll());
            }

            taskPerDay.add(izuri);

            for (int j = 0; j < o + 1; j++) {
                String[] task = taskPerDay.poll().split(" ");
                customerPerDay.add(task[0]);
                query(task);
            }

            arrKeranjang.clear();
            for (String namaKeranjang : mapKeranjang.keySet()) {
                Object[] isi = new Object[] { namaKeranjang, mapKeranjang.get(namaKeranjang)[2] };
                arrKeranjang.add(isi);
            }

            sort(arrKeranjang, 0, arrKeranjang.size() - 1);
            out.println("Hari ke-" + (i + 2) + ":");
            out.println("Permintaan yang dilayani");

            for (int j = 0; j < o + 1; j++) {
                out.print(customerPerDay.poll() + " ");
            }
            out.println("\nHasil Panen");

            for (Object[] panen : arrKeranjang) {
                out.println("" + panen[0] + " " + panen[1]);
            }

            out.println("");
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