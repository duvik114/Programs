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

void set(int i, int data, long long* t, long long* mas, int nd)
{
	t[i + nd - 1] = data;
	mas[i] = data;
	i += nd - 1;
	while (i != 0)
	{
		i = (i - 1) / 2;
		t[i] = t[2 * i + 1] + t[2 * i + 2];
	}
}

long long rsq(int v, int l, int r, int a, int b, long long* t)
{
	if (r <= a || l >= b)
	{
		return 0;
	}
	else if (l >= a && r <= b)
	{
		return t[v];
	}
	else
	{
		return rsq(2 * v + 1, l, (l + r) / 2, a, b, t) +
			rsq(2 * v + 2, (l + r) / 2, r, a, b, t);
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

	int i, v1, v2;
	while (m > 0)
	{
		scanf_s("%d %d %d", &i, &v1, &v2);
		if (i == 1)
		{
			set(v1, v2, t, mas, nd);
		}
		else if (i == 2)
		{
			printf("%lld\n", rsq(0, 0, nd, v1, v2, t));
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