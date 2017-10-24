#include <iomanip>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include "bigint.h"
using namespace std;

// Stream declaration
class Stream {
public:
   bool isNull;
   const void *first;
   virtual Stream *rest () { cout << "Whoops!\n"; return NULL; }
};

// Inputs: two streams of integers in increasing order
// Output: a stream in increasing order consisting of exactly those
// numbers in the input streams (assume only infinite streams).
class Merge : public Stream {
   Stream *s1, *s2;
   int (*compare) (const void *, const void *), t;

public:
   Merge (Stream *a, Stream *b, int (*cmp)(const void *, const void *)) {
      isNull = false;
      s1 = a;
      s2 = b;
      compare = cmp;
      if (a->isNull && b->isNull) {
	 isNull = true;
      } else if (a->isNull) {
	 first = b->first;
	 t = 1;
      } else if (b->isNull) {
	 first = a->first;
	 t = 2;
      } else if (compare(a->first, b->first) < 0) {
	 first = a->first;
	 t = 3;
      } else {
	 first = b->first;
	 t = 4;
      }
   }

   Stream *rest () {
      if (isNull) return this;
      switch (t) {
      case 1: return s2->rest();
      case 2: return s1->rest();
      case 3: return new Merge(s1->rest(), s2, compare);
      case 4: return new Merge(s1, s2->rest(), compare);
      }
      return this;
   }
};

// Inputs: an integer n and a Stream s of numbers
// Output: the Stream of all tokens of s multiplied by n
class Times : public Stream {
   Stream *s1;  char *multiplier;

public:
   Times (char *n, Stream *s) {
      isNull = s->isNull;
      s1 = s;
      multiplier = new char[strlen(n)+1];
      strcpy(multiplier,n);
      if (!isNull) first = multiplyBigInt(n, (char *)s->first);
   }

   Times *rest() { return new Times (multiplier, s1->rest()); }
};

// Finite size array of objects as a stream
class Array : public Stream {
   const void **array;
   int n;

public:
   Array (const void **a, int m) {
      if (m < 1 || a == NULL) isNull = true;
      else { isNull = false; first = a[0]; }
      n = m;
      array = a;
   }

   Array *rest () { return new Array(&array[1], n-1); }
};

int bi_cmp (const void *a, const void *b) {
   if (strlen((char*)a) < strlen((char*)b)) return -1;
   if (strlen((char*)a) > strlen((char*)b)) return 1;
   return strcmp((char*)a, (char*)b);
}

// p is a list of prime numbers in increasing order
// + is concatenate, * is streams times
// hamming(p) =
//    if (p == null) return null;
//    return first(p) + merge(first(p)*hamming(p), hamming(rest(p));
//
/* Note: "this" replaces "new Hamming(primes)" in "Stream *rest()" */
class Hamming : public Stream {
   Array *primes;

public:
   Hamming (Array *primes) {
      isNull = primes->isNull;
      first = primes->first;
      this->primes = primes;
   }

   Merge *rest () {
      return new Merge(new Hamming(primes->rest()),
		       new Times((char*)primes->first, this),
		       bi_cmp);
   }
};

// Test of the above
/*int main() {
   // Hamming
   cout << "Hamming sequence given primes 3,5,11:\n";
   cout << "-------------------------------------\n";
   const void **primes = (const void **)new char*[3];
   primes[0] = "3";
   primes[1] = "5";
   primes[2] = "11";
   Stream *s = new Hamming(new Array(primes, 3));
   for (int i=0 ; i < 10000 ; i++, s=s->rest()) {
      char buffer[1024];
      sprintf(buffer,"%5d:%s", i+1, (char*)s->first);
      cout << setw(40) << setiosflags(ios::left) << buffer;
      if (!((i+1) % 2)) cout << "\n";
   }
   cout << "\n";
}*/
