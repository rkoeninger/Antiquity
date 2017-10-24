#include <iostream>
#include "hash_funcs.h"
#include "partition.h"
using namespace std;

int hash_size;

// This function would be defined when establishing the Partition class
// Meaning:
//   key: use this to index into the hash table for the 1st time
int hashfunction(void *object) {
   long long n = (long long)valuefunction(((Node*)object)->object);
   return (int)(n*357 % hash_size);  
}

void dispfunction(void *obj) {  
   cout << valuefunction(((Node*)obj)->object) << " ";  
}

int cmpfunction(void *obj1, void *obj2) {
   return (valuefunction(((Node*)obj1)->object) ==
	   valuefunction(((Node*)obj2)->object)) ? 1 : 0;
}

// For exposition we will just take a size 1000 hash table.  More experienced
// coders can change this value dynamically.
Partition::Partition (int s) {
   hash_size = s;
   hash = new HashTable(s);
}

// Add an object to the partitioned set, as a stand-alone subset.
void Partition::add(void *object) { 
   hash->add(new Node(object)); 
}

// Returns from the hashtable data base a Node object matching the identity
// of the input object node.  Not visible to the outside world
Node *Partition::identify (Node *node) {
   Node *rep;
   if ((rep = (Node*)hash->lookup(node)) == NULL) return NULL;
   return rep->identify();
}

// Returns true if and only if object a is in the same subset as object b
bool Partition::redundant(void *a, void *b) {
   Node *rep1, *rep2;
   if ((rep1 = identify(new Node(a))) == NULL || 
       (rep2 = identify(new Node(b))) == NULL) return false;
   return rep1 == rep2;
}

// If objects a and b are in different subsets, those subsets are unioned
// by having the parent of the representative for the smaller set be the
// the representative of the larger set.  Path compression is performed
// by "identity".
void Partition::setunion(void *a, void *b) {
   Node *p1 = identify((Node*)hash->lookup(new Node(a)));
   Node *p2 = identify((Node*)hash->lookup(new Node(b)));
   if (p1 == p2 || p1 == NULL || p2 == NULL) return;
   
   if (p1->size > p2->size) {
      p2->rep = p1;
      p1->size += p2->size;
   } else {
      p1->rep = p2;
      p2->size += p1->size;
   }
}

// Overload the << operator
ostream &operator<<(ostream &out, Partition *part) {
   out << part->hash;
   return out;
}
