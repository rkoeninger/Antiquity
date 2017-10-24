#include <iostream>
#include "partition.h"
#include "hashtable.h"
#include "node.h"

Node* Partition::identify(void* obj){
    Node* n = (Node*)hash->lookup(new Node(obj));
    if (n != NULL) return n;
    return new Node(obj);
}

Partition::Partition(int s){
    hash = new HashTable(s);
}

void Partition::add(void* obj){
    Node* newNode = new Node(obj);
    hash->add(obj);
}

void Partition::setunion(void* a, void* b){
    Node* nodeA = identify(a);
    Node* nodeB = identify(b);
    if (nodeA->size > nodeB->size){
        nodeB->rep = nodeA;
    }else{
        nodeA->rep = nodeB;
    }
}

bool Partition::redundant(void* a, void* b){
    //find node's ultimate rep's
    Node* nodeA = identify(a);
    Node* nodeB = identify(b);

    int valA = valuefunction(nodeA->identify());
    int valB = valuefunction(nodeB->identify());

    return valA == valB;

}

ostream& operator<<(ostream& out, Partition *p){
    out << p->hash;
}
