/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

public class test {

    public static void main(String[] args) {
        int N = 10;
        QuickFindUF uf = new QuickFindUF(N);

        uf.union(1,5);
        uf.union(4,5);
        uf.union(3,8);
        uf.union(7,9);

        QuickUnionUF ufQ = new QuickUnionUF(N);

        ufQ.union(1,5);
        ufQ.union(4,5);
        ufQ.union(3,8);
        ufQ.union(7,9);

    }

    public static class QuickFindUF
    {
        public int[] id;
        public QuickFindUF(int N)
        {
            id = new int[N];
            for (int i = 0; i < N; i++)
                id[i] = i;
        }
        public boolean connected(int p, int q)
        { return id[p] == id[q]; }
        public void union(int p, int q)
        {
            int pid = id[p];
            int qid = id[q];
            for (int i = 0; i < id.length; i++)
                if (id[i] == pid) id[i] = qid;
        }
    }

    public static class QuickUnionUF
    {
        public int[] id;
        public QuickUnionUF(int N)
        {
            id = new int[N];
            for (int i = 0; i < N; i++) id[i] = i;
        }
        private int root(int i)
        {
            while (i != id[i]) i = id[i];
            return i;
        }
        public boolean connected(int p, int q)
        {
            return root(p) == root(q);
        }
        public void union(int p, int q)
        {
            int i = root(p);
            int j = root(q);
            id[i] = j;

        }
    }

    public static class QuickUnionUFWeighted
    {
        public int[] id;
        public int[] sz;
        public QuickUnionUFWeighted(int N)
        {
            id = new int[N];
            sz = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
            }
        }
        private int root(int i)
        {
            while (i != id[i]) {
                id[i] = id[id[i]];
                i = id[i];
            }
            return i;
        }

        public boolean connected(int p, int q)
        {
            return root(p) == root(q);
        }

        public void union(int p, int q)
        {
            int i = root(p);
            int j = root(q);
            if (i == j) return;
            if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
            else { id[j] = i; sz[i] += sz[j]; }

        }
    }
}
