/*
 * Robert Koeninger
 * Section 002
 * Project 1 - Large Integer ADT
 * Monday, April 17, 2006
 *
 * This include file contains the class member function and operator overload
 * implementations.
 */

#ifndef INTEGER_CLASSIMPL
    #define INTEGER_CLASSIMPL
    #include <iostream>
    using std::istream;
    using std::ostream;
    using std::cout;
    using std::endl;
    #include <vector>
    using std::vector;
    #include <cmath>
    using std::max;
    using std::min;
    #include <string>
    using std::string;
    
Integer::Integer()
{

    sign = 0;
    digits.resize(1);
    digits[0] = 0;

}

Integer::Integer(const Integer& original)
{

    Copy(original);

}

void Integer::Copy(const Integer& original)
{

    sign = original.sign;
    digits = original.digits;

}

void Integer::Insert(ostream& out) const
{

    if (sign == 0)
    {

        out << '0';
        return;

    }
    else if (sign == -1)
        out << '-';

    // Display each digit
    for (int x = digits.size() - 1; x >= 0; --x)
        out << digits[x];

}

void Integer::Extract(istream& in)
{

    sign = 1;
    digits.resize(0);

    char currentDigit;
    int currentPosition = 0;
    bool couldBeZero = true;

    for (;;)
    {

        currentDigit = in.get();

        // Check for explicit sign
        if (currentPosition == 0)
        {

            // Explicit plus sign
            if (currentDigit == '+')
            {

                sign = 1;
                continue;

            }

            // Explicit negative sign
            else if (currentDigit == '-')
            {

                sign = -1;
                continue;

            }

        }

        // Check for end-of-integer
        if ((currentDigit < '0') || (currentDigit > '9'))
        {

            //if (currentPosition == 0)
            //    continue;
            //else
            if (couldBeZero)
                sign = 0;

            // Input is done
            // (the digits are in the wrong order, reverse them)

            for (int x = 0; x < (digits.size() / 2); ++x)
            {

                int temp = digits[x];
                digits[x] = digits[digits.size() - 1 - x];
                digits[digits.size() - 1 - x] = temp;

            }

            return;

        }

        // Check for leading zeroes
        else if ((currentDigit == '0') && (currentPosition == 0))
        {

            // Ignore leading zeroes
            continue;

        }

        // Make room and record next digit
        digits.resize(digits.size() + 1);
        digits[currentPosition++] = currentDigit - '0';
        couldBeZero = false;

    }

}

int Integer::Compare(const Integer& comparand) const
{

    //  Compare signs first
    if (sign > comparand.sign)
        return 1;
    else if (sign < comparand.sign)
        return -1;
    else if ((sign == 0) && (comparand.sign == 0))
        return 0;

    // Compare the number of digits in each argument, they are of the same sign
    if (digits.size() > comparand.digits.size())
        return 1;
    else if (digits.size() < comparand.digits.size())
        return -1;

    // Compare digit by digit most to least significant)
    for (int x = digits.size() - 1; x >= 0; --x)
        if (digits[x] > comparand.digits[x])
            return 1;
        else if (digits[x] < comparand.digits[x])
            return -1;

    return 0;

}

Integer& Integer::AbsoluteValue() const
{

    Integer* abs = new Integer();
    
    if (sign == 0)
    {

        abs->Copy(*this);
        return *abs;

    }
    
    abs->Copy(*this);
    abs->sign = 1;
    return *abs;

}

string Integer::ToBinaryString() const
{

    if (sign == 0)
        return "";

    void binString(string&, const Integer&);

    string str = "";
    binString(str, *this);
    return str;

}

bool Integer::LessThanTwo() const
{

    if ((sign == 0) || (sign == -1))
        return true;
    else if (digits.size() > 1)
        return false;
        
    return digits[0] < 2;

}

void binString(string& str, const Integer& integer)
{

    if (integer.sign == 0)
        return;

    binString(str, integer.divideByTwo());
    str += integer.isEven() ? '0' : '1';

}

