#include <iostream>
#include <fstream>
#include "supervisor.h"
#include "cable.h"

Supervisor::Supervisor(char* fname) : filename(fname){
    cableList = new List();
}

void Supervisor::readData(){
    fstream in(filename);
    if (in.fail()){
        cout << "File does not exist";
        exit(1);
    }
    int city1, city2, cost;
    while (! in.fail()){
        in >> city1;
        in >> city2;
        in >> cost;
        cableList->add(new Cable(city1, city2, cost));
    }
    in.clear();
    in.close();
}

void Supervisor::sort(){

}

void Supervisor::solve(){
    cities = new Partition(cableList->size());
}

void Supervisor::printSolution(){
}
