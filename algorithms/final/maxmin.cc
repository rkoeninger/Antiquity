#include "iostream"

using namespace std;

int* maxmin(int* array, int low, int high){
	int* res = new int[2];
	if (high == low){
		res[0] = res[1] = array[low];
		return res;
	}
	int middle = high > low + 1 ? (low + high) / 2 : low;
	int* res1 = maxmin(array, low, middle);
	int* res2 = maxmin(array, middle + 1, high);
	res[0] = res1[0] > res2[0] ? res1[0] : res2[0];
	res[1] = res1[1] < res2[1] ? res1[1] : res2[1];
	delete[] res1;
	delete[] res2;
	return res;
}

int main(int argc, char** argv){
	int array[] = {4, 5, 6, 2, 1, -5, 8, 4, 5, -5, 10};
	int* res = maxmin(array, 0, 10);
	cout << res[0] << "\t" << res[1] << endl;
	cout.flush();
	return 0;
}
