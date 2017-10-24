// Include only this file

#ifndef DATE_CLASS
    #define DATE_CLASS

/*
 * Dates are organized in most-significant part first: year-month-day
 */
class Date
{

    private: int year, month, day;

    public: Date()
    {

        init(); // Initialize to current date

    }
    
    public: Date(int initYear, int initMonth, int initDay)
    {

        init(initYear, initMonth, initDay); // Initialize with given fields

    }

    // Implemented in source1.cpp
    public: void init(int, int, int);
    public: void init();

    // Implemented in source2.cpp
    public: void printDate();

};

    // Function Implementations
    #include "source1.cpp"
    #include "source2.cpp"
    
#endif
