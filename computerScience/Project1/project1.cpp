/**
 * Robert Koeninger
 * 20ECES121-001
 * Project 1
 * 10/01/06
 *
 * This program displays some of my information and quits.
 */

#include <iostream>  // Include for cout and cin
#include <conio.h>   // Include for getch()

using namespace std;

// Program entry point, uses no command-line argumets
int main()
{

    // Program description
    cout << "This is some of my information.\n\n";
    
    // Name
    cout << "Robert Koeninger\n";
    
    // Major
    cout << "Computer Engineering\n";
    
    // Street address
    cout << "523 St. Thomas Ct.\n";
    
    // Last four digits of SS#
    cout << "5336\n\n";
    
    cout << "Press any key to exit";
    getch();
    
    return 0;

}
