#include<stdio.h>
#include<math.h>
#include <iostream>
#include <algorithm>

using namespace std;

int nd = 0;

int pow_of_two(int n)
{
	int pow = 0;
	while (n > 0) {
		n /= 2;
		pow++;
	}
	return pow;
}

void set(int i, int data, long long* t, long long* mas)
{
	t[i + nd - 1] = data;
	mas[i] = data;
	i += nd - 1;
	while (i != 0)
	{
		i = (i - 1) / 2;
		t[i] = max(t[2 * i + 1], t[2 * i + 2]);
	}
}

long long min_index_search(int v, int x, long long* t)
{
	if (v >= nd - 1)
		return v - (nd - 1);
	else if (t[2 * v + 1] >= x)
		return min_index_search(2 * v + 1, x, t);
	else
		return min_index_search(2 * v + 2, x, t);
}

long long rMq(int v, int l, int r, int a, int b, int x, long long* t)
{
	if (r <= a || l >= b)
	{
		return -1;
	}
	else if (l >= a && r <= b)
	{
		if (t[v] < x)
			return -1;
		else
		{
			return min_index_search(v, x, t);
		}
	}
	else
	{
		int i1 = rMq(2 * v + 1, l, (l + r) / 2, a, b, x, t);
		if (i1 != -1)
			return i1;
		int i2 = rMq(2 * v + 2, (l + r) / 2, r, a, b, x, t);
		if (i2 != -1)
			return i2;
		return -1;
	}
}

int main()
{
	int n, m;
	scanf_s("%d %d", &n, &m);
	nd = (int)pow(2, pow_of_two(n));
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
			t[nd - 1 + i] = LLONG_MIN;
			mas[i] = LLONG_MIN;
		}
	}
	for (int i = nd - 2; i >= 0; i--)
	{
		t[i] = max(t[2 * i + 1], t[2 * i + 2]);
	}

	int i, v1, v2;
	while (m > 0)
	{
		scanf_s("%d %d %d", &i, &v1, &v2);
		if (i == 1)
		{
			set(v1, v2, t, mas);
		}
		else if (i == 2)
		{
			printf("%lld\n", rMq(0, 0, nd, v2, nd, v1, t));
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
