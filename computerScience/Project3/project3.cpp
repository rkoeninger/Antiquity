/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 3
 * 10/01/06
 *
 * Accepts a user-input radius and finds and displays the circumference of a
 * circle with that radius. The program displays an error message and
 * terminates early if the radius is negative. The namespace "mathconstants"
 * exists to demonstrate the use of namespaces.
 */

#include <iostream>  // Include for cout and cin
#include <conio.h>   // Include for getch()

using namespace std;

// namespace containing mathematical tools
namespace math
{

    namespace constants
    {

         // The constant value of pi, precise to 30 decimal places
         const double PI = 3.1415926535897932384626433832795;

    }

}

// A function that takes a radius and returns the circumference of a circle
// with that radius.
double findCircumference(int radius);

// Program entry point, uses no command-line argumets
int main()
{

    int radius = 0;
    
    // Program description
    cout << "This program accepts an integer radius from the user\n";
    cout << "a displays the circumference of a circle with that radius\n\n";
    
    // User-input prompt
    cout << "Radius: ";
    cin >> radius;
    cout << "\n";
    
    // Error condition, show message and quit
    if (radius < 0)
    {

        cout << "ERROR: Negative radii are ot allowed.\n";
        system("pause");
        return 1;

    }
    
    // Find and display circumference
    cout << "Circumference: " << findCircumference(radius) << "\n\n";

    cout << "Press any key to exit";
    getch();
    return 0;

}

double findCircumference(int radius)
{

    using math::constants::PI;
    
    // C = pi*d = 2*pi*r
    return 2.0 * PI * radius;

}
