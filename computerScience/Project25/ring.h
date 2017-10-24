#ifndef RING_CLASSDEF
    #define RING_CLASSDEF
    #include <iostream>
    #include <ctime>
    #include <cstdlib>
    
using namespace std;

namespace rob
{

    class Ring
    {

        public:

            enum Material
            {

                GOLD = 1,
                SILVER = 2

            };

        private:

            float price;
            Material material;
            int size;

        public:

            Ring();
            explicit Ring(float);
            explicit Ring(float, Material, int);
            ~Ring();
            
            float getPrice() const;
            Material getMaterial() const;
            int getSize() const;
            
            friend ostream& operator<<(ostream&, const Ring&);

    };
    
    Ring::Ring()
    {

        price = 5.0; // $5
        srand(time(0));
        material = Ring::Material((rand() % 2) + 1);
        size = (rand() % 6) + 5;

    }
    
    Ring::Ring(float initPrice)
    {

        if (initPrice < 0.0)
            throw initPrice;
            
        price = initPrice;
        srand(time(0));
        material = Ring::Material((rand() % 2) + 1);
        size = (rand() % 6) + 5;

    }
    
    Ring::Ring(float initPrice, Ring::Material initMaterial, int initSize)
    {

        if (initPrice < 0.0)
            throw initPrice;
        else if (initSize < 0)
            throw initSize;
            
        price = initPrice;
        material = initMaterial;
        size = initSize;

    }
    
    Ring::~Ring()
    {

        cout << "Ring Destroyed\n";

    }
    
    float Ring::getPrice() const
    {

        return price;

    }
    
    Ring::Material Ring::getMaterial() const
    {

        return material;

    }
    
    int Ring::getSize() const
    {

        return size;

    }
    
    ostream& operator<<(ostream &out, const Ring &ring)
    {

        out << "Ring object\n";
        out << "Price: " << ring.getPrice() << "\n";
        out << "Material: " << ring.getMaterial() << "\n";
        out << "Size: " << ring.getSize() << "\n";
        return out;

    }

}

#endif
