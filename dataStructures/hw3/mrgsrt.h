#ifndef _MERGESORT
#define _MERGESORT
#include <fstream>
#include "cell.h"
using namespace std;

class MergeSort {
  private:
    long (*value) (void*);         // Value function
    void* (*create) (fstream&);    // Object creation function
    Cell* data;                    // Pointer to unsorted/sorted structure
    bool sorted;                   // true iff list is sorted

    Cell* installData(fstream&, int);  // Creates input list from file data
    Cell* merge(Cell*, Cell*);         // Merges two increasing lists
    Cell* mergeSort(Cell*);            // Sorts list pointed to by Cell object
    void showTree(Cell*);              // Display the input list

  public:
    MergeSort(long (*)(void*), void* (*)(fstream&));
    void sort();        // Sort the input list using method mergeSort
    int readData(char*);// Read data from file and build list using installData
    void showOutput();  // Display sorted list
    void showInput();   // Display unsorted list
};
#endif
