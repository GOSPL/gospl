package gospl.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import au.com.bytecode.opencsv.CSVReader;
import core.configuration.dictionary.IGenstarDictionary;
import core.metamodel.attribute.Attribute;
import core.metamodel.io.GSSurveyType;
import core.metamodel.value.IValue;

/**
 * Handles the DBF DBase INSEE tables.
 * 
 * @see https://en.wikipedia.org/wiki/.dbf
 * 
 * TODO : switch from nl.knaw.dans.common.dbflib to another support for dbf readings - no more available as maven dependancies
 * 
 * @author Samuel Thiriot
 */
@Deprecated
public class DBaseInputHandler extends AbstractInputHandler {
	
	
	public DBaseInputHandler(GSSurveyType dataFileType, File file) {
		super(dataFileType, file);
		// TODO Auto-generated constructor stub
	}

	/*
	
	private static Logger logger = LogManager.getLogger();
	
	private Table table = null;
	private Map<Integer,String> idx2columnName = null;
			
	public DBaseInputHandler(GSSurveyType surveyType, String databaseFilename) {

		super(surveyType, databaseFilename);
		
		this.table = null;
		this.idx2columnName = null;
	}
	
	public DBaseInputHandler(GSSurveyType surveyType, File databaseFile) {

		super(surveyType, databaseFile);
		
		this.table = null;
		this.idx2columnName = null;
	}
	
	protected Table getDBFTable() {

		if (table == null) {
			table = new Table(new File(surveyCompleteFile));
	
			try {
			    table.open(IfNonExistent.ERROR);
	
			    List<Field> fields = table.getFields();
			    
			    idx2columnName = new HashMap<>();
			    
			    for (int i=0; i<fields.size(); i++) {
			    	idx2columnName.put(i, fields.get(i).getName());
			    }
			    
			    /*
			    for (final Field field : fields)
			    {
			        System.out.println("Name:         " + field.getName());
			        System.out.println("Type:         " + field.getType());
			        System.out.println("Length:       " + field.getLength());
			        System.out.println("DecimalCount: " + field.getDecimalCount());
			        System.out.println();
			    }
				*/
				/*
			    //table.getFields().
	
			    
			} catch (CorruptedTableException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("the database "+surveyCompleteFile+" seems corrupted", e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("error reading data from database "+surveyCompleteFile, e);
			} 
			
		}
		return table;
		
	}
	*/

	@Override
	public String getName() {
		return new File(surveyCompleteFile).getName();
	}

	@Override
	public int getLastRowIndex() {

		return 0;//getDBFTable().getRecordCount();
	}

	@Override
	public int getLastColumnIndex() {
		return 0; // getDBFTable().getFields().size();
	}

	@Override
	public int getFirstRowIndex() {
		return 0;
	}

	@Override
	public int getFirstColumnIndex() {
		return 0;
	}

	@Override
	public String read(int rowIndex, int columnIndex) {
		return "";
		/*
		try {
			return getDBFTable().getRecordAt(rowIndex).getTypedValue(idx2columnName.get(columnIndex)).toString();
		} catch (CorruptedTableException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		*/
	}
	
	@Override
	public List<String> readLine(int rowIndex) {
		/*
		Record record;
		try {
			record = getDBFTable().getRecordAt(rowIndex);
		} catch (CorruptedTableException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
				
		return getDBFTable().getFields().stream().map(f -> record.getTypedValue(f.getName()).toString()).collect(Collectors.toList());
		*/
		return Collections.emptyList();
	}

	@Override
	public List<List<String>> readLines(int fromFirstRowIndex, int toLastRowIndex) {
		List<List<String>> res = new ArrayList<>(toLastRowIndex-fromFirstRowIndex);
		for (int i=fromFirstRowIndex; i<toLastRowIndex; i++)
			res.add(readLine(i));
		return res;
	}

	@Override
	public List<String> readLines(int fromFirstRowIndex, int toLastRowIndex, int columnIndex) {
		
		List<String> res = new ArrayList<>(toLastRowIndex-fromFirstRowIndex);
		
		/*
		final Table table = getDBFTable();
		final String colName = idx2columnName.get(columnIndex);
		
		for (int i=fromFirstRowIndex; i<toLastRowIndex; i++)
			try {
				res.add(table.getRecordAt(i).getTypedValue(colName).toString());
			} catch (CorruptedTableException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		*/
		
		return res;
	}

