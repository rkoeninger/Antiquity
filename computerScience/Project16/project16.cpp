/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 16
 * 14/02/06
 *
 * Reads student information from a file and displays it, organized by
 * class year and gender, also finds statistical MODE of female grades.
 */

#include <iostream>
#include <fstream>
#include <conio.h>
#include <vector>
#include "average.h"

using namespace std;

// Numeric constants for student year
#define FRESHMAN  1
#define SOPHOMORE 2

// Numeric constants for gender
#define FEMALE 1
#define MALE   2

struct StudentData
{

    // Personal
    int id, gender, year;

    // Scores
    double exam1, exam2, exam3, average;
    char letterGrade;

};

// Global stream to output file (copy of what's displayed on screen)
ofstream *fout;

// An overloaded insertor operator to read student data
istream* operator>>(istream*, StudentData&);

// Functions that write the same thing to cout and file output
void write(char*);
void write(char);
void write(int);
void write(double);

// Completes a student data record by finding the exam average and letter grade
void complete(StudentData&);

// Methods involved in recursive merge sort
void sort(vector<StudentData>&, bool); // bool is true for accending,
                                       // false for decending
void sortSubArray(vector<StudentData>&, bool, int, int);
void mergeSubArrays(vector<StudentData>&, bool, int, int, int, int);

// Returns the letter grade for the given percentage
char getLetterGrade(double);

// Finds the most recurring average of the
// grades of the given gender of students
double modeGender(vector<StudentData>, int);

// Displays records and averages of various groups
void showAll(vector<StudentData>);
void showClass(vector<StudentData>, int, char*);
void showGender(vector<StudentData>, int, char*);

int main()
{

    // Open streams to in and out files
    ifstream *scoresIn = new ifstream("scores16.txt");
    ofstream *fileOut = new ofstream("proj16out.txt");
    fout = fileOut;

    // A growable vector of student data entries
    vector<StudentData> dataList(1);

    int dataIndex = 0; // Counts off indexes in dataList

    // Read each data record one-at-a-time
    while (*scoresIn)
    {

        // Resize vector to hold more elements
        if (dataIndex == dataList.size())
            dataList.resize(dataList.size() * 2);

        scoresIn >> dataList[dataIndex++];

    }

    // The number of records read
    const int dataCount = dataIndex - 1;

    // The file was empty (no records)
    if (dataCount == 0)
    {

        write("Empty file");
        cout << "\nPress any key to exit";
        getch();
        (*scoresIn).close();
        (*fileOut).close();
        return 1;

    }

    // Trim vector down to number of records
    dataList.resize(dataCount);

    for (dataIndex = 0; dataIndex < dataList.size(); ++dataIndex)
        complete(dataList[dataIndex]); // Finds averages, letter grades, etc.

    // Sorts the list in reverse order
    sort(dataList, false);

    showAll(dataList);
    write("\n\n\n");
    showClass(dataList, FRESHMAN, "Freshman");
    write("\n\n\n");
    showClass(dataList, SOPHOMORE, "Sophomore");
    write("\n\n\n");
    showGender(dataList, FEMALE, "Girl's");
    write("\n\tMost Recurring: ");
    const double mode = modeGender(dataList, FEMALE);
    
    if (mode == 0.0)
    {

        write("<No Statistical Mode>");

    }
    else
    {

        write(mode);
        write(" ");
        write(getLetterGrade(mode));
        
    }

    // Close streams before exiting
    (*scoresIn).close();
    (*fileOut).close();

    cout << "\n\n";
    getch();
    return 0;

}

istream* operator>>(istream *in, StudentData &data)
{

    *in >> data.id;
    *in >> data.year;
    *in >> data.gender;
    *in >> data.exam1;
    *in >> data.exam2;
    *in >> data.exam3;
    return in;

}

void write(char* output)
{

    cout << output;
    *fout << output;

}

void write(char output)
{

    cout << output;
    *fout << output;

}

void write(int output)
{

    cout << output;
    *fout << output;

}

void write(double output)
{

    cout << output;
    *fout << output;

}

void complete(StudentData &data)
{

    data.average = (data.exam1 + data.exam2 + data.exam3) / 3.0;
    data.letterGrade = getLetterGrade(data.average);

}

char getLetterGrade(double grade)
{

    // Anything less than 0 still gets an 'F'
    // Anything above a 100 gets an 'A'
    if (grade >= 90.0)
        return 'A';
    else if (grade >= 80.0)
        return 'B';
    else if (grade >= 70.0)
        return 'C';
    else if (grade >= 60.0)
        return 'D';
    else
        return 'F';

}

void sort(vector<StudentData> &array, bool accending)
{

    // Sort the entire array (0 through size-1)
    sortSubArray(array, accending, 0, array.size() - 1);

}

