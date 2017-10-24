#include "iostream"
#include "fstream"

using namespace std;

int comparisons;

int partition(int* array, int low, int high){
	int left = low, right = high;
	int pivotValue = array[low];
	int temp;
	while (left < right){
		while ((array[left] <= pivotValue) && (left <= high)){
			left++;
			comparisons++;
		}
		while (array[right] > pivotValue){
			right--;
			comparisons++;
		}
		if (left < right){
			temp = array[right];
			array[right] = array[left];
			array[left] = temp;
		}
	}
	array[low] = array[right];
	array[right] = pivotValue;
	return right;
}

void quicksort(int* array, int low, int high){
	if (high > low){
		int position = partition(array, low, high);
		quicksort(array, low, position - 1);
		quicksort(array, position + 1, high);
	}
}

int main(int argc, char** argv){
	int array1[] = {1,2,3};
	int array2[] = {1,3,2};
	int array3[] = {2,1,3};
	int array4[] = {2,3,1};
	int array5[] = {3,1,2};
	int array6[] = {3,2,1};
	int compareArray[6];
	cout << "Writing results to \"qsort-results.csv\"..." << endl;
	cout.flush();
	ofstream outfile("./qsort-results.csv");
	comparisons = 0;
	quicksort(array1,0,2);
	outfile << comparisons << "," << endl;
	compareArray[0] = comparisons;
	comparisons = 0;
	quicksort(array2,0,2);
	outfile << comparisons << "," << endl;
	compareArray[1] = comparisons;
	comparisons = 0;
	quicksort(array3,0,2);
	outfile << comparisons << "," << endl;
	compareArray[2] = comparisons;
	comparisons = 0;
	quicksort(array4,0,2);
	outfile << comparisons << "," << endl;
	compareArray[3] = comparisons;
	comparisons = 0;
	quicksort(array5,0,2);
	outfile << comparisons << "," << endl;
	compareArray[4] = comparisons;
	comparisons = 0;
	quicksort(array6,0,2);
	outfile << comparisons << "," << endl;
	compareArray[5] = comparisons;
	int totalCompares = 0;
	double averageCompares;
	for (int x = 0; x < 6; ++x){
		totalCompares += compareArray[x];
	}
	averageCompares = double(totalCompares) / 6;
	outfile << totalCompares << "," << averageCompares << endl;
	outfile.flush();
	outfile.close();
	cout << endl << "done." << endl << endl;
	cout.flush();
	return 0;
}
