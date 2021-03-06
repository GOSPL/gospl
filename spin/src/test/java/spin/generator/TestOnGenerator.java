package spin.generator;

import gospl.GosplEntity;
import spin.SpinNetwork;
import spin.algo.generator.ISpinNetworkGenerator;
import spin.algo.generator.SpinCompleteNetworkGenerator;
import spin.algo.generator.SpinRandomNetworkGenerator;
import spin.algo.generator.SpinRegularNetworkGenerator;
import spin.algo.generator.SpinSFNetworkGenerator;

public class TestOnGenerator {

	/** Test pour les fonctions de generation de reseau sur une population
	 * A - Generation d'une population de taille variable
	 * B - Generation d'un graphe
	 * C - Affichage du graphe
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		ISyntheticGosplPopGenerator generator = new GSUtilGenerator(2, 4);
		IPopulation<ADemoEntity, Attribute<? extends IValue>> population = generator.generate(100);
		
		
		System.out.println("Debut de la generation de reseau regulier");
		SpinPopulation populationWithNetworkRegular = SpinNetworkFactory.getInstance().generateNetwork(ENetworkGenerator.Regular, population);
		SpinNetwork networkRegular = populationWithNetworkRegular.getNetwork();
		System.out.println("Fin de generation de reseau regulier");
		
		System.out.println("Debut de la generation de reseau Scale Free");
		SpinPopulation populationWithNetworkSF = SpinNetworkFactory.getInstance().generateNetwork(ENetworkGenerator.ScaleFree, population);
		SpinNetwork networkSF = populationWithNetworkSF.getNetwork();
		System.out.println("Fin de generation de reseau Scale Free");
		
		System.out.println("Debut de la generation de reseau Random");
		SpinPopulation populationWithNetworkRandom = SpinNetworkFactory.getInstance().generateNetwork(ENetworkGenerator.Random, population);
		SpinNetwork networkRandom = populationWithNetworkRandom.getNetwork();
		System.out.println("Fin de generation de reseau Random");
		
		System.out.println("Debut de la generation de reseau SmallWorld");
		SpinPopulation populationWithNetworkSW = SpinNetworkFactory.getInstance().generateNetwork(ENetworkGenerator.SmallWorld, population);
		SpinNetwork networkSW = populationWithNetworkSW.getNetwork();
		System.out.println("Fin de generation de reseau SmallWorld");
*/		
//		SpinPopulation populationWithNetwork = SpinNetworkFactory.getInstance().generateNetwork(ENetworkGenerator.ScaleFree, population);
//		SpinNetwork networkTest = populationWithNetwork.getNetwork();
//		networkTest.network.display();
		
//		Graph sampleGraph = networkTest.randomWalkSample(50);
//		sampleGraph.display();
		
		ISpinNetworkGenerator<GosplEntity> spinPopGen = new SpinCompleteNetworkGenerator<>("family");
		SpinNetwork networkedPop = spinPopGen.generate(30);
		
		System.out.println(networkedPop.toString());

		
		spinPopGen = new SpinRegularNetworkGenerator<>("friends",4);
		networkedPop = spinPopGen.generate(30);
		
		System.out.println(networkedPop.toString());		

		
		spinPopGen = new SpinRandomNetworkGenerator<>("workmates",0.1);
		networkedPop = spinPopGen.generate(30);
		
		System.out.println(networkedPop.toString());	
		
		
		spinPopGen = new SpinSFNetworkGenerator<>("colleagues");
		networkedPop = spinPopGen.generate(30);
		
		System.out.println(networkedPop.toString());			
	}
}
