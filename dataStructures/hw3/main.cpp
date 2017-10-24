#include <iostream>
#include <fstream>
#include "mrgsrt.h"
#include "cell.h"

using namespace std;

long getVal(void* obj){return *(long*)obj;}   // Turn void* into long value
void* makeObj(fstream& in){                   // Read int from file
    long val;
    in >> val;
    return new long(val);
}

int main(int argc, char** argv){
    if (argc != 2){
        cerr << "Usage: mergesort <input file>\n\n";
        return 1;
    }
    MergeSort* sorter = new MergeSort(&getVal,&makeObj);
    sorter->readData(argv[1]);
    sorter->showInput();
    cout<<"sorting..\n";
    sorter->sort();
    cout<<"sorted\n";
    sorter->showOutput();
	return 0;
}
