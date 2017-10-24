/*
 * Robert Koeninger
 * Section 002
 * Project 2 - Ordered Set ADT
 * Friday, April 28, 2006
 *
 * This file includes function and operator implementations for the class
 * OrderedSet.
 */
 
#ifndef ORDEREDSET_CLASSIMPL
    #define ORDEREDSET_CLASSIMPL
    
    #include <iostream>
    using std::ostream;
    #include <cstddef>
    #include <assert.h>
    
OrderedSet::OrderedSet()
{

    head = NULL;
    size = 0;

}

OrderedSet::OrderedSet(const OrderedSet& original)
{

    head = NULL;
    
    if (original.size == 0)
        return;
    
    Node* previous = NULL;
    Node* current = NULL;
    Node* originalCurrent = original.head;
    
    while (originalCurrent != NULL)
    {

        current = new Node();

        if (previous == NULL)
            head = current; // This is the first node, assign it to head
        else
            previous->next = current; // Connect this node on the chain

        current->value = originalCurrent->value; // Copy value
        originalCurrent = originalCurrent->next; // Advance original pointer
        previous = current;                      // Advance previous pointer
        size++;                                  // Increment size

    }
    
    size = original.size;

}

OrderedSet::~OrderedSet()
{

    if (size == 0)
        return;
    else if (size == 1)
    {

        delete head;
        head = NULL;
        return;

    }
    
    Node* current = head->next;
    Node* previous = head;
    
    while (current != NULL)
    {

        delete previous;

        previous = current;
        current = current->next;

    }
    
    head = NULL;
    
}

/*
 * Move down this list, one item at a time. Check for the existence of the item
 * until its value has been surpasses. Once we reach a node with a value
 * greater than the search item, there is no way it is in this list.
 */
bool OrderedSet::contains(int item) const
{

    Node* current = head;

    // Check item-by-item
    for (; current != NULL; current = current->next)
        if (current->value == item)     // We've found it
            return true;
        else if (current->value > item) // We're past where it could be
            return false;
            
    return false; // We've checked every node

}

/*
 * Move down each list one node at a time. Compare values at each position,
 * when an equality is reached, return the difference, it is the result of the
 * comparison.
 */
int OrderedSet::compare(OrderedSet& that) const
{

    Node* currentThis = head;
    Node* currentThat = that.head;
    
    // Loop until the end of one of the lists has been reached
    while ((currentThis != NULL) && (currentThat != NULL))
    {

        // Return the difference of unequal items
        if (currentThis->value != currentThat->value)
            return currentThis->value - currentThat->value;

        // Advance the pointers
        currentThis = currentThis->next;
        currentThat = currentThat->next;

    }
    
    if ((currentThis == NULL) && (currentThat == NULL))
        return 0;  // Both lists are same length, same values
    else if (currentThat == NULL)
        return 1;  // This list is longer, it is greater
    else if (currentThis == NULL)
        return -1; // That list is longer, it is greater

}

/*
 * Search for a spot for the new item to go (to maintain order) and then put
 * it there. When there is only zero or one items in the list, then a special
 * operation is needed. If there is no item in the list greater than the new
 * item, then a special operation will be needed.
 */
bool OrderedSet::add(int item)
{

    // If list is empty, item becomes first in list
    if (size == 0)
    {

        head = new Node();
        assert(head != NULL);
        head->value = item;

    }
    
    // If only one one, add before or after it
    else if ((size == 1) && (head->value != item))
    {

        Node* newEntry = new Node();
        assert(newEntry != NULL);
        newEntry->value = item;
            
        if (head->value > item)
        {

            // Add before the head
            newEntry->next = head;
            head->next = NULL;
            head = newEntry;

        }
        else
        {

            // Add after the head
            head->next = newEntry;

        }

    }

    // In any other situation, we search for a spot to put the new item
    else
    {

        Node* current = head;
        Node* previous = NULL;

        // Look for a position that is more than the new item
        while (current != NULL)
        {

            if (current->value == item)
            {

                // Item is already in this list
                return false;

            }
            else if (current->value > item)
            {

                Node* newEntry = new Node();
                assert(newEntry != NULL);
                newEntry->value = item;

                // Add the new item just before the current item
                if (previous == NULL)
                {

                    // Previous is NULL, so we're at the beginning of the list
                    head = newEntry;

                }
                else
                {
                
                    // Previous is the entry just before where our new one goes
                    previous->next = newEntry;
                
                }
                
                newEntry->next = current;
                size++;
                return true;

            }

            previous = current;
            current = current->next;

        }

        // The new item is the greatest item in the list, add here
        Node* newEntry = new Node();
        assert(newEntry != NULL);
        newEntry->value = item;
        previous->next = newEntry;
        newEntry->next = NULL;

    }
    
    size++;
    return true;

}

/*
 * Create a new set and add to it items found in both sets. Scan the shorter
 * set and check each item for presence in the other set as well. The result
 * will the the correct intersection, in order.
 */
OrderedSet& OrderedSet::findIntersection(const OrderedSet& that) const
{

    OrderedSet* result = new OrderedSet();
    assert(result != NULL);
    const OrderedSet* otherSet = size > that.size ? this : &that;
    Node* current = size <= that.size ? head : that.head;

    for (; current != NULL; current = current->next)
        if (otherSet->contains(current->value))
            result->add(current->value);
            
    return *result;
    
}

/*
 * Create a new set and add all items from both lists to it. Duplicate entries
 * will be ignored and the result will be the correct union, in order.
 */
OrderedSet& OrderedSet::findUnion(const OrderedSet& that) const
{

    OrderedSet* result = new OrderedSet();
    assert(result != NULL);
    Node* current = head;

    for (; current != NULL; current = current->next)
        result->add(current->value);

    current = that.head;

    for (; current != NULL; current = current->next)
        result->add(current->value);

    return *result;

}

ostream& operator<<(ostream& out, const OrderedSet& set)
{

    OrderedSet::Node* current = set.head;
    out << "{";
    
    if (set.size == 0)
    {

        out << "Empty Set";

    }
    else
    {
    
        for (;current != NULL; current = current->next)
        {

            out << current->value;

            if (current->next != NULL)
                out << ", ";

        }
    
    }
    
    out << "}";
    return out;

}

OrderedSet& operator&(const OrderedSet& a, const OrderedSet& b)
{

    return a.findIntersection(b);

}

OrderedSet& operator|(const OrderedSet& a, const OrderedSet& b)
{

    return a.findUnion(b);

}

#endif
