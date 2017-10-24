#include <stdlib.h>
#include "pqueue.h"

PQueue::PQueue (int s, long (*v)(void*)) {
   size = s;
   objects = new void*[size];
   tail = 0;
   valfn = v;
}

PQueue::PQueue (long (*v)(void*)) {
   size = 10000;
   objects = new void*[size];
   tail = 0;
   valfn = v;
}

PQueue::~PQueue() { delete objects; }

bool PQueue::empty() { return tail == 0; }

void PQueue::insert(void *obj) {
   if (obj == NULL) return;
   int ptr = tail;
   objects[tail++] = obj;
   while ((ptr-1)/2 >= 0 && valfn(objects[ptr]) < valfn(objects[(ptr-1)/2])) {
      void *tobj = objects[ptr];
      objects[ptr] = objects[(ptr-1)/2];
      objects[(ptr-1)/2] = tobj;
      ptr = (ptr-1)/2;
   }
}

void *PQueue::remove () {
   if (tail == 0) return NULL;
   void *t = objects[0];
   objects[0] = objects[--tail];
   int ptr = 0;
   while (2*(ptr+1) <= tail) {
      void *p = objects[ptr];
      if (valfn(objects[(ptr+1)*2-1]) < valfn(objects[ptr])) {
	 if (valfn(objects[(ptr+1)*2-1]) < valfn(objects[(ptr+1)*2])) {
	    objects[ptr] = objects[(ptr+1)*2-1];
	    objects[(ptr+1)*2-1] = p;
	    ptr = (ptr+1)*2-1;
	 } else {
	    objects[ptr] = objects[(ptr+1)*2];
	    objects[(ptr+1)*2] = p;
	    ptr = (ptr+1)*2;
	 }
      } else if (2*(ptr+1) < tail) {
	 if (valfn(objects[(ptr+1)*2]) < valfn(objects[ptr])) {
	    objects[ptr] = objects[(ptr+1)*2];
	    objects[(ptr+1)*2] = p;
	    ptr = (ptr+1)*2;
	 } else break;
      } else break;
   }
   return t;
}
