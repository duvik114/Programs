#include "LN.h"

LN::LN(LN &&ln) noexcept
{
	if (ln.is_NaN_)
		set_to_NaN();
	else
	{
		digits_ = ln.digits_;
		sign_ = ln.sign_;
		mas_ = ln.mas_;
	}
	ln.mas_ = nullptr;
}

LN::LN(const LN& ln)
{
	if (ln.is_NaN_)
		set_to_NaN();
	else
	{
		sign_ = ln.sign_;
		digits_ = ln.digits_;
		mas_ = new char[digits_];
		for (long long i = 0; i < digits_; i++)
			mas_[i] = ln.mas_[i];
	}
}

LN::LN(long long number)
{
	if (abs(number) <= 1)
		digits_ = 1;
	else
		digits_ = ceil(log10(abs(number)));

	mas_ = new char[digits_];
	if (number == 0)
	{
		mas_[0] = 0;
		return;
	}
	if (number < 0)
	{
		number = -number;
		sign_ = -1;
	}
	for (long long i = 0; i < digits_; i++)
	{
		mas_[i] = number / (long long)pow(10, (digits_ - i - 1));
		number %= (long long)pow(10, (digits_ - i - 1));
	}
}

LN::LN(const char* number)// : LN(std::string_view(number))
{
	long long i = 0, i_mas = 0;
	digits_ = strlen(number);
	long long digits_copy = digits_;
	if (digits_ == 0)
	{
		set_to_NaN();
		return;
	}
	if (number[0] == '-')
	{
		sign_ = -1;
		digits_--;
		i = 1;
	}
	mas_ = new char[digits_];
	for (; i < digits_copy; i++)
	{
		if (number[i] < '0' || number[i] > '9')
		{
			set_to_NaN();
			return;
		}
		mas_[i_mas] = number[i] - 48;
		i_mas++;
	}
}

LN::LN(std::string_view number)
{
	long long i = 0, i_mas = 0;
	digits_ = number.length();
	long long digits_copy = digits_;
	if (digits_ == 0)
	{
		set_to_NaN();
		return;
	}
	if (number[0] == '-')
	{
		sign_ = -1;
		digits_--;
		i = 1;
	}
	mas_ = new char[digits_];
	for (; i < digits_copy; i++)
	{
		if (number[i] < '0' || number[i] > '9')
		{
			set_to_NaN();
			return;
		}
		mas_[i_mas] = number[i] - 48;
		i_mas++;
	}
}

LN::~LN()
{
	delete []mas_;
	mas_ = nullptr;
}

LN& LN::operator=(const LN& other)
{
	if (this == &other)
		return *this;
	if (other.is_NaN_)
	{
		set_to_NaN();
		return (*this);
	}
	delete[] mas_;
	sign_ = other.sign_;
	digits_ = other.digits_;
	mas_ = new char[digits_];
	for (long long i = 0; i < digits_; i++)
		mas_[i] = other.mas_[i];
	return *this;
}

LN& LN::operator=(LN &&other) noexcept
{
	if (this == &other)
		return *this;
	if (other.is_NaN_)
		set_to_NaN();
	else
	{
		delete[] mas_;
		sign_ = other.sign_;
		digits_ = other.digits_;
		mas_ = other.mas_;
		other.mas_ = nullptr;
	}
	return *this;
}

bool LN::operator==(const LN& other) const
{
	if (other.is_NaN_ || is_NaN_)
		return false;
	return (*this).compare_to(other) == 0;
}

bool LN::operator!=(const LN& other) const
{
	if (other.is_NaN_ && is_NaN_)
		return true;
	else if (other.is_NaN_ || is_NaN_)
		return false;
	return (*this).compare_to(other) != 0;
}

bool LN::operator>(const LN& other) const
{
	if (other.is_NaN_ || is_NaN_)
		return false;
	return (*this).compare_to(other) > 0;
}

bool LN::operator>=(const LN& other) const
{
	if (other.is_NaN_ || is_NaN_)
		return false;
	return (*this).compare_to(other) > -1;
}