Integer& Integer::Add(const Integer& value) const
{

    Integer* sum = new Integer();

    // Zero plus that equals that
    if (sign == 0)
    {

        sum->Copy(value);
        return *sum;

    }

    // This plus zero equals this
    else if (value.sign == 0)
    {

        sum->Copy(*this);
        return *sum;

    }

    // Can be done as unsigned addition
    if (sign == value.sign)
    {

        Integer augend;

        if (*this >= value)
        {

            sum->Copy(*this);
            augend = value;

        }
        else if (*this < value)
        {

            sum->Copy(value);
            augend = *this;

        }
        
        long originalSumSize = sum->digits.size();
        
        // Add the two numbers column by column
        for (long cursor = 0; cursor < originalSumSize; ++cursor)
        {

            // Add the augend digit to the sum
            if (cursor < augend.digits.size())
                sum->digits[cursor] += augend.digits[cursor];

            // Cut place value and carry 1 if necessary
            if (sum->digits[cursor] > 9)
            {

                sum->digits[cursor] -= 10;
                
                // While carrying one, check for resize
                if ((cursor + 1) >= sum->digits.size())
                    sum->digits.resize(sum->digits.size() + 1);
                    
                sum->digits[cursor + 1] += 1;

            }
            
        }
        
    } // End of (equal signs)

    // Subtract the smaller from the greater
    else if (sign != value.sign)
    {

        Integer subtrahend;
        
        if (this->AbsoluteValue() == value.AbsoluteValue())
        {

            // a - a = 0
            sum->sign = 0;
            sum->digits.resize(0);
            return *sum;

        }
        else if (this->AbsoluteValue() >= value.AbsoluteValue())
        {

            sum->Copy(*this);
            subtrahend = value;

        }
        else if (this->AbsoluteValue() < value.AbsoluteValue())
        {

            sum->Copy(value);
            subtrahend = *this;

        }

        long originalSumSize = sum->digits.size();

        // Add the two numbers column by column
        for (long cursor = 0; cursor < originalSumSize; ++cursor)
        {

            // Subtract the subtrahend digit from the sum
            if (cursor < subtrahend.digits.size())
                sum->digits[cursor] -= subtrahend.digits[cursor];

            // Increase place value and carry -1 if necessary
            if (sum->digits[cursor] < 0)
            {

                sum->digits[cursor] += 10;
                sum->digits[cursor + 1] -= 1;

            }

        }

        sum->stripLeadingZeroes();
    
    } // End if (signs are not equal)

    return *sum;

}

Integer& Integer::Subtract(const Integer& subtrahend) const
{

    Integer opposite;
    opposite.Copy(subtrahend);
    opposite.sign = -(opposite.sign);
    return Add(opposite);

}

Integer& Integer::Multiply(const Integer& multiplicand) const
{

    Integer* product = new Integer();
    
    // If either sign is zero, the result is zero
    if ((sign == 0) || (multiplicand.sign == 0))
        return *product;

    // The lesser is left
    Integer left, right;
    
    if (this->AbsoluteValue() >= multiplicand.AbsoluteValue())
    {

        right.Copy(*this);
        left.Copy(multiplicand);

    }
    else
    {

        right.Copy(multiplicand);
        left.Copy(*this);

    }
    
    // The product is formed row by row
    Integer row;
    
    // The minimum size of each row
    unsigned long rowSize = max(multiplicand.digits.size(), digits.size());
    short currentPlace = 1; // The current place being multiplied
    short carry = 0;        // The value carried from the previous place
    
    for (unsigned long leftCursor = 0; leftCursor < left.digits.size(); ++leftCursor)
    {

        // Resize row to include extra zeroes in the lowest places
        row.digits.resize(leftCursor + rowSize);
        
        // Clear out the lowest places of the row
        for (unsigned long lowest = 0; lowest < leftCursor; ++lowest)
            row.digits[lowest] = 0;
            
        row.sign = 0; // Reset this row to zero
        carry = 0;    // Re-initialize the carry

        /*
         * Each place value in the current row is the product of the current
         * left hand digit and each of the right hand digits.
         */
        unsigned long rightOriginalSize = right.digits.size();
        
        for (unsigned long rightCursor = 0; rightCursor <
        rightOriginalSize; ++rightCursor)
        {

            // Multiply the two place values
            currentPlace = (left.digits[leftCursor] *
            right.digits[rightCursor]) + carry;
            carry = currentPlace / 10;
            currentPlace = currentPlace % 10;

            // Resize for carry
            if ((leftCursor + rightCursor) >= row.digits.size())
                row.digits.resize(leftCursor + rightCursor + 1);

            row.digits[leftCursor + rightCursor] = currentPlace;

            // If we've reached the end of this row, make room for carry
            if ((carry != 0) && (rightCursor == (right.digits.size() - 1)))
            {

                row.digits.resize(row.digits.size() + 1);
                row.digits[rightCursor + 1] = carry;

            }

            // We know that the sign of this row is no longer zero
            if ((carry != 0) || (currentPlace != 0))
                row.sign = 1;

        }
        
        // Add the row to the product
        *product += row;
        
    }
    
    // Determine the sign of the product
    product->sign = sign == multiplicand.sign ? 1 : -1;
    product->stripLeadingZeroes(); // Wrap-up
    
    return *product;

}

