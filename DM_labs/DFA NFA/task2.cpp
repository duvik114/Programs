/*
#include <iostream>
#include <fstream>

using namespace std;

bool terminals1[101];
bool maap1[101][256][101];

int main()
{
	string str;
	*/
/*
	＜￣｀ヽ、　　　　　　　／ ￣ ＞
　     ゝ、　　＼　／⌒ヽ,ノ 　 /´
　　　     ゝ、（(͡◉͜>͡◉) ／
　　 　　    >　 　 　 ,ノ
　　　　  　  ∠_,,,/´
	*//*

	ifstream f_in("problem5.in");
	ofstream f_out("problem5.out");
	f_in >> str;
	int n, m, k, tmp;
	f_in >> n >> m >> k;
	for (int i = 0; i < k; i++)
	{
		f_in >> tmp;
		terminals1[tmp] = true;
	}

	char c;
	int a, b;
	for (int i = 0; i < m; i++)
	{
		f_in >> a >> b >> c;
		//printf("%c\n", c);
		maap1[a][(int)c][b] = true;
	}

	bool *states = new bool[101]{};
	states[1] = true;
	for (char ch : str)
	{
		bool flag = true;
		bool *tmp_states = states;
		states = new bool[101]{};
		for (int i = 1; i < 101; i++)
		{
			if (!tmp_states[i])
				continue;
			for (int j = 1; j < 101; j++)
			{
				if (maap1[i][(int)ch][j])
				{
					states[j] = true;
					flag = false;
				}
			}
		}
		free(tmp_states);
		if (flag)
		{
			f_out << "Rejects";
			f_in.close();
			f_out.close();
			return 0;
		}
	}

	for (int i = 1; i < 101; i++)
	{
		if (!states[i])
			continue;
		if (terminals1[i])
		{
			f_out << "Accepts";
			f_in.close();
			f_out.close();
			return 0;
		}
	}

	f_out << "Rejects";
	f_in.close();
	f_out.close();

	return 0;
}
*/
