import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class of complex numbers
 * @author RaEzhov
 */
public class Complex {
    /**
     * Real part of complex number
     */
    private double real;
    /**
     * Imaginary part of complex number
     */
    private double imaginary;
    /**
     * Epsilon is number which comparable with zero
     * Used to compare double with zero
     */
    private static final double epsilon = 0.000000001;

    /**
     * Default constructor
     */
    public Complex() {
        real = 0;
        imaginary = 0;
    }

    /**
     * Constructor that takes only a real part of complex number
     *
     * @param real real part
     */
    public Complex(double real) {
        this.real = real;
        imaginary = 0;
    }

    /**
     * Constructor that takes Complex number
     *
     * @param rhs Complex variable
     */
    public Complex(Complex rhs) {
        real = rhs.real;
        imaginary = rhs.imaginary;
    }

    /**
     * Constructor that takes real and imaginary parts of complex number
     *
     * @param re real part
     * @param im imaginary part
     */
    public Complex(double re, double im) {
        real = re;
        imaginary = im;
    }

    /**
     * Setter for real part on complex number
     *
     * @param re new real part
     */
    public void set_re(double re) {
        real = re;
    }

    /**
     * Setter for imaginary part on complex number
     *
     * @param im new imaginary part
     */
    public void set_im(double im) {
        imaginary = im;
    }

    /**
     * Getter for real part of complex number
     *
     * @return real part
     */
    public double get_re() {
        return real;
    }

    /**
     * Getter for imaginary part of complex number
     *
     * @return imaginary part
     */
    public double get_im() {
        return imaginary;
    }

    /**
     * Setter for Complex number
     *
     * @param elem Complex number
     */
    public void set(Complex elem) {
        real = elem.real;
        imaginary = elem.imaginary;
    }

    /**
     * Procedure to print Complex number in Algebraic characterization
     */
    public void print_pair() {
        System.out.println(this.to_string());
    }

    /**
     * Method to convert number to String
     *
     * @return String number in Algebraic characterization
     */
    public String to_string() {
        String res;
        int scale = 2;
        double rounded_real = new BigDecimal(real).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
        double rounded_imaginary = new BigDecimal(imaginary).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
        if (Math.abs(real) < epsilon && Math.abs(imaginary) < epsilon) {
            res = Double.toString(rounded_real);
        } else if (Math.abs(real) < epsilon) {
            res = rounded_imaginary + "i";
        } else if (Math.abs(imaginary) < epsilon) {
            res = Double.toString(rounded_real);
        } else {
            if (imaginary > 0) {
                res = rounded_real + "+" + rounded_imaginary + "i";
            } else {
                res = rounded_real + "" + rounded_imaginary + "i";
            }
        }
        return res;
    }

    /**
     * Procedure to print Complex number in Polar form
     */
    public void print_polar() {
        if (real == 0 && imaginary == 0) {
            System.out.println(0);
            return;
        }
        double p = this.abs();
        double fi_angle = fi();
        fi_angle = new BigDecimal(fi_angle).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        System.out.println(p + "(cos " + fi_angle + " + i*sin " + fi_angle + ")");
    }

    /**
     * Method to calculate conjugate of a complex number
     *
     * @return conjugate of a complex number
     */
    public Complex conjugate() {
        Complex res = new Complex(real, imaginary);
        res.imaginary = -1 * imaginary;
        return res;
    }

    /**
     * Addition
     * Store the result in this
     *
     * @param rhs second addendum
     */
    public void add(Complex rhs) {
        real += rhs.real;
        imaginary += rhs.imaginary;
    }

    /**
     * Subtraction
     * Store the result in this
     *
     * @param rhs second subtrahend
     */
    public void sub(Complex rhs) {
        real -= rhs.real;
        imaginary -= rhs.imaginary;
    }

    /**
     * Calculate absolute value of a complex number
     *
     * @return absolute value
     */
    public double abs() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    /**
     * Calculate argument of a complex number
     *
     * @return argument
     */
    public double fi() {
        if (Math.abs(real) < epsilon) {
            throw new RuntimeException("Real part is equal of zero! Angle fi undefined.");
        }
        if (real > 0) {
            return Math.atan(imaginary / real);
        }
        if (imaginary > 0) {
            return Math.PI + Math.atan(imaginary / real);
        } else {
            return -Math.PI + Math.atan(imaginary / real);
        }
    }

    /**
     * Multiplication
     * Store the result in this
     *
     * @param rhs second multiplier
     */
    public void mul(Complex rhs) {
        double old_real = real;
        real = real * rhs.real - imaginary * rhs.imaginary;
        imaginary = old_real * rhs.imaginary + imaginary * rhs.real;
    }

    /**
     * Division
     * Store the result in this
     *
     * @param rhs divider
     */
    public void div(Complex rhs) {
        if (rhs.abs() < epsilon) {
            throw new RuntimeException("Division by zero!");
        }
        double old_real = real;
        real = (real * rhs.real + imaginary * rhs.imaginary) / (rhs.real * rhs.real + rhs.imaginary * rhs.imaginary);
        imaginary = (rhs.real * imaginary - old_real * rhs.imaginary) / (rhs.real * rhs.real + rhs.imaginary * rhs.imaginary);
    }
}