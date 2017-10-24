#include <iostream>
#include <time.h>
#include "supervisor.h"
#include "cable.h"
using namespace std;

// Solve the Minimum Spanning Network problem.
// Given: A collection of cables, each connecting two cities and having
//        a cost.
// Find:  A subset of cables which connects all the cities such that its
//        total cable cost is minimized.

int valuefunction (void *object) {  return ((Cable*)object)->cost;  }

int main (int argc, char **argv) {
   clock_t t,e,g,h;

   if (argc != 2) {
      cerr << "Usage: " << argv[0] << " <filename> \n";
      exit(0);
   }

   Supervisor *supervisor = new Supervisor(argv[1]);

   t = clock();
   supervisor->readData();
   e = clock();
   supervisor->sort();
   g = clock();
   supervisor->solve();
   h = clock();
   supervisor->printSolution();

   cout << "Time: proc: " << ((double)(h-g))/CLOCKS_PER_SEC
	<< " sort: " << ((double)(g-e))/CLOCKS_PER_SEC
	<< " read: " << ((double)(e-t))/CLOCKS_PER_SEC << "\n";
}
