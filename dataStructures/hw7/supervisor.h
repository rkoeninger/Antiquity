#ifndef SUPERVISOR_H
#define SUPERVISOR_H
#include "list.h"
#include "partition.h"

class Supervisor{
    char* filename;
    List* cableList;
    Partition* cities;

  public:
    Supervisor(char*);
    void readData();
    void sort();
    void solve();
    void printSolution();

};

#endif
