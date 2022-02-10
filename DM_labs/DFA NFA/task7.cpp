/*
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

bool terminals1[1001]{};
int maap1[1001][256]{};
bool terminals2[1001]{};
int maap2[1001][256]{};

int main()
{
	ifstream f_in("problem5.in");
	ofstream f_out("problem5.out");
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
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	bool flag = false;
	vector<vector<bool>> used(n1 + 1, vector<bool>(n2 + 1, false));
	queue<pair<int, int>> q;
	q.push(pair<int, int>(1, 1));
	while (!q.empty())
	{
		pair<int, int> uv = q.front();
		q.pop(); // // // // // // //
		if (terminals1[uv.first] != terminals2[uv.second])
		{
			f_out << "NO";
			f_in.close();
			f_out.close();
			return 0;
		}
		used[uv.first][uv.second] = true;
		for (int i = 0; i < 256; i++)
		{
			int i1 = maap1[uv.first][i];
			int i2 = maap2[uv.second][i];
			if (!used[i1][i2])
				q.push(pair<int, int>(maap1[uv.first][i], maap2[uv.second][i]));
		}
	}

	f_out << "YES";
	f_in.close();
	f_out.close();
	return 0;
}
*/
