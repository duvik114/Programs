#include <iostream>
#include <vector>

using namespace std;

int c = 0, n;

bool rq(int a, int b)
{
	if (a == b)
		c /= 0;
	c++;

	if (c == 10000)
	{
		cout << "0";
		for (int i = 0; i < n; i++)
			cout << " 0";
		exit(0);
	}

	cout << 1 << " " << a << " " << b << endl;
	string answ;
	cin >> answ;
	return answ == "YES";

}

void merge(int l, int r, vector<int>& mas)
{
	vector<int> sas = mas;
	int i = l, j = (l + r) / 2;
	while ((i < (l + r) / 2) || (j < r))
	{
		if (i == (l + r) / 2)
		{
			sas[i + j - (l + r) / 2] = mas[j];
			j++;
		}
		else if (j == r)
		{
			sas[i + j - (l + r) / 2] = mas[i];
			i++;
		}
		else if (rq(mas[i], mas[j]))
		{
			sas[i + j - (l + r) / 2] = mas[i];
			i++;
		}
		else
		{
			sas[i + j - (l + r) / 2] = mas[j];
			j++;
		}
	}
	mas = sas;
}

void mergeSort(int l, int r, vector<int>& mas)
{
	if (r - l > 1)
	{
		mergeSort(l, (l + r) / 2, mas);
		mergeSort((l + r) / 2, r, mas);
		merge(l, r, mas);
	}
}

int main()
{
	cin >> n;
	vector<int> mas(n);
	for (int i = 0; i < n; i++)
		mas[i] = i + 1;

	mergeSort(0, n, mas);

	cout << "0";
	for (int i : mas)
		cout << " " << i;
	cout << "" << endl;
}
