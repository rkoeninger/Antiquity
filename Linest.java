
public class Linest{
	public static void main(String[] args){
		double projectedYear = 6;
		double[] y = {8, 6, 14, -10, 25};
		double[] x = {5.0, 3.0, 10.0, -15.0, 20.0};
		int n = 5;
		Function linestFunction = leastSquaresFit(n, x, y);
		System.out.println(linestFunction.f(projectedYear));
	}

	public static Function leastSquaresFit(int n, double[] x, double[] y){
		double sumX = 0.0;
		double sumY = 0.0;
		double xDotX = 0.0;
		double xDotY = 0.0;
		for (int i = 0; i < n; ++i){
			sumX += x[i];
			sumY += y[i];
			xDotX += (x[i] * x[i]);
			xDotY += (x[i] * y[i]);
		}
		double denominator = (n * xDotX) - (sumX * sumX);
		final double a = ((xDotX * sumY) - (xDotY * sumX)) / denominator;
		final double b = ((n * xDotY) - (sumX * sumY)) / denominator;
		System.out.println(a);
		System.out.println(b);
		return new Function(){
			public double f(double arg){
				return a + (b * arg);
			}
		};
	}

	public static abstract class Function{
		public abstract double f(double arg);
	}
}
