/*
 * Robert Koeninger
 * Section 002
 * Project 4 - Event Driven Simulation
 * Friday, May 26, 2006
 *
 * This include file contains the class member function implementations.
 */

#ifndef QUEUE_CLASSIMPL
    #define QUEUE_CLASSIMPL

void Queue::enqueue(int item)
{

    addLast(item);

}

int Queue::dequeue()
{

    return removeFirst();

}

Queue::Queue()
{

    head = new Node();
    clear();

}

Queue& Queue::clear()
{

    head->next = head;
    head->previous = head;
    size = 0;
    return *this;

}

bool Queue::contains(int item) const
{

    for (Node* current = head->next; current != head; current = current->next)
        if (current->value == item)
            return true;
    
    return false;

}

int Queue::getSize() const
{

    return size;

}

Queue& Queue::set(unsigned int index, int item)
{

    Node* entry = getNode(index);
    
    if (entry != 0)
        entry->value = item;

    return *this;

}

int Queue::getFirst() const
{

    if (size == 0)
        return 0;
        
    return head->next->value;

}

int Queue::getLast() const
{

    if (size == 0)
        return 0;

    return head->previous->value;

}

int Queue::get(unsigned int index) const
{

    Node* entry = getNode(index);
    return entry == 0 ? 0 : entry->value;

}

Queue& Queue::addFirst(int item)
{

    Node* newEntry = new Node();
    newEntry->value = item;
    addNodeBefore(newEntry, head->next);
    return *this;

}

Queue& Queue::addLast(int item)
{

    Node* newEntry = new Node();
    newEntry->value = item;
    addNodeBefore(newEntry, head);
    return *this;

}

Queue& Queue::add(unsigned int index, int item)
{

    Node* newEntry = new Node();
    newEntry->value = item;
    addNodeBefore(newEntry, size == 0 ? head->next : getNode(index));
    return *this;
    
}

int Queue::removeFirst()
{

    if (size == 0)
        return 0;
        
    return removeNode(head->next)->value;

}

int Queue::removeLast()
{

    if (size == 0)
        return 0;

    return removeNode(head->previous)->value;

}

int Queue::remove(unsigned int index)
{

    Node* entry = getNode(index);
    
    if (entry == 0)
        return 0;
        
    return removeNode(entry)->value;

}

#endif
