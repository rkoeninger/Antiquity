#include <iostream>
#include "power.h"
#include "stacker.h"
#include "bigint.h"

void disp(void*v){std::cout << *(int*)v;}

char* Power::pow(int x, int y){
    Stack* s = new Stack(disp);
    while (y > 1){
        s->push(new int(y % 2));
        y/=2;
    }
    char* p = toBigInt(x);
    while (!s->empty()){
        p=multiplyBigInt(p,p);
        int z = *(int*)s->pop();
        if (z == 1)p=multiplyBigInt(p,toBigInt(x));
    }
    return p;
}
