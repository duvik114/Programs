#include <algorithm>
#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>

using namespace std;

int main()
{
	int n;
	cin >> n;
	unordered_map<int, int> pruf_map;
	for (int i = 1; i <= n; i++)
		pruf_map.insert(make_pair(i, 0));
	vector<int> pruf(n - 2);
	priority_queue<int, vector<int>, greater<>> leafs;
	for (int i = 0; i < n - 2; i++)
	{
		cin >> pruf[i];
		pruf_map.find(pruf[i])->second++;
	}
	for (int i = 1; i <= n; i++)
	{
		if (pruf_map.find(i)->second == 0)
			leafs.push(i);
	}

	int temp;
	for (int i = 0; i < n - 2; i++)
	{
		cout << leafs.top() << " " << pruf[i] << endl;
		temp = pruf[i];
		pruf[i] = -1;
		leafs.pop();
		pruf_map.find(temp)->second--;
		if (pruf_map.find(temp)->second == 0)
			leafs.push(temp);
	}

	cout << leafs.top() << " ";
	leafs.pop();
	cout << leafs.top();
}
