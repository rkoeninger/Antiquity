#ifndef _CELL
#define _CELL
class Cell {
   friend class List;
   void *object;
   Cell *next;

public:
   Cell (void*, Cell*);
};
#endif
