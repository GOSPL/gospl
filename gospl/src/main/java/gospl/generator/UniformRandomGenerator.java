package gospl.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import core.io.survey.attribut.ASurveyAttribute;
import core.io.survey.attribut.AttributeFactory;
import core.io.survey.attribut.GSEnumAttributeType;
import core.io.survey.attribut.value.AValue;
import core.util.data.GSEnumDataType;
import core.util.excpetion.GSIllegalRangedData;
import gospl.metamodel.GosplEntity;
import gospl.metamodel.GosplPopulation;

public class UniformRandomGenerator implements ISyntheticGosplPopGenerator {

	private int maxAtt;
	private int maxVal;
	
	char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	Random random = ThreadLocalRandom.current();

	public UniformRandomGenerator(int maxAtt, int maxVal) {
		this.maxAtt = maxAtt;
		this.maxVal = maxVal;
	}
	
	@Override
	public GosplPopulation generate(int numberOfIndividual) {
		
		// Basic population to feed
		GosplPopulation gosplPop = new GosplPopulation();
		
		// Attribute Factory
		AttributeFactory attF = new AttributeFactory();
		Set<ASurveyAttribute> attSet = new HashSet<>();
		for(int i = 0; i < random.nextInt(maxAtt)+1; i++){
			ASurveyAttribute asa;
			if(random.nextDouble() > 0.5)
				asa = createStringAtt(attF);
			else
				asa = createIntegerAtt(attF);
			attSet.add(asa);
		}
		
		IntStream.range(0, numberOfIndividual).forEach(i -> gosplPop.add(
				new GosplEntity(attSet.stream().collect(Collectors.toMap(att -> att, 
						att -> randomVal(att.getValues()))))));
		
		return gosplPop;
	}

	private ASurveyAttribute createIntegerAtt(AttributeFactory factory) {
		ASurveyAttribute asa = null;
		try {
			asa = factory.createAttribute(generateName(random.nextInt(6)+1), 
					GSEnumDataType.Integer, 
					IntStream.range(0, maxVal).mapToObj(i -> String.valueOf(i)).collect(Collectors.toList()), 
					GSEnumAttributeType.unique);
		} catch (GSIllegalRangedData e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return asa;
	}
	
	private ASurveyAttribute createStringAtt(AttributeFactory factory){
		ASurveyAttribute asa = null;
		try {
			asa = factory.createAttribute(generateName(random.nextInt(6)+1), 
					GSEnumDataType.String, 
					IntStream.range(0, random.nextInt(maxVal)).mapToObj(j -> 
							generateName(random.nextInt(j+1))).collect(Collectors.toList()), 
					GSEnumAttributeType.unique);
		} catch (GSIllegalRangedData e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return asa;
	}
	
	private String generateName(int size){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
	private AValue randomVal(Set<AValue> values){
		List<AValue> vals = new ArrayList<>(values);
		return vals.get(random.nextInt(vals.size()));
	}

}