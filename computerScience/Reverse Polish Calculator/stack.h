/*
 * Robert Koeninger
 * Section 002
 * Project 3 - Reverse Polish Calculator
 * Friday, May 12, 2006
 *
 * This include file contains the class member function declarations.
 */

#ifndef STACK_CLASSDEF
    #define STACK_CLASSDEF
    #include "linkedlist.h"
    
class Stack
{

    private:

        /*
         * A list of items that are the items in the stack. The doubly-linked
         * list can easily add and remove items from the same end.
         */
        LinkedList* items;
        
    public:

        /*
         * Default constructor. Creates empty stack.
         */
        Stack();
        
        /*
         * Destructor. Deletes underlying list of items.
         */
        ~Stack();
        
        /*
         * Returns true if this stack is empty, false if the stack contains
         * items.
         */
        bool isEmpty() const;
        
        /*
         * Puts the given item on top the stack.
         */
        Stack& push(int);
        
        /*
         * Pops the topmost item off of the top of the stack. If the stack is
         * empty, then a default value of 0 is returned.
         */
        int pop();

};

    // Include implementation
    #include "stack.cpp"
#endif
