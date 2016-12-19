package spin.algo.factory;

import core.metamodel.IPopulation;
import core.metamodel.pop.APopulationAttribute;
import core.metamodel.pop.APopulationEntity;
import core.metamodel.pop.APopulationValue;
import spin.algo.generator.NetworkEnumGenerator;
import spin.algo.generator.SWGenerator;
import spin.objects.SpinNetwork;

/** Propose de générer des réseaux 
 * 
 *
 */
public class NetworkFactory {
//	public static SpinNetwork getNetwork(NetworkEnumGenerator typeGenerator, 
//			IPopulation<? extends IEntity<ASurveyAttribute, AValue>, ASurveyAttribute, AValue> population){
	public static SpinNetwork getNetwork(NetworkEnumGenerator typeGenerator, 
			IPopulation<APopulationEntity, APopulationAttribute, APopulationValue> population){
		if(typeGenerator.equals(NetworkEnumGenerator.SmallWorld))
			return new SWGenerator().generateNetwork(population);
//		if(typeGenerator.equals(NetworkEnumGenerator.ScaleFree))
//			return new SFGenerator().generateNetwork(population);
		return null;
	}
	
//	public<V extends IValue, A extends IAttribute<V>> static SpinNetwork<V,A> 
	
}
