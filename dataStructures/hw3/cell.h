#ifndef _CELL
#define _CELL
#include <iostream>
using namespace std;

class Cell {
  private:
    friend class MergeSort;
    void *object;         // Pointer to an object, if terminal
    Cell *left, *right;   // Pointers to left and right sublists

  public:
    Cell(Cell*, Cell*); // Called when building intermediate nodes
    Cell(void*); // Called when building terminal nodes
};
#endif
