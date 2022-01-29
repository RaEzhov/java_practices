/**
 * Class with matrices of complex numbers
 * @author RaEzhov
 */
public class ComplexMatrix {
    /**
     * Matrix of complex numbers
     */
    private Complex[][] matrix;
    /**
     * Amount of rows
     */
    private int rows;
    /**
     * Amount of columns
     */
    private int cols;

    /**
     * Default constructor
     * Makes empty matrix with size 0 x 0
     */
    public ComplexMatrix() {
        rows = 0;
        cols = 0;
        matrix = new Complex[rows][cols];
    }

    /**
     * Constructor that create matrix with size rows x columns
     * Each element is initialized with a default constructor
     *
     * @param rows amount of rows
     * @param cols amount of columns
     */
    public ComplexMatrix(int rows, int cols) {
        if (rows == 0 && cols == 0) {
            throw new RuntimeException("Amount of rows and columns are equal to zero! Use default c-tor to make such matrix.");
        }
        if (rows <= 0) {
            throw new RuntimeException("Amount of rows less than zero!");
        }
        if (cols <= 0) {
            throw new RuntimeException("Amount of cols less than zero!");
        }
        this.rows = rows;
        this.cols = cols;
        matrix = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Complex();
            }
        }
    }

    /**
     * Method that prints matrix in algebraic form
     */
    public void print_matrix() {
        int str_len;
        String tmp;
        for (int i = 0; i < rows; i++) {
            str_len = 0;
            for (int j = 0; j < cols; j++) {
                tmp = matrix[i][j].to_string();
                System.out.print(tmp + " | ");
                str_len += tmp.length() + 3;
            }
            System.out.println();
            for (int j = 0; j < str_len; j++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    /**
     * Addition
     * Store the result in this
     *
     * @param rhs second addendum
     */
    public void add(ComplexMatrix rhs) {
        if (rows != rhs.rows || cols != rhs.cols) {
            throw new RuntimeException("Matrix sizes are not equal! Addition impossible.");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j].add(rhs.get_element(i, j));
            }
        }
    }

    /**
     * Subtraction
     * Store the result in this
     *
     * @param rhs second subtrahend
     */
    public void mul(ComplexMatrix rhs) {
        if (cols != rhs.rows) {
            throw new RuntimeException("Left cols is not equal to right rows! Multiplication impossible.");
        }
        ComplexMatrix res = new ComplexMatrix(rows, rhs.cols);
        Complex elem = new Complex();
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                elem.set(new Complex(0,0));
                for (int k = 0; k < cols; k++) {
                    Complex tmp = new Complex(matrix[i][k]);
                    tmp.mul(rhs.matrix[k][j]);
                    elem.add(tmp);
                }
                res.set_element(i, j, elem);
            }
        }
        matrix = res.matrix;
        rows = res.rows;
        cols = res.cols;
    }

    /**
     * Method that transposes a matrix
     * Store the result in this
     */
    public void transpose() {
        ComplexMatrix res = new ComplexMatrix(cols, rows);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                res.set_element(i, j, matrix[j][i]);
            }
        }
        matrix = res.matrix;
        rows = res.rows;
        cols = res.cols;
    }

    /**
     * Calculate determinant of matrix
     *
     * @return determinant
     */
    public Complex det() {
        if (rows != cols) {
            throw new RuntimeException("It is impossible to calculate the determinant. The matrix is not square.");
        }

        if (cols == 0) {
            throw new RuntimeException("Matrix is empty");
        }

        if (cols == 1) {
            return matrix[0][0];
        }

        Complex res = new Complex(matrix[0][0]);
        Complex tmp = new Complex(matrix[0][1]);
        if (cols == 2) {
            res.mul(matrix[1][1]);
            tmp.mul(matrix[1][0]);
            res.sub(tmp);
            return res;
        }
        res.set_re(0);
        res.set_im(0);
        tmp.set_re(0);
        tmp.set_im(0);
        for (int i = 0; i < rows; i++) {
            ComplexMatrix tmp_mat = new ComplexMatrix(rows - 1, cols - 1);
            int tmpR = 0, tmpC;
            for (int row = 0; row < rows; row++) {
                if (row == i) {
                    continue;
                }
                tmpC = 0;
                for (int col = 1; col < cols; col++) {
                    tmp_mat.set_element(tmpR, tmpC, matrix[row][col]);
                    tmpC++;
                }
                tmpR++;
            }
            tmp.set(matrix[i][0]);
            tmp.mul(tmp_mat.det());
            tmp.mul(new Complex(Math.pow(-1, i)));
            res.add(tmp);
        }
        return res;
    }

    /**
     * Private method that check indexes for set_element and get_element
     *
     * @param row to test
     * @param col to test
     */
    private void check_indexes(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            throw new RuntimeException("Invalid indexes!");
        }
    }

    /**
     * Method that set value of element [row][col]
     *
     * @param row  row to set
     * @param col  col to set
     * @param elem element to set
     */
    public void set_element(int row, int col, Complex elem) {
        check_indexes(row, col);
        matrix[row][col].set(elem);
    }

    /**
     * Method that returns element [row][col]
     *
     * @param row row to get
     * @param col col to get
     * @return element
     */
    public Complex get_element(int row, int col) {
        check_indexes(row, col);
        return matrix[row][col];
    }
}