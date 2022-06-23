/*****************************************************************/
/* Student Author:  YOU                                          */
/* Programmer: B. McVey                                          */
/* Purpose: Lab 2, CS 323, Fall 2016/18/20                       */
/*          Enumeration of all strings for an alphabet of size 2 */
/*          View DFA's as restricted C++ programs                */
/* Console mode program                                          */
/* Console I/O                                                   */
/* All Rights Reserved -                                         */
/*       No use without explicit permission of B. McVey          */
/*****************************************************************/
#include <iostream>
#include <string>
using namespace std;

// These seven functions are written for you and
// are undocumented since you need to figure out
// exactly what they do!
bool Q0();
bool Q1();
bool Q2();
bool Q3();
bool Q4();
bool Q5();
bool Q6();

// These are the two functions that you must complete.
// Skeletal definitions of them appear below main()
bool Iterative();
string Generate();

int main()
{

//PART I
 
	int j;
	string nextString;
	for (j = 1; j <= 20; j++)
	{
		nextString = Generate();
		cout << j << ": " << nextString << endl;
	}
 

// PART II - my code - recursion
/*
    cout << "Use CTRL-C to kill this loop ... and the program " << endl;
	while (1)
	{
		cout << "Enter a string consisting of symbols a and b" << endl;
		if ( Q0() )
			cout << "String is accepted!" << endl;
		else
			cout << "REJECTED!" << endl;
	}
*/
 
// PART II - your code
/*
	cout << "Use CTRL-C to kill this loop ... and the program " << endl;
	while(1)
	{
		cout << "Enter a string consisting of symbols a and b" << endl;
		if ( Iterative() )
			cout << "String is accepted!" << endl;
		else
			cout << "REJECTED!" << endl;
	}
*/
	cout << "To end the program, press a key and enter ... ";
	char ch;
	cin >> ch;
	return 0;
}
/*******************  FOR PART I **********************/
/******************************************************/
/*  Function Generate()                               */
/*  Purpose: Produces strings in lexicographic order  */
/*           from an alphabet of size 2.              */
/*  Parameters in:  None                              */
/*  Returns: one string ... the 'next' string in lex  */
/*           order with respect to previous call to   */
/*           Generate()                               */
/*  Notes:  static variables are initialized one time,*/
/*          and that is when first call to Generate() */
/*          is made.  Static variables retain their   */
/*          values from call to call                  */
/*  ie., static means (i) local in scope and          */
/*         (ii) persistent in memory (think lifetime) */
/******************************************************/    
string Generate()
{
	static string str = "";
	static int lex_number = 0;
	lex_number++;
	
	if (lex_number == 1)
		return str;

	// YOU WRITE!  determine the 'next' string ...

	string temp = str;

	int i = temp.size();

	for (i; i > 0; i--)
	{
		if (temp[i] == 'b')
		{
			temp[i] = 'a';
		}
		else
		{
			temp[i] = 'b';
			break;
		}
	}

	if (i == 0)
	{
		temp += 'a';
	}

	str = temp;

	return str;
}


/***************************  FOR PART II ************************/
/*****************************************************************/
/* Function: Iterative                                           */
/* Purpose:  Models DFA as in Q0-Q6 with restricted C++          */
/*        a single variable definition: int state;               */
/*       assignment statements only of the form state = ...      */
/*       switch statement(s), no other conditional statements    */
/*       a single while loop of the form while(1)                */
/*      return statement(s) which return bool with accept or not */
/*		only after the end of line character is detected.        */
/*      getchar() to read from standard input                    */
/* Parameters in:  none                                          */
/* Returns:  result of computation (accept, reject)              */
/*****************************************************************/
bool Iterative()
{
	int state = 0;
	while (1)
	{
	  // YOU WRITE USING THE MODEL BELOW!
	  
	    switch(state)
		{
			case 0: switch(getchar())
			{
				case 'a': state = 3;
					break;
				case 'b': state = 1;
					break;
				case '\n': return false;
			}
					break;
			case 1: 
			{
				switch (getchar())
				{
					case 'a': state = 4;
						break;
					case 'b': state = 2;
						break;
				}
				break;
			}
			break;
			case 2:
			{
				switch (getchar())
				{
				case 'a': state = 5;
					break;
				case 'b': state = 2;
					break;
				case '\n':
					return true;
				}
				break;
			}
			break;
			case 3:
			{
				switch (getchar())
				{
				case 'a': state = 6;
					break;
				case 'b': state = 4;
					break;
				}
				break;
			}
			break;
			case 4:
			{
				switch (getchar())
				{
				case 'a': state = 6;
					break;
				case 'b': state = 5;
					break;
				}
				break;
			}
			break;
			case 5:
			{
				switch (getchar())
				{
				case 'a': state = 6;
					break;
				case 'b': state = 5;
					break;
				case '\n':
					return true;
				}
				break;
			}
			break;
			case 6:
			{
				switch (getchar())
				{
				case 'a': state = 6;
					break;
				case 'b': state = 6;
					break;
				case '\n': return false;
				}
				return false;
			}
			break;
			// etc!!!
		}
	   
	}
}
/***************************************************/
/* Functions Qi                                    */
/* Purpose:  Each function models a state of a DFA */
/* Parameters in:  none                            */
/* Returns:  result of computation (accept, reject)*/
/***************************************************/

bool Q0()
{
	switch(getchar())
	{
		case 'a': return Q3();
		case 'b': return Q1();
		default :  return false;
	}
}
bool Q1()
{
	switch(getchar())
	{
		case 'a': return Q4();
		case 'b': return Q2();
		default :  return false;
	}
}
bool Q2()
{
	switch(getchar())
	{
		case 'a': return Q5();
		case 'b': return Q2();
		case '\n': return true;
		default: return false;
	}
}
bool Q3()
{
	switch(getchar())
	{
		case 'a': return Q6();
		case 'b': return Q4();
		default : return false;
	}
}
bool Q4()
{
	switch(getchar())
	{
		case 'a': return Q6();
		case 'b': return Q5();
		default : return false;
	}
}
bool Q5()
{
	switch(getchar())
	{
		case 'a': return Q6();
		case 'b': return Q5();
		case '\n': return true;
		default: return false;
	}
}
bool Q6()
{
	switch(getchar())
	{
		case'a': return Q6();
		case'b': return Q6();
		default: return false;
	}
}