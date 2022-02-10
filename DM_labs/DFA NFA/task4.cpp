/*
#include <iostream>
#include <fstream>

using namespace std;

int jump[101][256]{};
long long dp[1001][101]{};
bool terminals[101]{};

int main()
{
	ifstream f_in("problem5.in");
	ofstream f_out("problem5.out");
	int n, m, t, l, tmp;
	f_in >> n >> m >> t >> l;
	for (int i = 0; i < t; i++)
	{
		f_in >> tmp;
		terminals[tmp] = true;
	}
	for (int i = 0; i < m; i++)
	{
		int a, b;
		char c;
		f_in >> a >> b >> c;
		jump[a][c] = b;
	}
	for (int len = 0; len <= l; len++)
	{
		dp[0][1] = 1;
		for (int i = 1; i <= n; i++)
		{
			for (int ch = 0; ch < 256; ch++)
			{
				if (jump[i][ch] != 0)
				{
					dp[len + 1][jump[i][ch]] += dp[len][i] % (1000000000 + 7);
					dp[len + 1][jump[i][ch]] %= (1000000000 + 7);
				}
			}
		}
	}
	long long answ = 0;
	for (int i = 0; i <= n; i++)
	{
		if (terminals[i])
		{
			answ += dp[l][i] % (1000000000 + 7);
			answ %= (1000000000 + 7);
		}
	}
	f_out << answ;
	return 0;
}
*/
