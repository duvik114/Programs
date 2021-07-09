#include <string>
#include <stack>
#include "LN.h"

using namespace std;

void push_to_stack(string& str, stack<LN>& s)
{
	if ((str.length() > 1 && (str[1] >= '0' && str[1] <= '9'))
			|| (str[0] >= '0' && str[0] <= '9'))
	{
		LN ln = LN(str.c_str());
		s.push(ln);
		return;
	}
	LN ln2, ln1 = s.top();
	s.pop();
	switch (str[0])
	{
	case '+' :
	{
		ln2 = s.top();
		s.pop();
		s.push(ln1 + ln2);
		break;
	}
	case '-' :
	{
		ln2 = s.top();
		s.pop();
		s.push(ln1 - ln2);
		break;
	}
	case '*' :
	{
		ln2 = s.top();
		s.pop();
		s.push(ln1 * ln2);
		break;
	}
	case '/' :
	{
		ln2 = s.top();
		s.pop();
		s.push(ln1 / ln2);
		break;
	}
	case '%' :
	{
		ln2 = s.top();
		s.pop();
		s.push(ln1 % ln2);
		break;
	}
	case '=' :
	{
		ln2 = s.top();
		s.pop();
		s.push(LN((long long)(ln1 == ln2)));
		break;
	}
	case '!' :
	{
		ln2 = s.top();
		s.pop();
		s.push(LN((long long)(ln1 != ln2)));
		break;
	}
	case '<' :
	{
		ln2 = s.top();
		s.pop();
		if (str == "<=")
			s.push(LN((long long)(ln1 <= ln2)));
		else
			s.push(LN((long long)(ln1 < ln2)));
		break;
	}
	case '>' :
	{
		ln2 = s.top();
		s.pop();
		if (str == ">=")
			s.push(LN((long long)(ln1 >= ln2)));
		else
			s.push(LN((long long)(ln1 > ln2)));
		break;
	}
	case '~' :
	{
		s.push(~ln1);
		break;
	}
	case '_' :
	{
		s.push(-ln1);
		break;
	}
	}
}

int main(int argc, char** args)
{
	if (argc != 3)
	{
		cout << "incorrect number of args: should be 3\n";
		return 1;
	}
	ifstream f_in(args[1]);
	ofstream f_out(args[2]);
	if (!f_in.is_open())
	{
		cout << "Failed to open the file : " << args[1] << endl;
		f_out.close();
		return 1;
	}
	if (!f_out.is_open())
	{
		cout << "Failed to open the file : " << args[2] << endl;
		f_in.close();
		return 1;
	}

	stack<LN> s;
	try
	{
		while (!f_in.eof())
		{
			string str;
			f_in >> str;
			if (str.empty())
				continue;
			push_to_stack(str, s);
		}
		while (!s.empty())
		{
			s.top().print_mas(f_out);
			s.pop();
		}
	}
	catch (exception& e)
	{
		cout << e.what() << endl;
		f_in.close();
		f_out.close();
		return 2;
	}

	f_in.close();
	f_out.close();
	return 0;
}
