#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct str_struct
{
	char name[21];
	char surname[21];
	char thirdname[21];
	char telephone[13];
};

struct mas_struct
{
	long long size;
	struct str_struct *structs;
};

void swap_words(long long i1, long long i2, struct mas_struct *structs)
{
	struct str_struct tmp = structs->structs[i1];
	structs->structs[i1] = structs->structs[i2];
	structs->structs[i2] = tmp;
}

long long read_data(FILE *f_in, struct mas_struct *structs)
{
	long long i = 0;
	while (!feof(f_in))
	{
		if (i >= structs->size)
		{
			structs->structs = (struct str_struct*)realloc(structs->structs, structs->size * 2 * sizeof(struct str_struct));
			if (structs->structs == NULL)
			{
				printf("Failed to REallocate memory");
				free(structs->structs);
				free(structs);
				fclose(f_in);
				exit(2);
			}
			structs->size *= 2;
		}
		if (fscanf(f_in, "%s %s %s %s\n", structs->structs[i].surname, structs->structs[i].name, structs->structs[i].thirdname, structs->structs[i].telephone) != 4)
		{
			printf("Failed to read from file");
			free(structs->structs);
			free(structs);
			fclose(f_in);
			exit(2);
		}
		i++;
	}
	return i;
}

int compare_words(long long i1, long long i2, struct mas_struct *structs)
{
	int i = strcmp(structs->structs[i1].surname, structs->structs[i2].surname);
	if (i == 0)
	{
		i = strcmp(structs->structs[i1].name, structs->structs[i2].name);
		if (i == 0)
		{
			i = strcmp(structs->structs[i1].thirdname, structs->structs[i2].thirdname);
			if (i == 0)
				return strcmp(structs->structs[i1].telephone, structs->structs[i2].telephone);
			else
				return i;
		}
		else
			return i;
	}
	else
		return i;
}

long long get_index(long long l, long long r, struct mas_struct *structs)
{
	long long m = (l + r) / 2;
	int i = compare_words(l, m, structs);
	if (i < 0)
	{
		if (compare_words(m, r, structs) < 0)
			return m;
		return (compare_words(l, r, structs) < 0) ? r : l;
	}
	else
	{
		if (compare_words(l, r, structs) < 0)
			return l;
		return (compare_words(m, r, structs) < 0) ? r : m;
	}
}

void name_sort_words(long long l, long long r, struct mas_struct *structs)
{
	if (r - l <= 1)
		return;
	for (long long i = l; i < r; i++)
	{
		long long j = i;
		while (j > l && (compare_words(j, j - 1, structs) < 0))
		{
			swap_words(j, j - 1, structs);
			j--;
		}
	}
}

void quick_sort_words(long long l, long long r, struct mas_struct *structs)
{
	if (r - l <= 1)
		return;
	if (r - l < 16)
	{
		name_sort_words(l, r, structs);
		return;
	}
	long long index = get_index(l, r, structs);
	swap_words(l, index, structs);
	long long index_min = l;
	long long index_max = l + 1;
	for (long long i = l + 1; i < r; i++)
	{
		int k = compare_words(index_min, i, structs);
		if (k > 0)
		{
			swap_words(i, index_max, structs);
			swap_words(index_max, index_min, structs);
			index_min++;
			index_max++;
		}
		else if (k == 0)
		{
			swap_words(i, index_max, structs);
			index_max++;
		}
		// else skip();
	}
	quick_sort_words(l, index_min, structs);
	quick_sort_words(index_max, r, structs);
}

int main(int argc, char** argv)
{
	if (argc != 3)
	{
		printf("You should run program with 3 arguments");
		return 1;
	}

	FILE *f_in = fopen(argv[1], "r");
	if (f_in == NULL)
	{
		printf("Can't open input file");
		return 1;
	}

	struct mas_struct *structs = (struct mas_struct*)malloc(sizeof(struct mas_struct));
	if (structs == NULL)
	{
		printf("Failed to allocate memory");
		return 2;
	}
	structs->size = 1;
	structs->structs = (struct str_struct*)malloc(sizeof(struct str_struct));
	if (structs->structs == NULL)
	{
		printf("Failed to allocate memory");
		free(structs);
		return 2;
	}

	long long line_count = read_data(f_in, structs);
	quick_sort_words(0, line_count, structs);
	fclose(f_in);

	FILE *f_out = fopen(argv[2], "w");
	if (f_out == NULL)
	{
		printf("Can't open input file");
		free(structs->structs);
		free(structs);
		return 1;
	}
	for (int i = 0; i < line_count; i++)
		fprintf(f_in, "%s %s %s %s\n", structs->structs[i].surname, structs->structs[i].name, structs->structs[i].thirdname, structs->structs[i].telephone);
	fclose(f_out);
	free(structs->structs);
	free(structs);

	return 0;
}
