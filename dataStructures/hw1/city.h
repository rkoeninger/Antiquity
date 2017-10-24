#ifndef CITY_H
    #define CITY_H

    #include <iostream>
    #include <string>
    #include "queue.h"

class City
{

    string name;
    Queue* neighbors;
    City* from;
    bool visited;

  public:

    City();
    ~City();

    void setName(string);
    string getName() const;

    void markVisited();
    bool hasBeenVisited() const;

    void setFrom(City* from);
    City* getFrom();

    void addNeighbor(City*);
    City* nextNeighbor();

    void displayBestRoute(ostream&);

};

#endif
