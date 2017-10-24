
public class FunctionVariable {
	public enum FunctionVariableType {COURSE_ATTRIBUTE,PROFESSOR_ATTRIBUTE};
	private String var; 
	private String attributeName;
	private FunctionVariableType type;
	
	public FunctionVariable(String var, FunctionVariableType type, String attribute) {
		this.var = var;
		this.type = type;
		this.attributeName = attribute;
	}
	
	public String getVariableName() { return var; }
	public String getAttributeName() { return attributeName; }
	public FunctionVariableType getType() { return type; }
	public int hashCode() { return var.hashCode(); }
}
