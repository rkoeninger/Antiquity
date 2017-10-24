#include <stdlib.h>
#include "pqext.h"

void* PQExt::findmax(){
    if (valfn == NULL)
        return NULL;
    long currentHighVal = -1;
    void* currentHighObj = NULL;
    void* obj = NULL;
    for (int i = 0; i <size;++i){
        obj = objects[i];
        if (obj != NULL){
            int val = valfn(obj);
            if (currentHighVal < val){
                currentHighVal = val;
                currentHighObj = obj;
            }
        }
    }
    return currentHighObj;
}
