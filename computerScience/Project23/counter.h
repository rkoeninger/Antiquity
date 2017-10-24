#ifndef COUNTER_CLASSDEF
    #define COUNTER_CLASSDEF
    #include <ostream> // Reference for << operator
    #include <istream> // Reference for >> operator
    using std::ostream;
    using std::istream;

/*
 * Class implementations are in this file, include only this file.
 */

namespace rob
{

    /*
     * The numeric value type used to count with
     */
    typedef long long CountType;

    class Counter
    {

        private:

            int value; // The counter value
        
        public:

            Counter();    // Default constructor (0)
            Counter(CountType); // Initialization constructor
            ~Counter();   // Release counter resources (none)

            CountType getValue() const; // Returns the current counter value

            Counter operator++();    // Pre-Increments by one
            Counter operator++(int); // Post-Increment by one
            Counter operator--();    // Pre-Decrements by one
            Counter operator--(int); // Post-Decrement by one
            Counter operator+=(CountType); // Increments by the given argument
            Counter operator-=(CountType); // Decrements by the given argument

            operator CountType() const; // Casts to counter type
                                        // (i.e. returns current value)

            friend ostream& operator<<(ostream&, const Counter&); // Simple out
            friend istream& operator>>(istream&, Counter&); // Simple in (int)

    };
    
    Counter::Counter() : value(0)
    {
    }
    
    Counter::Counter(CountType initValue) : value(initValue)
    {
    }
    
    Counter::~Counter()
    {
    }
    
    CountType Counter::getValue() const
    {

        return value;

    }
    
    Counter Counter::operator++()
    {

        return *this += 1;

    }
    
    Counter Counter::operator++(int)
    {

        Counter temp = *this;
        operator++();
        return temp;

    }
    
    Counter Counter::operator--()
    {

        return *this -= 1;

    }
    
    Counter Counter::operator--(int)
    {

        Counter temp = *this;
        operator--();
        return temp;

    }
    
    Counter Counter::operator+=(CountType augend)
    {

        value += augend;
        return *this;

    }
    
    Counter Counter::operator-=(CountType subtrahend)
    {

        return *this += (-subtrahend);

    }
    
    Counter::operator CountType() const
    {

        return value;

    }
    
    ostream& operator<<(ostream &out, const Counter &counter)
    {

        out << "Counter value:\t";

        if (counter >= 0)
            out << "+";

        out << counter.getValue();
        return out;

    }
    
    istream& operator>>(istream &in, Counter &counter)
    {

        in >> counter.value;
        return in;

    }
    
}

#endif
