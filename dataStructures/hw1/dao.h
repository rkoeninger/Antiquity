#ifndef DAO_H
    #define DAO_H

    #include <string>
    #include "city.h"

class DAO
{

    int cityCount;
    City** cities;
    string filename;

  public:

    DAO();
    void readFile(string);
    City* getOrigin();
    City* getDestination();

};

#endif
