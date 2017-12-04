package core.metamodel.entity;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import core.metamodel.attribute.IAttribute;
import core.metamodel.value.IValue;

/**
 * An entity might represent an household, an individual, or even a geographical entity etc.
 * 
 * @author gospl-team
 *
 */
public interface IEntity<A extends IAttribute<? extends IValue>> {

	/**
	 * returns the mapped view of attribute / value pairs
	 * @return
	 */
	public Map<A, IValue> getAttributeMap();
	
	/**
	 * returns the list of the attributes for which the entity might have values
	 * @return
	 */
	public Collection<A> getAttributes();
	
	/**
	 * Returns true if this entity contains this attribute
	 * @param a
	 * @return
	 */
	public boolean hasAttribute(A a);

	/**
	 * returns values for each attributes of the entity
	 * 
	 * @return
	 */
	public Collection<? extends IValue> getValues();
	
	/**
	 * returns the value for an attribute if any; the value might be null if no
	 * value is defined; raises an exception if the attribute is not declared for this entity
	 * @param attribute
	 * @return
	 */
	public IValue getValueForAttribute(A attribute);
	
	/**
	 * returns the value for an attribute if any, based on attribute name. The name of 
	 * attribute should be access using {@link IAttribute#getAttributeName()}
	 * <p>
	 * @see #getValueForAttribute(IAttribute)
	 * 
	 * @param property
	 * @return
	 */
	public IValue getValueForAttribute(String property);

	/**
	 * Returns true if this agent has an entity type, that is if getEntityType 
	 * returns something else than null
	 * @return
	 */
	public boolean hasEntityType();

	/**
	 * Returns the entity type of this agent, or null if none was defined 
	 * (standard case if there is only one type of agent)
	 * @return
	 */
	public String getEntityType();
	
	/**
	 * Sets the type of this agent, without any control
	 * @param type
	 */
	public void setEntityType(String type);
	
	/**
	 * Returns true if this agent has a parent, 
	 * that is if getParent does not return null;
	 * @return
	 */
	public boolean hasParent();
	
	/**
	 * Returns the "parent" entity 
	 * @return
	 */
	public IEntity<?> getParent();
	
	/**
	 * defines the parent. Raises an exception if you 
	 * try to define an entity as its own parent. 
	 * Does not detects loops, which you should still 
	 * avoid.
	 * @param e
	 */
	public void setParent(IEntity<?> e);

	/**
	 * returns true if this entity has any children, 
	 * that is if getCountChildren returns more than 1
	 * @return
	 */
	public boolean hasChildren();
	
	/**
	 * returns the count of children
	 * @return
	 */
	public int getCountChildren();
	
	/**
	 * returns the set of children
	 * @return
	 */
	public Set<IEntity<?>> getChildren();
	
	/**
	 * Adds a child to the list of children
	 * @param e
	 */
	public void addChild(IEntity<?> e);
	
	/**
	 * Adds several children to the list of children
	 * @param e
	 */
	public void addChildren(Collection<IEntity<?>> e);
	

}