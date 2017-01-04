package gospl.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import core.metamodel.pop.APopulationAttribute;
import core.metamodel.pop.APopulationValue;
import core.metamodel.pop.io.GSSurveyType;
import core.util.GSPerformanceUtil;
import gospl.GosplPopulation;
import gospl.algo.ISyntheticReconstructionAlgo;
import gospl.algo.IndependantHypothesisAlgo;
import gospl.algo.sampler.IDistributionSampler;
import gospl.algo.sampler.ISampler;
import gospl.algo.sampler.sr.GosplBasicSampler;
import gospl.distribution.GosplDistributionBuilder;
import gospl.distribution.exception.IllegalControlTotalException;
import gospl.distribution.exception.IllegalDistributionCreation;
import gospl.distribution.matrix.INDimensionalMatrix;
import gospl.distribution.matrix.coordinate.ACoordinate;
import gospl.generator.DistributionBasedGenerator;
import gospl.generator.ISyntheticGosplPopGenerator;
import gospl.io.GosplSurveyFactory;
import gospl.io.exception.InvalidSurveyFormatException;

/**
 * TODO: move to a Template project in github
 * 
 * @author kevinchapuis
 *
 */
public class GosplIndependantEstimationTemplate {

	/**
	 * args[0] = the number of population's entity to generate
	 * args[1] = the absolute path to the configuration file  
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		// INPUT ARGS
		final int targetPopulation = Integer.parseInt(args[0]);
		final Path confFile = Paths.get(args[1].trim());

		// THE POPULATION TO BE GENERATED
		GosplPopulation population = null;

		// INSTANCIATE FACTORY
		GosplDistributionBuilder df = null;
		try {
			df = new GosplDistributionBuilder(confFile);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		// RETRIEV INFORMATION FROM DATA IN FORM OF A SET OF JOINT DISTRIBUTIONS
		try {
			df.buildDistributions();
		} catch (final RuntimeException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final InvalidSurveyFormatException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TRANSPOSE SAMPLES INTO IPOPULATION
		// TODO: yet to be tested
		try {
			df.buildSamples();
		} catch (final RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final InvalidSurveyFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// HERE IS A CHOICE TO MAKE BASED ON THE TYPE OF GENERATOR WE WANT:
		// Choice is made here to use distribution based generator

		// so we collapse all distribution build from the data
		INDimensionalMatrix<APopulationAttribute, APopulationValue, Double> distribution = null;
		try {
			distribution = df.collapseDistributions();
		} catch (final IllegalDistributionCreation e1) {
			e1.printStackTrace();
		} catch (final IllegalControlTotalException e1) {
			e1.printStackTrace();
		}

		// BUILD THE SAMPLER WITH THE INFERENCE ALGORITHM
		final ISyntheticReconstructionAlgo<IDistributionSampler> distributionInfAlgo = new IndependantHypothesisAlgo();
		ISampler<ACoordinate<APopulationAttribute, APopulationValue>> sampler = null;
		try {
			sampler = distributionInfAlgo.inferSRSampler(distribution, new GosplBasicSampler());
		} catch (final IllegalDistributionCreation e1) {
			e1.printStackTrace();
		}

		final GSPerformanceUtil gspu =
				new GSPerformanceUtil("Start generating synthetic population of size " + targetPopulation);

		// BUILD THE GENERATOR
		final ISyntheticGosplPopGenerator ispGenerator = new DistributionBasedGenerator(sampler);

		// BUILD THE POPULATION
		try {
			population = ispGenerator.generate(targetPopulation);
			gspu.sysoStempPerformance("End generating synthetic population: elapse time",
					GosplIndependantEstimationTemplate.class.getName());
		} catch (final NumberFormatException e) {
			e.printStackTrace();
		}

		// MAKE REPORT
		final GosplSurveyFactory sf = new GosplSurveyFactory();
		final String pathFolder = confFile.getParent().toString() + File.separator;
		final String report = "PopReport.csv";
		final String export = "PopExport.csv";
		
		try {
			sf.createSurvey(new File(pathFolder+export), GSSurveyType.Sample, population);
			sf.createSurvey(new File(pathFolder+report), GSSurveyType.GlobalFrequencyTable, population);
		} catch (IOException | InvalidSurveyFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}