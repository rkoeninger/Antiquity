// Node objects are intended for use only by the Partition class to
// "carry" any kind of set objects.  Objects of the Node class contain
// the "parent" and "size" fields needed to implement an inverted tree
// and contain the "identity" method which not only finds an object in
// the partition set but also compresses the path to the representative
// partition set object when performing the find.
//
// Any object that gets placed into a partition needs to have meaningful
// display and value functions.  Thus, the Node class is a subclass of
// the Object class.
#ifndef _NODE
#define _NODE
#include <iostream>
using namespace std;

class Node {
    friend class Partition;
    friend ostream &operator<<(ostream &, Node*);
    Node *rep;   // Link on path to representing city (Node* replaces void*)
    int size;    // If this object is a representative, size of its subset

  public:
    void *object;     // The set object that is stored in this class
    Node (void*);     // Constructor takes a set object as argument
    Node *identify(); // Returns the representing object for this object's subset
};
#endif
