
"use strict";

const add = (f1, f2) => (x, y, z) => f1(x, y, z) + f2(x, y, z);
const multiply = (f1, f2) => (x, y, z) => f1(x, y, z) * f2(x, y, z);
const subtract = (f1, f2) => (x, y, z) => f1(x, y, z) - f2(x, y, z);
const divide = (f1, f2) => (x, y, z) => f1(x, y, z) / f2(x, y, z);
const variable = (str) => (x, y, z) => {
    if (str === "x") return x;
    if (str === "y") return y;
    if (str === "z") return z;
};
const cnst = (c) => () => c;
const negate = (f) => (x, y, z) => -f(x, y, z);

/*
let expr = add(
    multiply(
        cnst(2),
        variable("x")
    ), cnst(3)
);

console.log(expr(5));
*/

/*
let expression = add(
    subtract(multiply(
        variable("x"), variable("x")
    ), multiply(
        cnst(2), variable("x")
    )), cnst(1)
);

for (let i = 0; i < 10; i++) {
    console.log(expression(i));
}
*/
