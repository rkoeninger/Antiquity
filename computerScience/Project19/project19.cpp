/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 19
 * 21/02/06
 *
 * Calculates factorials using a recursive method.
 */

#include <iostream>
#include <conio.h>

using namespace std;

unsigned long int factorial(unsigned int argument);

int main()
{

    int input = 0;
    
    for (;;)
    {

        cout << "Enter a non-negative integer: ";
        cin >> input;
        
        if (input < 0) // Exit code
        {

            cout << "\nThe End";
            getch();
            return 0;

        }
        else if (input > 33) // Result is too large
        {

            cout << "Overload\n\n";

        }
        else // Normal conditions
        {
        
            cout << input << "! is " << factorial((unsigned) input) << "\n\n";
            
        }

    }

}

unsigned long int factorial(unsigned int argument)
{

    if (argument < 0)
        throw argument;

    return argument == 0 ? 1 : argument * factorial(argument - 1);

}
