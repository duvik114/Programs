#include <iostream>

using namespace std;

int main(int argc, char** argv)
{
	int m;
	char s[100001];
	cin >> s >> m;
	while (m > 0)
	{
		int s1, s2, f1, f2;
		cin >> s1 >> f1 >> s2 >> f2;
		s1--; s2--;
		if (f1-s1 == f2-s2)
		{
			if (memcmp(&s[s1], &s[s2], f1-s1) == 0)
				cout << "Yes\n";
			else
				cout << "No\n";
		}
		else
			cout << "No\n";
		m--;
	}
}
