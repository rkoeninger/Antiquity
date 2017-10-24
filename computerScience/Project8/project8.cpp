/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 8
 * 24/01/06
 *
 * Merge Sort, Multi-Size Version
 *
 * Calculates the number of steps to reach the Kaprekar's Constant using
 * Kaprekar's Process on a 3-digit number entered by the user an entry
 * <= 0 will quit the program.
 */

#include <conio.h>   // Include for getch()
#include <iostream>  // Include for cout and cin
using std::cout;
using std::cin;

#include <cmath>     // Include for pow()
using std::pow;

#include <vector>    // Include for vector<> type
using std::vector;

typedef unsigned short digit; // Refer to place values as digits

/*
 * This constant identifies the number size because we're
 * using n-digit Kaprekar Processes.
 */
#define INTEGER_SIZE 3

/*
 * Kaprekar Constants:
 * Digits  Values
 * -----------------
 * 3       495
 * 4       6174
 *
 * Won't work above 9-digit on 32-bit systems
 *
 * For this program (n-digits), this is Kaprekar's Constants
 */
#define KAPREKAR_CONSTANT 495

/*
 * The number of iterations of Kaprekar's Process that will be run
 * before the loop is considered to have "runaway," either because of
 * an incorrect number size, memory restrictions or invalid input.
 */
#define RUNAWAY_LOOP_COUNT 10000

/*
 * Recursively finds the number of Kaprekar Process steps to get to
 * Kaprekar's constant for the number size we're using.
 */
int kaprekarProcess(long);

/*
 * Sorts the given array in place.
 * true for accending, false for descending.
 *
 * Implemented as a recursive Merge Sort.
 */
void sort(vector<digit>&, bool);

/*
 * Recursive helper functions for sort(),
 * should not be called from anywhere else.
 */
void sortSubArray(vector<digit>&, bool, int, int);
void mergeSubArrays(vector<digit>&, bool, int, int, int, int);

/*
 * Stores the place values of the given integer in the vector.
 *
 * The leftmost or [0] position in the vector
 * represents the highest place value.
 */
void deconstructDecimalInteger(vector<digit>&, long);

/*
 * Rebuilds an integer from the place values in the given vector.
 *
 * The leftmost or [0] position in the vector
 * represents the highest place value.
 */
long constructDecimalInteger(vector<digit>);

/*
 * Returns true if all digits are equal (the last INTEGER_SIZE digits).
 */
bool allDigitsEqual(long);

/*
 * Main method contains a loop that accepts input, process it and prompts
 * for more input.
 */
int main()
{

    // For 3-digit numbers,
    // highest = (10^3)-1 = 1000-1 = 999
    // lowest = (10^(3-1)) = 10^2  = 100
    const long highest = long(pow(10.0, INTEGER_SIZE) - 1);
    const long lowest = long(pow(10.0, INTEGER_SIZE - 1));
    long input; // The value inputted by the user

    // This loops only condition is that the console input is still working.
    // Can also be escaped by the return statement.
    while (cin)
    {

        // Prompt user for input
        cout << "Enter a positive, " << INTEGER_SIZE << "-digit integer: ";
        cin >> input;

        if (input <= 0) // User indication to quit
        {

            cout << "\nGoodbye!";
            getch();
            return 0;
        
        }
        else if (input > highest) // Input is too large
        {

            cout << "\nERROR: Number out of range, maximum = ";
            cout << highest << "\n\n\n";

        }
        else if (input < lowest) // Input is too small
        {

            cout << "\nERROR: Number out of range, minimum = ";
            cout << lowest << "\n\n\n";

        }
        else if (allDigitsEqual(input)) // All digits in input are equal
        {

            cout << "\nERROR: Number's digits are all equal\n\n\n";

        }
        else
        {

            int steps = kaprekarProcess(input); // number of steps
            
            // Kaprekar's Constant for n-digit does 0 iterations
            if (steps == 0)
                steps = 1;
            
            cout << "\tThe number " << input << " required ";
            cout << steps << " step" << (steps > 1 ? "s" : "");
            cout << " to reach " << (KAPREKAR_CONSTANT) << "\n\n";

        }

    }

}

