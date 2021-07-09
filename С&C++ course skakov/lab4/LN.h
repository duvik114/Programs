#include <iostream>
#include <fstream>
#include <cmath>
#include <string_view>
#include <cstring>

#ifndef LN_H
#define LN_H

class LN
{
public:
	LN() : LN((long long)0){}

	LN(LN &&ln) noexcept ; //

	LN(const LN& ln); //

	explicit LN(long long number); //

	explicit LN(const char *number); //

	explicit LN(std::string_view number); //

	~LN();

	LN& operator=(const LN &other); //

	LN& operator=(LN &&other) noexcept; //

	LN operator+() const; //

	LN operator-() const; //

	LN operator+(const LN& other) const;

	LN operator-(const LN& other) const;

	LN operator*(const LN& other) const;

	LN operator/(const LN& other) const;

	LN operator%(const LN& other) const;

	LN operator~() const;

	LN& operator+=(const LN& other);

	LN& operator-=(const LN& other);

	LN& operator*=(const LN& other);

	LN& operator/=(const LN& other);

	LN& operator%=(const LN& other);

	bool operator==(const LN& other) const;

	bool operator!=(const LN& other) const;

	bool operator>(const LN& other) const;

	bool operator>=(const LN& other) const;

	bool operator<(const LN& other) const;

	bool operator<=(const LN& other) const;

	explicit operator long long(); //

	explicit operator bool(); //

	friend inline LN operator "" _ln(const char* str); /*{return LN(str);}*/

	[[nodiscard]] int abs_compare_to(const LN& ln2) const;

	[[nodiscard]] int compare_to(const LN& ln2) const;

	[[nodiscard]] LN add(const LN& ln2) const;

	[[nodiscard]] LN subtract(const LN& ln2) const;

	[[nodiscard]] LN divide(const LN& ln2, bool need_rest) const;

	void right_shift();

	[[nodiscard]] long long count_digits() const;

	void remove_zero();

	void print_mas() const;

	void print_mas(std::ofstream& f_out) const;

	[[nodiscard]] bool get_is_NaN() const {return is_NaN_;}

	[[nodiscard]] int get_sign() const {return sign_;}

	[[nodiscard]] char* get_mas() const {return mas_;}

	[[nodiscard]] long long get_digits() const {return digits_;}

	void set_digits(long long int digits) { LN::digits_ = digits;}

	void set_mas(char* mas) { LN::mas_ = mas;}

	void set_sign(int i) { sign_ = i;}

	void set_to_NaN();
private:
	int sign_ = 1;
	bool is_NaN_ = false;
	long long digits_ = 0;
	char *mas_ = nullptr;
};

inline LN operator "" _ln(const char* str) {return LN(str);}

#endif