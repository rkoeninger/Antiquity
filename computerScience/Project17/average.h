#ifndef CLASS_RUNNINGAVG
    #define CLASS_RUNNINGAVG

class RunningAverage
{

    public: RunningAverage()
    {

        reset();

    }

    public: double update(int value)
    {

        ++count;
        return sum += (double) value;

    }

    public: double update(double value)
    {

        ++count;
        return sum += value;

    }

    public: double getAverage()
    {

        return sum / count;

    }
    
    public: double getTotal()
    {

        return sum;
        
    }
    
    public: int getCount()
    {

        return count;

    }

    public: void reset()
    {

        count = 0;
        sum = 0.0;

    }

    private: int count;
    private: double sum;

};

#endif
