/*
 * Robert Koeninger
 * Section 002
 * Project 5 - Threshold Sort
 * Friday, May 26, 2006
 *
 * This include file contains the client program for the sorts along with the
 * sorting algorithms.
 */

/*
 * Effectiveness of Threshold Sort
 *
 * My current version of the threshold sort shows that the higher the
 * threshold, the fewer comparisons made. This would mean that the insertion
 * sort is better than the merge sort, which is of course not true. But this
 * analysis is dependent on how to determine how many comparisons are made.
 * A previous version of this program showed the opposite: the lower the
 * threshold, the fewer comparisons, but then comparisons were counted
 * differently. According to the exact specifications of the assignment, the
 * insertion sort does fewer comparisons, however, it is not as efficent
 * according to the theory of their design.
 */

#include <iostream>
#include <conio.h>
#include <cstdlib>
#include <vector>

using namespace std;

// A global counter to track the number of comparisons done by the sorting
// functions. It is incremented everytime two values in the list are compared.
int comparisons = 0;

// The threshold at which the mergesort recursion would stop and use an
// insertion sort to sort the remaining region. Set to -1 for no threshold
int threshold = 0;

bool inputList(vector<int>*); // If false, list needs to randomly-generated

void thresholdSort(vector<int>&);
void mergeSortRegion(vector<int>&, int, int);     // Low,High
void merge(vector<int>&, int, int, int, int);     // Low,Middle1,Middle2,High
void insertionSortRegion(vector<int>&, int, int); // Low,High

bool isSorted(const vector<int>&);

int main()
{

    vector<int>* list;
    vector<int>* thresholds;

    for (;;)
    {

        srand(time(0) % rand());
        thresholds = new vector<int>(0);
        
        // Input the size
        int size;
        cout << "Input list size: ";
        cin >> size;
        cin.clear();
        
        if (size <= 0)
            return 0;
            
        list = new vector<int>(size);

        // Input the list
        if (! inputList(list)) // If inputList returns false, randomly generate
        {

            for (int x = 0; x < list->size(); ++x)
                list->at(x) = rand();
                
            cout << "\nList randomly generated.\n\n";

        }

        int inputThreshold = 1;

        // Input threshold values (-1 to stop)
        cout << "*** Input threshold values (Enter a -1 to stop) ***\n\n";
        
        while (inputThreshold >= 0)
        {

            cout << "Threshold value: ";
            cin.clear();
            cin >> inputThreshold;
            cin.clear();

            if (inputThreshold >= 0)
            {

                thresholds->resize(thresholds->size() + 1);
                thresholds->at(thresholds->size() - 1) = inputThreshold;

            }

        }

        vector<int>* listCopy;
        
        // Copy list and sort each copy with specified thresholds
        for (int t = 0; t < thresholds->size(); ++t)
        {

            listCopy = new vector<int>(*list); // Copy the list to sort
            comparisons = 0;
            threshold = thresholds->at(t);
            thresholdSort(*listCopy);

            bool sorted = isSorted(*listCopy);
            cout << "\nThreshold:   " << thresholds->at(t);
            cout << "\nComparisons: " << comparisons << "\n";
            cout << (sorted ? "List Sorted" :
            "List is NOT Sorted") << "\n";

            if (! sorted)
            {

                cout << "\n";
                
                for (int i = 0; i < listCopy->size(); ++i)
                    cout << listCopy->at(i) << endl;

                cout << endl;

            }

            cin.clear();
            system("pause");

        }

        char again;
        
        cout << "\nDo you want to enter another list (Y/N)? ";
        cin.clear();
        cin >> again;
        cin.clear();
        cin.ignore();
        
        if (! ((again == 'Y') || (again == 'y')))
            return 0;

        system("cls");

    }

}

bool inputList(vector<int>* list)
{

    if (list->size() <= 20)
    {

        // Ask for manual input
        char manual;
        cout << "Do you want to enter the list manually (Y/N)? ";
        cin.clear();
        cin >> manual;
        cin.clear();
        
        if (! ((manual == 'Y') || (manual == 'y')))
            return false; // List has not been filled
            
        int item;

        cout << "\n";

        for (int x = 0; x < list->size(); ++x)
        {

            cout << "Item " << (x + 1) << " :\t";
            cin.clear();
            cin >> item;
            cin.clear();
            
        }
        
        cout << "\n";
        
        return true; // List has been input

    }
    
    return false; // List needs to be generated

}

void thresholdSort(vector<int>& array)
{

    mergeSortRegion(array, 0, array.size() - 1);

}

void mergeSortRegion(vector<int>& array, int low, int high)
{

    if ((high - low) <= 0)
        return;
    else if ((high - low + 1) <= threshold)
    {

        // The threshold has been reached
        insertionSortRegion(array, low, high);

    }
    else
    {
    
        // Found boundaries
        int middle1 = (low + high) / 2;
        int middle2 = middle1 + 1;

        // Recursively sort sub-regions
        mergeSortRegion(array, low, middle1);
        mergeSortRegion(array, middle2, high);
        merge(array, low, middle1, middle2, high);

    }

}

void merge(vector<int>& array, int low, int middle1, int middle2, int high)
{

    int leftIndex = low;      // Position in first subsection to start merging
    int rightIndex = middle2; // Position in second subsection to start merging
    int copyIndex = low;      // Position in copy vector to place values in
    vector<int> copy(array.size()); // Array for merging (dirty work)

    // Move digits to the working vector in order
    while ((leftIndex <= middle1) && (rightIndex <= high))
    {

        copy[copyIndex++] = (array[leftIndex] <= array[rightIndex] ?
        array[leftIndex++] : array[rightIndex++]);
        comparisons++;

    }

    // Finish copying any characters in remaining subsection
    if (leftIndex == middle2)
    {

        while (rightIndex <= high)
        {

            copy[copyIndex++] = array[rightIndex++];
            comparisons++;
            
        }

    }
    else
    {

        while (leftIndex <= middle1)
        {

            copy[copyIndex++] = array[leftIndex++];
            comparisons++;
            
        }

    }

    // Move sorted subsection characters back to original array
    for (int x = low; x <= high; ++x)
    {

        array[x] = copy[x];
        comparisons++;
        
    }

}

void insertionSortRegion(vector<int>& array, int low, int high)
{

    // Scan through the unsorted region, inserting items into the sorted region
    for (int unsorted = low; unsorted <= high; ++unsorted)
    {

        int currentItem = array[unsorted];
        int pos = unsorted;
        
        // Shift items over while searching for position to insert
        for (; (pos > 0) && (array[pos - 1] > currentItem); --pos)
        {

            array[pos] = array[pos - 1];
            comparisons++;

        }
        
        // Copy item to new position
        array[pos] = currentItem;

    }

}

bool isSorted(const vector<int>& array)
{

    for (int x = 1; x < array.size(); ++x)
        if (array[x] < array[x - 1])
            return false; // One item is less than it's previous

    return true; // No two adjacent items were out of outer

}
