package spin.algo.factory;

import core.metamodel.IPopulation;
import core.metamodel.pop.APopulationAttribute;
import core.metamodel.pop.APopulationEntity;
import core.metamodel.pop.APopulationValue;
import spin.algo.generator.RandomNetworkGenerator;
import spin.algo.generator.RegularNetworkGenerator;
import spin.algo.generator.SFNetworkGenerator;
import spin.algo.generator.SWNetworkGenerator;
import spin.interfaces.ENetworkEnumGenerator;
import spin.objects.SpinNetwork;

/** Propose de générer des réseaux 
 *
 */
public class SpinNetworkFactory {
	
	private SpinNetwork network;
	
	// Singleton
	private static SpinNetworkFactory INSTANCE;
	
	public static SpinNetworkFactory getInstance(){
		if(INSTANCE == null)
			INSTANCE = new SpinNetworkFactory();
		return INSTANCE;
	}
	
	private SpinNetworkFactory(){
	}
	
	/** Renvoi un spinNetwork sur une population passé en paramètre, en prenant une population
	 * en entrée.
	 * 
	 * @param typeGenerator Type du réseau généré
	 * @param population Population en parametre. 
	 * @return
	 */
	public SpinNetwork generateNetwork(ENetworkEnumGenerator typeGenerator, IPopulation<APopulationEntity, APopulationAttribute, APopulationValue> population){
		if(typeGenerator.equals(ENetworkEnumGenerator.SmallWorld))
			network = new SWNetworkGenerator().generateNetwork(population,4, .1); 
		if(typeGenerator.equals(ENetworkEnumGenerator.Random))	
			network = new RandomNetworkGenerator().generateNetwork(population, .1);
		if(typeGenerator.equals(ENetworkEnumGenerator.Regular))	
			network = new RegularNetworkGenerator().generateNetwork(population, 2);
		if(typeGenerator.equals(ENetworkEnumGenerator.ScaleFree))	
			network = new SFNetworkGenerator().generateNetwork(population);
		
		return network;
	}
	
	public SpinNetwork getSpinNetwork(){
		return this.network;
	}
	
}
