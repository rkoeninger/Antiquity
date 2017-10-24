/*
 * Robert Koeninger
 * Section 002
 * Project 3 - Reverse Polish Calculator
 * Friday, May 12, 2006
 *
 * This include file contains the class member function implementations.
 */

#ifndef STACK_CLASSIMPL
    #define STACK_CLASSIMPL
    
Stack::Stack()
{

    items = new LinkedList();

}

Stack::~Stack()
{

    delete items;

}

bool Stack::isEmpty() const
{

    return (items->getSize() == 0);

}

Stack& Stack::push(int item)
{

    items->addFirst(item);
    return *this;

}

int Stack::pop()
{

    if (this->isEmpty())
        return 0; // Return default NULL value
        
    return items->removeFirst();

}
    
#endif
