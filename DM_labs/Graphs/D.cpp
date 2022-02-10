#include <iostream>
#include <string>
#include <vector>
#include <deque>

using namespace std;

int main()
{
	int n;
	cin >> n;
	string str;
	deque<int> path;
	vector<vector<bool>> kek(n + 1, vector<bool>(n + 1, false));
	getline(cin, str);
	for (int i = 1; i <= n; i++)
	{
		getline(cin, str);
		for (int j = 1; j < i; j++)
		{
			char val = str[j - 1];
			if (val != '0')
				kek[i][j] = true;
			else
				kek[j][i] = true;
		}
		path.push_back(i);
	}

	for (int k = 0; k < n * (n - 1); k++)
	{
		if (!kek[path[0]][path[1]])
		{
			int j = 2;
			for (; j < n; j++)
			{
				if (kek[path[0]][path[j]])
				{
					int jj = 0;
					while (1 + jj < j - jj)
					{
						swap(path[1 + jj], path[j - jj]);
						jj++;
					}
					break;
				}
			}
		}

		path.push_back(path[0]);
		path.pop_front();
	}

	for (int i = 0; i < n; i++)
	{
		cout << path[i] << " ";
	}

	return 0;
}


/*
#include <iostream>
#include <string>
#include <vector>
#include <list>

using namespace std;

int main()
{
	int n;
	cin >> n;
	string str;
	vector<vector<bool>> kek(n + 1, vector<bool>(n + 1, false));
	getline(cin, str);
	for (int i = 1; i <= n; i++)
	{
		getline(cin, str);
		for (int j = 1; j < i; j++)
		{
			char val = str[j - 1];
			if (val != '0')
				kek[i][j] = true;
			else
				kek[j][i] = true;
		}
	}

	list<int> res;
	res.push_back(1);
	for (int i = 2; i <= n; i++)
	{
		auto j = res.begin();
		while (j != res.end() && kek[*j][i])
			j++;
		res.insert(j, i);
	}

	auto i = res.begin();
	i++;
	if (n > 1)
		i++;
	while (i != res.end() && kek[*i][res.front()])
		i++;
	auto j = i, k = res.begin();
	for (; j != res.end(); j++) {
		bool is_j_goes_to_any_res = false;
		for (k = res.begin(); k != i; k++)
		{
			if (!kek[*j][*k]){
				is_j_goes_to_any_res = true;
				break;
			}
		}
		if (is_j_goes_to_any_res)
		{
			for (auto h = i; h != j; h++)
				res.insert(k, *h);
			res.insert(k, *j);
			j++;
			i = j;
			j--;
		}
	}

	for (auto r = res.begin(); r != i; r++)
		cout << *r << " ";

	return 0;
}
*/
