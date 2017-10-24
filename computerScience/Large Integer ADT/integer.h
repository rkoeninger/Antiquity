/*
 * Robert Koeninger
 * Section 002
 * Project 1 - Large Integer ADT
 * Monday, April 17, 2006
 *
 * This include file contains the class declaration and member function and
 * operator overload prototypes.
 *
 * ! This file includes the implementation file, not the other way around !
 */

#ifndef INTEGER_CLASSDEF
    #define INTEGER_CLASSDEF
    #include <iostream>
    using std::istream;
    using std::ostream;
    #include <vector>
    using std::vector;
    #include <string>
    using std::string;

class Integer
{

    public: // Not everything should be public, but I needed access
            // to private members for operations in the client program
            // and "helper" functions.

        /*
         * The base-10 digits of this integer, in least significant order.
         */
        vector<short> digits;
        
        /*
         * Utility method for cleaning up after an arithmetic operation.
         */
        void stripLeadingZeroes();

        /*
         * The sign of this integer, one of {-1, 0, 1}.
         */
        short sign;

        /*
         * Returns true if this integer is even.
         */
        bool isEven() const;

        /*
         * Returns true if this integer is less than two.
         * (Used by the factorial function in the client program).
         */
        bool LessThanTwo() const;

        /*
         * Divides this integer by two and returns the result.
         */
        Integer& divideByTwo() const;

        /*
         * Default (0) and copy constructors.
         */
        Integer();
        Integer(const Integer&);

        /*
         * Makes a copy of the sign and digits of the given integer.
         */
        void Copy(const Integer&);

        /*
         * Character stream readers and writers.
         */
        void Insert(ostream&) const;
        void Extract(istream& in);

        /*
         * Dynamically returns the absolute value of this integer.
         */
        Integer& AbsoluteValue() const;
        
        /*
         * Returns:
         * 1  if this is greater
         * -1 if the given integer is greater
         * 0 if they are equal
         */
        int Compare(const Integer&) const;

        /*
         * Returns a string containing the unsigned binary representation
         * of this integer.
         */
        string ToBinaryString() const;

        /*
         * Simple arthimetic, does what they're called.
         * Returns a new object.
         */
        Integer& Add(const Integer&) const;
        Integer& Subtract(const Integer&) const;
        Integer& Multiply(const Integer&) const;

        /*
         * All of the assignment operators change the contents of this
         * integer to the result of the operation.
         */
        Integer& operator=(const Integer&);
        Integer& operator+=(const Integer&);
        Integer& operator-=(const Integer&);
        Integer& operator*=(const Integer&);

};

Integer& operator+(const Integer&, const Integer&);
Integer& operator-(const Integer&, const Integer&);
Integer& operator*(const Integer&, const Integer&);

ostream& operator<<(ostream&, const Integer&);
istream& operator>>(istream&, Integer&);

bool operator==(const Integer&, const Integer&);
bool operator!=(const Integer&, const Integer&);
bool operator<(const Integer&, const Integer&);
bool operator>(const Integer&, const Integer&);
bool operator<=(const Integer&, const Integer&);
bool operator>=(const Integer&, const Integer&);

    // Include the implementation of this class's members
    #include "integer.cpp"
#endif