bool LN::operator<(const LN& other) const
{
	if (other.is_NaN_ || is_NaN_)
		return false;
	return (*this).compare_to(other) == -1;
}

bool LN::operator<=(const LN& other) const
{
	if (other.is_NaN_ || is_NaN_)
		return false;
	return (*this).compare_to(other) < 1;
}

LN LN::operator+() const
{
	//LN ln = (*this);*
	return LN(*this);
}

LN LN::operator-() const
{
	LN ln(*this);
	ln.sign_ = sign_ * -1;
	if (ln.is_NaN_)
		ln.sign_ = 1;
	return ln;
}

LN LN::operator+(const LN& other) const
{
	if ((*this).abs_compare_to(other) < 0)
		if (sign_ != other.sign_)
			return sign_ > other.sign_ ? (*this).subtract(-other) : -(-(*this)).subtract(other);
	if (sign_ < 0)
		return -(-(*this)).add(-other);
	return (*this).add(other);
}

LN LN::operator-(const LN& other) const
{
	return (*this) + -other;
	/* 0 */
}

LN LN::operator*(const LN& other) const
{
	LN res("0");
	if (other.is_NaN_ || is_NaN_)
	{
		res.set_to_NaN();
		return res;
	}
	for (long long i = digits_ - 1; i >= 0; i--)
	{
		if (mas_[i] == 0)
			continue;
		LN res1;
		res1.digits_ = other.digits_ + 1;
		res1.mas_ = new char[res1.digits_];
		char plus = 0;
		for (long long k = res1.digits_ - 1; k > 0; k--)
		{
			char tmp = other.mas_[k - 1] * mas_[i] + plus;
			plus = tmp / 10;
			res1.mas_[k] = tmp % 10;
		}
		res1.mas_[0] = plus;
		long long res_size = res1.digits_ + (digits_ - i - 1);
		LN res2;
		res2.digits_ = res_size;
		res2.mas_ = new char[res2.digits_];
		for (long long k = 0; k < res_size; k++)
			res2.mas_[k] = k < res1.digits_ ? res1.mas_[k] : 0;
		res += res2;
	}
	if (sign_ != other.sign_)
		res.sign_ = -1;
	res.remove_zero();
	return res;
}

LN LN::operator/(const LN& other) const
{
	LN ln1(*this);
	ln1.sign_ = 1;
	LN ln2(other);
	ln2.sign_ = 1;
	LN res(ln1.divide(ln2, false));
	if (!res.is_NaN_ && sign_ != other.sign_)
		res.sign_ = -1;
	return res;
}

LN LN::operator%(const LN& other) const
{
	LN ln1(*this);
	ln1.sign_ = 1;
	LN ln2(other);
	ln2.sign_ = 1;
	LN res(ln1.divide(ln2, true));
	if (!res.is_NaN_ && sign_ < 0)
		res.sign_ = -1;
	return res;
}

LN LN::operator~() const
{
	if (is_NaN_ || (*this) == LN(1)
			|| ((*this).abs_compare_to(LN("0")) == 0))
		return LN(*this);
	if (sign_ < 0)
	{
		LN res;
		res.set_to_NaN();
		return res;
	}

	LN left(1);
	LN right(*this + LN(1));
	LN middle(1);
	while (right - left > LN(1))
	{
		middle = (right + left) / LN(2);
		if (middle * middle <= (*this))
			left = middle;
		else
			right = middle;
	}
	left.remove_zero();
	return left;
}

LN& LN::operator+=(const LN& other)
{
	if (!is_NaN_)
		(*this) = (*this) + other;
	return (*this);
}

LN& LN::operator-=(const LN& other)
{
	if (!is_NaN_)
		(*this) = (*this) - other;
	return (*this);
}

LN& LN::operator*=(const LN& other)
{
	if (!is_NaN_)
		(*this) = (*this) * other;
	return (*this);
}

LN& LN::operator/=(const LN& other)
{
	if (!is_NaN_)
		(*this) = (*this) / other;
	return (*this);
}

LN& LN::operator%=(const LN& other)
{
	if (!is_NaN_)
		(*this) = (*this) % other;
	return (*this);
}

