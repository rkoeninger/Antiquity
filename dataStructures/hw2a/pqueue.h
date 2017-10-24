#ifndef _PQUEUE
#define _PQUEUE
#include <iostream>
using namespace std;

class PQueue {
protected:
   long (*valfn)(void*);  // Function which determines the "value" of an object
   void **objects;        // Array of pointers to objects
   long size, tail;       // The size of the array and index of its last object

public:
   PQueue(int, long(*)(void*)); // Constructor takes size and "value" function as arguments
   PQueue(long(*)(void*));      // Constructor takes "value" function as argument
   ~PQueue();                   // Destructor
   bool empty();                // Returns "true" iff no objects are stored
   void insert(void*);          // Inserts an object into the priority queue
   void *remove();              // Removes lowest valued object and returns a pointer to it
};
#endif
