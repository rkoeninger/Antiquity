#include <stdlib.h>
#include <string.h>
#include <math.h>

// Convert a C++ long long integer to a BigInteger.
char *toBigInt (long long n) {
   int ndigits;
   // Set aside the proper number of bytes to stick the string into
   if (n > 0)
      ndigits = (int)ceil(log10((double)(n+1)));
   else if (n < 0)
      ndigits = (int)ceil(log10(-(double)(n-1)))+1;  // +1 for the '-' sign
   else 
      ndigits = 1;  // Must catch this case as special because log10(0) is big
   char *ch = new char[ndigits+1];

   // Terminate the string with a 0
   // For the special case of n=0, put a '0' into the 1st position and return
   ch[ndigits] = 0;
   if (n == 0) {
      ch[0] = '0';
      return ch;
   }

   // For a negative number, put a '-' into the first position
   if (n < 0) ch[0] = '-';

   // Install the bits.  This works regardless of the sign of n
   for (int i=ndigits-1 ; n != 0 ; i--) {
      ch[i] = (n % 10) + '0';
      n /= 10;
   }
   return ch;
}

// First of several functions intended for use by the multiplier only.
// Add two BigIntegers.
// Assume n1 and n2 are non-negative numbers.
char *addBigInt (char *n1, char *n2) {
   if (n1 == NULL) return n2;
   if (n2 == NULL) return n1;

   int d1 = strlen(n1);
   int d2 = strlen(n2);
   int size = (d1 > d2) ? d1+1 : d2+1;

   char *result = new char[size+1];
   result[size] = 0;
   for (int i=0 ; i < size ; i++) result[i] = '0';
   int acc = 0;
   while (d1 > 0 || d2 > 0) {
      int n = acc;
      if (d1 > 0) n += n1[--d1] - '0';
      if (d2 > 0) n += n2[--d2] - '0';
      result[--size] = (n % 10) + '0';
      acc = (n / 10);
   }
   if (acc > 0) result[--size] = acc + '0';
   return &result[size];
}

// Second of several functions for use by the multiplier only.
// Multiply a given BigInteger by 10^s (that is, shift the BigInteger
// s places to the left).
char *shiftLeft (char *n, int s) {
   if (s <= 0) return n;
   char *result = new char[strlen(n)+s+1];
   unsigned int i=0;
   for ( ; i < strlen(n) ; i++) result[i] = n[i];
   for ( ; s > 0 ; s--) result[i++] = '0';
   result[i] = 0;
   return result;
}

// Third of several functions intended for use by the multiplier only.
// Mutiply a given BigInteger by a single digit.
// Assume '0' <= d && d <= '9' and n is non-negative.
char *multiplyLine (char *n, char d) {
   char *result = new char[strlen(n)+2];
   result[strlen(n)+1] = 0;
   int carry = 0;
   for (int i=strlen(n)-1 ; i >=0 ; i--) {
      int r = carry + (d - '0')*(n[i] - '0');
      result[i+1] = (r % 10) + '0';
      carry = (r / 10);
   }
   if (carry > 0) {
      result[0] = carry + '0';
      return result;
   }
   return &result[1];
}

// Multiply two BigIntegers.
char *multiplyBigInt (char *n1, char *n2) {
   // sign == 1 iff output is a positive number
   int sign = 1;
   char *x1, *x2;

   // Strip off any signs and record whether output should be negative
   if (n1[0] == '-') { sign *= -1;  x1 = &n1[1]; } else x1 = n1;
   if (n2[0] == '-') { sign *= -1;  x2 = &n2[1]; } else x2 = n2;

   char *result = "0";  // Accumulates the result
   int k=0;             // line multiply shifts k position
   for (int i=strlen(x2)-1 ; i >= 0 ;  i--) 
      result = addBigInt (result, shiftLeft(multiplyLine(x1, x2[i]), k++));

   // Strip leading 0s and add '-' if the product is negative
   char *p;
   for (p=result ; !('1' <= *p && *p <= '9') ; p++);
   if (sign < 0) *(--p) = '-';
   return p;
}

