package spll.popmapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.geotools.feature.SchemaException;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.operation.TransformException;

import core.metamodel.IPopulation;
import core.metamodel.attribute.demographic.DemographicAttribute;
import core.metamodel.entity.ADemoEntity;
import core.metamodel.entity.AGeoEntity;
import core.metamodel.io.IGSGeofile;
import core.metamodel.value.IValue;
import spll.SpllEntity;
import spll.SpllPopulation;
import spll.algo.LMRegressionOLS;
import spll.algo.exception.IllegalRegressionException;
import spll.datamapper.exception.GSMapperException;
import spll.io.exception.InvalidGeoFormatException;
import spll.popmapper.constraint.ISpatialConstraint;
import spll.popmapper.normalizer.SPLUniformNormalizer;

/**
 * This is the main object to localize population. It is the main ressource in Spll process.
 * It contains a <i>must have</i> module and two optional ones:
 * <p>
 * <ul>
 * <li>1) <b>MANDATORY</b>: A geographically referenced population, i.e. {@link SpllPopulation}
 * <li>2) <b>OPTIONAL</b>: A geographical match between a population's entity attribute and geographical entities (denote as <i>match</i>)
 * <li>3) <b>OPTIONAL</b>: A density map (without any match with population) OR a spatial regression setup to estimate one
 * </ul>
 * </p>
 * These three options outline what Spll localization process cover: <br> 
 * (1) localize entity into nest {@link ADemoEntity#getNest()} <br>
 * (2) match entity with the geography {@link ADemoEntity#getLocation()} (if no match, it is equal to the nest) <br> 
 * (3) ancillary information on density (even estimated one using regression techniques) <br>
 * <p>
 * 
 * @author kevinchapuis
 * @author taillandier patrick
 *
 */
public interface ISPLocalizer {

	// -------------- MAIN CONTRACT -------------- //
	
	/**
	 * Provide the higher order method that take a population and 
	 * return the population with localisation indication 
	 * 
	 * @param population
	 * @return
	 */
	public IPopulation<SpllEntity, DemographicAttribute<? extends IValue>> localisePopulation();
	
	////////////////////////////////////////////////
	// -------------- MATCHER PART -------------- //
	//  Matcher part corresponds to the matching  // 
	// 	   phase between population and space     //
	////////////////////////////////////////////////
	
	/**
	 * Setup a "matched geography" between population's entities attribute and a geographical entitites
	 * 
	 * @param match
	 * @param keyAttPop
	 * @param keyAttMatch
	 */
	public void setMatcher(IGSGeofile<? extends AGeoEntity<? extends IValue>, ? extends IValue> match, 
			String keyAttPop, String keyAttMatch);
	
	/**
	 * This method must setup matcher variable (i.e. the number of entity) in
	 * the proper output format geofile
	 * 
	 * @return
	 * @throws TransformException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws MismatchedDimensionException 
	 * @throws SchemaException 
	 */
	public IGSGeofile<? extends AGeoEntity<? extends IValue>, ? extends IValue> estimateMatcher(File destination) 
			throws MismatchedDimensionException, IllegalArgumentException, IOException, TransformException, SchemaException;
	
	
	///////////////////////////////////////////////
	// -------------- MAPPER PART -------------- //
	// 	  Mapper part corresponds to the Areal   // 
	// 	  Interpolation phase of localization    //
	///////////////////////////////////////////////
	
	
	/**
	 * Setup a density map - through external files that define spatial contingency without any 
	 * match with population entities
	 * 
	 * @param entityNbAreas
	 * @param numberProperty
	 */
	public void setMapper(IGSGeofile<? extends AGeoEntity<? extends IValue>, ? extends IValue> map, 
			String numberProperty);
	
	/**
	 * Setup a density map - from the result of spatial interpolation: this interpolation
	 * is based on the previous match setup !
	 * <p>
	 * WARNING: will throw a Exception if no match have been set before
	 * 
	 * @param endogeneousVarFile
	 * @param varList
	 * @param lmRegressionOLS
	 * @param splUniformNormalizer
	 * @throws IOException
	 * @throws TransformException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IllegalRegressionException
	 * @throws IndexOutOfBoundsException
	 * @throws GSMapperException
	 * @throws SchemaException 
	 * @throws InvalidGeoFormatException 
	 * @throws IllegalArgumentException 
	 * @throws MismatchedDimensionException 
	 */
	public void setMapper(List<IGSGeofile<? extends AGeoEntity<? extends IValue>, ? extends IValue>> endogeneousVarFile, 
			List<? extends IValue> varList, LMRegressionOLS lmRegressionOLS, SPLUniformNormalizer splUniformNormalizer) 
					throws IOException, TransformException, InterruptedException, ExecutionException, IllegalRegressionException, 
					IndexOutOfBoundsException, GSMapperException, SchemaException, MismatchedDimensionException, IllegalArgumentException, InvalidGeoFormatException;
	
	/**
	 * Setup a density map - from the result of spatial interpolation: this interpolation
	 * is based on a given match file 
	 * 
	 * @param mainMapper
	 * @param mainAttribute
	 * @param endogeneousVarFile
	 * @param varList
	 * @param lmRegressionOLS
	 * @param splUniformNormalizer
	 * @throws IOException
	 * @throws TransformException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IllegalRegressionException
	 * @throws IndexOutOfBoundsException
	 * @throws GSMapperException
	 * @throws SchemaException 
	 * @throws InvalidGeoFormatException 
	 * @throws IllegalArgumentException 
	 * @throws MismatchedDimensionException 
	 */
	public void setMapper(IGSGeofile<? extends AGeoEntity<? extends IValue>, ? extends IValue> mainMapper, 
			String mainAttribute, List<IGSGeofile<? extends AGeoEntity<? extends IValue>, ? extends IValue>> endogeneousVarFile, 
			List<? extends IValue> varList, LMRegressionOLS lmRegressionOLS, SPLUniformNormalizer splUniformNormalizer) 
					throws IOException, TransformException, InterruptedException, ExecutionException, IllegalRegressionException, 
					IndexOutOfBoundsException, GSMapperException, SchemaException, MismatchedDimensionException, IllegalArgumentException, InvalidGeoFormatException;
	
	///////////////////////////////////////////////////
	// -------------- CONSTRAINT PART -------------- //
	// 	  constraint corresponds to variable that    // 
	// 	  shapes final localization step: choose     //
	//	  a nest whitin defined constraints and a    //
	//	  x, y within it							 //
	///////////////////////////////////////////////////
	
	/**
	 * Add a new spatial constraint to this localizer
	 * 
	 * @see ISpatialConstraint
	 * 
	 * @param constraint
	 */
	public boolean addConstraint(ISpatialConstraint constraint);
	
	/**
	 * Set the constraint all in a row
	 * 
	 * @param constraints
	 */
	public void setConstraints(List<ISpatialConstraint> constraints);
	
	/**
	 * Returns all setted constraints
	 * 
	 * @return
	 */
	public List<ISpatialConstraint> getConstraints();
	
}
