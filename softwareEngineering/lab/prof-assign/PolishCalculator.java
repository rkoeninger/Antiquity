import java.util.*;

public class PolishCalculator {
	private VariableTable varTable;
	private Vector<String> input;
	private Stack<Float> result = new Stack<Float>();
	private boolean valid = true;
	
	public PolishCalculator( Vector<String> input )
	{
		this.input = input;
	}
	
	private boolean isNegative( String s )
	{
		if( s.length() > 1 && s.charAt(0) == '-' )
		{
			return true;
		}
		else
			return false;
	}
	
	public void setVarTable( VariableTable varTable )
	{
		this.varTable = varTable;
	}
	
	public boolean isValid(){
		Vector<String> variables = new Vector<String>();
		
		int resultCounter = 0;
		
		if ( input == null )
		{
			valid = false;
			return valid;
		}
		else
			for ( int i = 0; i < input.size(); i++ )
			{
				
				if( FunctionParser.isNumber(input.elementAt(i)) )
				{
					resultCounter++;
				}
				else
					if ( FunctionParser.isVariable(input.elementAt(i)) ) 
					{
						resultCounter++;
						variables.add(input.elementAt(i));
					}
					else						
						if ( FunctionParser.isOperator(input.elementAt(i)) && resultCounter >= 2 )
						{
							resultCounter--;
						}
						else
						{
							valid = false;
							return valid;
						}
			}
		
			for ( int i = 0; i < variables.size(); i++ )
			{
				if ( !varTable.contains(variables.elementAt(i)) )
				{
					valid = false;
					return valid;
				}
			}
			
			if ( resultCounter != 1 )
			{
				valid = false;
				return valid;
			}
			else 
				valid = true;
			
			return valid;
	}
	
	public float Execute( Professor p, Course c ){
		float a, b;
		for ( int i = 0; i < input.size(); i++ )
		{
			if ( input.elementAt(i).length() == 1 )
			{
				switch ( input.elementAt(i).charAt(0) )
				{
				case '+':
					// perform add
					b = result.pop().floatValue();
					a = result.pop().floatValue();
					result.push( new Float( a + b ) );
					break;
				case '-':
					// perform subtract
					b = result.pop().floatValue();
					a = result.pop().floatValue();
					result.push( new Float ( a - b ) );
					break;
				case '*':
					// perform multiplication
					b = result.pop().floatValue();
					a = result.pop().floatValue();
					result.push( new Float( a * b ) );
					break;
				case '/':
					// perform division
					b = result.pop().floatValue();
					a = result.pop().floatValue();
					result.push( new Float( a / b ) );
					break;
				case '^':
					// perform power
					b = result.pop().floatValue();
					a = result.pop().floatValue();
					result.push( new Float(Math.pow(a, b)) );
					break;
					default:
						// we have a single digit number/variable add it to stack
						if ( FunctionParser.isNumber( input.elementAt(i) ) )
						{
							result.push( new Float( input.elementAt(i) ) );
						}
						else
							// add value of single character variable
							if (FunctionParser.isVariable(input.elementAt(i)))
							{
								result.push(varTable.getVarValue(input.elementAt(i), c, p));
							}	
					break;
				}
			}
			else
				// Push multidigit number
				if ( FunctionParser.isNumber( input.elementAt(i) ) )
				{
					result.push(new Float(input.elementAt(i)));
				}
				else
					// Push negative of variable value 
					if ( FunctionParser.isVariable(input.elementAt(i)) && isNegative(input.elementAt(i)))
					{
						result.push(-1 * varTable.getVarValue(input.elementAt(i).substring(1), c, p));
					}
			}
		/*if ( result.size() == 1 )
		{*/
			return result.pop().floatValue();
		//}
		/*else
			// TODO: Produce error
			return Float.NaN;*/
	}
}
