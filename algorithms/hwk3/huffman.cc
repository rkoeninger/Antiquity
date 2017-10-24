#include "iostream"
#include "fstream"
#include "vector"
#include "string"

using namespace std;

struct huffnode{
	char symbol;
	int freq;
	huffnode* left;
	huffnode* right;
};

struct huffcode{
	char symbol;
	string code;
	int freq;
};

int indexOf(vector<char>& symbols, char symbol){
	for (int x = 0; x < symbols.size(); ++x)
		if (symbols[x] == symbol)
			return x;
	return -1;
}

int loadInputData(char* filename, vector<char>& symbols, vector<int>& freqs){
	ifstream infile(filename);
	char nextSymbol;
	string line;
	int index;
	int dataSize = 0;
	while (getline(infile, line)){
		for (int i = 0; i < line.size(); ++i){
			nextSymbol = line[i];
			index = indexOf(symbols, nextSymbol);
			if (index >= 0){
				freqs[index]++;
			} else {
				symbols.push_back(nextSymbol);
				freqs.push_back(1);
			}
			dataSize++;
		}
	}
	infile.close();
	return dataSize;
}

void quicksort(vector<char>& symbols, vector<int>& freqs, int low, int high){
	if ((high - low) > 1){
		int left = low, right = high;
		int pivotValue = freqs[low];
		int temp;
		char pivotChar = symbols[low];
		char tempChar;
		while (left < right){
			while ((freqs[left] <= pivotValue) && (left <= high)) left++;
			while ((freqs[right] > pivotValue) && (right >= 0)) right--;
			if (left < right){
				temp = freqs[right];
				freqs[right] = freqs[left];
				freqs[left] = temp;
				tempChar = symbols[right];
				symbols[right] = symbols[left];
				symbols[left] = tempChar;
			}
		}
		freqs[low] = freqs[right];
		freqs[right] = pivotValue;
		symbols[low] = symbols[right];
		symbols[right] = pivotChar;
		quicksort(symbols,freqs,low,right-1);
		quicksort(symbols,freqs,right+1,high);
	}
}

void getHuffCodes(huffnode* root, vector<huffcode>& codes, string code){
	if ((root->left != 0) || (root->right!=0)){
		string leftcode = code + "1";
		string rightcode = code + "0";
		if (root->left != 0) getHuffCodes(root->left,codes,leftcode);
		if (root->right != 0)getHuffCodes(root->right,codes,rightcode);
	}else{
		cout << root->symbol << "\t" << code << endl;
		cout.flush();
		for (int x = 0; x < codes.size(); x++){
			if (root->symbol = codes[x].symbol){
				codes[x].code = code;
				codes[x].freq = root->freq;
				return;
			}
		}
	}
}

int main(int argc, char** argv){
	vector<char> symbols(0);
	vector<int> freqs(0);
	int uncompressedsize = loadInputData("hufftext.txt", symbols, freqs);
	quicksort(symbols, freqs, 0, symbols.size() - 1);
	vector<huffnode*> treenodes(symbols.size());
	for (int x = 0; x < treenodes.size(); ++x){
		huffnode* node = new huffnode;
		node->symbol = symbols[x];
		node->freq = freqs[x];
		node->left=0;
		node->right=0;
		treenodes[x] = node;
	}
	while (treenodes.size() > 1){
		huffnode* low1 = treenodes[0];
		huffnode* low2 = treenodes[1];
		treenodes.erase(treenodes.begin());
		treenodes.erase(treenodes.begin()+1);
		huffnode* parent = new huffnode;
		parent->left = low1;
		parent->right = low2;
		parent->freq = low1->freq + low2->freq;
		parent->symbol = '\0';
		if (treenodes.size() == 1){
			huffnode *toberoot = new huffnode;
			if (parent->freq > treenodes[0]->freq){
				toberoot->left=treenodes[0];
				toberoot->right=parent;
				toberoot->freq=treenodes[0]->freq+parent->freq;
				treenodes[0]=toberoot;
			}else{
				toberoot->right=treenodes[0];
				toberoot->left=parent;
				toberoot->freq=treenodes[0]->freq+parent->freq;
				treenodes[0]=toberoot;
			}
		}
		if (parent->freq > treenodes[treenodes.size()-1]->freq){
			treenodes.insert(treenodes.end(),parent);
		}
		bool insertfound = false;
		for (int y = 1; ((y < treenodes.size()) && (!insertfound)); ++y){
			if (treenodes[y]->freq > parent->freq){
				treenodes.insert(treenodes.begin()+(y-1),parent);
				insertfound=true;
			}
		}
	}
	huffnode* root = treenodes[0];
	vector<huffcode> huffcodes(symbols.size());
	for (int i = 0; i < symbols.size(); ++i){
		huffcode code;
		code.symbol = symbols[i];
		huffcodes[i] = code;
	}
	string rootcode = "0";
	getHuffCodes(root,huffcodes,rootcode);
	int compressedsize = 0;
	for (int z = 0; z < huffcodes.size(); ++z){
		compressedsize += huffcodes[z].freq * huffcodes[z].code.size();
	}
	cout << "Uncompressed Size: " << uncompressedsize << endl;
	cout << "Compressed Size: " << compressedsize << endl;
	cout << "Compression Ratio: " <<
	(double(compressedsize)/double(uncompressedsize)) << endl;
	cout.flush();
	return 0;
}