int kaprekarProcess(long input)
{

    // Counts the number of steps necessary to reach Kaprekar's Constant
    int steps = 0;

    // Loop until a value is returned
    for (;;)
    {

        if (input == KAPREKAR_CONSTANT) // The process is over
            return steps;
        else if (input == 0)
        {

            // Catch any problems that might arise from a defective entry
            // or incorrect number size
            cout << "An error must have occurred, input is zero";
            getch();
            exit(1);

        }
        else if (steps == RUNAWAY_LOOP_COUNT)
        {

            // Terminate the process if it is out of control
            cout << "Loop has run too many iterations,\n";
            cout << "an error must have occurred";
            getch();
            exit(2);

        }

        // Deconstruct the input number into two arrays
        vector<digit> accendingPlaceValues(INTEGER_SIZE);
        vector<digit> descendingPlaceValues(INTEGER_SIZE);
        deconstructDecimalInteger(accendingPlaceValues, input);
        deconstructDecimalInteger(descendingPlaceValues, input);

        // Sort one array accending, the other descending
        sort(accendingPlaceValues, true);  // true  = accending sort
        sort(descendingPlaceValues, false); // false = descending sort

        // Rebuild numbers from sorted place values
        const long accendingInteger = constructDecimalInteger(accendingPlaceValues);
        const long descendingInteger = constructDecimalInteger(descendingPlaceValues);
        
        // Subtract the descending (larger) from the accending (smaller) number
        input = descendingInteger - accendingInteger;
        steps++;

    }

}

void sort(vector<digit> &array, bool accending)
{

    // Sort the entire array (0 through size-1)
    sortSubArray(array, accending, 0, array.size() - 1);

}

void sortSubArray(vector<digit> &array, bool accending, int begin, int end)
{

    // If length of subsection is one or less, then there's nothing to sort
    if ((end - begin) < 1)
        return;

    // Find the boundries in the sebsection
    const int middle1 = (begin + end) / 2;
    const int middle2 = middle1 + 1;
    
    // Recursively sort subsections and remerge
    sortSubArray(array, accending, begin, middle1);
    sortSubArray(array, accending, middle2, end);
    mergeSubArrays(array, accending, begin, middle1, middle2, end);

}

void mergeSubArrays(vector<digit> &array, bool accending,
int begin, int middle1, int middle2, int end)
{

    int leftIndex = begin;    // Position in first subsection to start merging
    int rightIndex = middle2; // Position in second subsection to start merging
    int copyIndex = begin;    // Position in copy vector to place values in
    vector<digit> copy(array.size()); // Array for merging (dirty work)
    
    // Move digits to the working vector in order
    while ((leftIndex <= middle1) && (rightIndex <= end))
    {

        if (accending)
        {

            copy[copyIndex++] = (array[leftIndex] <= array[rightIndex] ?
            array[leftIndex++] : array[rightIndex++]);
            
        }
        else
        {

            copy[copyIndex++] = (array[leftIndex] >= array[rightIndex] ?
            array[leftIndex++] : array[rightIndex++]);
            
        }

    }

    // Finish copying any characters in remaining subsection
    if (leftIndex == middle2)
    {

        while (rightIndex <= end)
            copy[copyIndex++] = array[rightIndex++];
            
    }
    else
    {

        while (leftIndex <= middle1)
            copy[copyIndex++] = array[leftIndex++];
            
    }
    
    // Move sorted subsection characters back to original array
    for (int x = begin; x <= end; ++x)
        array[x] = copy[x];

}

void deconstructDecimalInteger(vector<digit> &placeValues, long integer)
{

    // Increasing value of digit
    long placeFactor = 1;
    
    // Divide out each place value in "integer"
    for (int place = placeValues.size() - 1; place >= 0; --place)
    {

        placeValues[place] = (integer / placeFactor) % 10;
        placeFactor *= 10;

    }
    
}
 
long constructDecimalInteger(vector<digit> placeValues)
{

    // Running total, Increasing value of digit
    long integer = 0, placeFactor = 1;
    
    // Multiply each element in "placeValues" by its place value
    // and add to "integer"
    for (int place = placeValues.size() - 1; place >= 0; --place)
    {

        integer += (placeFactor * placeValues[place]);
        placeFactor *= 10;

    }
    
    return integer;

}

bool allDigitsEqual(long integer)
{

    // Deconstruct number into its digits
    vector<digit> digits(INTEGER_SIZE);
    deconstructDecimalInteger(digits, integer);

    // If any digit is not equal to it's previous digit
    // Then not all digits are equal (return false)
    for (int place = 1; place < digits.size(); ++place)
        if (digits[place] != digits[place - 1])
            return false;
            
    return true; // All digits are equal (return true)

}
