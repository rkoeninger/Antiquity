/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 13
 * 07/02/06
 *
 * Does some simple string manipulation using functions and instances of the
 * class "string" in the c++ standard library.
 */

#include <iostream>
#include <conio.h>
#include <string>

using namespace std;

int main()
{

    string s1 = "As time by...";
    string s2 = "goes";
    s1 = s1.substr(0, 8) + s2 + " " + s1.substr(8, 5);
    cout << s1 << "\n";
    getch();
    s1.erase(s1.find("by") + 2, 6);
    cout << s1 << "\n";
    getch();
    s1.replace(s1.find("time"), 4, "Jane");
    cout << s1 << "\n";
    getch();
    return 0;

}
