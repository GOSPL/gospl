package spll.algo;

import java.util.Map;
import java.util.Set;

import core.io.geo.entity.AGeoEntity;
import spll.datamapper.matcher.ISPLVariableFeatureMatcher;
import spll.datamapper.variable.ISPLVariable;

public interface ISPLRegressionAlgorithm<V extends ISPLVariable, T> {
	
	public Map<V, Double> getRegressionParameter();
	
	public Map<AGeoEntity, Double> getResidual();
	
	public double getIntercept();
	
	public void setupData(Map<AGeoEntity, Double> observations,
			Set<ISPLVariableFeatureMatcher<V, T>> regressors);
	
}
