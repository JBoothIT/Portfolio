package Lab05;
import Libs.*;
import Libs.In;
public class LazyPrimMST {
    private double weight;       
    private Queue<Edge> mst;     
    private boolean[] marked;   
    private MinPQ<Edge> pq;     

    public LazyPrimMST(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        pq = new MinPQ<Edge>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)  
            if (!marked[v]) prim(G, v);  

        assert check(G);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        scan(G, s);
        while (!pq.isEmpty()) {                      
            Edge e = pq.delMin();                     
            int v = e.either(), w = e.other(v);       
            assert marked[v] || marked[w];
            if (marked[v] && marked[w]) continue;   
            mst.enqueue(e);                       
            weight += e.weight();
            if (!marked[v]) scan(G, v);            
            if (!marked[w]) scan(G, w);    
        }
    }

   
    private void scan(EdgeWeightedGraph G, int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge e : G.adj(v))
            if (!marked[e.other(v)]) pq.insert(e);
    }
        
   
    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    private boolean check(EdgeWeightedGraph G) {

        
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        double EPSILON = 1E-12;
        if (Math.abs(totalWeight - weight()) > EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
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
    
    
    public static void main(String[] args) 
    {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        double start = System.nanoTime();
        double st = System.currentTimeMillis();
        LazyPrimMST mst = new LazyPrimMST(G);
        double stop = System.nanoTime() - start;
        double quit = System.currentTimeMillis() - st;
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

