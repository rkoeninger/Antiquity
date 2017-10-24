/*
 * Robert Koeninger
 * Section 002
 * Project 1 - Large Integer ADT
 * Monday, April 17, 2006
 *
 * This source file contains the client program for testing.
 */

#include <iostream>
#include <conio.h>
#include <string>
#include "integer.h"

using namespace std;

Integer Factorial(const Integer&);

Integer Powers(Integer, Integer);

int main()
{

    Integer arg1, arg2;
    int choice;

    for (;;)
    {

        cout << "1 - Add\n";
        cout << "2 - Subtract\n";
        cout << "3 - Multiply\n";
        cout << "4 - Factorial\n";
        cout << "5 - Convert to Binary\n";
        cout << "6 - Power\n";
        cout << "7 - Exit\n";
        
        cout << "\nEnter your choice: ";
        cin >> choice;
        cout << "\n";
        cin.ignore();
        
        switch (choice)
        {

            case 1:
                cout << "Argument 1: ";
                cin >> arg1;
                cout << "Argument 2: ";
                cin >> arg2;
                cout << "Result:     " << (arg1 + arg2);
                break;

            case 2:
                cout << "Argument 1: ";
                cin >> arg1;
                cout << "Argument 2: ";
                cin >> arg2;
                cout << "Result:     " << (arg1 - arg2);
                break;

            case 3:
                cout << "Argument 1: ";
                cin >> arg1;
                cout << "Argument 2: ";
                cin >> arg2;
                cout << "Result:     " << (arg1 * arg2);
                break;

            case 4:
                cout << "Argument: ";
                cin >> arg1;
                cout << "Result:   " << Factorial(arg1);
                break;

            case 5:
                 cout << "Argument:  ";
                 cin >> arg1;
                 cout << "in Binary: " << arg1.ToBinaryString();
                break;

            case 6:
                cout << "Base:     ";
                cin >> arg1;
                cout << "Power:    ";
                cin >> arg2;
                cout << "Result:   " << Powers(arg1, arg2);
                break;

            case 7:
                return 0;
                break;

            default:
                cout << "Invalid Entry, please try again.\n\n";

        }
        
        cout << "\n\n";
        system("pause");
        system("cls");

    }

    return 0;

}

Integer Factorial(const Integer& arg)
{

    // The value 1
    Integer ONE;
    ONE.digits.resize(1);
    ONE.digits[0] = 1;
    ONE.sign = 1;
    
    if (arg.LessThanTwo())
    {

        return ONE;

    }
        
    return arg * Factorial(arg - ONE);

}

Integer Powers(Integer base, Integer power)
{

    if (power.sign == 0)
    {

        Integer ONE;
        ONE.digits.resize(1);
        ONE.digits[0] = 1;
        ONE.sign = 1;

    }

    // Convert power to binary
    string binPower = power.ToBinaryString();

    // result starts with a value of 'base'
    Integer result(base);

    // Scan left ot right, starting with second most significant place
    for (int z = 1; z < binPower.size(); ++z)
    {

        if (binPower[z] == '0')
            result *= result;
        else if (binPower[z] == '1')
            result *= result * base;

    }
    
    return result;

}
