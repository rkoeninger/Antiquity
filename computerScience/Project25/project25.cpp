/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 25
 * 14/03/06
 *
 * Creates a array of ring objects on the heap.
 */

#include <iostream>
#include <conio.h>
#include "ring.h"

using namespace std;
using namespace rob;

int main()
{

    Ring rings[20];
    int x = 0;
    
    while (x < 20)
    {

        Ring newRing(8.0);
        rings[x] = newRing;
        cout << rings[x] << "\n";
        x++;
        
    }

    getch();
    return 0;

}
