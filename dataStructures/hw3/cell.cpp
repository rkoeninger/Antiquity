#include "cell.h"

// Branch constructor
Cell::Cell(Cell* left, Cell* right) : left(left),right(right),object(NULL){}

// Leaf constructor
Cell::Cell(void* data) : left(NULL),right(NULL),object(data){}
