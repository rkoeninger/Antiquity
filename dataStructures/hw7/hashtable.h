#ifndef _HASHTABLE
#define _HASHTABLE
#include <iostream>
#include "list.h"
#include "hash_funcs.h"
using namespace std;

class HashTable {
    List **table;           // The array of List pointers
    int size;               // The size of the array

    int (*key)(void*);

  public:
    HashTable(int);         // Constructor takes array size as argument
    void* add(void*);       // Add object to table: return NULL if there already
    void* lookup(void*);    // Find object in table matching input, return pointer to it
    void* pop(void*);       // Remove object from table, return pointer to it
    void display(ostream&); // Output contents of table in meaningful way
    ~HashTable();           // Destructor

    friend ostream &operator<<(ostream &, HashTable *);  // overload <<
};

#endif
