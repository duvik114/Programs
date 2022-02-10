#include <stdio.h>
#include <math.h>
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Cub
{
	int x1, y1, z1;
	int x2, y2, z2;
};

int pow_of_two(int n)
{
	int pow = 1;
	while (pow < n)
		pow *= 2;
	return pow;
}

long long addX(int v, Cub cur_cub, Cub need_cub, int k, vector<long long>& t)
{
	if (cur_cub.x2 <= need_cub.x1 || cur_cub.x1 >= need_cub.x2)
	{
		return 0;
	}
	else if (cur_cub.x1 >= need_cub.x1 && cur_cub.x2 <= need_cub.x2)
	{
		//cout << cur_cub.x1 << " = x" << endl;
		long long old_value = t[v];
		t[v] += k;
		if (t[v] < 0)
			t[v] = 0;
		return t[v] - old_value;
		//return t[v].sum;
	}
	else
	{
		Cub cubL = cur_cub, cubR = cur_cub;
		cubL.x2 = (cur_cub.x1 + cur_cub.x2) / 2;
		cubR.x1 = (cur_cub.x1 + cur_cub.x2) / 2;
		long long res = addX(2 * v + 1, cubL, need_cub, k, t)
			+ addX(2 * v + 2, cubR, need_cub, k, t);
		t[v] += res;
		return res;
	}
}

long long addY(int v, Cub cur_cub, Cub need_cub, int k, vector<vector<long long>>& t)
{
	if (cur_cub.y2 <= need_cub.y1 || cur_cub.y1 >= need_cub.y2)
	{
		return 0;
	}
	else if (cur_cub.y1 >= need_cub.y1 && cur_cub.y2 <= need_cub.y2)
	{
		//cout << cur_cub.y1 << " = y" << endl;
		return addX(0, cur_cub, need_cub, k, t[v]);
		//return t[v].sum;
	}
	else
	{
		Cub cubL = cur_cub, cubR = cur_cub;
		cubL.y2 = (cur_cub.y1 + cur_cub.y2) / 2;
		cubR.y1 = (cur_cub.y1 + cur_cub.y2) / 2;
		long long res = addY(2 * v + 1, cubL, need_cub, k, t)
			+ addY(2 * v + 2, cubR, need_cub, k, t);
		long long l = addX(0, cur_cub, need_cub, res, t[v]);
		return res;
	}
}

long long addZ(int v, Cub cur_cub, Cub need_cub, int k, vector<vector<vector<long long>>>& t)
{
	//cout << cur_cub.z2 << endl;
	if (cur_cub.z2 <= need_cub.z1 || cur_cub.z1 >= need_cub.z2)
	{
		return 0;
	}
	else if (cur_cub.z1 >= need_cub.z1 && cur_cub.z2 <= need_cub.z2)
	{
		//cout << cur_cub.z1 << " = z" << endl;
		return addY(0, cur_cub, need_cub, k, t[v]);
		//return t[v].sum;
	}
	else
	{
		Cub cubL = cur_cub, cubR = cur_cub;
		cubL.z2 = (cur_cub.z1 + cur_cub.z2) / 2;
		cubR.z1 = (cur_cub.z1 + cur_cub.z2) / 2;
		long long res = addZ(2 * v + 1, cubL, need_cub, k, t)
			+ addZ(2 * v + 2, cubR, need_cub, k, t);
		long long l = addY(0, cur_cub, need_cub, res, t[v]);
		return res;
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

long long rsqX(int v, Cub cur_cub, Cub need_cub, vector<long long>& t)
{
	if (cur_cub.x2 <= need_cub.x1 || cur_cub.x1 >= need_cub.x2)
	{
		return 0;
	}
	else if (cur_cub.x1 >= need_cub.x1 && cur_cub.x2 <= need_cub.x2)
	{
		return t[v];
	}
	else
	{
		Cub cubL = cur_cub, cubR = cur_cub;
		cubL.x2 = (cur_cub.x1 + cur_cub.x2) / 2;
		cubR.x1 = (cur_cub.x1 + cur_cub.x2) / 2;
		return rsqX(2 * v + 1, cubL, need_cub, t)
			+ rsqX(2 * v + 2, cubR, need_cub, t);
	}
}

long long rsqY(int v, Cub cur_cub, Cub need_cub, vector<vector<long long>>& t)
{
	if (cur_cub.y2 <= need_cub.y1 || cur_cub.y1 >= need_cub.y2)
	{
		return 0;
	}
	else if (cur_cub.y1 >= need_cub.y1 && cur_cub.y2 <= need_cub.y2)
	{
		return rsqX(0, cur_cub, need_cub, t[v]);
	}
	else
	{
		Cub cubL = cur_cub, cubR = cur_cub;
		cubL.y2 = (cur_cub.y1 + cur_cub.y2) / 2;
		cubR.y1 = (cur_cub.y1 + cur_cub.y2) / 2;
		return rsqY(2 * v + 1, cubL, need_cub, t)
			+ rsqY(2 * v + 2, cubR, need_cub, t);
	}
}

long long rsqZ(int v, Cub cur_cub, Cub need_cub, vector<vector<vector<long long>>>& t)
{
	if (cur_cub.z2 <= need_cub.z1 || cur_cub.z1 >= need_cub.z2)
	{
		return 0;
	}
	else if (cur_cub.z1 >= need_cub.z1 && cur_cub.z2 <= need_cub.z2)
	{
		return rsqY(0, cur_cub, need_cub, t[v]);
	}
	else
	{
		Cub cubL = cur_cub, cubR = cur_cub;
		cubL.z2 = (cur_cub.z1 + cur_cub.z2) / 2;
		cubR.z1 = (cur_cub.z1 + cur_cub.z2) / 2;
		return rsqZ(2 * v + 1, cubL, need_cub, t)
			+ rsqZ(2 * v + 2, cubR, need_cub, t);
	}
}

int main()
{
	int n;
	scanf_s("%d\n", &n);
	int nd = pow_of_two(n);
	vector<vector<vector<long long>>> t(2*nd - 1, vector<vector<long long>> (2 * nd - 1, vector<long long>(2 * nd - 1, 0)));
	//заполнили нулями весь куб
	int i, v1, v2, v3, v4, v5, v6;
	char buf[512];
	while (true)
	{
		gets_s(buf);
		if (buf[0] == '1')
		{
			sscanf_s(buf, "%d %d %d %d %d\n", &i, &v1, &v2, &v3, &v4);
			Cub cub1 = { 0, 0, 0, nd, nd, nd };
			Cub cub2 = { v1, v2, v3, v1 + 1, v2 + 1, v3 + 1 };
			addZ(0, cub1, cub2, v4, t);
			//Cub cub22 = { v1 + 1, v2 + 1, v3 + 1, 0, 0, 0 };
			//add(v4, cub22, t, n_up);
		}
		else if (buf[0] == '2')
		{
			sscanf_s(buf, "%d %d %d %d %d %d %d\n", &i, &v1, &v2, &v3, &v4, &v5, &v6);
			v4++; v5++; v6++;
			Cub cub1 = { 0, 0, 0, nd, nd, nd };
			Cub cub2 = { v1, v2, v3, v4, v5, v6 };
			printf("%lld\n", rsqZ(0, cub1, cub2, t));
		}
		/*else if (buf[0] == '4')
		{
			for (int z = 1; z <= nd; z++)
			{
				for (int y = 0; y <= 0; y++)
				{
					for (int x = 0; x <= nd; x++)
						cout << t[z][y][x] << " ";
					cout << "" << endl;
				}
				cout << "======================================" << endl;
			}
		}*/
		else
			break;
	}

	return 0;
}
