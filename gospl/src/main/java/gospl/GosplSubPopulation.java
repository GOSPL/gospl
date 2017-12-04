package gospl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.bouncycastle.crypto.RuntimeCryptoException;

import core.metamodel.IPopulation;
import core.metamodel.attribute.IAttribute;
import core.metamodel.entity.IEntity;
import core.metamodel.value.IValue;

/**
 * A subpopulation is a view of an actual multitypePopulation.
 * Any change to this subpopulation is reflected to the original MultitypePopulation 
 * (and reciprocally).
 * 
 * @author Samuel Thiriot
 */
public final class GosplSubPopulation<E extends IEntity<A>, A extends IAttribute<? extends IValue>> 
				implements IPopulation<E, A> {

	protected final GosplMultitypePopulation<E,A> multiPop;
	protected final String type;
	protected final Set<E> set;
	
	protected GosplSubPopulation(GosplMultitypePopulation<E,A> p, String type) {
		this.multiPop = p;
		this.type = type;
		this.set = multiPop.getSetForType(type);
	}

	@Override
	public Set<A> getPopulationAttributes() {
		return this.multiPop.getAttributesForType(this.type);
	}
	
	@Override
	public boolean add(E e) {
		if (e.hasEntityType() && !type.equals(e.getEntityType()))
			throw new RuntimeCryptoException("cannot add this agent of type "+e.getEntityType()+" to a subpopulation of "+type);
		
		return multiPop.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return multiPop.addAll(type, c);
	}
	
	@Override
	public void clear() {
		multiPop.clear(type);
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return set.iterator();
	}

	@Override
	public boolean remove(Object o) {
		if (!set.contains(o))
			return false;
		multiPop.remove(o);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean anyChange = false;
		for (Object e: c)
			anyChange = remove(e) || anyChange;
		return anyChange;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean anyChange = set.retainAll(c);
		if (anyChange)
			multiPop.recomputeSize();
		return anyChange;
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	
	
}