package perlt;


import perlt.union_find.MyUnionFind;;

public class Main {
  
  public static void main(String[] args) {
    MyUnionFind mu = new MyUnionFind(10);

    mu.union(1, 9);
    mu.union(2, 3);
    mu.union(2, 6);
    mu.union(2, 8);
    mu.union(1, 6);
    mu.union(7, 8);
    mu.union(7, 4);
    mu.union(0, 4);
    mu.union(0, 5);
    
    // System.out.println(mu.find(7));
    // System.out.println(mu.connected(1, 5));
  }
}
