#include <stdio.h>
#include <stdlib.h>

int get_utf_version(FILE* f_in)
{
	int i = fgetc(f_in);
	switch (i)
	{
	case 0xEF : {fseek(f_in, 2, SEEK_CUR); return 1;}
	case 0xFE : {fseek(f_in, 1, SEEK_CUR); return 2;}
	case 0x00 : {fseek(f_in, 3, SEEK_CUR); return 4;}
	case 0xFF :
	{
		fseek(f_in, 1, SEEK_CUR);
		i = fgetc(f_in);
		if (i == 0x00)
		{
			fseek(f_in, 1, SEEK_CUR);
			return 5;
		}
		else
		{
			fseek(f_in, -1, SEEK_CUR);
			return 3;
		}
	}
	default:
	{
		fseek(f_in, -1, SEEK_CUR);
		return 0;
	}
	}
}

void set_utf_version(FILE* f_out, int mode)
{
	switch (mode)
	{
	case 0:
		break;
	case 1:
		fprintf(f_out, "%c%c%c", (char)0xef, (char)0xbb, (char)0xbf);
		break;
	case 2:
		fprintf(f_out, "%c%c", (char)0xff, (char)0xfe);
		break;
	case 3:
		fprintf(f_out, "%c%c", (char)0xfe, (char)0xff);
		break;
	case 4:
		fprintf(f_out, "%c%c%c%c", (char)0xff, (char)0xfe, (char)0x00, (char)0x00);
		break;
	case 5:
		fprintf(f_out, "%c%c%c%c", (char)0x00, (char)0x00, (char)0xfe, (char)0xff);
		break;
	default:
		printf("incorrect utf version");
		exit(2);
	}
}

