#include <iostream>
#include "partition.h"
using namespace std;

// Consider a collection of objects.  Define groups.  Place every object
// into exactly one group (no more, no less).  The assignment is called
// a partition.  The Partition class facilitates maintenance of a partition.
// It provides the following services:
//   1. redundant: A test whether two given objects are in the same group
//   2. setunion:  Joining all objects of two groups into one group

// Any type of object can be placed into a "Partition".  All that is needed
// is tp define a "value" function which is used to hash, compare, and
// display in the HashTable, and List classes.  Here is an example.
class A {
    int number;

  public:
    A(int x){number = x;}
    void display(){cout << number << " ";}
    int valueOf(){return number;}
};

int valuefunction (void *obj){
    return ((A*)(((Node*)obj)->object))->valueOf();
}

int hashfunction(void *object){
   long long n = (long long)valuefunction(object);
   return (int)(n*3573 % 1000);
}

int cmpfunction(void* a, void* b){
    return valuefunction(a) == valuefunction(b) ? 1 : 0;
}

int dispfunction(void* obj){
    std::cout << valuefunction(obj);
}

int main (int argc, char** argv) {
   Partition *part = new Partition(1000);
   part->add(new A(1));
   part->add(new A(2));
   part->add(new A(3));
   part->add(new A(4));
   cout << part;

   cout << "==Redundancies===============\n";
   for (int i=1 ; i <= 4 ; i++) {
      cout << "[" << i << "] ";
      for (int j=1 ; j <= 4 ; j++) {
         cout << j << ":"
              << part->redundant(new A(i), new A(j)) << " ";
      }
      cout << " ";
   }
   cout << "\n\n==Set Union 1 and 3==========\n";

   part->setunion(new A(1), new A(3));

   cout << "==Redundancies===============\n";
   for (int i=1 ; i <= 4 ; i++) {
      cout << "[" << i << "] ";
      for (int j=1 ; j <= 4 ; j++) {
         cout << j << ":"
              << part->redundant(new A(i), new A(j)) << " ";
      }
      cout << " ";
   }
   cout << "\n\n==Set Union 2 and 4==========\n";

   part->setunion(new A(2), new A(4));

   cout << "==Redundancies===============\n";
   for (int i=1 ; i <= 4 ; i++) {
      cout << "[" << i << "] ";
      for (int j=1 ; j <= 4 ; j++) {
         cout << j << ":"
              << part->redundant(new A(i), new A(j)) << " ";
      }
      cout << " ";
   }
   cout << "\n\n==Set Union 2 and 3==========\n";

   part->setunion(new A(2), new A(3));

   cout << "==Redundancies===============\n";
   for (int i=1 ; i <= 4 ; i++) {
      cout << "[" << i << "] ";
      for (int j=1 ; j <= 4 ; j++) {
         cout << j << ":"
              << part->redundant(new A(i), new A(j)) << " ";
      }
      cout << " ";
   }
   cout << "\n=============================\n";

   return 0;
}
