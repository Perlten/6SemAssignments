package perlt.union_find;

import java.util.Arrays;

import perlt.union_find.IUnionFind;;

public class MyUnionFind implements IUnionFind {

  private int[] data;
  private int[] size;
  private int count;

  public MyUnionFind(int dataSize) {
    this.size = new int[dataSize];
    this.data = new int[dataSize];
    this.count = dataSize;
    for (int i = 0; i < dataSize; i++) {
      this.data[i] = i;
      this.size[i] = 1;
    }
  }

  
  /** 
   * Connect the two points if not connected already
   * @param p First node 
   * @param q Second node
   */
  @Override
  public void union(int p, int q) {
    int pRoot = find(p);
    int qRoot = find(q);
    if (pRoot == qRoot) {
      return;
    }

    if (this.size[qRoot] >= this.size[pRoot]) {
      this.data[qRoot] = pRoot;
      this.size[qRoot] += this.size[pRoot];
    } else {
      this.data[pRoot] = qRoot;
      this.size[pRoot] += this.size[qRoot];
    }
    this.count--;
  }

  
  /** 
   * Finds the root index of given node
   * @param p Node
   * @return int
   */
  @Override
  public int find(int p) {
    int ref = this.data[p];
    while (ref != this.data[ref]) {
      ref = this.data[ref];
    }
    return ref;
  }

  
  /**
   * Check if the two nodes are connected 
   * @param p First node
   * @param q Second node
   * @return boolean
   */
  @Override
  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  
  /** 
   * Returns the total count of clusters
   * @return int
   */
  @Override
  public int count() {
    return this.count;
  }

  /**
   * Prints data as pretty array
   */
  public void printData() {
    System.out.println(Arrays.toString(this.data));
  }

}