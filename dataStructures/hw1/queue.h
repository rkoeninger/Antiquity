#ifndef _QUEUE
#define _QUEUE
#include <iostream>
using namespace std;

class Cell {
friend class Queue;
friend ostream & operator<<(ostream & out, Queue &que);
   void *object; Cell *next;
   Cell(void *ptr, Cell *lst) {  object = ptr;   next = lst;  }
};

class Queue {
friend ostream & operator<<(ostream & out, Queue &que);
   Cell *tail;

public:
   Queue();
   void enqueue(void *);
   void *dequeue();
   bool isEmpty();
};
#endif
