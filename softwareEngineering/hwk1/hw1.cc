/*
 * Homework 1 - 
 * Robert Koeninger
 * June 30, 2008
 * Summer Quarter
 */

#include "iostream"
#include "fstream"
#include "string.h"
#include "vector"
#include "math.h"

using namespace std;

/*
 * Reads the lines of text from the source file and puts each line into
 * a vector as a string
 */
vector<string> readFileLines(char* filename){
    // Open up a file read stream
    ifstream fileIn(filename);
    vector<string> nLines;
    string textLine;
    // Push each line as an element into the vector
    while (fileIn){
        getline(fileIn, textLine);
        nLines.push_back(textLine);
    }
    return nLines;
}

/*
 * Search function - returns a vector containing only the lines from the
 * provided vector in which the search key was found
 */
vector<string> searchFileLines(vector<string> nLines, string key){
    vector<string> nResultLines;
    for (int i = 0; i < nLines.size(); ++i){
	if (nLines[i].find(key) != string::npos){
	    nResultLines.push_back(nLines[i]);
	}
    }
    return nResultLines;
}

/*
 * Comparison function used by qsort(). Determines which argument is of
 * lesser lexical value
 */
int compareStrings(const void* arg1, const void* arg2){
    string* str1 = (string*) arg1;
    string* str2 = (string*) arg2;
    int length = min(str1->length(), str2->length());
    for (int i = 0; i < length; ++i){
	// Calculate lexical difference for this pair of chars
	int lexDiff = str2->at(i) - str1->at(i);
	if (lexDiff != 0)
            return -lexDiff; // Reverse order, so negate difference
    }
    // No difference between the two strings
    return 0;
}

int main(int argc, char** argv){
    // Check argument validity
    if (argc != 3){
        cout << "Usage: " << argv[0] << " <file name> <search key>";
	return -1;
    }
    // Read the target file
    vector<string> nLines = readFileLines(argv[1]);
    // Search the text
    vector<string> nResultLines = searchFileLines(nLines, argv[2]);
    string nLinesArray[nResultLines.size()];
    // Copy the elements from the vector into an array
    // *** another way to do this? ***
    for (int x = 0; x < nResultLines.size(); ++x){
	nLinesArray[x] = nResultLines[x];
    }
    // Sort search results
    qsort(nLinesArray, nResultLines.size(), sizeof(string), &compareStrings);
    // Display sorted lines
    for (int y = 0; y < nResultLines.size(); ++y){
	cout << nLinesArray[y] << "\n";
    }
    return 0;
}
