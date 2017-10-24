import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;


public class ObjectiveFunction {
	
	private String functionText;
	private FunctionParser parser;
	private PolishCalculator calc;
	private VariableTable varTable = new VariableTable();
	private int limit;

	private Course currentCourse;
	private Professor currentProf;
	
	public ObjectiveFunction() { 
		functionText = "0";
	}
	
	public ObjectiveFunction(String functionText) {
		this.functionText = functionText;
		initialize();
	}
	
	public void initialize() { 
		parser = new FunctionParser(functionText);
		calc = new PolishCalculator(parser.getOutput());
		calc.setVarTable(varTable);
	}
	
	public void load(String filename) {
		try {
			StringTokenizer st;
			String line;
			FunctionVariable v;
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    
			while ((line = br.readLine()) != null)   {
				st = new StringTokenizer(line,",");
				line = st.nextToken();
				
				if(line.equals("eq"))	{
					this.functionText = st.nextToken();
				}
				else if(line.equals("var")) {	
					v = new FunctionVariable(st.nextToken(),
							FunctionVariable.FunctionVariableType.valueOf(st.nextToken()), 
							st.nextToken());
					this.addVariable(v);
				}
				else if(line.equals("lmt")){
					this.limit = Integer.valueOf(st.nextToken()); 
				}
			}

			in.close();
		}
		catch(Exception e) {
			System.err.println("Error: Config file could not be loaded:\n" + e);
		}
	}
	
	public void save(String filename) {
		try{ 
			FileWriter fstream = new FileWriter(filename);
        	BufferedWriter out = new BufferedWriter(fstream);

        	//Write the text of the function
        	out.write("eq," + functionText + "\r\n");
        	
        	Iterator<FunctionVariable> i = varTable.getVariableIterator();
        	FunctionVariable v;
        	
        	//Write course attributes to file
        	while(i.hasNext()) {
        		v = i.next();
        		out.write("var," + v.getVariableName() + "," +
        				v.getType().toString() + "," + 
        				v.getAttributeName() + "\r\n");
        	}
        	
        	out.close();
		}
		catch (Exception e) {
			System.err.println("Error: Config file could not be written:\n" + e.getMessage());
		}
	}
	
	public boolean isValid() {
		return calc.isValid();
	}
	
	private boolean hasVariable(String v) { return varTable.contains(v); }
	
	public void addVariable(FunctionVariable v) { varTable.add(v); }
	
	public float value(Course c, Professor p) {
		return calc.Execute(p,c);
	}
	
	public Professor bestProfessor(Course c, Iterator<Professor> pIt) {
		//Minimum value
		if(c != null && pIt.hasNext()) {
			Professor p, pBest = pIt.next();
			p = pBest;
			float best = value(c,p);
			float currentValue = best;
			while(pIt.hasNext()) {
				p = pIt.next();
				currentValue = value(c,p);
				if(currentValue < best && p.getAssigns() < limit) {
					pBest = p;
					best = currentValue;
				}
			}
			return pBest;
		}
		return null;
	}

}
