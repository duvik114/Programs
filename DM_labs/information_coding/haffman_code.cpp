#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int main()
{
    int n;
    scanf("%d", &n);
    long long mas[n];
    long long bits[n];
    long long elements[n];
    for (int i = 0; i < n; i++)
    {
        scanf("%lld", &mas[i]);
        bits[i] = 0;
    }

    sort(mas, mas + n);
    for (int i = 0; i < n; i++) { elements[i] = mas[i]; }

    for (int i = 0; i < n; i++)
    {
        if (i == n - 1)
        {
            cout << bits[i] << endl;
            break;
        }
        int index = i + 1;
        while (mas[index - 1] > mas[index])
        {
            swap(mas[index - 1], mas[index]);
            swap(bits[index - 1], bits[index]);
            swap(elements[index - 1], elements[index]);
            index++;
            if (index == n)
                break;
        }
        mas[i + 1] += mas[i];
        bits[i + 1] = bits[i] + elements[i] + bits[i + 1] + elements[i + 1];
        elements[i + 1] += elements[i];
    }
}