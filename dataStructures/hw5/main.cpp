#include <iostream>
#include "hash_funcs.h"
#include "hashtable.h"
using namespace std;


int main() {
/*
    List* l = new List();
    A* a = new A(34);
    cout <<"a";
    l->display();
    cout << "b\n";
    l->add(a);
    cout <<"a";
    l->display();
    cout << "b\n";
    cout << (l->lookup(a)) << endl;
    cout << "end";
    return 0;
*/
   HashTable *h = new HashTable();
   cout << h;
   system("pause");
   cout << "Add: " << ((A*)h->add(new A(12)))->valueOf() << "\n";
   cout << "Add: " << ((A*)h->add(new A(264)))->valueOf() << "\n";
   system("pause");
   cout << h;
   system("pause");
   cout << "Add: " << ((A*)h->add(new A(112)))->valueOf() << "\n";
   cout << h;
   system("pause");
   cout << "Add: " << ((A*)h->add(new A(212)))->valueOf() << "\n";
   cout << "Add: " << ((A*)h->add(new A(312)))->valueOf() << "\n";
   cout << "Add: " << ((A*)h->add(new A(835)))->valueOf() << "\n";
   cout << h;
   system("pause");
   cout << "Pop: " << ((A*)(h->pop(new A(212))))->valueOf() << "\n";
   cout << h;
   system("pause");
   cout << "Pop: " << ((A*)(h->pop(new A(12))))->valueOf() << "\n";
   cout << h;
   system("pause");
   cout << "Lup: " << ((A*)(h->lookup(new A(835))))->valueOf() << "\n";
   cout << h;

   delete h;
}
