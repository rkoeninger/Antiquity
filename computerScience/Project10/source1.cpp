#ifndef SOURCE1_IMPL
    #define SOURCE1_IMPL
    #include <cstring>

const int dayLength[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
char* monthNames[] = {"Jan", "Feb", "Mar", "Apr",
"May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

/*
 * Sets the fields of this date to those given.
 */
void Date::init(int initYear, int initMonth, int initDay)
{

    if (initYear < 0)
        throw initYear;
    else if ((initMonth < 1) || (initMonth > 12))
        throw initMonth;
    else if (initDay < 0)
        initDay=-1;
    else if (initDay > dayLength[initMonth - 1])
    {

        if (! ((initMonth == 2) && (initYear % 4 == 0) && (initDay == 29)))
            throw initDay;
        
    }

    year = initYear;
    month = initMonth;
    day = initDay;

}

/*
 * Sets the fields of this date to that of the current system date.
 * (@compile-time)
 */
void Date::init()
{

    char* date = __DATE__;
    char monthString[4];
    strncpy(monthString, date, 3);

    day = ((date[4] - '0') * 10) + (date[5] - '0');

    for (int x = 0; x < 12; ++x)
        if (strncmp(monthString, monthNames[x], 3) == 0)
            month = x + 1;

    year = ((date[7] - '0') * 1000) + ((date[8] - '0') * 100) +
    ((date[9] - '0') * 10) + (date[10] - '0');

}

#endif
