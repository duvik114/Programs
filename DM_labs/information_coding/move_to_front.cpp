#include <stdio.h>
#include <iostream>
#include <list>

using namespace std;

int main()
{
    list<char> alph;
    for (int i = 97; i <= 122; i++)
        alph.insert(alph.end(), (char)i);

    char ch;
    while (cin >> ch)
    {
        list<char>::iterator it = alph.begin();
        int schetchik = 1;
        while ((*it) != ch) { it++; schetchik++; }/**/
        printf("%d ", schetchik);
        alph.erase(it);
        alph.insert(alph.begin(), ch);
    }
}