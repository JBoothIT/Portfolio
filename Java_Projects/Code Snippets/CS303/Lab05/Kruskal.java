package Lab05;

import Libs.In;
import Libs.StdOut;

public class Kruskal 
{
    private double weight; 
    private Queue<Edge> mst = new Queue<Edge>(); 

   
    public Kruskal(EdgeWeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<Edge>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) { 
                uf.union(v, w);  
                mst.enqueue(e); 
                weight += e.weight();
            }
        }

       
        assert check(G);
    }

   
    public Iterable<Edge> edges() {
        return mst;
    }

   
    public double weight() {
        return weight;
    }
    
    private boolean check(EdgeWeightedGraph G) {

     
        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        double EPSILON = 1E-12;
        if (Math.abs(total - weight()) > EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
            return false;
        }

    
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

   
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

 
        for (Edge e : edges()) {
            int v = e.either();

          
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }
            
     
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        double start = System.currentTimeMillis();
        double st = System.nanoTime();
        Kruskal mst = new Kruskal(G);
        double quit = System.currentTimeMillis() - start;
        double stop = System.nanoTime() - st;
        for (Edge e : mst.edges()) 
        {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
        StdOut.println("Sec: " + quit / 1000);
        StdOut.println("Milli: " + quit);
        StdOut.println("Nano: " + stop);
    }

}


