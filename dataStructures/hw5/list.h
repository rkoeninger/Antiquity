#ifndef _LIST
#define _LIST
#include "cell.h"

class List {
   Cell *head;
   Cell *tail;
   int (*cmpfunc)(void*,void*);
   void (*dispfunc)(void*);

public:
   List ();
   void add (void*);      // Add an object to the list (at the end)
   void *pop (void*);     // Find an object and return a pointer to it or NULL
   void *lookup (void*);  // Find an object in list matching input (use
                          // cmpfunc and valuefunction to compare) and
                          // return a pointer to the found object or NULL
   void display();        // Display the contents of the list
};
#endif
