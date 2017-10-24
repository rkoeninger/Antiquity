
import java.util.*;



public class VariableTable {
	private Hashtable<Integer,FunctionVariable> varTable;
	
	public VariableTable() {
		varTable = new Hashtable<Integer,FunctionVariable>();
	}
	
	public void add(FunctionVariable v) {
		if(v != null)
			varTable.put(v.hashCode(), v);
	}
	
	public void remove(String v) { varTable.remove(v.hashCode()); }
	
	public float getVarValue(String v, Course c, Professor p) {
		FunctionVariable fv = varTable.get(v.hashCode());
		if(fv != null){
			if(fv.getType() == FunctionVariable.FunctionVariableType.COURSE_ATTRIBUTE 
					&& c != null) {
				return Float.valueOf(c.getAttribute(fv.getAttributeName()).getValue());
			}
			else if (fv.getType() == FunctionVariable.FunctionVariableType.PROFESSOR_ATTRIBUTE 
					&& p != null) {
				return Float.valueOf(p.getAttribute(fv.getAttributeName()).getValue());
			}
		}
		return 0;
	}
	
	public boolean contains(String v) {
		return varTable.containsKey(v.hashCode());
	}
	
	public boolean contains(FunctionVariable v) {
		return varTable.containsValue(v);
	}
	
	public Iterator<FunctionVariable> getVariableIterator() {
		return varTable.values().iterator();
	}
}
