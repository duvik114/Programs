#include <iostream>
#include <vector>

using namespace std;

vector<vector<int>> matrix(10000, vector<int>());
vector<bool> visited(10000, false);
vector<int> color(10000, 0);
vector<int> deg(10000, 0);
int max_deg = 0;

void dfs(int v)
{
	visited[v] = true;
	vector<bool> colored(max_deg + 1, false);
	for (auto i = matrix[v].begin(); i != matrix[v].end(); i++)
			colored[color[*i]] = true;
	int col = 1;
	while (colored[col])
		col++;
	color[v] = col;

	for (auto i = matrix[v].begin(); i != matrix[v].end(); i++)
	{
		if (!visited[*i])
			dfs(*i);/*
		colored[color[*i]] = true;*/
	}
}

int main()
{
	int n, m;
	cin >> n >> m;
	for (int i = 0; i < m; i++)
	{
		int i1, i2;
		cin >> i1 >> i2;
		deg[i1]++;deg[i2]++;
		matrix[i1].push_back(i2);
		matrix[i2].push_back(i1);
		max_deg = max(max_deg, max(deg[i1], deg[i2]));
	}

	max_deg = max_deg % 2 == 0 ? max_deg + 1 : max_deg;

	dfs(1);

	cout << max_deg << endl;
	for (int i = 1; i <= n; i++)
		cout << color[i] << endl;

	return 0;
}
