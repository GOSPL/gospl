package gospl.distribution;

import java.util.Map;
import java.util.Set;

import gospl.distribution.exception.MatrixCoordinateException;
import gospl.distribution.matrix.AFullNDimensionalMatrix;
import gospl.distribution.matrix.control.AControl;
import gospl.distribution.matrix.control.ControlContingency;
import gospl.distribution.matrix.coordinate.ACoordinate;
import gospl.metamodel.attribut.IAttribute;
import gospl.metamodel.attribut.value.IValue;
import gospl.survey.GosplMetatDataType;
import io.data.GSDataParser;
import io.data.GSDataType;

/**
 * 
 * TODO: javadoc 
 * 
 * @author kevinchapuis
 *
 */
public class GosplContingencyTable extends AFullNDimensionalMatrix<Integer> {
	
	protected GosplContingencyTable(Map<IAttribute, Set<IValue>> dimensionAspectMap) 
			throws MatrixCoordinateException {
		super(dimensionAspectMap, GosplMetatDataType.ContingencyTable);
	}
		
	// ----------------------- SETTER CONTRACT ----------------------- //

	
	@Override
	public boolean addValue(ACoordinate<IAttribute, IValue> coordinates, AControl<? extends Number> value){
		if(matrix.containsKey(coordinates))
			return false;
		return setValue(coordinates, value);
	}

	@Override
	public boolean setValue(ACoordinate<IAttribute, IValue> coordinate, AControl<? extends Number> value){
		if(isCoordinateCompliant(coordinate)){
			coordinate.setHashIndex(matrix.size()+1+matrix.hashCode());
			matrix.put(coordinate, new ControlContingency(value.getValue().intValue()));
			return true;
		}
		return false;
	}
	
	// ----------------------- SIDE CONTRACT ----------------------- //
	
	@Override
	public AControl<Integer> getNulVal() {
		return new ControlContingency(0);
	}
	
	@Override
	public AControl<Integer> getIdentityProductVal() {
		return new ControlContingency(1);
	}
	
	@Override
	public AControl<Integer> parseVal(GSDataParser parser, String val){
		if(!parser.getValueType(val).equals(GSDataType.Integer))
			return getNulVal();
		return new ControlContingency(Integer.valueOf(val));
	}
				
}