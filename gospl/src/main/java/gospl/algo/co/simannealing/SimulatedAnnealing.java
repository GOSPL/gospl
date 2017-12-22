package gospl.algo.co.simannealing;

import org.apache.logging.log4j.Level;

import core.util.GSPerformanceUtil;
import gospl.algo.co.metamodel.AGSOptimizationAlgorithm;
import gospl.algo.co.metamodel.IGSSampleBasedCOSolution;
import gospl.algo.co.simannealing.transition.GSSADefautTransFunction;
import gospl.algo.co.simannealing.transition.IGSSimAnnealingTransFunction;
import gospl.sampler.IEntitySampler;

/**
 * The simulated annealing algorithm that fit combinatorial optimization framework from 
 * synthetic population field of research. It is used by {@link IEntitySampler} to generate
 * a synthetic population based on a data sample and data aggregated constraints (also called
 * marginals)
 * <p>
 * Basic principles of the algorithm are: we start with a synthetic population (may be randomly generated 
 * from a sample, the sample itself or any user provided synthetic population) which represents the current state. 
 * Each step of the algorithm provide a new population, slightly different from the current state, 
 * which represent a "neighbor state". This new candidate state as a probability to be accepted that depend
 * on its own energy (or fitness) and the temperature of the global system. As algorithm iterates, it will be less
 * incline to accept candidate state with lower energy (worst fitness) and more and more rely on the best candidate
 * he has visited. 
 * 
 * @author kevinchapuis
 *
 */
public class SimulatedAnnealing extends AGSOptimizationAlgorithm {

	private int bottomTemp = 1;
	private double minStateEnergy = 0;
	
	private int temperature = 100000;
	private double coolingRate = Math.pow(10, -3);
	
	private IGSSimAnnealingTransFunction transFunction;

	public SimulatedAnnealing(double minStateEnergy,
			int initTemp, double coolingRate, 
			IGSSimAnnealingTransFunction transFonction) {
		this.minStateEnergy = minStateEnergy;
		this.temperature = initTemp;
		this.coolingRate = coolingRate;
	}

	public SimulatedAnnealing(double minStateEnergy,
			IGSSimAnnealingTransFunction transFonction){
		this.minStateEnergy = minStateEnergy;
		this.transFunction = transFonction;
	}
	
	public SimulatedAnnealing(IGSSimAnnealingTransFunction transFunction){
		this.transFunction = transFunction;
	}
	
	public SimulatedAnnealing(){
		this.transFunction = new GSSADefautTransFunction();
	}
	
	@Override
	public IGSSampleBasedCOSolution run(IGSSampleBasedCOSolution initialSolution){
		
		IGSSampleBasedCOSolution currentState = initialSolution;
		IGSSampleBasedCOSolution bestState = initialSolution;
		
		GSPerformanceUtil gspu = new GSPerformanceUtil(
				"Start Simulated annealing algorithm in CO synthetic population generation process", 
				Level.DEBUG);
		
		double currentEnergy = currentState.getFitness(this.getObjectives());
		double bestEnergy = currentEnergy;
		
		// Iterate while system temperature is above cool threshold 
		// OR while system energy is above minimum state energy
		while(temperature < bottomTemp ||
				currentEnergy > minStateEnergy){
			
			gspu.sysoStempPerformance("Elicit a random new candidate for a transition state", this);
			IGSSampleBasedCOSolution systemStateCandidate = currentState.getRandomNeighbor();
			double candidateEnergy = systemStateCandidate.getFitness(this.getObjectives());
			
			// IF probability function elicit transition state
			// THEN change current state to be currentCandidate 
			if(transFunction.getTransitionProbability(currentEnergy, candidateEnergy, temperature)){
				gspu.sysoStempPerformance("Current state have been updated from "
						+ currentEnergy+" to "+candidateEnergy, this);
				currentState = systemStateCandidate;
				currentEnergy = candidateEnergy;
			}
			
			// Keep track of best state visited
			if(bestEnergy > currentEnergy){
				bestState = currentState;
				bestEnergy = currentEnergy;
			}
			
			gspu.sysoStempPerformance("Cool down system from "
					+ temperature+ " to " +(temperature*(1-coolingRate)), this);
			temperature *= 1 - coolingRate;
		}
		
		return bestState;
	}
	
}
