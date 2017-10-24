#ifndef STACKER_H
#define STACKER_H

#include <iostream>
using namespace std;

class Stack;

class Cell {
friend class Stack;
friend ostream & operator << (ostream &, Stack &);
   void *item;
   Cell *next;
   Cell (void*, Cell*);
};

class Stack {
friend ostream & operator << (ostream &, Stack &);
   Cell *head;
   void (*dispfn)(void *);

public:
   Stack(void (*d)(void*));
   void push(void*);
   void *pop();
   void *peek();
   void display();
   bool empty();
};
#endif