	@Override
	public List<List<String>> readLines(int fromFirstRowIndex, int toLastRowIndex, int fromFirstColumnIndex,
			int toLastColumnIndex) {
		
		List<List<String>> res = new ArrayList<>(toLastRowIndex-fromFirstRowIndex);
		/*
		final Table table = getDBFTable();
		
		final List<String> colNames = table.getFields().subList(fromFirstColumnIndex, toLastColumnIndex).stream().map(f -> f.getName()).collect(Collectors.toList());
		
		for (int i=fromFirstRowIndex; i<toLastRowIndex; i++)
			try {
				Record record = table.getRecordAt(i);
				res.add(colNames.stream().map(n -> record.getTypedValue(n).toString()).collect(Collectors.toList()) );
			} catch (CorruptedTableException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		*/	
		return res;
	}

	@Override
	public List<String> readColumn(int columnIndex) {
		/*
		final Table table = getDBFTable();
		List<String> res = new ArrayList<>(table.getRecordCount());
		
		final String colName = idx2columnName.get(columnIndex);
		
		for (int i=0; i<table.getRecordCount(); i++)
			try {
				res.add(table.getRecordAt(i).getTypedValue(colName).toString());
			} catch (CorruptedTableException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		return res;
		*/
		return Collections.emptyList();
	}

	@Override
	public List<List<String>> readColumns(int fromFirstColumnIndex, int toLastColumnIndex) {
		/*
		final Table table = getDBFTable();
		
		List<List<String>> res = new ArrayList<>(table.getRecordCount());
		
		final List<String> colNames = table.getFields().subList(fromFirstColumnIndex, toLastColumnIndex).stream().map(f -> f.getName()).collect(Collectors.toList());
		
		for (int i=0; i<table.getRecordCount(); i++)
			try {
				Record record = table.getRecordAt(i);
				res.add(colNames.stream().map(n -> record.getTypedValue(n).toString()).collect(Collectors.toList()) );
			} catch (CorruptedTableException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		return res;
		*/
		return Collections.emptyList();
	}

	@Override
	public List<String> readColumns(int fromFirstColumnIndex, int toLastColumnIndex, int rowIndex) {
		
		/*
		final Table table = getDBFTable();
		final List<String> colNames = table.getFields().subList(fromFirstColumnIndex, toLastColumnIndex).stream().map(f -> f.getName()).collect(Collectors.toList());

		Record record;
		try {
			record = table.getRecordAt(rowIndex);
		} catch (CorruptedTableException | IOException e) {
			throw new RuntimeException(e);
		}
		
		return colNames.stream().map(n -> record.getTypedValue(n).toString()).collect(Collectors.toList());
		*/
		return Collections.emptyList();

	}

	@Override
	public List<List<String>> readColumns(int fromFirstRowIndex, int toLastRowIndex, int fromFirstColumnIndex,
			int toLastColumnIndex) {
		/*
		final Table table = getDBFTable();
		
		List<List<String>> res = new ArrayList<>(table.getRecordCount());

		final List<String> colNames = table.getFields().subList(fromFirstColumnIndex, toLastColumnIndex).stream().map(f -> f.getName()).collect(Collectors.toList());

		for (int i=fromFirstRowIndex; i<toLastRowIndex; i++)
			try {
				Record record = table.getRecordAt(i);
				res.add(colNames.stream().map(n -> record.getTypedValue(n).toString()).collect(Collectors.toList()) );
			} catch (CorruptedTableException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		return res;
		*/
		return Collections.emptyList();
	}

	@Override
	public Map<Integer, Set<IValue>> getColumnHeaders(
			IGenstarDictionary<Attribute<? extends IValue>> dictionnary) {
		
		Map<Integer, Set<IValue>> res = new HashMap<>(dictionnary.getAttributes().size());
				
		// prepare attributes information
		Map<String,Attribute<? extends IValue>> name2attribute = 
				dictionnary.getAttributes()
							.stream()
							.collect(Collectors.toMap(a->a.getAttributeName(), a->a));
		
		/*
		
		// prepare table information
		final Table table = getDBFTable();
		final List<Field> fields = table.getFields();
		
		for (int iField = 0; iField < fields.size(); iField++) {
		
			Field currentField = fields.get(iField);
			
			Attribute<? extends IValue> att = name2attribute.get(currentField.getName());
			
			if (att == null)
				// ignore missing attributes
				continue;
			
			res.put(iField, att.getValueSpace().getValues().stream().collect(Collectors.toSet()));
		}
		*/
		
		return res;
		
	}

