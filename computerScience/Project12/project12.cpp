/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 12
 * 31/01/06
 *
 * This program finds all 3-digit and 4-digit narcissistic numbers.
 * An n-digit narcissistic number has the following property (in base 10):
 *     a,b,c,... z are the digits of the number
 *     a^n + b^n + c^n + ... z^n = the number
 * The smallest (beyond one digit) narcissistic number is 153, because:
 * 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153
 *
 * Considering that this property is number syntax (base-10) dependent,
 * there is no pattern or appreciable restrictions that are on candidates
 * for narcissistic numbers. This program uses a brute-force approach, checking
 * every value in range for narcissism.
 */

#include <iostream>
#include <conio.h>
#include <ctime>

using namespace std;

/*
 * Breaks up the lowest digits in the given number, taking the power of each
 * one nd summing them. Returns true only if the number is a narcissist.
 */
bool isNarcissistic(int, unsigned int);

/*
 * Recursively takes a power to an exponent; integer multiplication.
 */
int pow(int, int);

int main()
{

    // Holds a flag telling if the working label is showing
    bool displayingWorking = false;

    // Variables used to track how long the computer has taken
    // to find the net narcissist
    time_t lastTime, thisTime;
    time(&lastTime);
    time(&thisTime);

    for (int numberSize = 3; numberSize <= 9; ++numberSize)
    {

        cout << numberSize << "-digit Narcissistic Numbers:\n\n";

        // Calculate the range of values to check
        const int minimum = pow(10, numberSize - 1);
        const int maximum = pow(10, numberSize);

        for (int number = minimum; number < maximum; ++number)
        {

            // Track the "working..." label
            if (! displayingWorking)
            {

                cout << "   Working...\r";
                displayingWorking = true;
                
            }

            // Display the number if passed
            if (isNarcissistic(number, numberSize))
            {

                cout << "                         \r";
                cout << "   " << number << "\n";
                displayingWorking = false;
                
                time(&thisTime);
                
                if ((thisTime - lastTime) > 1) // More than one second
                {

                    cout << "\a"; // Alert because user's been waiting a while
                    time(&lastTime);

                }
                
            }
                
        }

        // A "working..." would be left over at the bottom of one section,
        // and wouldn't show up the first time in the second section
        displayingWorking = false;
        cout << "                  \r";
        cout << "\n";
    
    }

    cout << "That's all";
    getch();
    return 0;

}

bool isNarcissistic(int candidate, unsigned int size)
{

    if (candidate < 1)
        throw candidate; // Illegal candidate

    int placeFactor = 1; // The factor representing the worth of each place
    int sum = 0;         // The running sum of the powers of the place values

    for (int place = 0; place < size; ++place)
    {

        sum += pow((candidate / placeFactor) % 10, size);
        placeFactor *= 10;
        
    }
    
    // Is the sum of the powers of the digits equal to the number?
    return sum == candidate;

}

int pow(int base, int power)
{

    return power == 0 ? 1 : base * pow(base, power - 1);

}
