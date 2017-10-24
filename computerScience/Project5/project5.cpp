/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 5
 * 17/01/06
 *
 * Display a multiplication table of 55 to 77 incremented by 2 (row) by
 * 47 to 40 decrementd by 1 (column). Display the table nicely with proper
 * headings. Implemented using only do...while loops
 * (instead of for and while loops.)
 *
 ******************************************************************************
 * This program has been expanded to build tables from user-defined row       *
 * and column mins and maxs and step sizes.                                   *
 * Table factors must be two-digit or less positive or negative integers,     *
 * this is that a factor f must be -100 < f < 100, step sizes are unsigned    *
 * Step sizes must be unsigned integers                                       *
 ******************************************************************************
 */
 
#include <iostream>  // Include for cout and cin
#include <conio.h>   // Include for getch()

using namespace std;

// The first column (containing each row factor) is only four units wide,
// all other columns are 6 units
#define FIRST_COL_WIDTH 4
#define COL_WIDTH       6

// Reads a row/column min/max/stepsize within the given bounds
// Does not return until the user does so
int consoleReadFactor(char*, int, int);

// Displays a signed integer in the given number of spaces, leaving only
// as many trailing spaces as are necessary
void consoleWriteInteger(int, int);

// Displays the specified number of spaces
void consoleWriteSpaces(unsigned int);

int main()
{

    // The minimum and maximum factors and step sizes for the
    // table rows and columns
    int rowMin, rowMax, rowStep;
    int colMin, colMax, colStep;
    
    // Program description to user
    
    do
    {
    
        // User input
        rowMin =  consoleReadFactor("Row Minimum", -99, 99);
        rowMax =  consoleReadFactor("Row Maximum", rowMin, 99);
        rowStep = consoleReadFactor("Row Step Size", 1, 99);
        colMin =  consoleReadFactor("Column Minimum", -99, 99);
        colMax =  consoleReadFactor("Column Maximum", colMin, 99);
        colStep = consoleReadFactor("Column Step Size", 1, 99);
    
        // Table header display (column factors)
        int colFactor = colMin;
        int rowFactor = rowMin;

        cout << "\n";
        consoleWriteSpaces(FIRST_COL_WIDTH);
            
        do
        {

            consoleWriteInteger(colFactor, COL_WIDTH);
            colFactor += colStep;

        } while (colFactor <= colMax);
        
        // End of table header
        cout << "\n";
    
        // Table display
        do
        {

            // Display row factor
            colFactor = colMin;
            consoleWriteInteger(rowFactor, FIRST_COL_WIDTH);

            // Display row of products
            do
            {

                consoleWriteInteger(rowFactor * colFactor, COL_WIDTH);
                colFactor += colStep;

            } while (colFactor <= colMax);

            rowFactor += rowStep;
            cout << "\n";

        } while (rowFactor <= rowMax);
        
        cout << "\n\n";
        cout << "Press the \'y\' key to make another table,\n";
        cout << "or any other key to exit";
        
        // If the user presses any key except 'y', then program terminates
        if (getch() != 'y')
            return 0;

        system("cls"); // clear the screen for another input

    } while (true); // Main program loop

}

int consoleReadFactor(char* fieldName, const int min, const int max)
{

    int input = 0;

    do
    {

        cout << fieldName << ": ";
        cin >> input;

        if ((input <= max) & (input >= min))
            return input;

        cout << "\nError: Invalid factor, please enter again.\n";

    } while (true); // Only ends with return statement

}

void consoleWriteInteger(const int product, int spacing)
{

    // Find number of spaces number takes to display
    if (abs(product) >= 10000)
        spacing -= 5;
    else if (abs(product) >= 1000)
        spacing -= 4;
    else if (abs(product) >= 100)
        spacing -= 3;
    else if (abs(product) >= 10)
        spacing -= 2;
    else
        spacing -= 1;
        
    // line-up negative signs in front of number line-up
    if (product >= 0)
        cout << " ";
    
    // Empty space or negative sign
    spacing -= 1;

    cout << product;
    consoleWriteSpaces(spacing);

}

void consoleWriteSpaces(unsigned int spaces)
{

    do
    {

        cout << " ";
        --spaces;
        
    } while (spaces > 0);

}