	@Override
	protected void finalize() throws Throwable {
		/*
		if (table != null) {
		    try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.warn("error while closing the database table "+surveyCompleteFile, e);
			}  
		    table = null;
		}
		*/
		super.finalize();
	}

	@Override
	public Map<Integer, Set<IValue>> getRowHeaders(
			IGenstarDictionary<Attribute<? extends IValue>> dictionnary) {
		return Collections.emptyMap();
	}
	
	/**
	 * WARNING
	 * 
	 * @param f
	 * @param t
	 * @return
	 */
	/*
	protected GSEnumDataType getGosplDatatypeForDatabaseType(Field f, Table t) {
		switch (f.getType()) {
		case NUMBER:
			// hard to say... maybe it's integer, maybe not...
			// let's have a look to the table
			if (t.getRecordCount() == 0)
				// well, we have no clue, let's follow theory
				return GSEnumDataType.Integer;
			else {
				String strVal;
				try {
					strVal = t.getRecordAt(0).getTypedValue(f.getName()).toString();
				} catch (CorruptedTableException e1) {
					return GSEnumDataType.Integer;
				} catch (IOException e1) {
					return GSEnumDataType.Integer;
				}
				try {
					Double.parseDouble(strVal);
					return GSEnumDataType.Continue;
				} catch (NumberFormatException e) {
					try {
						Integer.parseInt(strVal);
						return GSEnumDataType.Integer;
					} catch (NumberFormatException e2) {
						return GSEnumDataType.Nominal;
					}
				}
			}
		case FLOAT:
			return GSEnumDataType.Continue;
		case CHARACTER:
		case MEMO:
			return GSEnumDataType.Nominal;
		case LOGICAL:
			return GSEnumDataType.Boolean;
		default:
			throw new IllegalArgumentException();
		}
	}
	*/

	@Override
	public Map<Integer, Attribute<? extends IValue>> getColumnSample(
			IGenstarDictionary<Attribute<? extends IValue>> dictionnary) {

		Map<Integer, Attribute<? extends IValue>> res = new HashMap<>(dictionnary.size());
				
		// prepare attributes information
		Map<String,Attribute<? extends IValue>> name2attribute = 
				dictionnary.getAttributes()
						.stream()
						.collect(Collectors.toMap(a->a.getAttributeName(), a->a));
		
		// prepare table information
		/*
		final Table table = getDBFTable();
		final List<Field> fields = table.getFields();
		
		// create an index mapping each index of column with the corresponding attribute
		for (int iField = 0; iField < fields.size(); iField++) {
		
			Field currentField = fields.get(iField);
			
			Attribute<? extends IValue> att = name2attribute.get(currentField.getName());
			
			if (att == null)
				// ignore missing attributes
				continue;
			
			res.put(iField, att);
			
			// check if we should, in any way, complete the attribute information
			if (att.getValueSpace().getType() == null) {
				// data type is not defined. Let's define it. 
				// what is the datatype in the field ?
				GSEnumDataType dt = null;
				try {
					dt = getGosplDatatypeForDatabaseType(currentField, table);
				} catch (IllegalArgumentException e) {
					logger.warn("unable to automatically define the type for field "+currentField+"; will treat it as a Nominal string value", e);
					dt = GSEnumDataType.Nominal;
				}
				
				// update this attribute !
				logger.info("refining the properties of attribute {} based on database content: its type is now {}", att, dt);

				try {
					Attribute<? extends IValue> updatedAtt = AttributeFactory.getFactory()
							.createAttribute(att.getAttributeName(), dt, att.getValueSpace().getValues().stream()
									.map(IValue::getStringValue).collect(Collectors.toList()));
					dictionnary.getAttributes().remove(att);
					dictionnary.getAttributes().add(updatedAtt);
					name2attribute.put(currentField.getName(), updatedAtt);
				} catch (GSIllegalRangedData e) {
					// unable to do that; don't touch this attribute
					logger.warn("error while trying to refine the definition of attribute {} with type {}; leaving it untouched", att, dt); 
					e.printStackTrace();
				}
				
			}
			
		}
		*/
		return res;
	}
	
	@Override
	public CSVReader getBufferReader(boolean skipHeader) { throw new UnsupportedOperationException(); }
	
}
