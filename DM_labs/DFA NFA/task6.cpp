/*
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

int assoc[100001]{};
bool used[100001]{};
bool terminals1[100001]{};
int maap1[100001][256]{};
bool terminals2[100001]{};
int maap2[100001][256]{};

bool dfs(int u, int v, vector<bool> &diavol1, vector<bool> &diavol2)
{
	used[u] = true;
	if (terminals1[u] != terminals2[v])
		return false;
	assoc[u] = v;
	bool res = true;
	for (int i = 0; i < 256; i++)
	{
		int p1 = maap1[u][i];
		int p2 = maap2[v][i];
		if (diavol1[p1] != diavol2[p2])
			return false;
		if (used[p1])
			res &= (assoc[p1] == p2);
		else
			res &= dfs(p1, p2, diavol1, diavol2);
	}
	return res;
}

int main()
{
	ifstream f_in("problem5.in");
	ofstream f_out("problem5.out");
	vector<bool> diavol1(100001, true);
	vector<bool> diavol2(100001, true);
	int n1, n2, m, k, tmp, a, b; char c;

	f_in >> n1 >> m >> k;
	for (int i = 0; i < k; i++)
	{
		f_in >> tmp;
		terminals1[tmp] = true;
	}


	for (int i = 0; i < m; i++)
	{
		f_in >> a >> b >> c;
		maap1[a][(int)c] = b;
		if (a != b)
			diavol1[a] = false;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	f_in >> n2 >> m >> k;
	for (int i = 0; i < k; i++)
	{
		f_in >> tmp;
		terminals2[tmp] = true;
	}

	for (int i = 0; i < m; i++)
	{
		f_in >> a >> b >> c;
		maap2[a][(int)c] = b;
		if (a != b)
			diavol2[a] = false;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	if (n1 != n2)
	{
		f_out << "NO";
		f_in.close();
		f_out.close();
	}
	if (!dfs(1, 1, diavol1, diavol2))
	{
		f_out << "NO";
		f_in.close();
		f_out.close();
	}
	else
	{
		f_out << "YES";
		f_in.close();
		f_out.close();
	}
	return 0;
}
*/
