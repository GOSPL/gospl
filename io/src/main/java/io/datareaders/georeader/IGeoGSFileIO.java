package io.datareaders.georeader;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import io.datareaders.georeader.geodat.GSFeature;
import io.datareaders.georeader.geodat.IGeoGSAttribute;

public interface IGeoGSFileIO {

	public GeoGSFileType getGeoGSFileType();
	
	/**
	 * Retrieve main spatial component of the file: the type of data implement {@link IGeoGSAttribute}.
	 * This method could leads to store huge amount of data into collection and then not be quite efficient
	 * 
	 * @return
	 * @throws TransformException 
	 * @throws IOException 
	 */
	public Collection<? extends IGeoGSAttribute> getGeoData() throws IOException, TransformException;

	/**
	 * Says if geographical information of the two files are congruent in term of space.
	 * That implies that, if true, the two files share at least the same projection, coordinate system
	 * and some point in space (coordinate that are present in the two files) 
	 * 
	 * @param file
	 * @return
	 */
	public boolean isCoordinateCompliant(IGeoGSFileIO file);

	/**
	 * The {@link CoordinateReferenceSystem} used by this file
	 * 
	 * @return
	 */
	public CoordinateReferenceSystem getCoordRefSystem();
	
	/**
	 * Access to file content without memory stored collection
	 * 
	 * @return
	 */
	public Iterator<? extends IGeoGSAttribute> getGeoAttributeIterator();

	/**
	 * Access and transpose to the given crs of file content without any memory storage
	 * 
	 * @param crs
	 * @return
	 * @throws FactoryException 
	 * @throws IOException 
	 */
	public Iterator<? extends IGeoGSAttribute> getGeoAttributeIterator(CoordinateReferenceSystem crs) throws FactoryException, IOException;
	
	/**
	 * Access to file data but limited to geo data that match with the given GSFeature default geometry
	 * 
	 * @param feature
	 * @return
	 */
	public Iterator<? extends IGeoGSAttribute> getGeoAttributeIterator(GSFeature feature);
	
}
