#include <iostream>
#include <vector>
#include <queue>

using namespace std;

class Node {
public:
	vector<int> children;
};

int main()
{
	int n;
	cin >> n;
	vector<Node> tree(n+1);
	priority_queue<int, vector<int>, greater<>> lists;
	for (int i = 0; i < n - 1; i++) {
		int a, b;
		cin >> a >> b;
		tree[a].children.push_back(b);
		tree[b].children.push_back(a);
	}
	for (int i = 0; i < n; i++) {
		if (tree[i+1].children.size() < 2)
			lists.push(i+1);
	}

	while (n > 2) {
		int i = lists.top();
		lists.pop();
		tree[tree[i].children[0]].children.erase(find(tree[tree[i].children[0]].children.begin(),
				tree[tree[i].children[0]].children.end(), i));
		if (tree[tree[i].children[0]].children.size() < 2)
			lists.push(tree[i].children[0]);

		cout << tree[i].children[0] << " ";
		tree[i].children.clear();
		n--;
	}

	return 0;
}
