package gospl.algo.co.metamodel;

import java.util.Collection;
import java.util.Set;

import core.metamodel.IPopulation;
import core.metamodel.attribute.demographic.DemographicAttribute;
import core.metamodel.entity.ADemoEntity;
import core.metamodel.value.IValue;
import gospl.distribution.matrix.AFullNDimensionalMatrix;

public interface IGSSampleBasedCOSolution {

	public IGSSampleBasedCOSolution getRandomNeighbor();
	
	public IGSSampleBasedCOSolution getRandomNeighbor(int dimensionalShiftNumber);
	
	public Collection<IGSSampleBasedCOSolution> getNeighbors();

	public Double getFitness(Set<AFullNDimensionalMatrix<Integer>> objectives);
	
	public IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> getSolution();
	
}