#include <iostream>
#include <vector>
#include <mem.h>

using namespace std;

int main(int argc, char** argv)
{
	string s;
	cin >> s;
	vector<int> z_fun(s.length(), 0);
	int l = 0, r = 0;
	for (int i = 1; i < s.length(); i++)
	{
		z_fun[i] = min(r - i, z_fun[i - l]);
		if (z_fun[i] < 0)
			z_fun[i] = 0;

		while (i + z_fun[i] < s.length())
		{
			if (s[z_fun[i]] != s[i + z_fun[i]])
				break;
			z_fun[i] += 1;
		}
		if (i + z_fun[i] > r)
		{
			l = i; /*_*/
			r = l + z_fun[i];
		}
	}

	for (int i = 1; i < s.length(); i++)
	{
		if (z_fun[i] == s.length() - i) {
			if (memcmp(&s[0], &s[s.length() - i], i) == 0)
				cout << i;
			else
				cout << s.length();
			return 0;
		}
	}
	cout << s.length();
}