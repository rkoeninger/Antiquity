/*
 * Robert Koeninger
 * Section 002
 * Project 4 - Event Driven Simulation
 * Friday, May 26, 2006
 *
 * This include file contains the class member function declarations.
 *
 * Note: This linked list maintains the order of it's elements. The addFirst,
 *       addLast, add (at an index) and set functions have been removed.
 *
 * Note2:The int values remain, even though this list is designed to hold
 *       Event objects. The only function that needs to be aware of the
 *       function struct is the "add(int)" function, which needs to be able
 *       to compare Event structs.
 */

#ifndef LINKEDLIST_CLASSDEF
    #define LINKEDLIST_CLASSDEF
    
    #include "events.h"

class LinkedList
{

    private:

        /*
         * A group of references to the next and previous nodes in the chain
         * and the value at the position this node represents
         */
        struct Node
        {

            Node* previous;
            Node* next;
            int value;

        };

        /*
         * The top of the linked list, holds reference to the first and last
         * nodes. When this list is empty, head refers to itself.
         *
         * The head node should never be NULL.
         */
        Node* head;
        
        /*
         * The current size of this LinkedList, should be 0 or greater.
         */
        unsigned int size;
        
    public:

        /*
         * Initializes a new LinkedList with length 0.
         */
        LinkedList();

        /*
         * Destroys all nodes in the list.
         */
        ~LinkedList();

        /*
         * Re-Initializes this LinkedList to length 0.
         */
        LinkedList& clear();

        /*
         * Looks for the specified value in this list. Returns true if the item
         * is present in this list.
         */
        bool contains(int) const;

        /*
         * Returns the current size of this LinkedList.
         */
        int getSize() const;
        
        /*
         * Retrieves the first value in the list. Returns NULL if the first
         * value does not exist.
         */
        int getFirst() const;

        /*
         * Retrieves the last value in the list. Returns NULL if the last value
         * does not exist.
         */
        int getLast() const;
        
        /*
         * Retrieves the value in the list at the index specified. Returns NULL
         * if the first value does not exist.
         */
        int get(unsigned int) const;
        
        /*
         * Adds the given item just before the lowest item greater than it.
         */
        LinkedList& add(int);
        
        /*
         * Removes the first item in the list and returns the value in its
         * place.
         */
        int removeFirst();
        
        /*
         * Removes the last item in the list and returns the value in its
         * place.
         */
        int removeLast();

        /*
         * Removes the item in the list at the specified index and returns the
         * value in its place.
         */
        int remove(unsigned int);

    private:

        /*
         * Locates and returns the node at the provided index. Returns NULL if
         * the index is out of bounds.
         */
        Node* getNode(int index) const
        {

            // Check index for in-bounds
            if (index < 0 || index >= size)
                return 0;

            Node* current = head;

            if (index < (size / 2))
            {

                for (int position = 0; position <= index; ++position)
                    current = current->next; // Advance to next node

            }
            else
            {

                for (int position = size - 1; position >= index; --position)
                    current = current->previous; // Step back to previous node

            }

            return current;

        }

        /*
         * Inserts the new node directly before the given node. This method
         * handles all pointers and the size member.
         */
        Node* addNodeBefore(Node* newEntry, Node* entry)
        {

            newEntry->next = entry;
            newEntry->previous = entry->previous;
            entry->previous->next = newEntry;
            entry->previous = newEntry;
            size++;
            return newEntry;

        }

        /*
         * Removes the specified node from the list. This method handles all
         * pointers and the size member.
         */
        Node* removeNode(Node* entry)
        {

            entry->previous->next = entry->next;
            entry->next->previous = entry->previous;
            entry->next = 0;
            entry->previous = 0;
            size--;
            return entry;

        }

};

    // Include class implementation
    #include "linkedlist.cpp"

#endif
