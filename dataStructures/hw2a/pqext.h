#ifndef PQEXT_H
#define PQEXT_H
#include "pqueue.h"

class PQExt:public PQueue{
  public:
    PQExt(int s,long(*val)(void*)):PQueue(s,val){}
    PQExt(long(*val)(void*)):PQueue(val){}
    void* findmax();
};

#endif
