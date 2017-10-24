/*
 * Robert Koeninger
 * Section 002
 * Project 2 - Ordered Set ADT
 * Friday, April 28, 2006
 *
 * This is the main client program for manipulating two ordered sets and
 * finding the union and intersection of the two lists. A new set is entered
 * for each operation.
 */

#include <iostream>
#include <sstream>
#include <conio.h>
#include <string>
#include "orderedset.h"

using namespace std;

void readSet(OrderedSet*);

int main()
{

    OrderedSet* a;
    OrderedSet* b;
    int item;

    for (;;)
    {

        // Display instructions
        cout << "                        -=:Ordered Set Operations:=-\n";
        cout << "\n";
        cout << "Press one of the number keys (1.. 6) to select the\n";
        cout << "operation you wish to run. When entering sets for\n";
        cout << "operations, type them one at a time, seperated only\n";
        cout << "by spaces. Once you press enter, your input of the set\n";
        cout << "will be done. Individual items are entered as one number,\n";
        cout << "which can be negative, and terminated by pressing enter.\n";
        cout << "\n";

        // Display menu
        cout << "1- Find Union\n";
        cout << "2- Find Intersection\n";
        cout << "3- Search for Item\n";
        cout << "4- Add an Item\n";
        cout << "5- Compare\n";
        cout << "6- Quit\n";
        cout << "\n";

        // Re-initialize sets
        a = new OrderedSet();
        b = new OrderedSet();

        switch (getch())
        {

            case '1':

                // Display union instructions
                cout << "The union of two sets is a set that contains\n";
                cout << "all elements found in either set.\n";
                cout << "\n";

                // Find the union of A and B
                cout << "Find Union\n";
                cout << "Input Set A: ";
                readSet(a);
                cout << "Input Set B: ";
                readSet(b);
                cout << "Union = " << (*a | *b) << "\n";
                break;
                
            case '2':

                // Display intersection instructions
                cout << "The intersection of two sets is a set that\n";
                cout << "contains only elements found in both sets.\n";
                cout << "\n";

                // Find the intersection of A and B
                cout << "Find Intersection\n";
                cout << "Input Set A: ";
                readSet(a);
                cout << "Input Set B: ";
                readSet(b);
                cout << "Intersection = " << (*a & *b) << "\n";
                break;
                
            case '3':

                // Display find instructions
                cout << "The find operation determines if an item is\n";
                cout << "present in a given set\n";
                cout << "\n";

                // Find X in A
                cout << "Find X in A\n";
                cout << "Input Set A: ";
                readSet(a);
                cout << "Search For: ";
                cin >> item;
                cout << item << (a->contains(item) ?
                " is" : " is not") << " in list A\n";
                break;
                
            case '4':

                // Display add instructions
                cout << "The add operation takes an item and adds it into\n";
                cout << "a set, placing in a position that maintains the\n";
                cout << "order and no-duplicate-entry properties of the set\n";
                cout << "\n";

                // Add X in A
                cout << "Add X to A\n";
                cout << "Input Set A: ";
                readSet(a);
                cout << "New Item: ";
                cin >> item;
                a->add(item);
                cout << "A = " << *a << "\n";
                break;
                
            case '5':

                // Display comparison instructions
                cout << "The comparison operation finds the\n";
                cout << "lexicographical difference of two sets.\n";
                cout << "The result of this operation is the difference\n";
                cout << "between the first two unequal entries in the sets\n";
                cout << "\n";

                // Determine A < B
                cout << "Compare A to B\n";
                cout << "Input Set A: ";
                readSet(a);
                cout << "Input Set B: ";
                readSet(b);
                cout << "A is " << (a->compare(*b) < 0 ?
                "less than" : "not less than") << " B\n";
                break;
                
            case '6':

                // Quit
                delete a;
                delete b;
                cout << "Goodbye!";
                getch();
                return 0;
                break;
                
            default:

                cout << "Option not recognized.\n";

        }
        
        system("pause");
        delete a;
        delete b;
        system("cls");

    }

    return 0;

}

void readSet(OrderedSet* set)
{

    string input;
    getline(cin, input);
    istringstream charStream(input);
    int entry;
    
    while (charStream)
    {

        charStream >> entry;
        set->add(entry);

    }

}
