
#include<stdio.h>
#include<math.h>
#include<iostream>
#include<algorithm>

using namespace std;

int pow_of_two(int n)
{
	int pow = 1;
	while (pow < n)
		pow *= 2;
	return pow;
}

long long get_value(int v, int l, int r, long long* t1, long long* t2, long long* t3)
{
	/*if (t2[v] != NULL)
		return (t2[v] + t3[v]) * (r - l);
	else
		return t1[v] + t3[v] * (r - l);*/
	if (t3[v] == 0)
	{
		if (t2[v] == LLONG_MIN)
			return t1[v];
		else
			return t2[v] * (r - l);
	}
	else
	{
		if (t2[v] == LLONG_MIN)
			return t1[v] + t3[v] * (r - l);
		else
			return (t2[v] + t3[v]) * (r - l);
	}
}

void push(int v, int l, int r, long long* t1, long long* t2, long long* t3)
{
	if (t2[v] != LLONG_MIN)
	{
		t3[2 * v + 1] = 0;
		t3[2 * v + 2] = 0;
		t2[2 * v + 1] = t2[v];
		t2[2 * v + 2] = t2[v];
		t2[v] = LLONG_MIN;
	}
	if (t3[v] != 0)
	{
		t3[2 * v + 1] += t3[v];
		t3[2 * v + 2] += t3[v];
		t3[v] = 0;
	}
	t1[v] = get_value(2 * v + 1, l, (l + r) / 2, t1, t2, t3) +
		get_value(2 * v + 2, (l + r) / 2, r, t1, t2, t3);
}

void set(int v, int l, int r, int a, int b, int x, long long* t1, long long* t2, long long* t3)
{
	if (r <= a || l >= b)
	{
		return;
	}
	else if (l >= a && r <= b)
	{
		t2[v] = x;
		t3[v] = 0;
		return;
	}
	else
	{
		push(v, l, r, t1, t2, t3);
		set(2 * v + 1, l, (l + r) / 2, a, b, x, t1, t2, t3);
		set(2 * v + 2, (l + r) / 2, r, a, b, x, t1, t2, t3);
		t1[v] = get_value(2 * v + 1, l, (l + r) / 2, t1, t2, t3) +
			get_value(2 * v + 2, (l + r) / 2, r, t1, t2, t3);
	}
}

void add(int v, int l, int r, int a, int b, int x, long long* t1, long long* t2, long long* t3)
{
	if (r <= a || l >= b)
	{
		return;
	}
	else if (l >= a && r <= b)
	{
		t3[v] += x;
		return;
	}
	else
	{
		push(v, l, r, t1, t2, t3);
		add(2 * v + 1, l, (l + r) / 2, a, b, x, t1, t2, t3);
		add(2 * v + 2, (l + r) / 2, r, a, b, x, t1, t2, t3);
		t1[v] = get_value(2 * v + 1, l, (l + r) / 2, t1, t2, t3) +
			get_value(2 * v + 2, (l + r) / 2, r, t1, t2, t3);
	}
}

long long rsq(int v, int l, int r, int a, int b, long long* t1, long long* t2, long long* t3)
{
	if (r <= a || l >= b)
	{
		return 0;
	}
	else if (l >= a && r <= b)
	{
		return get_value(v, l, r, t1, t2, t3);
	}
	else
	{
		push(v, l, r, t1, t2, t3);
		return rsq(2 * v + 1, l, (l + r) / 2, a, b, t1, t2, t3) +
			rsq(2 * v + 2, (l + r) / 2, r, a, b, t1, t2, t3);
	}
}

int main()
{
	int n, m;
	scanf_s("%d %d", &n, &m);
	int nd = pow_of_two(n);
	long long* t1 = new long long[2 * nd - 1]{};
	long long* t2 = new long long[2 * nd - 1];
	for (int i = 0; i < 2 * nd - 1; i++)
		t2[i] = LLONG_MIN;
	long long* t3 = new long long[2 * nd - 1]{};
	//long long* mas = new long long[nd] {};

	int i, v1, v2, x;
	while (m > 0)
	{
		scanf_s("%d %d %d", &i, &v1, &v2);
		if (i == 1)
		{
			scanf_s("%d", &x);
			set(0, 0, nd, v1, v2, x, t1, t2, t3);
		}
		else if (i == 2)
		{
			scanf_s("%d", &x);
			add(0, 0, nd, v1, v2, x, t1, t2, t3);
		}
		else if (i == 3)
		{
			printf("%lld\n", rsq(0, 0, nd, v1, v2, t1, t2, t3));
		}
		else
		{
			for (int i = 0; i < nd; i++)
				cout << "" << endl;
		}
		m--;
	}

	return 0;
}
