#include <fstream>
#include <string>
#include "queue.h"
#include "dao.h"

DAO::DAO(){cities = NULL;}

City* DAO::getOrigin(){return cities[0];}
City* DAO::getDestination(){return cities[cityCount - 1];}

void DAO::readFile(string fname)
{

    if (fname != "") this->filename = fname;
    fstream file(filename.c_str());

    if (file.fail()){
        cerr << "File is inaccessible or does not exist.";
        return;
    }

    int count = 0;
    string* cityName = new string();

    // Count the number of cities
    file >> *cityName;
    for (; cityName->at(0) != '-'; ++count){
        cityName = new string();
        file >> *cityName;
    }

    cityCount = count;

    // Close and re-open the file
    file.close();
    file.clear();
    file.open(filename.c_str());

    // Read in cables
    string input;
    cities = new City*[cityCount];

    // Read in city names from first line
    for (int cityIndex = 0; cityIndex < cityCount; ++cityIndex){
        file >> input;
        cities[cityIndex] = new City();
        cities[cityIndex]->setName(input);
    }

    file >> input; // Ignore the '-' at the end of the line
    City* current = NULL;

    // Read off the list of cables for each city
    for (int line = 0; line < cityCount; ++line){
        current = cities[line];

        file >> input;
        while (input.at(0) != '-'){
            current->addNeighbor(cities[atoi(input.c_str())]);
            file >> input;
        }
    }

}
