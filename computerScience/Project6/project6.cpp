/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 6
 * 17/01/06
 *
 * Calculates the average of even integers between 32000 and 33000 including
 * both of these numbers. Also displays the number of integers included
 * have included in your calculation and what is the average as a float value.
 */

#include <iostream>  // Include for cout and cin
#include <conio.h>   // Include for getch()

using namespace std;

unsigned int consoleReadInteger(char*, int, int);

int main()
{

    long lowerBound, upperBound, count;
    double sum = 0.0, average = 0.0;

    // Get bounds from user
    lowerBound = consoleReadInteger("Lower Bound", 1, 1000000);
    upperBound = consoleReadInteger("Upper Bound", lowerBound, 1000000);
    
    // Find count of samples, sum and average
    count = (upperBound - lowerBound) + 1;

    for (long currentInt = lowerBound; currentInt <= upperBound; ++currentInt)
        sum += currentInt;
        
    average = sum / count;
    
    // Display results
    cout << "Count:\t" << count << "\n";
    cout << "Average:\t" << average << "\n";
    cout << "\n";
    cout << "Press the \'y\' key to find another average,\n";
    cout << "or any other key to exit";
    
    // If presses any key except 'y', then program terminates
    if (getch() == 'y')
    {

        system("cls");
        return main(); // Recursively run again

    }

    return 0;

}

unsigned int consoleReadInteger(char* fieldName, int min, int max)
{

    int input = 0;

    do
    {

        cout << fieldName << ": ";
        cin >> input;

        if ((input <= max) & (input >= min))
            return input;

        cout << "\nError: Invalid value, please enter again.\n";

    } while (true); // Only ends with return statement

}
