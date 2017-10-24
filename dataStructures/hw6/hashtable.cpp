#include <iostream>
#include "hashtable.h"
#include "hash_funcs.h"

ostream& operator<<(ostream& out, HashTable* h){
    h->display(out);
}
HashTable::HashTable(int s){
    size = s;
    table = new List*[size];
    for (int x=0;x<size;++x){
        table[x] = new List();
    }
}

HashTable::~HashTable(){
    //delete all the lists
    for (int x=0; x < size; ++x){
        delete table[x];
    }
}

void* HashTable::add(void* item){
    table[key(item)]->add(item);
    return item;
}

void* HashTable::lookup(void* item){
    return table[key(item)]->lookup(item);
}

void* HashTable::pop(void* item){
    return table[key(item)]->pop(item);
}

void HashTable::display(ostream& out){
    for (int x=0; x < this->size; ++x){
        this->table[x]->display();
    }
}
