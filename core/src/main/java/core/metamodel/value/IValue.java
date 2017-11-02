package core.metamodel.value;

import core.metamodel.attribute.IValueSpace;
import core.util.data.GSEnumDataType;

/**
 * The values that characterise Genstar's attribute of entity
 * 
 * @author kevinchapuis
 *
 */
public interface IValue {
	
	/**
	 * The type of data this value encapsulate
	 * 
	 * @return
	 */
	public GSEnumDataType getType();
	
	/**
	 * The value represented as a String
	 * 
	 * @return
	 */
	public String getStringValue();
	
	/**
	 * The value space this value is part of
	 * 
	 * @return
	 */
	public IValueSpace<? extends IValue> getValueSpace();
	
	// --------------------------------------------------------------- //
	
	/**
	 * Force to overload hashcode method to ensure equals consistency
	 * 
	 * @return
	 */
	public int hashCode();
	
	/**
	 * Force to overload equals method
	 * 
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj);
	
}
