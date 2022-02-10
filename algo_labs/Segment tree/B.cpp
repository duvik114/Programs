#include<stdio.h>
#include <iostream>
#include<algorithm>

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

void set(int i, int data, long long* t1, long long* t2, long long* mas, int nd)
{
	t1[i + nd - 1] = data;
	//t2[i + nd - 1] = 1;
	mas[i] = data;
	i += nd - 1;
	while (i != 0)
	{
		i = (i - 1) / 2;
		t1[i] = min(t1[2 * i + 1], t1[2 * i + 2]);
		if (t1[2 * i + 1] == t1[2 * i + 2])
		{
			t2[i] = t2[2 * i + 1] + t2[2 * i + 2];
		}
		else
		{
			if (t1[2 * i + 1] < t1[2 * i + 2])
			{
				t2[i] = t2[2 * i + 1];
			}
			else
			{
				t2[i] = t2[2 * i + 2];
			}
		}
	}
}

long long rmq(int v, int l, int r, int a, int b, long long* t1, long long* t2)
{
	if (r <= a || l >= b)
	{
		return LLONG_MAX;
	}
	else if (l >= a && r <= b)
	{
		return t1[v];
	}
	else
	{
		return min(rmq(2 * v + 1, l, (l + r) / 2, a, b, t1, t2),
			rmq(2 * v + 2, (l + r) / 2, r, a, b, t1, t2));
	}
}

long long rmq_count(int v, int l, int r, int a, int b, long long* t1, long long* t2, long long mmin)
{
	if (r <= a || l >= b)
	{
		return 0;
	}
	else if (l >= a && r <= b)
	{
		if (t1[v] == mmin)
			return t2[v];
		else
		{
			return 0;
		}
	}
	else
	{
		return rmq_count(2 * v + 1, l, (l + r) / 2, a, b, t1, t2, mmin) +
			rmq_count(2 * v + 2, (l + r) / 2, r, a, b, t1, t2, mmin);
	}
}

int main()
{
	int n, m;
	scanf_s("%d %d", &n, &m);
	int nd = (int)pow(2, pow_of_two(n));
	long long* t1 = new long long[2 * nd - 1];
	long long* t2 = new long long[2 * nd - 1];
	long long* mas = new long long[nd];
	long long s;
	for (int i = 0; i < nd; i++)
	{
		if (i < n)
		{
			scanf_s("%lld ", &s);
			mas[i] = s;
			t1[nd - 1 + i] = s;
		}
		else
		{
			t1[nd - 1 + i] = LLONG_MAX;
			mas[i] = LLONG_MAX;
		}
		t2[nd - 1 + i] = 1;
	}
	for (int i = nd - 2; i >= 0; i--)
	{
		t1[i] = min(t1[2 * i + 1], t1[2 * i + 2]);
		if (t1[2 * i + 1] == t1[2 * i + 2])
		{
			t2[i] = t2[2 * i + 1] + t2[2 * i + 2];
		}
		else
		{
			if (t1[2 * i + 1] < t1[2 * i + 2])
			{
				t2[i] = t2[2 * i + 1];
			}
			else
			{
				t2[i] = t2[2 * i + 2];
			}
		}
	}

	int i, v1, v2;
	while (m > 0)
	{
		scanf_s("%d %d %d", &i, &v1, &v2);
		if (i == 1)
		{
			set(v1, v2, t1, t2, mas, nd);
		}
		else if (i == 2)
		{
			printf("%lld %lld \n", rmq(0, 0, nd, v1, v2, t1, t2),
				rmq_count(0, 0, nd, v1, v2, t1, t2, rmq(0, 0, nd, v1, v2, t1, t2)));
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