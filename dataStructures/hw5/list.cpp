#include <iostream>
#include <stdlib.h>
#include "list_funcs.h"
#include "list.h"
using namespace std;

List::List () {
   head = tail = NULL;
   cmpfunc = cmpfunction;
   dispfunc = dispfunction;
}

void List::add (void *obj) {
   if (head == NULL) {
      head = tail = new Cell(obj, NULL);
      return;
   }
   tail->next = new Cell(obj, NULL);
   tail = tail->next;
}

void *List::lookup (void *object) {
   for (Cell *p=head ; p != NULL ; p = p->next) {
      if (cmpfunc(p->object, object)) return p->object;
   }
   return NULL;
}

void *List::pop (void *object) {
   void *obj;
   Cell *ptr;

   if (cmpfunc(head->object, object)) {
      obj = head->object;
      ptr = head;
      head = head->next;
      delete ptr;
      return obj;
   }
   for (Cell *p=head ; p->next != NULL; p = p->next) {
      if (cmpfunc(p->next->object, object)) {
	 obj = p->next->object;
	 ptr = p->next;
	 p->next = p->next->next;
	 delete ptr;
	 return obj;
      }
   }
   return NULL;
}

void List::display() {
   for (Cell *p=head ; p != NULL ; p = p->next) {dispfunc(p->object);cout<<"\n";}
}
