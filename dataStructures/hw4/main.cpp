#include <iostream>
#include "pqueue.h"

class HamUnit{
  public:
    long multiplier;
    int primec; // Number of primes in list
    long* primes;
    HamUnit(long m,int c,long* p):multiplier(m),primec(c),primes(p){}
};

long hamval(void* obj){
    HamUnit* h = (HamUnit*)obj;
    return (h->multiplier) * *(h->primes); // The first value times the multiplier
}

long first(PQueue* p){
    return ((HamUnit*)p->peek())->primes[0];
}

long* rest(PQueue* p){
    return ((HamUnit*)p->peek())->primes+1;
}

PQueue* mult(PQueue* p, long m){
    HamUnit h = (HamUnit*)p->peek();
}

PQueue* concat(long, PQueue* q){
}

PQueue* merge(PQueue* p, PQueue* q){
}
/*
PQueue* ham(PQueue* p){
    if (p->empty())
        return NULL;
    else
        return concat(first(p), merge(mult(ham(p),first(p)),ham(rest(p))));
}
*/
int main(int argc, char** argv){
    PQueue* hams = new PQueue(hamval);


}
