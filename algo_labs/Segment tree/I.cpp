#include <stdio.h>
#include <math.h>
#include <iostream>
#include <vector>

using namespace std;

int r;

struct Matrix
{
	int i11, i12, i21, i22;
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

Matrix mat_mul(Matrix m1, Matrix m2)
{
	Matrix m;
	m.i11 = (m1.i11 * m2.i11 + m1.i12 * m2.i21) % r;
	m.i12 = (m1.i11 * m2.i12 + m1.i12 * m2.i22) % r;
	m.i21 = (m1.i21 * m2.i11 + m1.i22 * m2.i21) % r;
	m.i22 = (m1.i21 * m2.i12 + m1.i22 * m2.i22) % r;
	return m;
}

Matrix rMq(int v, int l, int r, int a, int b, vector<Matrix>& t)
{
	if (r <= a || l >= b)
	{
		Matrix m;
		m.i11 = 1;
		m.i12 = 0;
		m.i21 = 0;
		m.i22 = 1;
		return m;
	}
	else if (l >= a && r <= b)
	{
		return t[v];
	}
	else
	{
		return mat_mul(rMq(2 * v + 1, l, (l + r) / 2, a, b, t),
			rMq(2 * v + 2, (l + r) / 2, r, a, b, t));
	}
}

int main()
{
	int n, m;
	scanf_s("%d %d %d\n", &r, &n, &m);
	int nd = (int)pow(2, pow_of_two(n));
	vector<Matrix> t(2 * nd - 1);
	for (int i = nd - 1; i < 2*nd - 1; i++)
	{
		if (i - (nd - 1) < n)
		{
			scanf_s("%d %d\n", &t[i].i11, &t[i].i12);
			scanf_s("%d %d\n\n", &t[i].i21, &t[i].i22);
		}
		else
		{
			t[i].i11 = 1;
			t[i].i12 = 0;
			t[i].i21 = 0;
			t[i].i22 = 1;
		}
	}
	for (int i = nd - 2; i >= 0; i--)
	{
		t[i] = mat_mul(t[2 * i + 1], t[2 * i + 2]);
	}

	int v1, v2;
	while (m > 0)
	{
		cin >> v1 >> v2; v1 -= 1;
		Matrix matrix = rMq(0, 0, nd, v1, v2, t);
		printf("%d %d\n", matrix.i11, matrix.i12);
		printf("%d %d\n\n", matrix.i21, matrix.i22);
		m--;
	}

	return 0;
}
