#include <iostream>
#include "pqueue.h"
#include "pqext.h"

using namespace std;

long value(void* obj){(long)*(int*)obj;}

int main(){
       PQExt *lst = new PQExt(value);
       lst->insert(new int(34));
       lst->insert(new int(12));
       lst->insert(new int(9));
       lst->insert(new int(22));
       lst->insert(new int(55));
       lst->insert(new int(2));
       lst->insert(new int(3));
       lst->insert(new int(16));
       cout << *(int*)(lst->findmax()) << "\n";
       cout << *(int*)(lst->remove()) << " "
            << *(int*)(lst->remove()) << " "
            << *(int*)(lst->remove()) << "\n";
	return 0;
}
