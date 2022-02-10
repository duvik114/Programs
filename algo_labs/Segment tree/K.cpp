#include<stdio.h>
#include<math.h>
#include <iostream>
#include <vector>

using namespace std;

struct Tree
{
	int left;
	long long count;
	int right;
	long long sum;
	int upd;
};

struct Result
{
	long long count;
	long long sum;
	int right;
	int left;
};

int pow_of_two(int n)
{
	int pow = 0;
	while (n > 0) {
		n /= 2;
		pow++;
	}
	return pow;
}

/*void set(int i, int data, long long* t, int nd)
{
	t[i + nd - 1] = data;
	i += nd - 1;
	while (i != 0)
	{
		i = (i - 1) / 2;
		t[i] = t[2 * i + 1] + t[2 * i + 2];
	}
}*/

long long get_count(int v, int l, int r, vector<Tree>& t)
{
	if (t[v].upd == 1)
		return 1;
	if (t[v].upd == 0)
		return 0;
	else
		return t[v].count;
}

long long get_sum(int v, int l, int r, vector<Tree>& t)
{
	if (t[v].upd == 1)
		return r - l;
	if (t[v].upd == 0)
		return 0;
	else
		return t[v].sum;
}

int get_right(int v, vector<Tree>& t)
{
	if (t[v].upd == 1)
		return 1;
	if (t[v].upd == 0)
		return 0;
	else
		return t[v].right;
}

int get_left(int v, vector<Tree>& t)
{
	if (t[v].upd == 1)
		return 1;
	if (t[v].upd == 0)
		return 0;
	else
		return t[v].left;
}
void push(int v, int l, int r, vector<Tree>& t)
{
	if (t[v].upd == -1)
		return;
	t[2 * v + 1].upd = t[v].upd;
	t[2 * v + 2].upd = t[v].upd;
	t[v].upd = -1;
}

void set(int v, int l, int r, int a, int b, int upd, vector<Tree>& t)
{
	if (r <= a || l >= b)
	{
		return;
	}
	else if (l >= a && r <= b)
	{
		t[v].upd = upd;
		return ;
	}
	else
	{
		push(v, l, r, t);

		set(2 * v + 1, l, (l + r) / 2, a, b, upd, t);
		set(2 * v + 2, (l + r) / 2, r, a, b, upd, t);

		t[v].count = get_count(2 * v + 1, l, (l + r) / 2, t) + get_count(2 * v + 2, (l + r) / 2, r, t);
		if (get_right(2*v + 1, t) == 1 && get_left(2*v + 2, t) == 1)
			t[v].count -= 1;
		t[v].right = get_right(2 * v + 2, t);
		t[v].left = get_left(2 * v + 1, t);
		t[v].sum = get_sum(2 * v + 1, l, (l + r) / 2, t) + get_sum(2 * v + 2, (l + r) / 2, r, t);
		
		return ;
	}
}

int main()
{
	int n;
	scanf_s("%d\n", &n);
	int nd = (int)pow(2, 20);
	vector<Tree> t(2 * nd - 1);
	for (int i = 0; i < 2 * nd - 1; i++)
	{
		t[i].count = 0;
		t[i].left = 0;
		t[i].right = 0;
		t[i].sum = 0;
		t[i].upd = -1;
	}

	char i;
	int v1, v2;
	while (n > 0)
	{
		cin >> i;
		if (i == 'W')
		{
			cin >> v1 >> v2;
			set(0, 0, nd, 500000 + v1, 500000 + v1 + v2, 0, t);
			printf("%lld %lld\n", get_count(0, 0, nd, t), get_sum(0, 0, nd, t));
		}
		else if (i == 'B')
		{
			cin >> v1 >> v2;
			set(0, 0, nd, 500000 + v1, 500000 + v1 + v2, 1, t);
			printf("%lld %lld\n", get_count(0, 0, nd, t), get_sum(0, 0, nd, t));
		}
		else
		{
			//for (int i = 0; i < 2*nd - 1; i++)
				//cout << t[i].count << " "; не надо!
			cout << "" << endl;
		}
		n--;
	}

	return 0;
}
