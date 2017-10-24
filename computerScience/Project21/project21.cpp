/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 21
 * 28/02/06
 *
 * Implements a fixed-size stack of integers and tests it.
 */

#include <iostream>
#include <conio.h>
#include "mystack.h"

using namespace std;
using namespace rob;

int main()
{

    cout << "Menu\n";
    cout << "p - Push an item onto the stack\n";
    cout << "o - Pop an item off of the stack\n";
    cout << "l - List the items currently in the stack\n";
    cout << "i - Initialize all items in the stack\n";
    cout << "s - Show the size of the stack\n";
    cout << "c - Show the capacity of the stack\n";
    cout << "q - Quit program\n";
    cout << "\n";

    MyStack* stack = new MyStack(10); // The stack we're working with
    
    for (;;)
    {

        cout << "Enter your choice: ";
        
        switch (getch()) // Warning: Just press the key not <Enter>
        {

            case 'c': // Show the capacity of the stack
                cout << "\n\nCurrent capacity: ";
                cout << stack->getCapacity() << "\n\n";
                break;

            case 'i': // Initialize all items in the stack
                {

                    int initValue = 0;
                    cout << "\n\n";
                    cout << "Initial value for all elements: ";
                    cin >> initValue;
                    stack->initialize(initValue);
                    cout << "\n";
                
                }
                
                break;
                
            case 'l': // List all of the items in the stack
                cout << "\n\n";
                
                if (stack->isEmpty())
                    cout << "Stack Empty";
                else
                    stack->list();
                    
                cout << "\n\n";
                break;
                
            case 'o': // Pop an item off of the stack
                cout << "\n\n";
                
                if (stack->isEmpty())
                    cout << "Stack Empty";
                else
                    cout << "Popped Item: " << stack->pop();
                    cout << " @ " << stack->getLocation();
                    
                cout << "\n\n";
                break;
                
            case 'p': // Push an item onto the stack
                {

                    int item = 0;
                    cout << "\n\n";

                    if (stack->isFull())
                        cout << "Stack Full";
                    else
                    {

                        cout << "New Item: ";
                        cin >> item;
                        stack->push(item);
                        cout << "Item Pushed @ " << stack->getLocation();

                    }

                    cout << "\n\n";
                
                }
                break;
                
            case 'q': // Quit the program
                return 0;
                
            case 's': // Show the current size of the stack
                cout << "\n\nCurrent size: ";
                cout << stack->getSize() << "\n\n";
                
            default: // Unknown command
                cout << "Unrecognized option!\n\n";

        }

    }
    
    return 1;

}
