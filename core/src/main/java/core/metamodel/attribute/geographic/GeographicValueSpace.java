package core.metamodel.attribute.geographic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import core.metamodel.attribute.IAttribute;
import core.metamodel.attribute.IValueSpace;
import core.metamodel.value.IValue;
import core.util.data.GSDataParser;
import core.util.data.GSEnumDataType;

public class GeographicValueSpace<V extends IValue> implements IValueSpace<V> {

	private IValueSpace<V> innerValueSpace;
	private Set<V> noDataValues;
	
	public GeographicValueSpace(IValueSpace<V> innerValueSpace){
		this.innerValueSpace = innerValueSpace;
		this.noDataValues = new HashSet<>();
	}
	
	public GeographicValueSpace(IValueSpace<V> innerValueSpace,
			Collection<V> noDataValues){
		this.innerValueSpace = innerValueSpace;
		this.noDataValues = new HashSet<>(noDataValues);
	}
	
	// ------------------- GEO RELATED CONTRACT ------------------- //
	
	/**
	 * Add a list of excluded values
	 * 
	 * @param asList
	 * @return
	 */
	public boolean addExcludedValues(Collection<V> asList) {
		return this.noDataValues.addAll(asList);
	}
	
	/**
	 * Get a numerical representation of a given value
	 * 
	 * @param val
	 * @return
	 */
	public Number getNumericValue(V val) {
		if(!val.getType().isNumericValue())
			return Double.NaN;
		return new GSDataParser().parseNumbers(val.getStringValue());
	}

	// ---------------------- ADDER CONTRACT ---------------------- //
	
	@Override
	public V addValue(String value) throws IllegalArgumentException {
		V val = null;
		try {
			val = this.getValue(value);
		} catch (NullPointerException npe) {
			val = innerValueSpace.addValue(value);
		} catch (IllegalArgumentException iae){
			iae.printStackTrace();
			System.exit(1);
		}
		return val;
	}

	@Override
	public V getValue(String value) throws NullPointerException {
		if(noDataValues.stream().anyMatch(val -> val.getStringValue().equals(value)))
			throw new IllegalArgumentException(value+" has been defined as a no data value");
		return innerValueSpace.getValue(value);
	}
	
	@Override 
	public Set<V> getValues(){
		return innerValueSpace.getValues();
	}
	
	// ---------------------------------------------------- //

	@Override
	public boolean isValidCandidate(String value) {
		return innerValueSpace.isValidCandidate(value);
	}

	@Override
	public GSEnumDataType getType() {
		return innerValueSpace.getType();
	}

	@Override
	public V getEmptyValue() {
		return innerValueSpace.getEmptyValue();
	}

	@Override
	public void setEmptyValue(String value) {
		innerValueSpace.setEmptyValue(value);
	}

	@Override
	public IAttribute<V> getAttribute() {
		return innerValueSpace.getAttribute();
	}
	
}
