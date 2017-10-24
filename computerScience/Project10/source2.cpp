#ifndef SOURCE2_IMPL
    #define SOURCE2_IMPL

#include <iostream>
using std::cout;

/*
 * Date is printed on cout and formatted US-order: <month>/<day>/<year>
 */
void Date::printDate()
{

    cout << month << "/" << day << "/" << year;

}

#endif
