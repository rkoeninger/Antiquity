/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 24
 * 14/03/06
 *
 * Plays with some instances of a ring class, creating them dynamically.
 */

#include <iostream>
#include <conio.h>
#include "ring.h"

using namespace std;
using namespace rob;

int main()
{

    Ring* rings[20];
    int x = 0;
    
    while (x < 20)
    {

        rings[x] = new Ring(5.0); // $5
        x++;
        
    }

    getch();
    return 0;

}
