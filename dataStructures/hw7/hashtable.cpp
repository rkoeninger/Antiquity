#include <iostream>
#include <stdlib.h>
#include "list_funcs.h"
#include "hash_funcs.h"
#include "hashtable.h"
using namespace std;

HashTable::HashTable(int sz) {
   key = hashfunction;
   table = new List*[sz];
   size = sz;
   for (int i=0 ; i < size ; i++) table[i] = NULL;  // No bucket lists yet
}

// Add an object to the data base.
void *HashTable::add(void *object) {
   int index = key(object);
   if (table[index] == NULL) table[index] = new List();
   table[index]->add(object);
   return object;
}

// Find an object in the data base and return a pointer to it.   
void *HashTable::lookup (void *object) {
   int index = key(object);
   if (table[index] == NULL) return NULL;
   return table[index]->lookup(object);
}

// Find an object in the data base and remove it, if it is there.  Otherwise,
// return NULL.
void *HashTable::pop (void *object) {
   int index = key(object);
   if (table[index] == NULL) return NULL;
   return table[index]->pop(object);
}

// Print contents of the hash table - indicies with lists are shown, contents
// of each list is shown (using the user defined value function).
void HashTable::display(ostream &out) {
   bool flag = false;
   int i;
   
   out << "--Display--\n";
   for (i=0 ; i < size ; i++) {
      if (table[i] != NULL) {
	 flag = true;
	 out << "[" << i << "] ";
	 table[i]->display();
      }
   }
   if (!flag) cout << "Hash Table Empty\n";
   cout << "-----------\n";
}

ostream &operator<<(ostream &out, HashTable *ht) {
   ht->display(out);
   return out;
}

