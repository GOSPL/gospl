package gospl.algo.co.metamodel;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.metamodel.IPopulation;
import core.metamodel.attribute.demographic.DemographicAttribute;
import core.metamodel.entity.ADemoEntity;
import core.metamodel.value.IValue;
import gospl.GosplPopulation;
import gospl.distribution.GosplNDimensionalMatrixFactory;
import gospl.distribution.matrix.AFullNDimensionalMatrix;
import gospl.validation.GosplIndicatorFactory;

/**
 * WARNING: huge performance issue with {@link #getTabuValue(Set)} because of transposed population
 * to contingency table (with possible population of several million of entity)
 * 
 * TODO: have a 'proxy' solution from current state solution, i.e. only store differences, that is
 * entity which have been remove and added 
 * 
 * @author kevinchapuis
 *
 */
public abstract class AGSSampleBasedCOSolution implements IGSSampleBasedCOSolution {

	protected IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> population;
	protected Set<IValue> valueList;
	
	protected Collection<ADemoEntity> sample;
	
	private double fitness = -1;
	
	public AGSSampleBasedCOSolution(IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> population,
			Collection<ADemoEntity> sample){
		this.population = population;
		this.sample = sample;
		this.valueList = population.stream().flatMap(entity -> entity.getValues().stream())
				.collect(Collectors.toSet());
	}
	
	public AGSSampleBasedCOSolution(Collection<ADemoEntity> population, Collection<ADemoEntity> sample){
		this(new GosplPopulation(population), sample);
	}
	
	@Override
	public Double getFitness(Set<AFullNDimensionalMatrix<Integer>> objectives) {
		// Only compute once
		if(fitness == -1){
			AFullNDimensionalMatrix<Integer> popMatrix = GosplNDimensionalMatrixFactory
					.getFactory().createContingency(population);
			fitness = objectives.stream().mapToDouble(obj -> GosplIndicatorFactory.getFactory()
					.getIntegerTAE(obj, popMatrix)).sum();
		}
		return fitness;
	}
	
	@Override
	public IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> getSolution() {
		return population;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((population == null) ? 0 : population.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AGSSampleBasedCOSolution other = (AGSSampleBasedCOSolution) obj;
		if (population == null) {
			if (other.population != null)
				return false;
		} else if (!population.equals(other.population))
			return false;
		return true;
	}
	
	/* (non-javadoc)
	 * Inner purpose find a pair of entity from population & sample to be swap
	 * knowing that their share all values but the value in argument.
	 * <p>
	 * May returns an empty map if no pair is find
	 */
	protected Map<ADemoEntity, ADemoEntity> findAnyTargetRemoveAddPair(
			IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> population,
			IValue value){
		Map<ADemoEntity, Collection<IValue>> expectedRemove = population.stream()
				.filter(entity -> entity.getValues().contains(value))
				.collect(Collectors.toSet()).stream()
				.collect(Collectors.toMap(Function.identity(), 
						entity -> entity.getValues().stream().filter(val -> !val.equals(value))
						.collect(Collectors.toList())));
		Optional<ADemoEntity> newEntity = sample.stream().filter(entity -> expectedRemove.values()
				.stream().anyMatch(values -> entity.getValues().containsAll(values)))
				.findFirst();
		if(newEntity.isPresent()){
			ADemoEntity oldEntity = expectedRemove.keySet().stream().filter(entity -> newEntity.get().getValues()
					.containsAll(expectedRemove.get(entity)))
					.findFirst().get();
			return Stream.of(oldEntity).collect(Collectors.toMap(Function.identity(), e -> newEntity.get()));
		}
		return Collections.emptyMap();
	}
	
	/* (non-javadoc)
	 * Swap the two entity in the encapsulated population. If the old entity
	 * cannot be remove (population.remove(oldEntity) returns false) or if
	 * the new entity cannot be add (population.add(newEntity) returns false)
	 * the method throw an exception
	 */
	protected IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> deepSwitch(
			IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> population, 
			ADemoEntity oldEntity, ADemoEntity newEntity){
		if(!population.remove(oldEntity) || !population.add(newEntity))
				throw new RuntimeException("Encounter a problem while switching between two entities:\n"
						+ "remove entity = "+oldEntity.toString()+"\n"
						+ "new entity = "+newEntity.toString());
		return population;
	}
	
}