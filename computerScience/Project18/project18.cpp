/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 18
 * 21/02/06
 *
 * Finds the max of two numbers using a variety of methods.
 */

#include <iostream>
#include <conio.h>

using namespace std;

#define USER_INPUT

// Macro to find maximum of x and y
#define MAX(x, y) \
( \
( \
x \
) \
> \
( \
y \
) \
? \
x \
: \
y \
)
#undef MAX // Undefines MAX macro
#define MAX(x, y) ((x) > (y) ? (x) : (y)) // Redfines MAX macro

// Inline function to find maximum of x and y
inline int inlineMax(int, int);

// Pass-by-value function to find maximum of x and y
int max(int, int);

// Pass-by-reference function that sets x and y as the maximum of x and y
void setMax(int&, int&);

int main()
{

    int number1, number2;

#ifdef USER_INPUT

    // Get some numbers from the user
    cout << "Number One: ";
    cin >> number1;
    cout << "Number Two: ";
    cin >> number2;
    cout << "\n";
    
#else

    // Use some inital values
    number1 = +3;
    number2 = -4;

#endif
    
    cout << "Macro Compare:     " << MAX(number1, number2) << "\n";
    cout << "Inline Compare:    " << inlineMax(number1, number2) << "\n";
    cout << "Function Compare:  " << max(number1, number2) << "\n";
    setMax(number1, number2);
    cout << "Set Value Compare: " << number1 << "\n";
    cout << "\nDone";
    getch();

    return 0;

}

int max(int a, int b)
{

    return a > b ? a : b;

}

inline int inlineMax(const int a, const int b)
{

    return a > b ? a : b;

}

void setMax(int &a, int &b)
{

    a = b = (a > b ? a : b);

}
