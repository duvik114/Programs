#include <stdio.h>
#include <stdlib.h>

_Bool find_not_null(int i, int j, int size, float (* mas)[size]);

int print_res(int s, float (* mas)[s + 1], FILE* f_out);

int main(int argc, char** argv)
{
	if (argc != 3)
	{
		printf("you should run this program with 2 arguments : <file_in> <file_out>");
		return 1;
	}
	int size;
	FILE* f_in = fopen(argv[1], "r");
	if (f_in == NULL)
	{
		printf("could not open file : ");
		printf("%s", argv[1]);
		return 1;
	}
	fscanf(f_in, "%d", &size);
	float(* mas)[size + 1] = malloc(size * (size + 1) * sizeof(float));
	if (mas == NULL)
	{
		printf("failed to allocate memory");
		fclose(f_in);
		return 2;
	}
	for (int i = 0; i < size; i++)
	{
		for (int j = 0; j <= size; j++)
		{
			fscanf(f_in, "%f", &mas[i][j]);
		}
	}
	fclose(f_in);

	FILE* f_out = fopen(argv[2], "w");
	if (f_out == NULL)
	{
		printf("could not open file : ");
		printf("%s", argv[2]);
		free(mas);
		return 1;
	}
	_Bool flag = 0;
	_Bool flag_no_sol = 0;
	int j = 0;
	for (int i = 0; i < size; i++)
	{
		if (!find_not_null(i, j, size, mas))
		{
			flag = 1;
			if (j == size - 1)
			{
				if (mas[i][size] != 0)
				{
					fprintf(f_out, "%s", "no solution");
					flag_no_sol = 1;
					break;
				}
			}
			else
			{
				j++;
				i--;
				continue;
			}
		}
		float m = mas[i][j];
		for (int k = j; k <= size; k++)
		{
			mas[i][k] /= m;
		}
		for (int h = i + 1; h < size; h++)
		{
			m = mas[h][j];
			for (int k = j; k <= size; k++)
			{
				mas[h][k] -= mas[i][k] * m;
			}
		}
		if (j < size - 1)
		{
			j++;
		}
	}

	if (!flag)
	{
		if (print_res(size, mas, f_out) != 0)
		{
			fclose(f_out);
			free(mas);
			return 2;
		}
	}
	else if (!flag_no_sol)
	{
		fprintf(f_out, "%s", "many solutions");
	}
	fclose(f_out);
	free(mas);
	return 0;
}

int print_res(int s, float (* mas)[s + 1], FILE* f_out)
{
	float(* res) = malloc(s * sizeof(float));
	if (res == NULL)
	{
		printf("failed to allocate memory");
		return 2;
	}
	for (int i = 0; i < s; i++)
	{
		res[i] = mas[i][s];
	}
	for (int i = s - 1; i >= 0; i--)
	{
		for (int j = s - 1; j > i; j--)
		{
			res[i] -= res[j] * mas[i][j];
		}
	}

	for (int i = 0; i < s; i++)
	{
		fprintf(f_out, "%g\n", res[i]);
	}
	free(res);
	return 0;
}

_Bool find_not_null(int i, int j, int size, float (* mas)[size + 1])
{
	int i_copy = i;
	for (i; i < size; i++)
	{
		if (mas[i][j] != 0)
		{
			for (j; j <= size; j++)
			{
				float tmp = mas[i_copy][j];
				mas[i_copy][j] = mas[i][j];
				mas[i][j] = tmp;
			}
			return 1;
		}
	}
	return 0;
}
