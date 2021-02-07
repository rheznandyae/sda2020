import java.io.*;
import java.math.BigInteger;
import java.util.*;

// Colaborator Naufal Adi W. , Affan Mirza Chairy , Fachri Syarifudin , Faris Muzhafar , Faris S. Ardhaffa , Haidar Labib , Ikhsan Asa Pambayun

public class Tp2 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    static class Vertex implements Comparable<Vertex> {
        public String namaToko;
        public LinkedList<Edge> jalanBiasa;
        public LinkedList<Edge> jalanEksklusif;
        public LinkedList<Edge> jalanCampur;
        public Vertex prev;

        public int scratch;
        public double costSebelum;
        public double waktuSebelum;
        public double jarak;

        public Vertex(String nama) {
            namaToko = nama;
            jalanBiasa = new LinkedList<Edge>();
            jalanEksklusif = new LinkedList<Edge>();
            jalanCampur = new LinkedList<Edge>();
            reset();
        }

        public void reset() {
            scratch = 0;
            jarak = Double.MAX_VALUE;
            costSebelum = 0;
            waktuSebelum = 0;
            prev = null;
        }

        public int compareTo(Vertex vertex) {
            double costLain = vertex.jarak;
            return jarak < costLain ? -1 : jarak > costLain ? 1 : 0;
        }

    }

    static class Edge {
        public Vertex destination;
        public int kupon;
        public int waktu;
        public int batasWaktu;
        public boolean isEks;

        public Edge(Vertex destination, int kupon, int waktu, int batasWaktu) {
            this.destination = destination;
            this.kupon = kupon;
            this.waktu = waktu;
            this.batasWaktu = batasWaktu;
            this.isEks = false;
        }

        public Edge(Vertex destination, int kupon, int batasWaktu) {
            this.destination = destination;
            this.kupon = kupon;
            this.batasWaktu = batasWaktu;
            this.waktu = 1;
            this.isEks = true;
        }

    }
    // Comparator untuk priority queue tanya biasa & Eksklusif
    static class PathWaktu implements Comparable<PathWaktu>{
        public Vertex dest;
        public double cost;

        public PathWaktu(Vertex dest, double cost){
            this.dest = dest;
            this.cost = cost;
        }

        public int compareTo( PathWaktu other){
            return (int) (other.cost - this.cost);
        }
    }

    static class PathKupon implements Comparable<PathKupon>{
        public Vertex dest;
        public double cost;

        public PathKupon(Vertex dest, double cost){
            this.dest = dest;
            this.cost = cost;
        }

        public int compareTo( PathKupon other){
            return (int) (this.cost - other.cost);
        }
    }

    static class Graph {
        public int banyakToko;
        public int banyakJalanBiasa;
        public int banyakJalanEksklusif;
        public HashMap<String, Vertex> mapToko;
        public ArrayList<Edge> jalanBiasa;
        public ArrayList<Edge> jalanEks;
        public int a = 1;
        public BigInteger pembagi = new BigInteger("1000000007");

        Graph(int banyakToko, int banyakJalanBiasa, int banyakJalanEksklusif) {
            this.banyakToko = banyakToko;
            this.banyakJalanBiasa = banyakJalanBiasa;
            this.banyakJalanEksklusif = banyakJalanEksklusif;
            mapToko = new HashMap<String, Vertex>();
            jalanBiasa = new ArrayList<Edge>();
            jalanEks = new ArrayList<Edge>();

        }

        public void addToko(String namaToko, Vertex toko) {
            mapToko.put(namaToko, toko);
        }

        public void addJalanBiasa(Vertex v1, Vertex v2, int kupon, int waktu, int batasWaktu) {
            Edge jalan1 = new Edge(v2, kupon, waktu, batasWaktu);
            Edge jalan2 = new Edge(v1, kupon, waktu, batasWaktu);
            jalanBiasa.add(jalan1);
            v2.jalanBiasa.add(jalan2);
            v1.jalanBiasa.add(jalan1);

            v2.jalanCampur.add(jalan2);
            v1.jalanCampur.add(jalan1);

            if ((kupon != 1 && a == 1) || (a != 1 && kupon < a && kupon != 1)) {
                a = kupon;
            }
        }

        public void addJalanEks(Vertex v1, Vertex v2, int kupon, int batasWaktu) {
            Edge jalan1 = new Edge(v2, kupon, batasWaktu);
            Edge jalan2 = new Edge(v1, kupon, batasWaktu);
            jalanEks.add(jalan1);
            v2.jalanEksklusif.add(jalan2);
            v1.jalanEksklusif.add(jalan1);

            v2.jalanCampur.add(jalan2);
            v1.jalanCampur.add(jalan1);

            if ((kupon != 1 && a == 1) || (a != 1 && kupon < a && kupon != 1)) {
                a = kupon;
            }
        }

        public void clearAll() {
            for (String toko : mapToko.keySet()) {
                mapToko.get(toko).reset();
            }
        }

        public String tanyaHubung(String toko1, String toko2) {
            clearAll();

            Vertex sumber = mapToko.get(toko1);
            Queue<Vertex> queue = new LinkedList<>();
            sumber.scratch = 1;

            queue.add(sumber);
            while (!queue.isEmpty()) {
                Vertex toko = queue.poll();
                for (Edge jalan : toko.jalanCampur) {
                    Vertex tujuan = jalan.destination;
                    if (tujuan.namaToko.equals(toko2)) {
                        return "YA";
                    }
                    if (tujuan.scratch != 1) {
                        queue.add(tujuan);
                        tujuan.scratch = 1;
                    }
                }
            }
            return "TIDAK";
        }

        public String tanyaKupon(String toko2, String toko1) {
            Vertex sumber = mapToko.get(toko1);
            PriorityQueue<PathKupon> pq = new PriorityQueue<PathKupon>();
            clearAll();

            pq.add(new PathKupon(sumber, 0));
            sumber.jarak = 0;

            int nodeSeen = 0;
            while (!pq.isEmpty() && nodeSeen < banyakToko) {
                PathKupon path = pq.poll();
                Vertex toko = path.dest;

                if (toko.scratch != 0) {
                    continue;
                }
                Double pangkatToko = toko.jarak;
                toko.scratch = 1;
                nodeSeen++;

                for (Edge jalur : toko.jalanCampur) {
                    Vertex w = jalur.destination;
                    double jarakBanding = Math.log10(jalur.kupon) / Math.log10(a);

                    if (w.jarak > pangkatToko + jarakBanding) {
                        w.jarak = pangkatToko + jarakBanding;
                        pq.add(new PathKupon(w, w.jarak));
                    }
                }
            }

            Vertex target = mapToko.get(toko2);

            if (target.jarak == Double.MAX_VALUE) {
                return "-1";
            }
            
            BigInteger basis = new BigInteger(Integer.toString(a));
            BigInteger hasil = (basis.pow((int) target.jarak)).mod(pembagi);
            return hasil.toString();

        }

        public int tanyaBiasa(String sumber, String tujuan) {
            PriorityQueue<PathWaktu> pq = new PriorityQueue<PathWaktu>();
            Vertex start = mapToko.get(sumber);
            Vertex tujuanAkhir = mapToko.get(tujuan);
            clearAll();
            pq.add(new PathWaktu(start, 0));

            for (Vertex vertex : mapToko.values()) {
                vertex.jarak = Integer.MIN_VALUE;
            }

            int nodeSeen = 0;
            while (!pq.isEmpty() && nodeSeen < banyakToko) {

                PathWaktu path = pq.poll();
                Vertex cek = path.dest;
                if (cek.scratch != 0) {
                    continue;
                }

                cek.scratch = 1;
                nodeSeen++;
                for (Edge edge : cek.jalanBiasa) {
                    Vertex dest = edge.destination;
                    if(dest == start){
                        continue;
                    }
                    int temp1 = edge.batasWaktu - edge.waktu;
                    int batas = start == cek ? temp1 : (int) Math.min(temp1, cek.jarak - edge.waktu);
                    if (batas > dest.jarak) {
                        dest.jarak = batas;
                        pq.add(new PathWaktu(dest, dest.jarak));
                    }
                }
            }
            System.out.println(tujuanAkhir.jarak);
            if (tujuanAkhir.jarak < 0) {
                return -1;
            }
            return (int) tujuanAkhir.jarak;
        }

        public int tanyaEks(String sumber, String tujuan) {
            PriorityQueue<PathWaktu> pq = new PriorityQueue<PathWaktu>();
            Vertex start = mapToko.get(sumber);
            Vertex tujuanAkhir = mapToko.get(tujuan);
            clearAll();
            pq.add(new PathWaktu(start, 0));

            for (Vertex vertex : mapToko.values()) {
                vertex.jarak = Integer.MIN_VALUE;
            }

            int nodeSeen = 0;
            while (!pq.isEmpty() && nodeSeen < banyakToko) {
                // pq.add(pq.poll());

                PathWaktu path = pq.poll();
                Vertex cek = path.dest;
                if (cek.scratch != 0) {
                    continue;
                }

                cek.scratch = 1;
                nodeSeen++;
                for (Edge edge : cek.jalanEksklusif) {
                    Vertex dest = edge.destination;
                    if(dest == start){
                        continue;
                    }
                    int temp1 = edge.batasWaktu - edge.waktu;
                    int batas = start == cek ? temp1 : (int) Math.min(temp1, cek.jarak - edge.waktu);
                    if (batas > dest.jarak) {
                        dest.jarak = batas;
                        pq.add(new PathWaktu(dest, dest.jarak));
                    }
                }
            }
            if (tujuanAkhir.jarak < 0) {
                return -1;
            }
            return (int) tujuanAkhir.jarak;
        }
    }

    public static void main(String[] args) {

        int n = in.nextInt();
        int m = in.nextInt();
        int e = in.nextInt();
        int q = in.nextInt();

        Graph graph = new Graph(n, m, e);

        for (int i = 0; i < n; i++) {
            String namaToko = in.next();
            graph.addToko(namaToko, new Vertex(namaToko));
        }
        for (int i = 0; i < m; i++) {
            Vertex toko1 = graph.mapToko.get(in.next());
            Vertex toko2 = graph.mapToko.get(in.next());

            int waktu = in.nextInt();
            int kupon = in.nextInt();
            int batasWaktu = in.nextInt();
            graph.addJalanBiasa(toko1, toko2, kupon, waktu, batasWaktu);
        }

        for (int i = 0; i < e; i++) {
            Vertex toko1 = graph.mapToko.get(in.next());
            Vertex toko2 = graph.mapToko.get(in.next());
            int kupon = in.nextInt();
            int batasWaktu = in.nextInt();
            graph.addJalanEks(toko1, toko2, kupon, batasWaktu);

        }
        for (int i = 2; i <= graph.a; i++) {
            Double cek = Math.log10(graph.a) / Math.log10(i);
            if (Math.ceil(cek) == Math.floor(cek)) {
                graph.a = i;
                break;
            }
        }
        for (int i = 0; i < q; i++) {
            String query = in.next();

            if (query.equals("TANYA_JALAN")) {
                int waktu = in.nextInt();
                int hitung = 0;
                for (Edge jalan : graph.jalanBiasa) {
                    if (jalan.batasWaktu > waktu) {
                        hitung++;
                    }
                }
                for (Edge jalan : graph.jalanEks) {
                    if (jalan.batasWaktu > waktu) {
                        hitung++;
                    }
                }
                out.println(hitung);

            } else if (query.equals("TANYA_HUBUNG")) {
                String toko1 = in.next();
                String toko2 = in.next();
                out.println(graph.tanyaHubung(toko1, toko2));

            } else if (query.equals("TANYA_KUPON")) {
                String toko1 = in.next();
                String toko2 = in.next();
                out.println(graph.tanyaKupon(toko1, toko2));

            } else if (query.equals("TANYA_EX")) {
                String toko1 = in.next();
                String toko2 = in.next();
                out.println(graph.tanyaEks(toko1, toko2));

            } else if (query.equals("TANYA_BIASA")) {
                String toko1 = in.next();
                String toko2 = in.next();
                out.println(graph.tanyaBiasa(toko1, toko2));
            }
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