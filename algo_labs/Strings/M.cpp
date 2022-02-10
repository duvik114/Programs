#include <algorithm>
#include <iostream>
#include <vector>
#include <set>

using namespace std;

long long PP = 61;
vector<long long> p_fac;

class Hash
{
public:
	long long str_i;
	long long start_pos;
	long long hash_code;

	Hash(long long str_i, long long startPos, long long hashCode)
			:str_i(str_i), start_pos(startPos), hash_code(hashCode)
	{
	}

	Hash()
	{
	}

	friend bool operator<(const Hash& hash1, const Hash& hash2)
	{
		return hash1.hash_code < hash2.hash_code;
	}
};

int main(int argc, char** argv)
{
	int n = 2;
	vector<string> strings;
	//cin >> n; /*_*/ ыгыс
	size_t max_length = 0;
	for (int i = 0; i < n; i++)
	{
		string s;
		cin >> s;
		max_length = max(max_length, s.length());
		strings.push_back(s);
	}

	p_fac.resize(max_length + 1);
	p_fac[0] = 1;/*p_fac[1] = 1;*/
	for (int i = 1; i < p_fac.size(); i++)
		p_fac[i] = p_fac[i - 1] * PP;

	vector<vector<long long>> pref_hashes(n, vector<long long>(max_length, 0));
	for (int i = 0; i < n; i++)
	{
		pref_hashes[i][0] = strings[i][0] - 'a' + 1;
		for (int j = 1; j < strings[i].length(); j++)
			pref_hashes[i][j] = pref_hashes[i][j - 1] + (strings[i][j] - 'a' + 1) * p_fac[j];
	}

	long long l = 0;
	long long r = 100001;
	string res;
	while (r - l > 1)
	{
		set<Hash> s;
		long long m = (r + l) / 2;

		if (m <= strings[0].size())
		{
			Hash hash1;
			hash1.str_i = 0;
			hash1.start_pos = 0;
			hash1.hash_code = (pref_hashes[0][m - 1]) * p_fac[max_length];
			s.insert(hash1);
		}

		for (int j = 1; j <= (int)strings[0].size() - m; j++)
		{
			Hash hash;
			hash.str_i = 0;
			hash.start_pos = j;
			hash.hash_code = (pref_hashes[0][j + m - 1] - pref_hashes[0][j - 1]) * p_fac[max_length - j];
			s.insert(hash);
		}
		for (int i = 1; i < n; i++)
		{
			set<Hash> s_new, s_old = s;
			s.clear(); /*    wtf    */

			if (m <= strings[i].size())
			{
				Hash hash2;
				hash2.str_i = i;
				hash2.start_pos = 0;
				hash2.hash_code = (pref_hashes[i][m - 1]) * p_fac[max_length];
				s_new.insert(hash2);
			}

			for (int j = 1; j <= (int)strings[i].size() - m; j++)
			{
				Hash hash;
				hash.str_i = i;
				hash.start_pos = j;
				hash.hash_code = (pref_hashes[i][j + m - 1] - pref_hashes[i][j - 1]) * p_fac[max_length - j];
				s_new.insert(hash);
			}
			set_intersection(s_old.begin(), s_old.end(), s_new.begin(), s_new.end(),
					std::inserter(s, s.begin())/*, ascendingHash*/);
		}

		if (!s.empty())
		{
			l = m;
			res = "";
			for (auto i : s)
				res = res.empty() ? strings[i.str_i].substr(i.start_pos, m) 
						          : min(res, strings[i.str_i].substr(i.start_pos, m));
		}
		else
			r = m;
	}

	cout << res;
}
