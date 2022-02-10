#include<stdio.h>
#include<math.h>
#include <iostream>

using namespace std;

int pow_of_two(int n)
{
	int pow = 0;
	while (n > 0) {
		n /= 2;
		pow++;
	}
	return pow;
}

void set(int i, long long* t, long long* mas, int nd)
{
	if (t[i + nd - 1] == 0)
	{
		t[i + nd - 1] = 1;
		mas[i] = 1;
	}
	else
	{
		t[i + nd - 1] = 0;
		mas[i] = 0;
	}
	i += nd - 1;
	while (i != 0)
	{
		i = (i - 1) / 2;
		t[i] = t[2 * i + 1] + t[2 * i + 2];
	}
}

long long get_k_index(int v, int nd, int k, long long* t)
{
	if (v >= nd - 1) // if v == index of leaf
	{
		return v - (nd - 1);
	}
	else
	{
		if (t[2*v + 1] > k)
			return get_k_index(2*v + 1, nd, k, t);
		else
			return get_k_index(2*v + 2, nd, k - t[2*v + 1], t);
	}
}

int main()
{
	int n, m;
	scanf_s("%d %d", &n, &m);
	int nd = (int)pow(2, pow_of_two(n));
	long long* t = new long long[2 * nd - 1];
	long long* mas = new long long[nd];
	long long s;
	for (int i = 0; i < nd; i++)
	{
		if (i < n)
		{
			scanf_s("%lld ", &s);
			mas[i] = s;
			t[nd - 1 + i] = s;
		}
		else
		{
			t[nd - 1 + i] = 0;
			mas[i] = 0;
		}
	}
	for (int i = nd - 2; i >= 0; i--)
	{
		t[i] = t[2 * i + 1] + t[2 * i + 2];
	}

	int i, v;
	while (m > 0)
	{
		scanf_s("%d %d", &i, &v);
		if (i == 1)
		{
			set(v, t, mas, nd);
		}
		else if (i == 2)
		{
			printf("%lld\n", get_k_index(0, nd, v, t));
		}
		else
		{
			for (int i = 0; i < nd; i++)
				cout << mas[i] << " ";
			cout << "" << endl;
		}
		m--;
	}

	return 0;
}