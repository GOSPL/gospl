package gospl.algo.co.metamodel;

import java.util.Collection;
import java.util.Set;

import core.metamodel.IPopulation;
import core.metamodel.attribute.demographic.DemographicAttribute;
import core.metamodel.entity.ADemoEntity;
import core.metamodel.value.IValue;
import gospl.distribution.matrix.AFullNDimensionalMatrix;

/**
 * Represents a solution (or state) of the combinatorial optimization algorithm. It encapsulates
 * a {@link IPopulation} and gives two main insights about it: 
 * <p><ul>
 * <li>the fitness (or energy): how it fits optimization requirement
 * <li>the neighbors: a somehow close but different solution.
 * </ul><p> 
 * 
 * WARNING: To define neighboring is the most important challenge in the synthetic population domain. Should it
 * be a population that shift one or more entity ? that shift should concern only one attribute per entity ? should
 * it only concern attribute (have less of this attribute value and more of this other one) ? etc.
 *  
 * @author kevinchapuis
 *
 */
public interface IGSSampleBasedCOSolution {

	/**
	 * Get one random neighbor from all possible neighbors
	 * 
	 * @return
	 */
	public IGSSampleBasedCOSolution getRandomNeighbor();
	
	/**
	 * Get one random neighbor with n diverging dimension
	 * 
	 * @param dimensionalShiftNumber
	 * @return
	 */
	public IGSSampleBasedCOSolution getRandomNeighbor(int dimensionalShiftNumber);
	
	/**
	 * Get the overall collection of neighbors
	 * 
	 * @return
	 */
	public Collection<IGSSampleBasedCOSolution> getNeighbors();

	/**
	 * The fitness of the population or how it fits the requirement of the optimization algorithm.
	 * By convention, 0 mean complete fit and the more it is the worse it is in term of fit
	 * 
	 * @param objectives
	 * @return the fitness of this solution
	 */
	public Double getFitness(Set<AFullNDimensionalMatrix<Integer>> objectives);
	
	/**
	 * The synthetic population this solution represent
	 * 
	 * @return
	 */
	public IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> getSolution();
	
}
