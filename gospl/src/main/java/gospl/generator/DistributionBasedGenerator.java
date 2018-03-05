package gospl.generator;

import java.util.stream.Collectors;

import core.metamodel.attribute.Attribute;
import core.metamodel.value.IValue;
import gospl.GosplEntity;
import gospl.GosplPopulation;
import gospl.distribution.matrix.coordinate.ACoordinate;
import gospl.sampler.ISampler;

/**
 * A generator that take a defined distribution and a given sampler
 * 
 * @author kevinchapuis
 *
 */
public class DistributionBasedGenerator implements ISyntheticGosplPopGenerator {
	
	private ISampler<ACoordinate<Attribute<? extends IValue>, IValue>> sampler;
	
	public DistributionBasedGenerator(ISampler< ACoordinate<Attribute<? extends IValue>, IValue>> sampler) {
		this.sampler = sampler;
	}
	
	@Override
	public GosplPopulation generate(int numberOfIndividual) {
		return new GosplPopulation(sampler.draw(numberOfIndividual).stream()
				.map(coord -> new GosplEntity(coord.getMap())).collect(Collectors.toSet()));
	}

}
