/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 2
 * 10/01/06
 *
 * Converts a user-input Fahrenheit temperature measurement and converts it to
 * a Celsius measurement, then quits.
 */

#include <iostream>  // Include for cout and cin
#include <conio.h>   // Include for getch()

using namespace std;

// Converts the given celsius measurement to a fahrenheit measurement
double toFahrenheit(double celsius);

// Converts the given fahrenheit measurement to a celsius measurement
double toCelsius(double fahrenheit);

// Program entry point, uses no command-line argumets
int main()
{

    double fahrenheitMeasurement = 32.0;

    // Program description
    cout << "This program takes a temperature measurement in Fahrentheit\n";
    cout << "and displays the value converted to celsius.\n\n";
    
    // User-input prompt
    cout << "Temperature: ";
    cin >> fahrenheitMeasurement;
    cout << "\n";
    
    // Convert and display
    cout << "In Celsius: " << toCelsius(fahrenheitMeasurement) <<
    " degrees\n\n";

    cout << "Press any key to exit";
    getch();
    return 0;

}

double toFahrenheit(double celsiusMeasurement)
{

    return (celsiusMeasurement * 9.0 / 5.0) + 32.0;

}

double toCelsius(double fahrenheitMeasurement)
{

    return (fahrenheitMeasurement - 32.0) * 5.0 / 9.0;

}
