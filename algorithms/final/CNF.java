import java.util.Arrays;
import java.util.Random;
import java.io.*;

public class CNF{
	public static void main(String[] args) throws IOException{
		PrintWriter outfile = new PrintWriter(
		new FileOutputStream(new File("cnf-results.csv")));
		int[] ns = {1,3,5};
		int ns_size = 3;
		int n;
		int m_min = 3, m_max = 13;
		int probTotal = 100;
		int successful;
		double avg;
		for (int a = 0; a < ns_size; ++a){
			n = ns[a];
System.out.println("n=" + n);
			outfile.println("n =," + n);
			outfile.println("m =," + m_min + "," + m_max);
			outfile.println("m's:,averages:");
			for (int m = m_min; m <= m_max; ++m){
System.out.print("m="+m+" ");
			successful = 0;
				for (int k = 0; k < probTotal; ++k){
					if (getResultBruteforce(generateRandom3CNF(n,m))){
						successful++;
					}else{
System.err.println("---NOT SATISFIED---");
					}
				}
				avg = ((double)successful) / ((double)probTotal);
System.out.println("succ=" + successful + " avg=" + avg);
				outfile.println(m + "," + avg);
			}

System.out.println();
		}
		outfile.flush();
		outfile.close();
	}
	
	// Returns an array of boolean values that are a solution to the CNF
	// retval[0] is the value of X1, retval[1] is the value of X2, etc
	// returns null if no result found
	// currently uses a brute-force function
	static boolean getResultBruteforce(CNF cnf){
		cnf.valTable = new boolean[cnf.variableCount];
		for (long valueFlag = 0; valueFlag < (1 << cnf.variableCount);
		++valueFlag){
//System.out.println(valueFlag);
			for (int x = 0; x < cnf.valTable.length; ++x){
				cnf.valTable[x] = (valueFlag & (1 << x)) != 0;
			}
			if (cnf.getValue()){
//System.out.println(cnf.toString());
//System.out.println(Arrays.toString(values));
				return true;
			}
		}
		return false;
	}

	// Random 3-CNF generator for N number of clauses with
	// M unique variables
	// n = numClauses, m = numVars
	static CNF generateRandom3CNF(int numClauses, int numVars){
		CNF cnf = new CNF();
		Random rand = new Random();
		cnf.clauses = new Clause[numClauses];
		cnf.variableCount = numVars;
		boolean[] varsUsed = new boolean[numVars];
		for (int x = 0; x < cnf.clauses.length; ++x){
			Arrays.fill(varsUsed, false);
			cnf.clauses[x] = new Clause();
			cnf.clauses[x].vars = new Variable[3];
			for (int y = 0; y < cnf.clauses[x].vars.length; ++y){
				boolean looping = true;
				cnf.clauses[x].vars[y] = new Variable();
				while (looping){
					int newVar = rand.nextInt(numVars) + 1;
					if (! varsUsed[newVar - 1]){
						cnf.clauses[x].vars[y].id = newVar;
						cnf.clauses[x].vars[y].negated = rand.nextBoolean();
						varsUsed[newVar - 1] = true;
						looping = false;
					}
				}
			}
		}
		return cnf;
	}

	/* Class structure for generic CNF */
	
	// list of clauses in the CNF
	private Clause[] clauses;
	private int variableCount;
	private static boolean[] valTable;

	boolean getValue(){
		for (int x = 0; x < clauses.length; ++x){
			if (!clauses[x].getValue())
				return false;
		}
		return true;
	}

	public String toString(){
		String retval = "";
		for (int x = 0; x < clauses.length; ++x){
			for (int y = 0; y < clauses[x].vars.length; ++y){
				retval += (clauses[x].vars[y].negated ? "-" : "");
				retval += clauses[x].vars[y].id + " ";
			}
			retval += "\n";
		}
		return retval;
	}

	// each clause contains an array of variables
	private static class Clause{
		Variable[] vars;
		boolean getValue(){
			for (int x = 0; x < vars.length; ++x){
				boolean v = vars[x].getValue();
				if (vars[x].negated){
					v = !v;
				}
				if (v)
					return true;
			}
			return false;
		}
	}

	// each variable has an id associated with it and if it is negated
	// in the clause
	private static class Variable{
		int id;
		boolean negated;
		boolean getValue(){
			return valTable[id - 1];
		}
	}
}
