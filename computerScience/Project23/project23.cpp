/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 23
 * 07/03/06
 *
 * Implements a counter class with the following operators:
 * ++    (pre-increment)
 * ++    (post-increment)
 * --    (pre-decrement)
 * --    (post-decrement)
 * +=    (variable increment)
 * -=    (variable decrement)
 * int() (cast to integer)
 * <<    (stream output)
 * >>    (stream input)
 */

#include <iostream>
#include <conio.h>
#include "counter.h"

using namespace std;
using namespace rob;

int main()
{

    for (Counter c(-3); c <= 3;)    // int cast for comparison
        cout << c++ << "\n";        // stream-out, post-decrement

    getch();
    return 0;

}
