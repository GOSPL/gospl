package gospl.algo.co;

import core.metamodel.IPopulation;
import core.metamodel.attribute.demographic.DemographicAttribute;
import core.metamodel.entity.ADemoEntity;
import core.metamodel.value.IValue;
import gospl.sampler.IEntitySampler;
import gospl.sampler.ISampler;

public class SampleBasedAlgorithm implements ICombinatorialOptimizationAlgo<IEntitySampler> {

	@Override
	public ISampler<ADemoEntity> setupCOSampler(
			IPopulation<ADemoEntity, DemographicAttribute<? extends IValue>> sample, 
			IEntitySampler sampler) {
		sampler.setSample(sample);
		return sampler;
	}

}
