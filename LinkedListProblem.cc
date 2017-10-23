#include "iostream"
using namespace std;
template<typename T>
class LinkedList{
public:
	T ref;
	void assign(T& value){
		ref = value;
	}
	T get(){
		return ref;
	}
	T* getPointer(){
		return &ref;
	}
};
class Object{
public:
	int val;
	Object(){
		val = 0;
	}
};
void readData(Object* objPtr){
	objPtr->val = 5;
}
int main(int argc, char** argv){
	LinkedList<Object> list;     // [declared eariler]
	Object obj;                  // TimeSeries series;
	list.assign(obj);            // m_list.appendValues(series);
	readData(list.getPointer()); // might have to add this method
	cout << list.getPointer()->val << "\t" << list.get().val << endl;
	return 0;
}
