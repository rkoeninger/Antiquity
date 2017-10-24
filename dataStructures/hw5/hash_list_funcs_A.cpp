#include <iostream>

class A {
   int number;

public:
   A(int n) { number = n; }
   int valueOf() {  return number;  }
};

int valuefunction (void *obj) {  return ((A*)obj)->valueOf();  }

int hashfunction(void *object) {
   long long n = (long long)valuefunction(object);
   return (int)(n*3573 % 1000);
}

int cmpfunction(void* a, void* b){
    return valuefunction(a) == valuefunction(b) ? 1 : 0;
}

int dispfunction(void* obj){
    std::cout << valuefunction(obj);
}
