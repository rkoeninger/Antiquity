#include "iostream"
#include "fstream"
#include "ctime"
#include "sys/time.h"

using namespace std;

void quicksort(float* array, int low, int high){
	if ((high - low) > 1){
		int left = low, right = high;
		float pivotValue = array[low];
		float temp;
		while (left < right){
			while ((array[left] <= pivotValue) && (left <= high)) left++;
			while ((array[right] > pivotValue) && (right >= 0)) right--;
			if (left < right){
				temp = array[right];
				array[right] = array[left];
				array[left] = temp;
			}
		}
		array[low] = array[right];
		array[right] = pivotValue;
		quicksort(array,low,right-1);
		quicksort(array,right+1,high);
	}
}

int main(int argc, char** argv){
	if (argc < 3){
		cout << "specify arraycount and increment" << endl;
		return 1;
	}
	const int ARRAY_COUNT = atoi(argv[1]);
	const int INCREMENT = atoi(argv[2]);
	float* array;
	int length;
	int x;
	long int elapsedMillis;
	struct timeval start, finish;
	srand(time(0));
	cout << "Writing results to \"results.csv\"..." << endl;
	cout.flush();
	ofstream outfile("./results.csv");
	for (int i = 1; i <= ARRAY_COUNT; ++i){
		length = i * INCREMENT;
		cout << "\rArray Number: " << i << "   Length: " << length;
		cout.flush();
		array = new float[length];
		for (x = 0; x < length; ++x){
			array[x] = rand()/double(rand());
		}
		gettimeofday(&start, 0);
		quicksort(array, 0, length - 1);
		gettimeofday(&finish, 0);
		delete[] array;
		elapsedMillis = ((finish.tv_sec - start.tv_sec) * 1000) +
		((finish.tv_usec - start.tv_usec) / 1000);
		outfile << length << "," << elapsedMillis << endl;
	}
	outfile.flush();
	outfile.close();
	cout << endl << "done." << endl << endl;
	cout.flush();
	return 0;
}
