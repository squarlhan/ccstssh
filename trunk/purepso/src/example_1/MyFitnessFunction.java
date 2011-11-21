package example_1;

import pso.FitnessFunction;

/**
 * Sample Fitness function
 * 		f( x1 , x2 ) = 1 - Sqrt( ( x1 - 1/2 )^2 + ( x2 - 1/2 )^2 )
 * 
 * @author Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public class MyFitnessFunction extends FitnessFunction {
	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------
	public  static int count = 0;
	/**
	 * Evaluates a particles at a given position
	 * @param position : Particle's position
	 * @return Fitness function for a particle
	 */
	public double evaluate(double position[]) {
		double sum = 0;
		double s = position[0] - 0.5;
		sum += s * s;
		s = position[1] - 0.5;
		sum += s * s;
		
		count++;
		
		return (1 - Math.sqrt(sum));
	}

}
