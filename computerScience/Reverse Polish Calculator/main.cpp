/*
 * Robert Koeninger
 * Section 002
 * Project 3 - Reverse Polish Calculator
 * Friday, May 12, 2006
 *
 * This include file contains the client program for the calculator.
 */

#include <iostream>
#include <sstream>
#include <conio.h>
#include <string>
#include "stack.h"

using namespace std;

bool readExpression(Stack*,string); // Puts expression tokens on stack,
                                    // false if fail
int idToken(string); // 0=bad, 1=operator, 2=number
int parseInt(string); // Returns number in string
int calculate(Stack*); // Recursively calculates the RPN expression tokens
int executeOp(char,int,int); // Runs the operator rep. by the char with
                             // 1st int on left side and 2nd on right

int main()
{

    Stack* stk;
    string line;
    
    for (;;)
    {

        cout << "Reverse Polish Notation Calculator\n";
        cout << "\n";
        cout << "Enter a PRN expression in a single line, with the symbols\n";
        cout << "separated only by spaces. Only the +, -, *, / and % (mod)\n";
        cout << "operators are supported. Numeric values are all be integers\n";
        cout << "positive or negative. Enter \"exit\" to quit.";
        cout << "\n\n";
        
        stk = new Stack(); // Create a new token stack
        
        bool success = false;

        while (! success){
            cout << "Input Expression: ";
            cin.clear();
            getline(cin, line);
            if (line == "exit" || line == "Exit" || line == "quit" || line == "Quit")
                return 0;
            success = readExpression(stk,line);
            if (! success){
                cout << "\nInvalid Expression, Please Re-Enter\n\n";
            }
        }
        
        cout << "\nResult: " << calculate(stk) << "\n\n";
        system("pause");
        system("cls");

    }
    
    return 1; // This should never happen

}

// Returns true if valid expression
// The tokens will be in the stack leftmost on bottom and rightmost on top
// If there is an error, the stack should be reset
bool readExpression(Stack* tokens,string line)
{

    string* token = new string();
    line += ' '; // Add a space as a final delimiter
    int opCount=0,numCount=0; // Keep counters to make sure correct map of
                              // operators to nums

    for (int lineIndex = 0; lineIndex < int(line.size()); ++lineIndex)
    {

        if (line[lineIndex] == ' ') // If a space, then skip
        {

            if (token->size() > 0) // The space delimits a token
            {

                // Check token for validity
                switch (idToken(*token)){
                    case 0: return false;
                    case 1: ++opCount;break;
                    case 2: ++numCount;break;
                }
                
                tokens->push(int(token));
                token = new string();

            }

            continue;

        }
        else if (line[lineIndex] < ' ') // Invalid whitespace character
        {

            return false;
            
        }
        
        *token += line[lineIndex]; // Append the current token

    }

    if ((opCount + 1) != (numCount)) // There is only one more num than operator
        return false;                // with binary operators
    
    return true;

}

int calculate(Stack* tokens)
{

    string tkn = *((string*)tokens->pop());
    
    if (idToken(tkn) == 2) // If a number, then return
        return parseInt(tkn);

    int rhs = calculate(tokens);
    int lhs = calculate(tokens);

    return executeOp(tkn[0], lhs, rhs);

}

int executeOp(char op, int lhs, int rhs)
{

    switch (op) // Select the operation and run it
    {

        case '+': return lhs + rhs;
        case '-': return lhs - rhs;
        case '*': return lhs * rhs;
        case '/': return lhs / rhs;
        case '%': return lhs % rhs;
        
        // What to do?
        default : cout << "An error has occurred that shouldn't have.\n";

    }
    
    return 0; // Keep compiler happy

}

// Returns 0 if invalid, 1 if operator, 2 if number
int idToken(string token)
{

    // The token is an operator
    if ((token == "+") || (token == "-") ||
    (token == "*") || (token == "/") || (token == "%"))
        return 1;
        
    for (int x = 0; x < token.size(); ++x)
    {

        if ((x == 0) && (token[x] == '-'))
            continue;
            
        if ((token[x] < '0') || (token[x] > '9'))
            return 0;

    }
    
    return 2;

}

// Returns the int in the given string
int parseInt(string str)
{

    istringstream stream(str);
    int result;
    stream >> result;
    return result;

}
