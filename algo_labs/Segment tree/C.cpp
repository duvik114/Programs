#include <stdio.h>
#include <math.h>
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Tree
{
	long long left, right, sum, maxV;
};

int pow_of_two(int n)
{
	int pow = 1;
	while (pow < n)
		pow *= 2;
	return pow;
}

void set(int i, int data, vector<Tree>& t, int nd)
{
	t[i + nd - 1].left = data;
	t[i + nd - 1].right = data;
	t[i + nd - 1].sum = data;
	t[i + nd - 1].maxV = data;
	i += nd - 1;
	while (i != 0)
	{
		i = (i - 1) / 2;
		Tree a = t[2 * i + 1];
		Tree b = t[2 * i + 2];
		t[i].left = max(a.left, (a.sum + b.left));
		t[i].right = max(b.right, (b.sum + a.right));
		t[i].sum = a.sum + b.sum;
		t[i].maxV = max(max(a.maxV, b.maxV), (a.right + b.left));
		//t[i] = t[2 * i + 1] + t[2 * i + 2];
	}
}

/*long long rsq(int v, int l, int r, int a, int b, vector<Tree>& t)
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
}*/

int main()
{
	int n, m;
	cin >> n >> m;
	int nd = pow_of_two(n);
	vector<Tree> t(2 * nd - 1);
	long long s;
	for (int i = nd - 1; i < 2 * nd - 1; i++)
	{
		if (i < n + nd - 1)
		{
			cin >> s;
			t[i].left = s;
			t[i].right = s;
			t[i].sum = s;
			t[i].maxV = s;
			//t[nd - 1 + i] = s;
		}
		else
		{
			t[i].left = 0;
			t[i].right = 0;
			t[i].sum = 0;
			t[i].maxV = 0;
		}
	}
	for (int i = nd - 2; i >= 0; i--)
	{
		Tree a = t[2 * i + 1];
		Tree b = t[2 * i + 2];
		t[i].left = max(a.left, (a.sum + b.left));
		t[i].right = max(b.right, (b.sum + a.right));
		t[i].sum = a.sum + b.sum;
		t[i].maxV = max(max(a.maxV, b.maxV), (a.right + b.left));
		//t[i] = t[2 * i + 1] + t[2 * i + 2];
	}
	long long max_sum = t[0].maxV;
	if (max_sum < 0)
		max_sum = 0;
	printf("%lld\n", max_sum);

	int i, v;
	while (m > 0)
	{
		cin >> i >> v;
		if (i >= 0)
		{
			set(i, v, t, nd);
			max_sum = t[0].maxV;
			if (max_sum < 0)
				max_sum = 0;
			printf("%lld\n", max_sum);
		}
		else
		{
			for (int i = 0; i < nd; i++)
				cout << t[nd - 1 + i].sum << " ";
			cout << "" << endl;
		}
		m--;
	}

	return 0;
}
