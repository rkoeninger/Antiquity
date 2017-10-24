import java.util.*;

public class FunctionParser {
	
	private String rawEquation;
	private String reverseEquation = "";
	private String[] rawSplit;
	private Vector<String> output = new Vector<String>();
	private Stack<String> operator = new Stack<String>();
	private boolean valid = true;
	
	public FunctionParser( String rawEquation ){
		this.rawEquation = rawEquation;
		rawSplit = this.rawEquation.split(" ");
		createOutput();
		reversePolish();
	}
	
	private void createOutput(){
		for( int i = 0; i < rawSplit.length; i++ )
		{
			
			if( isNumber( rawSplit[i] ) || isVariable( rawSplit[i] ) )
			{
				output.add(rawSplit[i]);
			}
			else
			
				if ( isOperator( rawSplit[i] ) )
				{
					if( !operator.isEmpty() )
					{
						while ( !operator.isEmpty() && isLowerPrecedance( rawSplit[i], operator.peek() ) )
						{
							output.add( operator.pop() );
						}
						operator.push( rawSplit[i] );					
					}
					else
					{
						operator.push( rawSplit[i] );
					}
				}
				else
		
					if ( isLeftParenthesis( rawSplit[i] ) )
					{
						operator.push( rawSplit[i] );
					}
					else
					
						if ( isRightParenthesis( rawSplit[i] ) )
						{
							while( !operator.isEmpty() )/*&& !isLeftParenthesis( operator.peek() ) )*/
							{
								if ( !isLeftParenthesis( operator.peek() ) )
								{
									output.add(operator.pop());
								}
								else
								{
									break;
								}
							}
							if ( !operator.isEmpty() )/* && isLeftParenthesis( operator.peek() ))*/
							{
								operator.pop();
							}
							else
							{
								//TODO: Mismatched parenthesis
								valid = false;
								return;
							}
						}
						else
						{
							//TODO: We have an invalid string (not an operator or operand
							valid = false;
						}
		}
		
		while ( !operator.isEmpty() ) // && !isRightParenthesis( operator.peek() ) && !isLeftParenthesis( operator.peek() ))
		{
			output.add( operator.pop() );
		}		
	}
	
	private void reversePolish(){
		for( int i = 0; i < output.size(); i++ )
		{
			reverseEquation = reverseEquation + output.elementAt(i) + " ";
		}
	}
	
	public static boolean isVariable( String s ){
		if ( ( s.length() == 1 ) && ((s.charAt(0) <= 'Z' && s.charAt(0) >= 'A') || (s.charAt(0) <= 'z' && s.charAt(0) >= 'a')) ) 
		{
			return true;
		}
		else
			if ( ( s.length() == 2 ) && ( s.charAt(0) == '-' ) && ((s.charAt(1) <= 'Z' && s.charAt(1) >= 'A') || (s.charAt(1) <= 'z' && s.charAt(1) >= 'a')) )
			{
				return true;
			}
			else
				return false;
	}
	
	public static boolean isNumber( String s ){
		for ( int i = 0; i < s.length(); i++ )
		{
			if (  i == 0 && s.charAt(i) == '-' && s.length() > 1)
			{
				continue;
			}
			else
			{
				if ( s.charAt(i) == '.' )
				{
					continue;
				}
				else
				{
					if( s.charAt(i) > '9' || s.charAt(i) < '0' )
					{
						return false;
					}
				}
			}
		}
		return true;		
	}
	
	public static boolean isOperator( String s ){
		if ( s.length() == 1 && (s.charAt(0) == '+' || s.charAt(0) == '-' || s.charAt(0) == '*' || s.charAt(0) == '/' || s.charAt(0) == '^') ){
			return true;
		}
		else
			return false;
	}
	
	/*private boolean isGreaterPrecedance( String a, String b){
		if( ( a.charAt(0) == '*' || a.charAt(0) == '-' ) && ( b.charAt(0) == '*' || b.charAt(0) == '/' ) )
			return true;
		else
			return false;
	}*/
	
	private boolean isLowerPrecedance( String a, String b ){
	/*	if ( b.charAt(0) == '(')
		{
			return false;
		}
		else
			if ( a.charAt(0)== '-' || a.charAt(0) == '+')
			{
				return true;
			}
			else
				if ( b.charAt(0) == '/' || b.charAt(0) == '*' )
				{
					return true;
				}
				else
					return false;*/
		if ( b.charAt(0) == '(')
		{
			return false;
		}
		else
			if ( a.charAt(0)== '-' || a.charAt(0) == '+')
			{
				return true;
			}
			else
				if ( a.charAt(0) == '/' || a.charAt(0) == '*' )
				{
					if ( b.charAt(0) == '*' || b.charAt(0) == '/' || b.charAt(0) == '^' )
						return true;
					else
						return false;
				}
				else
					if ( a.charAt(0) ==  '^')
					{
						if ( b.charAt(0) == '^' )
						{
							return true;
						}
						else
							return false;
					}
					return false;
	}
	
	private boolean isLeftParenthesis( String a ){
		if ( a.length() == 1 && a.charAt(0) == '(' )
		{
			return true;
		}
		else
			return false;
	}
	
	private boolean isRightParenthesis( String a ){
		if ( a.length() == 1 && a.charAt(0) == ')' )
		{
			return true;
		}
		else
			return false;		
	}

	public String getRawEquation(){
		return rawEquation;
	}
		
	public String getReverseEquation(){
		return reverseEquation;
	}
	
	public Vector<String> getOutput(){
		if (valid)
			return output;
		else return null;
	}
}