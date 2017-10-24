#include <stdlib.h>
#include "node.h"

Node::Node (void *obj) {
   rep = this;
   size = 1;
   object = obj;
}

Node *Node::identify() { 
   if (this == rep) return this;
   return rep = rep->identify();
}
