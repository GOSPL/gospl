package spin.algo.factory;

import core.io.survey.entity.AGenstarEntity;
import core.io.survey.entity.attribut.AGenstarAttribute;
import core.io.survey.entity.attribut.value.AGenstarValue;
import core.metamodel.IPopulation;
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
			IPopulation<AGenstarEntity, AGenstarAttribute, AGenstarValue> population){
		if(typeGenerator.equals(NetworkEnumGenerator.SmallWorld))
			return new SWGenerator().generateNetwork(population);
//		if(typeGenerator.equals(NetworkEnumGenerator.ScaleFree))
//			return new SFGenerator().generateNetwork(population);
		return null;
	}
	
//	public<V extends IValue, A extends IAttribute<V>> static SpinNetwork<V,A> 
	
}