void sortSubArray(vector<StudentData> &array, bool accending, int begin, int end)
{

    // If length of subsection is one or less, then there's nothing to sort
    if ((end - begin) < 1)
        return;

    // Find the boundries in the sebsection
    const int middle1 = (begin + end) / 2;
    const int middle2 = middle1 + 1;

    // Recursively sort subsections and remerge
    sortSubArray(array, accending, begin, middle1);
    sortSubArray(array, accending, middle2, end);
    mergeSubArrays(array, accending, begin, middle1, middle2, end);

}

void mergeSubArrays(vector<StudentData> &array, bool accending,
int begin, int middle1, int middle2, int end)
{

    int leftIndex = begin;    // Position in first subsection to start merging
    int rightIndex = middle2; // Position in second subsection to start merging
    int copyIndex = begin;    // Position in copy vector to place values in
    vector<StudentData> copy(array.size()); // Array for merging (dirty work)

    // Move digits to the working vector in order
    while ((leftIndex <= middle1) && (rightIndex <= end))
    {

        if (accending)
        {

            copy[copyIndex++] = (array[leftIndex].id <= array[rightIndex].id ?
            array[leftIndex++] : array[rightIndex++]);

        }
        else
        {

            copy[copyIndex++] = (array[leftIndex].id >= array[rightIndex].id ?
            array[leftIndex++] : array[rightIndex++]);

        }

    }

    // Finish copying any characters in remaining subsection
    if (leftIndex == middle2)
    {

        while (rightIndex <= end)
            copy[copyIndex++] = array[rightIndex++];

    }
    else
    {

        while (leftIndex <= middle1)
            copy[copyIndex++] = array[leftIndex++];

    }

    // Move sorted subsection characters back to original array
    for (int x = begin; x <= end; ++x)
        array[x] = copy[x];

}

double modeGender(vector<StudentData> dataList, int gender)
{

    // Sort records according to accending average
    for (int a = 0; a < dataList.size(); ++a)
        for (int b = a + 1; b < dataList.size(); ++b)
            if (dataList[a].average > dataList[b].average)
            {

                StudentData temp = dataList[a];
                dataList[a] = dataList[b];
                dataList[b] = temp;

            }

    // The standing average that occurred the most number of times
    double standingMode = 0.0;
    int standingCount = 0;

    // The current running mode and count
    double runningMode = 0.0;
    int runningCount = 0;
    
    for (int x = 1; x < dataList.size(); ++x)
        if (dataList[x].gender == gender)
        {

            if (dataList[x].average == dataList[x - 1].average)
            {

                runningCount = runningCount == 0 ? 2 : runningCount + 1;
                runningMode = dataList[x].average;

            }
            else
            {

                if (runningCount > standingCount)
                {

                    standingMode = runningMode;
                    standingCount = runningCount;

                }

                runningCount = 0;
                
            }

        }

        if (runningCount > standingCount)
        {

            standingMode = runningMode;
            standingCount = runningCount;

        }
        
    return standingMode;

}

void showAll(vector<StudentData> dataList)
{

    write("Overall Averages\n");
    write("----------------\n\n");

    RunningAverage average;
    average.reset();

    for (int x = 0; x < dataList.size(); ++x)
    {

        // Show line of student data
        write(dataList[x].id);
        write("\t");
        write(dataList[x].average);
        write("\t");
        write(dataList[x].letterGrade);
        write("\n");

        // Keep running total average information
        average.update(dataList[x].average);

    }

    // Overall average
    write("\n\tOverall Average: ");
    write(average.getAverage());
    write(" ");
    write(getLetterGrade(average.getAverage()));

}

void showClass(vector<StudentData> dataList, int year, char* yearLabel)
{

    write(yearLabel);
    write(" Class Averages\n");
    write("------------------------\n\n");

    RunningAverage average;
    average.reset();

    for (int x = 0; x < dataList.size(); ++x)
        if (dataList[x].year == year)
        {

            // Show line of student data
            write(dataList[x].id);
            write("\t");
            write(dataList[x].average);
            write("\t");
            write(dataList[x].letterGrade);
            write("\n");

            // Keep running total average information
            average.update(dataList[x].average);

        }

    // Class average
    write("\n\tClass Average: ");
    write(average.getAverage());
    write(" ");
    write(getLetterGrade(average.getAverage()));

}

void showGender(vector<StudentData> dataList, int gender, char* genderLabel)
{

    write(genderLabel);
    write(" Student Averages\n");
    write("------------------------\n\n");

    RunningAverage average;
    average.reset();

    for (int x = 0; x < dataList.size(); ++x)
        if (dataList[x].gender == gender)
        {

            // Show line of student data
            write(dataList[x].id);
            write("\t");
            write(dataList[x].average);
            write("\t");
            write(dataList[x].letterGrade);
            write("\n");

            // Keep running total average information
            average.update(dataList[x].average);

        }

    // Gender average
    write("\n\tAverage: ");
    write(average.getAverage());
    write(" ");
    write(getLetterGrade(average.getAverage()));

}
