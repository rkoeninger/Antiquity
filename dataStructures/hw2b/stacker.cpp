#include <iostream>
#include <stdlib.h>
#include "stacker.h"
using namespace std;

Cell::Cell(void *ptr, Cell *lst) {
   item = ptr;
   next = lst;
}

Stack::Stack(void (*dispfn)(void *)) { 
   this->dispfn = dispfn; 
   head = NULL; 
}

void Stack::push (void *object) {
   if (object == NULL) return;
   head = new Cell(object, head);
}

void *Stack::pop() {
   if (head == NULL) return NULL;
   Cell *ptr = head;
   void *object = head->item;
   head = head->next;
   delete ptr;
   return object;
}

void *Stack::peek() {
   if (head == NULL) return NULL;
   return head->item;
}

void Stack::display() {
   if (head == NULL) { cout << "(empty)\n";  return; }
   for (Cell *ptr=head ; ptr != NULL ; ptr=ptr->next) dispfn(ptr->item);
   cout << "\n";
}

bool Stack::empty() {  return head == NULL;  }

ostream & operator << (ostream & out, Stack & stack) {
   if (stack.head == NULL) { out << "(empty)\n"; return out; }
   for (Cell *ptr=stack.head ; ptr != NULL ; ptr=ptr->next)
      stack.dispfn(ptr->item);
   out << "\n";
   return out;
}
