#include<stdio.h>
#include<math.h>
#include<iostream>
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

long long get_value(int v, long long* t1, long long* t2)
{
	if (t2[v] != NULL)
		return t2[v];
	else
		return t1[v];
}

void push(int v, long long* t1, long long* t2)
{
	if (t2[v] != NULL)
	{
		t2[2 * v + 1] = t2[v];
		t2[2 * v + 2] = t2[v];
	}
	t1[v] = min(get_value(2 * v + 1, t1, t2),
		get_value(2 * v + 2, t1, t2)); 
	t2[v] = NULL;
}

void set(int v, int l, int r, int a, int b, int x, long long* t1, long long* t2)
{
	if (r <= a || l >= b)
	{
		//return xyz;
	}
	else if (l >= a && r <= b)
	{
		t2[v] = x;
	}
	else
	{
		push(v, t1, t2);
		set(2 * v + 1, l, (l + r) / 2, a, b, x, t1, t2);
		set(2 * v + 2, (l + r) / 2, r, a, b, x, t1, t2);
		t1[v] = min(get_value(2 * v + 1, t1, t2),
			get_value(2 * v + 2, t1, t2));
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
		return get_value(v, t1, t2);
	}
	else
	{
		push(v, t1, t2);
		return min(rmq(2 * v + 1, l, (l + r) / 2, a, b, t1, t2),
			rmq(2 * v + 2, (l + r) / 2, r, a, b, t1, t2));
	}
}

int main()
{
	int n, m;
	scanf_s("%d %d", &n, &m);
	int nd = (int)pow(2, pow_of_two(n));
	long long* t1 = new long long[2 * nd - 1]{};
	long long* t2 = new long long[2 * nd - 1]{};
	for (int i = 0; i < 2 * nd - 1; i++)
		t2[i] = NULL;
	long long* mas = new long long[nd]{};
	int i, v1, v2, x;
	while (m > 0)
	{
		scanf_s("%d %d %d", &i, &v1, &v2);
		if (i == 1)
		{
			scanf_s("%d", &x);
			set(0, 0, nd, v1, v2, x, t1, t2);
		}
		else if (i == 2)
		{
			printf("%lld\n", rmq(0, 0, nd, v1, v2, t1, t2));
		}
		else
		{
			for (int i = 0; i < nd; i++)
				cout << get_value(i + nd - 1, t1, t2) << " ";
			cout << "" << endl;
		}
		m--;
	}

	return 0;
}
