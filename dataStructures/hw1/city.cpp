#include <iostream>
#include <string>
#include "city.h"

City::City()
{

    name = "";
    neighbors = new Queue();
    from = NULL;
    visited = false;

}

City::~City(){delete neighbors;}
void City::setName(string name){this->name = name;}
string City::getName() const{return name;}
void City::markVisited(){visited = true;}
bool City::hasBeenVisited() const{return visited;}
void City::setFrom(City* from){this->from = from;}
City* City::getFrom(){return from;}
void City::addNeighbor(City* city){neighbors->enqueue(city);}
City* City::nextNeighbor(){return (City*)neighbors->dequeue();}

void City::displayBestRoute(ostream& out)
{

    out << name << endl;
    if (from != NULL) from->displayBestRoute(out);

}