LN::operator long long()
{
	if (is_NaN_)
		throw std::bad_cast();
	long long res = 0;
	if ((*this).compare_to(*new LN(LLONG_MAX)) >= 0 || (*this).compare_to(*new LN(LLONG_MIN)) <= 0)
		throw std::bad_cast();
	for (long long i = 0; i < digits_; i++)
		res += mas_[i] * (long long)pow(10, digits_ - i - 1);
	res *= sign_;
	return res;
}

LN::operator bool()
{
	if (is_NaN_ || (*this) != LN("0"))
		return true;
	else
		return false;
}

int LN::abs_compare_to(const LN& ln2) const
{
	long long digits1 = digits_;
	long long digits2 = ln2.digits_;
	long long i1 = 0;
	while (mas_[i1] == 0 && i1 < digits_)
	{
		digits1--;
		i1++;
	}
	long long i2 = 0;
	while (i2 < ln2.digits_ && ln2.mas_[i2] == 0)
	{
		digits2--;
		i2++;
	}
	if (digits1 > digits2)
		return 1;
	else if (digits1 < digits2)
		return -1;
	while (i1 < digits_ && i2 < ln2.digits_)
	{
		if (mas_[i1] > ln2.mas_[i2])
			return 1;
		else if (mas_[i1] < ln2.mas_[i2])
			return -1;
		i1++;
		i2++;
	}
	return 0;
}

int LN::compare_to(const LN& ln2) const
{
	if ((*this).abs_compare_to(LN("0")) == 0 && ln2.abs_compare_to(LN("0")) == 0)
		return 0;
	else if (sign_ > ln2.sign_)
		return 1;
	else if (sign_ < ln2.sign_)
		return -1;
	else if (sign_ < 0)
	{
		if ((*this).abs_compare_to(ln2) == 0)
			return 0;
		else
			return -(*this).abs_compare_to(ln2);
	}
	else
		return (*this).abs_compare_to(ln2);
}

LN LN::add(const LN& ln2) const
{
	LN res;
	if (ln2.is_NaN_ || is_NaN_)
	{
		res.set_to_NaN();
		return res;
	}
	res.digits_ = digits_ + 1;
	res.mas_ = new char[res.digits_];

	LN other_copy;
	other_copy.digits_ = digits_;
	other_copy.mas_ = new char[digits_];
	for (long long ii = 0; ii < digits_; ii++)
	{
		if (ii < digits_ - ln2.digits_)
			other_copy.mas_[ii] = 0;
		else
			other_copy.mas_[ii] = ln2.mas_[ii - (digits_ - ln2.digits_)];
	}
	char add = 0;
	long long i = digits_;

	while (true)
	{
		if (i == 0)
		{
			res.mas_[i] = add;
			break;
		}
		char tmp = mas_[i - 1] + other_copy.mas_[i - 1] + add;
		if (tmp > 9)
		{
			res.mas_[i] = tmp - 10;
			add = 1;
		}
		else
		{
			res.mas_[i] = tmp;
			add = 0;
		}
		i--;
	}
	res.remove_zero();
	return res;
}

LN LN::subtract(const LN& ln2) const
{
	LN res;
	if (ln2.is_NaN_ || is_NaN_)
	{
		res.set_to_NaN();
		return res;
	}
	res.digits_ = digits_;
	res.mas_ = new char[res.digits_];
	char minus = 0;
	long long ir = res.digits_ - 1;
	long long i1 = digits_ - 1;
	long long i2 = ln2.digits_ - 1;
	long long digits_index = ln2.digits_ - ln2.count_digits();
	while (true)
	{
		if (i2 < digits_index)
		{
			if (minus == 1)
			{
				res.mas_[ir + 1] += 10;
				while (mas_[i1] == 0)
				{
					res.mas_[ir] = 9;
					ir--;
					i1--;
				}
				res.mas_[ir] = mas_[i1] - 1;
				ir--;
				i1--;
			}
			while (ir >= 0)
			{
				res.mas_[ir] = mas_[i1];
				ir--;
				i1--;
			}
			break;
		}
		res.mas_[ir + 1] += 10 * minus;
		char tmp = mas_[i1] - ln2.mas_[i2] - minus;
		if (tmp < 0)
			minus = 1;
		else
			minus = 0;
		res.mas_[ir] = tmp;
		i1--;
		i2--;
		ir--;
	}
	res.remove_zero();
	return res;
}

