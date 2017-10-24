/*
 * Robert Koeninger
 * Section 002
 * Project 4 - Event Driven Simulation
 * Wednesday, May 24, 2006
 *
 * This flie includes the class definition, function prototypes and operator
 * overloads for the class OrderedSet.
 */

#ifndef ORDEREDSET_CLASSDEF
    #define ORDEREDSET_CLASSDEF

    #include <iostream>
    using std::ostream;

class OrderedSet
{

    private:

        /*
         * A group of references to the next and previous nodes in the chain
         * and the value at the position this node represents
         */
        struct Node
        {

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
         * Default constructor. Creates empty set.
         */
        OrderedSet();
        
        /*
         * Copy constructor. Makes a deep copy of the given set.
         */
        OrderedSet(const OrderedSet&);
        
        /*
         * Destructor. Deletes references to all nodes in the linked list.
         */
        ~OrderedSet();

        /*
         * Returns true if the provided item is contained in this set.
         */
        bool contains(int) const;
        
        /*
         * Returns the lexicographical difference between this set and the
         * given set. A positive result if this set is greater, a negative
         * result if that is greater and zero if the sets are equal.
         */
        int compare(OrderedSet&) const;
        
        /*
         * Adds the given item in the proper position if it is not already
         * in this set. Returns true if the item was added to the list.
         */
        bool add(int);

        /*
         * Returns an intersection of this set and the given set. Only items
         * present in both sets will be included in the resulting set.
         */
        OrderedSet& findIntersection(const OrderedSet&) const;

        /*
         * Returns an intersection of this set and the given set. All items
         * present in either set will be included in the resulting set.
         */
        OrderedSet& findUnion(const OrderedSet&) const;

        /*
         * Writes this set to the output stream formatted.
         */
        friend ostream& operator<<(ostream&, const OrderedSet&);

};

/*
 * Operator for intersection. Used because items in the intersection
 * must be in set A *AND* in set B.
 */
OrderedSet& operator&(const OrderedSet&, const OrderedSet&);

/*
 * Operator for union. Used because items in the union can be in
 * set A *OR* in set B.
 */
OrderedSet& operator|(const OrderedSet&, const OrderedSet&);

    // Include class implementation
    #include "orderedset.cpp"
    
#endif
