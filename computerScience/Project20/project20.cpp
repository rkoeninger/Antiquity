/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 20
 * 28/02/06
 *
 * Reverses the words in a string, and
 * counts the frequency of each word length.
 */

#include <iostream>
#include <conio.h>
#include <vector>
#include <string>

using namespace std;

char* copyString(const char*, int, int);

int main()
{

    // The string we are manipulating
    const char sentence[] =
    "I go and it is done; the bell invites me.  Hear it not, Duncan;"
    " for it is a knell that summons thee to heaven or to hell.\0";
    
    // A vector to hold the words
    vector<string> words(0);    // The words from the sentence
    vector<int> lengthFreqs(0); // Words of length 1-16 chars
    
    // The begining and ending indecies of each word
    int startIndex = -1;
    
    for (int index = 0;; index++)
    {

        if (sentence[index] > ' ') // Found a character
        {

            if (startIndex < 0)
                startIndex = index; // Mark start of word

        }
        else  if (startIndex >= 0) // found end of the word
        {

            const int length = index - startIndex;
            string word(copyString(sentence, startIndex, length));
            
            char last = word[word.size() - 1];

            if (! ((last >= 'A' && last <= 'Z') || // Last char is a punc. mark
            (last >= 'a' && last <= 'z'))){         // Then swap
            
                for (int x = word.size() - 1; x >= 0; x--)
                    word[x] = word[x - 1];

                word[0] = last;
            
            }
            
            words.resize(words.size() + 1);
            words[words.size() - 1] = word;
            
            // Increment length frequency counter
            if (length > lengthFreqs.size())
                lengthFreqs.resize(length);
                
            lengthFreqs[length - 1]++;
            
            startIndex = -1; // Now we're looking for the next word

        }

        if (sentence[index] == '\0')
            break;
        
    }
    
    cout << sentence << "\n\n";

    for (int w = words.size() - 1; w >= 0; w--)
        cout << words[w] << " ";

    cout << "\n\n";
    cout << "Word Length Frequencies";
    cout << "\n";
    
    for (int l = 1; l < lengthFreqs.size() - 1; l++)
        cout << l << " characters: " << lengthFreqs[l - 1] << "\n";
        
    cout << "\ngoodbye";
    
    getch();
    return 0;

}

char* copyString(const char* src, int start, int length)
{

    static char dst[64];

    for (int i = 0; i < length; ++i)
        dst[i] = src[start + i];

    dst[length] = '\0';
    return dst;

}