LN LN::divide(const LN& ln2, bool need_rest) const
{
	if (is_NaN_ || ln2.is_NaN_
			|| ln2 == LN("-0") || ln2 == LN("0")) // ?
	{
		LN res;
		res.set_to_NaN();
		return res;
	}
	if ((*this).abs_compare_to(ln2) == -1)
		return need_rest ? LN(*this) : LN("0");

	if(ln2.abs_compare_to(LN(1)) == 0)
		return need_rest ? LN("0") : LN(*this);

	int res_index = 0;
	char *res = new char[(*this).count_digits()];

	LN this_copy(*this);
	LN other_copy; // //
	other_copy.digits_ = (*this).count_digits();
	other_copy.mas_ = new char[other_copy.digits_];

	long long other_index = 0;
	while (ln2.mas_[other_index] == 0)
		other_index++;

	for (long long i = 0; i < (*this).count_digits(); i++)
	{
		if (i < ln2.count_digits())
		{
			other_copy.mas_[i] = ln2.mas_[other_index];
			other_index++;
		}
		else
			other_copy.mas_[i] = 0;
	}

	while(true)
	{
		char i = 0;
		while(this_copy.abs_compare_to(other_copy) > -1)
		{
			this_copy = this_copy - other_copy;
			i++;
		}

		res[res_index] = (char)(48 + i);
		res_index++;

		if (other_copy.count_digits() == ln2.count_digits())
			break;
		else
			other_copy.right_shift();
	}

	res[res_index] = '\0';
	LN res_ln = need_rest ? this_copy : LN(res);
	res_ln.remove_zero();
	return res_ln;
}

void LN::right_shift()
{
	for (long long i = digits_ - 1; i > 0; i--)
		mas_[i] = mas_[i - 1];
	mas_[0] = 0;
}

long long LN::count_digits() const
{
	if (is_NaN_)
		return 0;
	long long i = 0;
	long long res = digits_;
	while (mas_[i] == 0 && i < digits_)
	{
		res--;
		i++;
	}
	return res;
}

void LN::remove_zero()
{
	long long new_digits = count_digits();
	if (new_digits == digits_)
		return;
	char* new_mas;
	if (new_digits == 0)
	{
		new_mas = new char[1];
		new_digits = 1;
		new_mas[0] = 0;
	}
	else
	{
		new_mas = new char[new_digits];
		for (long long i = digits_ - new_digits; i < digits_; i++)
			new_mas[i - (digits_ - new_digits)] = mas_[i];
	}
	delete[] mas_;
	mas_ = new_mas;
	digits_ = new_digits;
}

void LN::print_mas(std::ofstream& f_out) const
{
	if (is_NaN_)
	{
		f_out << "NaN\n";
		return;
	}
	long long i = 0;
	while (mas_[i] == 0 && i < digits_)
		i++;
	if (i == digits_)
		f_out << 0;
	else if (sign_ == -1)
		f_out << "-";
	for (; i < digits_; i++)
		f_out<< (int)mas_[i];
	f_out << "\n";
}

void LN::print_mas() const
{
	if (is_NaN_)
	{
		std::cout << "NaN\n";
		return;
	}
	long long i = 0;
	while (mas_[i] == 0 && i < digits_)
		i++;
	if (i == digits_)
		std::cout << 0;
	else if (sign_ == -1)
		std::cout << "-";
	for (; i < digits_; i++)
		std::cout << (int)mas_[i];
	std::cout << "" << std::endl;
}

void LN::set_to_NaN()
{
	is_NaN_ = true;
	delete []mas_;
	mas_ = nullptr;
	digits_ = 0;
	sign_ = 1;
}
