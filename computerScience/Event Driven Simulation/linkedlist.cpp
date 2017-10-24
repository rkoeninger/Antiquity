/*
 * Robert Koeninger
 * Section 002
 * Project 4 - Event Driven Simulation
 * Friday, May 26, 2006
 *
 * This include file contains the class member function implementations.
 */

#ifndef LINKEDLIST_CLASSIMPL
    #define LINKEDLIST_CLASSIMPL
    
    #include "events.h"

LinkedList::LinkedList()
{

    head = new Node();
    clear();

}

LinkedList::~LinkedList()
{

    if (size == 0)
        return;
    else if (size == 1)
    {

        delete head->next;
        return;

    }

    Node* current = head;
    Node* next = head->next;
    
    while (next != head)
    {

        delete current;
        current = next;
        next = next->next;

    }
    
    delete current;

}

LinkedList& LinkedList::clear()
{

    head->next = head;
    head->previous = head;
    size = 0;
    return *this;

}

bool LinkedList::contains(int item) const
{

    for (Node* current = head->next; current != head; current = current->next)
        if (current->value == item)
            return true;
    
    return false;

}

int LinkedList::getSize() const
{

    return size;

}

int LinkedList::getFirst() const
{

    if (size == 0)
        return 0;
        
    return head->next->value;

}

int LinkedList::getLast() const
{

    if (size == 0)
        return 0;

    return head->previous->value;

}

int LinkedList::get(unsigned int index) const
{

    Node* entry = getNode(index);
    return entry == 0 ? 0 : entry->value;

}

LinkedList& LinkedList::add(int item)
{

    Node* newEntry = new Node();
    newEntry->value = item;
    Event* event = (Event*) item;

    if (size == 0) // Queue is empty
    {

        addNodeBefore(newEntry, head->next); // Only item in queue
        return *this;

    }

    // Search for a node with a time greater than the newEntry
    for (Node* current = head->next; current != head; current = current->next)
    {

        if (((Event*) (current->value))->time > event->time)
        {

            // We've found a spot, insert before the greater event
            addNodeBefore(newEntry, current);
            return *this;

        }

    }
    
    // None of the nodes in the list are later than the new one,
    // add the newEntry at the end
    addNodeBefore(newEntry, head);
    return *this;
    
}

int LinkedList::removeFirst()
{

    if (size == 0)
        return 0;
        
    return removeNode(head->next)->value;

}

int LinkedList::removeLast()
{

    if (size == 0)
        return 0;

    return removeNode(head->previous)->value;

}

int LinkedList::remove(unsigned int index)
{

    Node* entry = getNode(index);
    
    if (entry == 0)
        return 0;
        
    return removeNode(entry)->value;

}

#endif
