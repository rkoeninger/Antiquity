/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 15
 * 14/02/06
 *
 * Plays with pointers and references.
 */


#include <iostream>
#include <conio.h>

using std::cout;

int main()
{

    int value = 1; // Make a new int variable
    cout << "value=1";
    cout << "\nvalue    is " << value;
    getch();
    int &valueRef = value; // Make reference
    cout << "\n\n&valueRef=value";
    cout << "\nvalue    is " << value;
    cout << "\nvalueRef is " << valueRef;
    getch();
    valueRef = 2; // Assign 2 to reference
    cout << "\n\nvalueRef=2";
    cout << "\nvalue    is " << value;
    cout << "\nvalueRef is " << valueRef;
    getch();
    int *valuePtr;
    valuePtr = &value; // Assign location of variable to pointer
    cout << "\n\n*valuePtr=&value";
    cout << "\nvalue    is " << value;
    cout << "\nvalueRef is " << valueRef;
    cout << "\nvaluePtr is " << *valuePtr;
    cout << "\nlocation at " << valuePtr;
    getch();
    *valuePtr = 3; // Assign 3 to dereferenced pointer
    cout << "\n\n*valuePtr=3";
    cout << "\nvalue    is " << value;
    cout << "\nvalueRef is " << valueRef;
    cout << "\nvaluePtr is " << *valuePtr;
    cout << "\nlocation at " << valuePtr;
    cout << "\n\ndone";
    getch();
    return 0;

}
