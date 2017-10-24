#ifndef _MYSTACK_CLASSDEF
    #define _MYSTACK_CLASSDEF
    #include <iostream>
    
using namespace std;

namespace rob
{

    class MyStack
    {

        public:

        MyStack(unsigned int initCapacity)
        {

            elements = new int[initCapacity];
            capacity = initCapacity;
            size = 0;

        }

        bool isEmpty() const
        {

            return getSize() == 0;

        }

        bool isFull() const
        {

            return getSize() == getCapacity();

        }

        unsigned int getSize() const
        {

            return size;

        }

        unsigned int getCapacity() const
        {

            return capacity;

        }

        void clear()
        {

            size = 0;

        }

        void push(int item)
        {

            if (isFull())
                throw item;

            // Add an item, then increment to next position
            elements[size++] = item;

        }

        int pop()
        {

            if (isEmpty())
                throw -1;

            // Return item, then decrement down the stack
            return elements[--size];

        }

        void list()
        {

            for (int x = getSize() - 1; x >= 0; x--)
                cout << elements[x] << "\n";

        }

        void initialize(int initValue)
        {

            for (int x = 0; x < getCapacity(); x++)
                elements[x] = initValue;

        }

        int* getLocation()
        {

            return size > 0 ? &elements[size - 1] : 0;

        }

        private:

        int* elements;
        unsigned int capacity;
        unsigned int size;

    };

}

#endif
