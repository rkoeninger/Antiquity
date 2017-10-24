#ifndef _PARTITION
#define _PARTITION
#include "hashtable.h"
#include "node.h"

class Partition {
    friend ostream& operator<<(ostream&, Partition*);
    HashTable* hash;              // References the hash table
    Node* identify (void*);       // Returns the Node carrying the
                                  // representing object

  public:
    // Set partititon - decide how many objects are to be partitioned
    Partition(int);               // Argument is size of hash table
    void add(void*);              // Add an object to the set - it
                                  // is a subset all to itself
    void setunion(void*, void*);  // Join two subsets.  The arguments
                                  // are the representative objects
    bool redundant(void*, void*); // Returns true iff the two argument
                                  // objects are in same subset
};

ostream& operator<<(ostream&, Partition*);
#endif
