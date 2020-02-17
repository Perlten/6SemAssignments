package perlt.union_find;

interface IUnionFind {
  void union(int p, int q);

  int find(int p);

  boolean connected(int p, int q);

  int count();
}