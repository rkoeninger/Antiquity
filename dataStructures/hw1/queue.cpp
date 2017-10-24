#include <iostream>
#include "queue.h"
using namespace std;

Queue::Queue() { tail = NULL;  }

void Queue::enqueue(void *t) {
   if (t == NULL) return;
   if (tail == NULL) {
      tail = new Cell(t, NULL);
      tail->next = tail;
   } else {
      Cell *h = new Cell(t, tail->next);
      tail->next = h;
      tail = h;
   }
}

void *Queue::dequeue() {
   if (tail == NULL) return NULL;
   Cell *ptr = tail->next;
   void *t = ptr->object;
   if (ptr != tail) tail->next = ptr->next; else tail = NULL;
   delete ptr;
   return t;
}

bool Queue::isEmpty() {  return tail == NULL;  }

ostream & operator<<(ostream & out, Queue &que) {
   if (que.isEmpty()) { 
      out << "(empty)";
   } else {
      for (Cell *ptr=que.tail->next ; ptr != que.tail ; ptr=ptr->next)
	 out << *((int *)(ptr->object)) << " ";
      out << *((int *)(que.tail->object));
   }   
   out << "\n";
   return out;
}

