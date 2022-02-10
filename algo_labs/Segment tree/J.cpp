#include<stdio.h>
#include<math.h>
#include<iostream>
#include<algorithm>
#include<vector>

using namespace std;

int nd;

struct Tree
{
	int max, min, upd;
	long long count;
};

int get_max(int v, vector<Tree>& t);
int get_min(int v, vector<Tree>& t);

int pow_of_two(int n)
{
	int pow = 1;
	while (pow < n)
		pow *= 2;
	return pow;
}

void push(int v, vector<Tree>& t)
{
	if (t[v].upd <= 0)
		return;
	t[2 * v + 1].upd = max(t[2 * v + 1].upd, t[v].upd);
	t[2 * v + 2].upd = max(t[2 * v + 2].upd, t[v].upd);
	t[v].upd = -1;
}

long long get_count(int v, vector<Tree>& t)
{
	if (t[v].upd <= 0)
		return t[v].count;
	if (t[v].max <= t[v].upd)
		return 0;
	if (t[v].min > t[v].upd)
		return t[v].count;
	push(v, t);

	int res = get_count(2 * v + 1, t) + get_count(2 * v + 2, t);
	t[v].max = max(get_max(2 * v + 1, t),
		get_max(2 * v + 2, t));
	t[v].min = min(get_min(2 * v + 1, t),
		get_min(2 * v + 2, t));
		if (get_min(2 * v + 1, t) <= 0)
			t[v].min = get_min(2 * v + 2, t);
		if (get_min(2 * v + 2, t) <= 0)
			t[v].min = get_min(2 * v + 1, t);
	t[v].count = res;

	return res;
}

int get_max(int v, vector<Tree>& t)
{
	if (t[v].upd <= 0)
		return t[v].max;
	if (t[v].max <= t[v].upd)
		return 0;
	if (t[v].min > t[v].upd)
		return t[v].max;
	push(v, t);

	int res = max(get_max(2 * v + 1, t), get_max(2 * v + 2, t));
	t[v].max = res;
	t[v].min = min(get_min(2 * v + 1, t),
		get_min(2 * v + 2, t));
		if (get_min(2 * v + 1, t) <= 0)
			t[v].min = get_min(2 * v + 2, t);
		if (get_min(2 * v + 2, t) <= 0)
			t[v].min = get_min(2 * v + 1, t);
	t[v].count = get_count(2 * v + 1, t) +
		get_count(2 * v + 2, t);

	return res;
}

int get_min(int v, vector<Tree>& t)
{
	if (t[v].upd <= 0)
		return t[v].min;
	if (t[v].max <= t[v].upd)
		return 0;
	if (t[v].min > t[v].upd)
		return t[v].min;
	push(v, t);

	int res = min(get_min(2 * v + 1, t), get_min(2 * v + 2, t));
	if (get_min(2 * v + 1, t) <= 0)
		res = get_min(2 * v + 2, t);
	if (get_min(2 * v + 2, t) <= 0)
		res = get_min(2 * v + 1, t);
	t[v].max = max(get_max(2 * v + 1, t),
		get_max(2 * v + 2, t));
	t[v].min = res;
	t[v].count = get_count(2 * v + 1, t) +
		get_count(2 * v + 2, t);

	return res;
}

void set(int v, int l, int r, int a, int b, int h, vector<Tree>& t)
{
	if (r <= a || l >= b)
	{
		return;
	}
	else if (l >= a && r <= b)
	{
		t[v].max = h;
		t[v].min = h;
		t[v].count = 1;
		t[v].upd = -1;
		return;
	}
	else
	{
		push(v, t);
		set(2 * v + 1, l, (l + r) / 2, a, b, h, t);
		set(2 * v + 2, (l + r) / 2, r, a, b, h, t);
		t[v].max = max(get_max(2 * v + 1, t),
			get_max(2 * v + 2, t));
		t[v].min = min(get_min(2 * v + 1, t),
			get_min(2 * v + 2, t));
			if (get_min(2 * v + 1, t) <= 0)
				t[v].min = get_min(2 * v + 2, t);
			if (get_min(2 * v + 2, t) <= 0)
				t[v].min = get_min(2 * v + 1, t);
		t[v].count = get_count(2 * v + 1, t) +
			get_count(2 * v + 2, t);
		return;
	}
}

long long rPq(int v, int l, int r, int a, int b, int p, vector<Tree>& t)
{
	if (r <= a || l >= b)
	{
		return 0;
	}
	else if (l >= a && r <= b)
	{
		int old_count = get_count(v, t);
		t[v].upd = max(p, t[v].upd);
		return old_count - get_count(v, t);
	}
	else
	{
		push(v, t);
		long long res = rPq(2 * v + 1, l, (l + r) / 2, a, b, p, t) + 
			rPq(2 * v + 2, (l + r) / 2, r, a, b, p, t);
		t[v].max = max(get_max(2 * v + 1, t),
			get_max(2 * v + 2, t));
		t[v].min = min(get_min(2 * v + 1, t),
			get_min(2 * v + 2, t));
			if (get_min(2 * v + 1, t) <= 0)
				t[v].min = get_min(2 * v + 2, t);
			if (get_min(2 * v + 2, t) <= 0)
				t[v].min = get_min(2 * v + 1, t);
		t[v].count = get_count(2 * v + 1, t) + 
			get_count(2 * v + 2, t);
		return res;
	}
}

int main()
{
	int n, m;
	scanf_s("%d %d", &n, &m);
	nd = pow_of_two(n);
	vector<Tree> t(2 * nd - 1);
	for (int i = 0; i < 2 * nd - 1; i++)
	{
		t[i].min = 0;
		t[i].max = 0;
		t[i].count = 0;
		t[i].upd = -1;
	}
	int i, v1, v2, p;
	while (m > 0)
	{
		scanf_s("%d %d %d", &i, &v1, &v2);
		if (i == 1)
		{
			set(0, 0, nd, v1, v1 + 1, v2, t);
		}
		else if (i == 2)
		{
			scanf_s("%d", &p);
			printf("%lld\n", rPq(0, 0, nd, v1, v2, p, t));
		}
		else
		{
			for (int i = 0; i < nd; i++)
				cout << t[nd - 1 + i].max << " ";
			cout << "" << endl;
		}
		m--;
	}

	return 0;
}
