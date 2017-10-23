/*
 * Robert Koeninger
 * Section 002
 * Project 2 - Ordered Set ADT
 * Monday, April 26, 2006
 *
 * This include file contains the class member function implementations.
 */

#ifndef LINKEDLIST_CLASSIMPL
    #define LINKEDLIST_CLASSIMPL

LinkedList::LinkedList()
{

    head = new Node();
    clear();

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

LinkedList& LinkedList::set(unsigned int index, int item)
{

    Node* entry = getNode(index);
    
    if (entry != 0)
        entry->value = item;

    return *this;

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

LinkedList& LinkedList::addFirst(int item)
{

    Node* newEntry = new Node();
    newEntry->value = item;
    addNodeBefore(newEntry, head->next);
    return *this;

}

LinkedList& LinkedList::addLast(int item)
{

    Node* newEntry = new Node();
    newEntry->value = item;
    addNodeBefore(newEntry, head);
    return *this;

}

LinkedList& LinkedList::add(unsigned int index, int item)
{

    Node* newEntry = new Node();
    newEntry->value = item;
    addNodeBefore(newEntry, size == 0 ? head->next : getNode(index));
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
