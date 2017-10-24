#include <iostream>
#include "mrgsrt.h"
#include "cell.h"

using namespace std;

MergeSort::MergeSort(long (*val)(void*), void* (*crt)(fstream&)) :
value(val),create(crt),sorted(false){
}

void MergeSort::sort(){
    data = mergeSort(data);
    sorted = true;
}

int MergeSort::readData(char *filename){
    fstream fin;
    fin.open(filename, ios::in);
    if (fin.fail()) {
          cout << "Cannot open " << filename << "\n";
          exit(2);
    }
    sorted = false;
    int count = 0;
    long number;
    while (fin >> number) count++;
    fin.close();
    fin.clear();
    fin.open(filename, ios::in);

    data = installData(fin, count); // Read data into cells

    fin.close();
    fin.clear();
    return count;
}

void MergeSort::showInput(){
    if (sorted){
        cout << "List already sorted\n";
        return;
    }
    showTree(data);
}

void MergeSort::showOutput(){
    if (!sorted){
        cout << "List not sorted\n";
        return;
    }
    showTree(data);
}

Cell* MergeSort::installData(fstream &fin, int n){
    int dleft;

    if (n == 1) {
        void *object = create(fin);
        return new Cell(object);
    }

    for (dleft=0 ; ((1 << dleft) < n/2) ; dleft++);
    Cell *q1 = installData(fin, (1 << dleft));
    Cell *q2 = installData(fin, (n - (1 << dleft)));
    return new Cell(q1,q2);
}

Cell* MergeSort::mergeSort(Cell *c){
    if (c == NULL) return NULL;
    if (c->left == NULL) return c;
    return merge(mergeSort(c->left), mergeSort(c->right));
}

Cell* MergeSort::merge(Cell* left, Cell* right){
    if (left == NULL){//in case of weirdness
        return right;
    }else if (right == NULL){
        return left;
    }

    Cell* leftPtr = left;   // Current item in left list
    Cell* rightPtr = right; // Current item in right list
    Cell* sorted = NULL;    // List of sorted items
    Cell* sortedPtr = NULL; // Current (Last) item in sorted list

   // Copy the smallest item from either input list until we reach the end
   // of one of them
    while ((leftPtr != NULL) && (rightPtr != NULL)){
        if (value(leftPtr->object) <= value(rightPtr->object)){
            if (sortedPtr == NULL){//Need to make the first cell in outputlist
                sorted = sortedPtr = leftPtr;
            }else{
                sortedPtr->left = leftPtr;
                sortedPtr = sortedPtr->left;
            }
            leftPtr = leftPtr->left;
        }else{
            if (sortedPtr == NULL){//Need to make the first cell in outputlist
                sorted = sortedPtr = rightPtr;
            }else{
                sortedPtr->left = rightPtr;
                sortedPtr = sortedPtr->left;
            }
            rightPtr = rightPtr->left;
        }
    }

    // Finish copying unfinished input list
        while (rightPtr != NULL){
            sortedPtr->left = rightPtr;
            sortedPtr = sortedPtr->left;
            rightPtr = rightPtr->left;
        }
        while (leftPtr != NULL){
            sortedPtr->left = leftPtr;
            sortedPtr = sortedPtr->left;
            leftPtr = leftPtr->left;
        }

    return sorted;

}

void MergeSort::showTree(Cell* tree){
    if (tree->object != NULL){
        cout << value(tree->object) << "\n";
    }
    if (tree->left != NULL)  showTree(tree->left);
    if (tree->right != NULL) showTree(tree->right);
}
