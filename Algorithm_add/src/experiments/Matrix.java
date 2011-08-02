package experiments;

/**
 * 矩阵操作类
 * @author GEO
 *
 */
final public class Matrix {
	/**
	 * 行
	 */
    private final int M;             // number of rows
    
    /**
     * 列
     */
	private final int N;             // number of columns
    /**
     * 数据
     */
	public final double[][] data;   // M-by-N array

	
	
    /**
     * 新建矩阵  create M-by-N matrix of 0's
     * @param M 行
     * @param N 列
     */
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }
    
    /**
     * create matrix based on 2d array
     * @param data 数据
     */
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                    this.data[i][j] = data[i][j];
    }
    
    /**
     * 获得行
     * @return 行
     */
    public int getM() {
		return M;
	}

    /**
     * 获取列
     * @return 列
     */
    public int getN() {
		return N;
	}
    
    /**
     * 获取数据
     * @return 数据
     */
    public double[][] getData() {
		return data;
	}


    /**
     * 复制
     * @param A 矩阵
     */
    private Matrix(Matrix A) { this(A.data); }

    /**
     *  create and return a random M-by-N matrix with values between 0 and 1
     * @param M 行
     * @param N 列
     * @return  矩阵
     */
     
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = Math.random();
        return A;
    }

    // create and return the N-by-N identity matrix
    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; i++)
            I.data[i][i] = 1;
        return I;
    }
    
    // create and return a random M-by-N matrix with value = 0
    public static Matrix zeros(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = 0;
        return A;
    }

    // swap rows i and j
    private void swap(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     *  create and return the transpose of the invoking matrix
     * @return 矩阵
     */
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    /**
     *  return C = A + B
     * @param B 矩阵
     * @return 完成加法后的矩阵
     */
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }


    /**
     *  return C = A - B
     * @param B 矩阵
     * @return 完成减法后的矩阵
     */
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }

    /**
     *  does A = B exactly?
     * @param B 矩阵
     * @return A=B返回true
     */
    public boolean eq(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (A.data[i][j] != B.data[i][j]) return false;
        return true;
    }

    /**
     *  return C = A * B
     * @param B 矩阵
     * @return 完成乘法后的矩阵
     */
    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }


    /**
     *  return x = A^-1 b, assuming A is square and has full rank
     * @param rhs 矩阵
     * @return
     */
    public Matrix solve(Matrix rhs) {
        if (M != N || rhs.M != N || rhs.N != 1)
            throw new RuntimeException("Illegal matrix dimensions.");

        // create copies of the data
        Matrix A = new Matrix(this);
        Matrix b = new Matrix(rhs);

        // Gaussian elimination with partial pivoting
        for (int i = 0; i < N; i++) {

            // find pivot row and swap
            int max = i;
            for (int j = i + 1; j < N; j++)
                if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
                    max = j;
            A.swap(i, max);
            b.swap(i, max);

            // singular
            if (A.data[i][i] == 0.0) throw new RuntimeException("Matrix is singular.");

            // pivot within b
            for (int j = i + 1; j < N; j++)
                b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];

            // pivot within A
            for (int j = i + 1; j < N; j++) {
                double m = A.data[j][i] / A.data[i][i];
                for (int k = i+1; k < N; k++) {
                    A.data[j][k] -= A.data[i][k] * m;
                }
                A.data[j][i] = 0.0;
            }
        }

        // back substitution
        Matrix x = new Matrix(N, 1);
        for (int j = N - 1; j >= 0; j--) {
            double t = 0.0;
            for (int k = j + 1; k < N; k++)
                t += A.data[j][k] * x.data[k][0];
            x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
        }
        return x;
   
    }

    /**
     *  print matrix to standard output<br>
     *  显示信息
     */
    public void show() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) 
                System.out.printf("%9.4f ", data[i][j]);
            System.out.println();
        }
    }

}


