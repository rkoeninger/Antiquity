/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 10
 * 31/01/06
 *
 * Tests instances of the class Date.
 */

#include <iostream>
#include <conio.h>
#include "date.h"

using namespace std;

int main()
{

    try
    {

        Date* currentDate = new Date();
        Date* someDate = new Date(2004, 2, 29); // Leap-day 2004

        cout << "Current Date:    ";
        (*currentDate).printDate();
        cout << "\nSome Date:       ";
        (*someDate).printDate();
        
    }
    catch (int illegalField)
    {

        cout << "Illegal field entered";

    }

    getch();
    return 0;

}
