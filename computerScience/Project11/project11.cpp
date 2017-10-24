/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 11
 * 31/01/06
 *
 * Reads student information from a file and displays it, organized by
 * class year.
 */

#include <iostream>
#include <fstream>
#include <conio.h>
#include <vector>

using namespace std;

// Numeric constants for student year
#define FRESHMAN  1
#define SOPHOMORE 2

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
void complete(StudentData &data);

// Returns the letter grade for the given percentage
char getLetterGrade(double);

// Displays the specified class year records in dataList
void showClass(vector<StudentData>, int, char*);

int main()
{

    // Open streams to in and out files
    ifstream *scoresIn = new ifstream("scores11.txt");
    ofstream *fileOut = new ofstream("proj11out.txt");
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

    showClass(dataList, FRESHMAN, "Freshman");
    write("\n\n\n");
    showClass(dataList, SOPHOMORE, "Sophomore");

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

void showClass(vector<StudentData> dataList, int year, char* yearLabel)
{

    write(yearLabel);
    write(" Class Averages\n");
    write("------------------------\n\n");

    double sum = 0.0, count = 0.0, average;

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
            sum += dataList[x].average;
            count++;

        }

    // Class average
    average = sum / count;
    write("\n\tClass Average: ");
    write(average);
    write("\t");
    write(getLetterGrade(average));

}
