#include <iostream>
#include <string>
#include "city.h"
#include "dao.h"
#include "queue.h"

using namespace std;

int main(int argc, char** argv)
{

    if (argc != 2){
        cerr << "Usage: hops <filename>" << endl;
        return -1;
    }

	DAO file;
	file.readFile(argv[1]);
    City* origin = file.getOrigin();
    City* dest = file.getDestination();

    City* current = NULL;
    City* neighbor = NULL;
    Queue* q = new Queue();

    origin->markVisited();
    q->enqueue(origin);

    while (!q->isEmpty()){
        current = (City*)q->dequeue();

        // Visit each one of this city's neighbors
        while ((neighbor = current->nextNeighbor()) != NULL){
            if (neighbor == dest){ // We've found the end
                dest->setFrom(current);
                dest->displayBestRoute(cout);
                return 0;
            }

            if (!neighbor->hasBeenVisited()){
                neighbor->markVisited();
                neighbor->setFrom(current);
                q->enqueue(neighbor);
            }
        }
    }

    cerr << "No route found" << endl;
	return 1;
}
