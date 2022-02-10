#include <iostream>
#include <vector>

using namespace std;

int main(int argc, char** argv)
{
	string s;
	cin >> s;
	vector<int> p(s.length(), 0);
	for (int i = 1; i < s.length(); i++)
	{
		int k = p[i - 1];
		while (k > 0)
		{
			if (s[i] != s[k])
				k = p[k - 1];
			else
				break;
		}
		p[i] = k;
		if (s[i] == s[k])
			p[i] += 1;
	}
	for (auto i : p)
		cout << i << " ";
}
