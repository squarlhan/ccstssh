package test;

import pso.Particle;
import pso.Swarm;
import example_2.SwarmShow2D;

public class Example {

	//-------------------------------------------------------------------------
	// Main
	//-------------------------------------------------------------------------
	public static void main(String[] args) {
		System.out.println("Begin: MaxTest 1\n");

		// Create a swarm (using 'MyParticle' as sample particle and 'MyFitnessFunction' as finess function)
		Swarm swarm = new Swarm(40, new MaxParticle(30), new CosMaxFunction());

		// Set position (and velocity) constraints. I.e.: where to look for solutions
		swarm.setInertia(0.95);
		swarm.setMaxPosition(5.12);
		swarm.setMinPosition(-5.12);
		swarm.setMaxMinVelocity(0.7);

		int numberOfIterations = 200;
		boolean showGraphics = false;

		if( showGraphics ) {
			int displayEvery = numberOfIterations / 100 + 1;
			SwarmShow2D ss2d = new SwarmShow2D(swarm, numberOfIterations, displayEvery, true);
			ss2d.run();
		} else {
			// Optimize (and time it)
			for( int i = 0; i < numberOfIterations; i++ )
				swarm.evolve();
		}

		// Print en results
		System.out.println(swarm.toStringStats());
		System.out.println("Fitness count: "+ MaxFunction.counts);
		System.out.println("End: Example 1");
	}
}