int get_utf_number(FILE *f_in, int mode)
{
	int res = 0;
	_Bool rev = 1;
	switch (mode)
	{
	case 0:
	case 1:
	{
		int ch = 0;
		fread(&ch, sizeof(char), 1, f_in);
		int i = 7, sch = 0;
		while (((1 << i) & ch) != 0)
		{
			sch++;
			i--;
		}
		if (sch == 0)
		{
			res = ch;
			break;
		}
		else
			sch--;
		ch &= 255 >> (sch + 2);
		res += ch;
		res <<= 6;
		while (sch > 0)
		{
			fread(&ch, sizeof(char), 1, f_in);
			ch &= 255 >> 2;
			res += ch;
			if (sch > 1)
				res <<= 6;
			sch -= 1;
		}
		break;
	}
	case 2:
		rev = 0;
	case 3:
	{
		int ch1 = 0, ch2 = 0;
		fread(&ch1, sizeof(char), 1, f_in);
		fread(&ch2, sizeof(char), 1, f_in);
		if (rev)
		{
			int tmp = ch1;
			ch1 = ch2;
			ch2 = tmp;
		}
		int i1 = ((255 & ch1) << 8) + (255 & ch2), i2;
		if (i1 >= 0xd800 && i1 <= 0xdfff)
		{
			fread(&ch1, sizeof(char), 1, f_in);
			fread(&ch2, sizeof(char), 1, f_in);
			if (rev)
			{
				int tmp = ch1;
				ch1 = ch2;
				ch2 = tmp;
			}
			i2 = ((255 & ch1) << 8) + (255 & ch2);
			i1 = (((i1 & 1023) << 10) + (i2 & 1023)) + 0x10000;
		}
		res = i1;
		break;
	}
	case 4:
		rev = 0;
	case 5:
	{
		int ch1 = 0, ch2 = 0, ch3 = 0, ch4 = 0;
		fread(&ch1, sizeof(char), 1, f_in);
		fread(&ch2, sizeof(char), 1, f_in);
		fread(&ch3, sizeof(char), 1, f_in);
		fread(&ch4, sizeof(char), 1, f_in);
		if (rev)
		{
			int tmp1 = ch1;
			int tmp2 = ch2;
			ch1 = ch4;
			ch2 = ch3;
			ch3 = tmp2;
			ch4 = tmp1;
		}
		res += (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
		break;
	}
	default:
		printf("incorrect utf version");
		exit(2);
	}
	return res;
}

void set_utf_number(FILE *f_out, int number, int mode)
{
	int ch1 = 0, ch2 = 0, ch3 = 0, ch4 = 0;
	_Bool rev = 0;
	switch (mode)
	{
	case 1:
	case 0:
		if (number >= 0x00 && number < 0x80)
		{
			ch1 = 127 & (char)number;
			fwrite(&ch1, sizeof(char), 1, f_out);
		}
		else if (number >= 0x80 && number < 0x800)
		{
			ch2 = (63 & (char)number) + 128;
			number >>= 6;
			ch1 = (31 & (char)number) + 192;
			fwrite(&ch1, sizeof(char), 1, f_out);
			fwrite(&ch2, sizeof(char), 1, f_out);
		}
		else if (number >= 0x800 && number < 0x10000)
		{
			ch3 = (63 & (char)number) + 128;
			number >>= 6;
			ch2 = (63 & (char)number) + 128;
			number >>= 6;
			ch1 = (15 & (char)number) + 224;
			fwrite(&ch1, sizeof(char), 1, f_out);
			fwrite(&ch2, sizeof(char), 1, f_out);
			fwrite(&ch3, sizeof(char), 1, f_out);
		}
		else if (number >= 0x10000 && number < 0x10ffff)
		{
			ch4 = (63 & (char)number) + 128;
			number >>= 6;
			ch3 = (63 & (char)number) + 128;
			number >>= 6;
			ch2 = (63 & (char)number) + 128;
			number >>= 6;
			ch1 = (7 & (char)number) + 240;
			fwrite(&ch1, sizeof(char), 1, f_out);
			fwrite(&ch2, sizeof(char), 1, f_out);
			fwrite(&ch3, sizeof(char), 1, f_out);
			fwrite(&ch4, sizeof(char), 1, f_out);
		}
		else
		{
			printf("Incorrect unicode code");
			exit(2);
		}
		break;
	case 2:
		rev = 1;
	case 3:
		if (number >= 0x10000)
		{
			number -= 0x10000;
			int HiSurrogate = 0xd800 + ((number >> 10) & 1023);
			int LoSurrogate = 0xdc00 + (number & 1023);
			ch2 = HiSurrogate & 255;
			ch1 = (HiSurrogate >> 8) & 255;
			ch4 = LoSurrogate & 255;
			ch3 = (LoSurrogate >> 8) & 255;
			if (rev)
			{
				int tmp = ch1;
				ch1 = ch2;
				ch2 = tmp;
				tmp = ch3;
				ch3 = ch4;
				ch4 = tmp;
			}
			fwrite(&ch1, sizeof(char), 1, f_out);
			fwrite(&ch2, sizeof(char), 1, f_out);
			fwrite(&ch3, sizeof(char), 1, f_out);
			fwrite(&ch4, sizeof(char), 1, f_out);
		}
		else
		{
			ch2 = number & 255;
			ch1 = (number >> 8) & 255;
			if (rev)
			{
				int tmp = ch1;
				ch1 = ch2;
				ch2 = tmp;
			}
			fwrite(&ch1, sizeof(char), 1, f_out);
			fwrite(&ch2, sizeof(char), 1, f_out);
		}
		break;
	case 4:
		rev = 1;
	case 5:
		ch4 = 255 & (char)number;
		number >>= 8;
		ch3 = 255 & (char)number;
		number >>= 8;
		ch2 = 255 & (char)number;
		number >>= 8;
		ch1 = 255 & (char)number;
		if (rev)
		{
			int tmp1 = ch1;
			int tmp2 = ch2;
			ch1 = ch4;
			ch2 = ch3;
			ch3 = tmp2;
			ch4 = tmp1;
		}
		fwrite(&ch1, sizeof(char), 1, f_out);
		fwrite(&ch2, sizeof(char), 1, f_out);
		fwrite(&ch3, sizeof(char), 1, f_out);
		fwrite(&ch4, sizeof(char), 1, f_out);
		break;
	default:
		printf("incorrect utf version");
		exit(2);
	}
}

int main(int argc, char** argv)
{
	if (argc != 4)
	{
		printf("You should write 3 arguments");
		return 1;
	}

	FILE* f_in = fopen(argv[1], "rb");
	FILE* f_out = fopen(argv[2], "wb");
	if (f_in == NULL || f_out == NULL)
	{
		printf("Failed to open files");
		fclose(f_in);
		fclose(f_out);
		return 1;
	}
	int mode_in = get_utf_version(f_in);
	int mode_out = (*argv[3]) - 48;
	set_utf_version(f_out, mode_out);

	char ch;
	while (fscanf(f_in, "%c", &ch) != EOF)
	{
		fseek(f_in, -1, SEEK_CUR);
		int number = get_utf_number(f_in, mode_in);
		set_utf_number(f_out, number, mode_out);
	}

	fclose(f_in);
	fclose(f_out);
	return 0;
}
