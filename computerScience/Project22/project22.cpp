/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 22
 * 07/03/06
 *
 * Implements a menu which does some stuff to two numbers.
 */

#include <iostream>
#include <conio.h>

using namespace std;

void set(int&, int&);     // Asks for user-input and sets a,b
void swap(int&, int&);    // Swaps a,b
void square(int&, int&);  // Squares a
void cube(int&, int&);    // Cubes a
void display(int&, int&); // Displays a,b

int main()
{

    int a, b;                    // The two numbers being processed
    int input;                   // Menu choice
    void (*toCall) (int&, int&); // Function pointer to next operation
    
    for (;;)
    {

        cout << "Enter your option: ";
        cin >> input;

        switch (input)
        {

            case 0:
                return 0;
            case 1:
                toCall = set;
                break;
            case 2:
                toCall = swap;
                break;
            case 3:
                toCall = square;
                break;
            case 4:
                toCall = cube;
                break;
            default:
                cout << "\nUnrecognized option\n\n";
                continue;

        }
        
        cout << "\n";
        toCall(a, b);
        cout << "\n";
        display(a, b);
        cout << "\n";

    }

    return 1;

}

void set(int &a, int &b)
{

    int input;
    cout << "a = ?";
    cin >> input;
    a = input;
    cout << "b = ?";
    cin >> input;
    b = input;

}

void swap(int &a, int &b)
{

    int temp = a;
    a = b;
    b = temp;

}

void square(int &a, int &b)
{

    a = a * a;

}

void cube(int &a, int &b)
{

    a = a * a * a;

}

void display(int &a, int &b)
{

    cout << "a = " << a << "\n";
    cout << "b = " << b << "\n";

}
