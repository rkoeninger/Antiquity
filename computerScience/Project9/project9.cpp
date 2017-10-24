/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 9
 * 24/01/06
 *
 * Declares a class of Ties and creates and manipulates instances of the class.
 */

#include <iostream>  // Include for cout and cin
#include <conio.h>   // Include for getch()

using namespace std;

// Ties have a color and a price
class Tie
{

    public:

    // Constructor takes inital color and price
    // Throws price if it is invalid (< 0.0)
    Tie(char* initColor, double initPrice)
    {

        if (! isValidPrice(initPrice))
            throw initPrice;

        price = initPrice;
        color = initColor;
        cout << "Tie Created\n";

    }

    // Displays a message showing that the tie no longer exists
    ~Tie()
    {

        cout << "Tie Destroyed\n";

    }
    
    // Shows the price on standard output
    void reportPrice()
    {

        cout << "Price: $" << price << "\n";

    }

    // Returns the price of this tie
    double getPrice()
    {

        return price;

    }
    
    // Returns the color string of this tie
    char* getColor()
    {

        return color;

    }

    private:

    char* color;   // The color string of this tie
    double price;  // The price of this tie

    // Returns true only if price is non-negative
    bool isValidPrice(double price)
    {

        return price >= 0.0;

    }

};

int main()
{

    Tie cheapRedTie("Red", 1.0);
    Tie cheapBlueTie("Blue", 1.25);
    Tie cheapBlackTie("Black", 0.5);
    Tie expensiveGreenTie("Green", 85.0);
    Tie expensiveOrangeTie("Orange", 100.0);
    
    cout << "\n";
    cheapRedTie.reportPrice();
    expensiveOrangeTie.reportPrice();
    
    //delete ties via pointer
    cout << "\n";
    Tie* toDelete;
    toDelete = &cheapRedTie;
    delete toDelete;
    toDelete = &cheapBlueTie;
    delete toDelete;
    toDelete = &cheapBlackTie;
    delete toDelete;
    toDelete = &expensiveGreenTie;
    delete toDelete;
    toDelete = &expensiveOrangeTie;
    delete toDelete;
    
    cout << "\n\nNice Ties";
    getch();
    return 0;

}
