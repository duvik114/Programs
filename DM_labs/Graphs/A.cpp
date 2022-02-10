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
	vector<vector<bool>> kek(n+1, vector<bool>(n+1, false));
	getline(cin, str);
	for (int i = 1; i <= n; i++) {
		getline(cin, str);
		for (int j = 1; j < i; j++) {
			char val = str[j - 1];
			if (val != '0')
			{
				kek[i][j] = true;
				kek[j][i] = true;
			}
		}
		path.push_back(i);
	}

	for (int k = 0; k < n * (n - 1); k++)
	{
		if (!kek[path[0]][path[1]]) {
			int j = 2;
			for (; j + 1 < n; j++){
				if (kek[path[0]][path[j]] && kek[path[1]][path[j + 1]])
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

		/*int i = path.front();
		path.pop_front();
		if (!kek[i][path.front()]) {
			int j = 1;
			for (; j + 1 < n; j++){
				if (kek[i][path[j]] && kek[path.front()][path[j + 1]])
				{
					int jj = 0;
					while (jj < j - jj)
					{
						swap(path[jj], path[j - jj]);
						jj++;
					}
					break;
				}
			}
		}

		path.push_back(i);*/
	}

	for (int i = 0; i < n; i++) {
		cout << path[i] << " ";
	}

	return 0;
}