void Integer::stripLeadingZeroes()
{

    // A value of zero needs no digits
    if (sign == 0)
    {

        digits.resize(0);
        return;

    }

    long extraZeroes = 0;

    for (int x = digits.size() - 1; x >= 0; --x, ++extraZeroes)
        if (digits[x] != 0)
            break;

    digits.resize(digits.size() - extraZeroes);
    
    // No digits, must be zero
    if (digits.size() == 0)
        sign = 0;
        
}

bool Integer::isEven() const
{

    if (sign == 0)
        return 0;
        
    return (digits[0] % 2) == 0;

}

Integer& Integer::divideByTwo() const
{

    Integer* qoutient = new Integer();

    if (sign == 0)
        return *qoutient;

    qoutient->Copy(*this);

    short prevCarry = 0; // Carried from the previous place, one of {0, +5}
    short nextCarry = 0; // Carried to the next place, one of {0, +5}
    
    // Run though digits in reverse order
    for (long cursor = qoutient->digits.size() - 1; cursor >= 0; --cursor)
    {

        // Remember what to carry for next time
        nextCarry = (qoutient->digits[cursor] % 2 == 0) ? 0 : 5;
        
        qoutient->digits[cursor] /= 2;
        qoutient->digits[cursor] += prevCarry;
        
        // What the next iteration should carry
        prevCarry = nextCarry;

    }
    
    qoutient->sign = sign;          // The sign is the sign of this
    qoutient->stripLeadingZeroes(); // Clean-up
    
    return *qoutient;

}

/*
 * These operator overloads are fairly self-explanitory. Each one calls a
 * similiar member function.
 */

Integer& Integer::operator=(const Integer& original)
{

    Copy(original);
    return *this;

}

Integer& Integer::operator+=(const Integer& original)
{

    Copy(this->Add(original));
    return *this;

}

Integer& Integer::operator-=(const Integer& original)
{

    Copy(this->Subtract(original));
    return *this;

}

Integer& Integer::operator*=(const Integer& original)
{

    Copy(this->Multiply(original));
    return *this;

}

Integer& operator+(const Integer& left, const Integer& right)
{

    return left.Add(right);

}

Integer& operator-(const Integer& left, const Integer& right)
{

    return left.Subtract(right);

}

Integer& operator*(const Integer& left, const Integer& right)
{

    return left.Multiply(right);

}
        
ostream& operator<<(ostream& out, const Integer& integer)
{

     integer.Insert(out);
     return out;

}

istream& operator>>(istream& in, Integer& integer)
{

    integer.Extract(in);
    return in;

}

bool operator==(const Integer& left, const Integer& right)
{

    return left.Compare(right) == 0;

}

bool operator!=(const Integer& left, const Integer& right)
{

    return left.Compare(right) != 0;

}

bool operator<(const Integer& left, const Integer& right)
{

    return left.Compare(right) < 0;

}

bool operator>(const Integer& left, const Integer& right)
{

    return left.Compare(right) > 0;

}

bool operator<=(const Integer& left, const Integer& right)
{

    return left.Compare(right) <= 0;

}

bool operator>=(const Integer& left, const Integer& right)
{

    return left.Compare(right) >= 0;

}

#endif
