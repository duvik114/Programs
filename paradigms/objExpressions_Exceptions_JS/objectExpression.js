'use strict'

/*
* выводить в каком месте в строчке ошибка
* поподробнее ошибки // смотри на тесты PrefixParsingErrorTest
* сделать switch case через map
 */

let str = "";
let pos = 0;
let mapBin = {
    "+": Add,
    "-": Subtract,
    "*" : Multiply,
    "/" : Divide
}
let mapUn = {
    "negate" : Negate,
    "sinh" : Sinh,
    "cosh" : Cosh
}

function BinOperation(exp1, exp2, operation, operationStr) {
    this.exp1 = exp1;
    this.exp2 = exp2;
    this.operation = operation;
    this.operationStr = operationStr;
}

BinOperation.prototype = {
    evaluate: function (x, y, z) {
        return this.operation(this.exp1.evaluate(x, y, z), this.exp2.evaluate(x, y, z));
    },
    toString: function () {
        return this.exp1.toString() + " " + this.exp2.toString() + " " + this.operationStr;
    },
    prefix: function () {
        return "(" + this.operationStr + " " + this.exp1.prefix() + " " + this.exp2.prefix() + ")";
    },
    constructor: BinOperation
}

function UnOperation(expression, operation, operationStr) {
    this.expression = expression;
    this.operation = operation;
    this.operationStr = operationStr;
}

UnOperation.prototype = {
    evaluate: function (x, y, z) {
        return this.operation(this.expression.evaluate(x, y, z));
    },
    toString: function () {
        return this.expression.toString() + " " + this.operationStr;
    },
    prefix: function () {
        return "(" + this.operationStr + " " + this.expression.prefix() + ")";
    },
    constructor: UnOperation
}

function Const(c) {
    this.c = c;
}

Const.prototype = {
    evaluate: function () {
        return this.c;
    },
    toString: function () {
        return String(this.c);
    },
    prefix: function () {
        return String(this.c);
    },
    constructor: Const
}

function Variable(str) {
    this.str = str;
}

Variable.prototype = {
    evaluate: function (x, y, z) {
        switch (this.str) {
            case "x" :
                return x;
            case "y" :
                return y;
            case "z" :
                return z;
        }
    },
    toString: function () {
        return this.str;
    },
    prefix: function () {
        return this.str;
    },
    constructor: Variable
}

function Add(exp1, exp2) {
    BinOperation.call(this, exp1, exp2, (x1, x2) => x1 + x2, "+");
}

Add.prototype = Object.create(BinOperation.prototype);
Add.prototype.constructor = Add;

function Subtract(exp1, exp2) {
    BinOperation.call(this, exp1, exp2, (x1, x2) => x1 - x2, "-");
}

Subtract.prototype = Object.create(BinOperation.prototype);
Subtract.prototype.constructor = Subtract;

function Multiply(exp1, exp2) {
    BinOperation.call(this, exp1, exp2, (x1, x2) => x1 * x2, "*");
}

Multiply.prototype = Object.create(BinOperation.prototype);
Multiply.prototype.constructor = Multiply;

function Divide(exp1, exp2) {
    BinOperation.call(this, exp1, exp2, (x1, x2) => x1 / x2, "/");
}

Divide.prototype = Object.create(BinOperation.prototype);
Divide.prototype.constructor = Divide;

function ArcTan2(exp1, exp2) {
    BinOperation.call(this, exp1, exp2, (x1, x2) => Math.atan2(x1, x2), "atan2");
}

ArcTan2.prototype = Object.create((BinOperation.prototype));
ArcTan2.prototype.constructor = ArcTan2;

function Negate(expression) {
    UnOperation.call(this, expression, (x) => -x, "negate");
}

Negate.prototype = Object.create(UnOperation.prototype);
Negate.prototype.constructor = Negate;

function ArcTan(expression) {
    UnOperation.call(this, expression, (x) => Math.atan(x), "atan");
}

ArcTan.prototype = Object.create(UnOperation.prototype);
ArcTan.prototype.constructor = ArcTan;

function Sinh(expression) {
    UnOperation.call(this, expression, (x) => Math.sinh(x), "sinh");
}

Sinh.prototype = Object.create(UnOperation.prototype);
Sinh.prototype.constructor = Sinh;

function Cosh(expression) {
    UnOperation.call(this, expression, (x) => Math.cosh(x), "cosh");
}

Cosh.prototype = Object.create(UnOperation.prototype);
Cosh.prototype.constructor = Cosh;

//////////////////////////////////////

function skipWhiteSpace() {
    while (str[pos] === ' ' || str[pos] === '\t') {
        pos++;
    }
}

function ParsingError(message) {
    message = "Error in '" + str + "' - " + message;
    Error.call(this, message);
    this.message = message;
}

ParsingError.prototype = Object.create(Error.prototype);
ParsingError.prototype.name = "ParsingError";
ParsingError.prototype.constructor = ParsingError;

function getSymbols() {
    skipWhiteSpace();
    let res = "";
    while (pos !== str.length && str[pos] !== ' ' && str[pos] !== '\t' && str[pos] !== '(' && str[pos] !== ')') {
        res += str[pos];
        pos++;
    }
    return res;
}

function parseVar() {
    const posCopy = pos;
    let res = getSymbols();
    if (!(res.length === 1 && res[0] && res[0] >= 'x' && res[0] <= 'z')) {
        throw new ParsingError("Position '" + posCopy + "' : incorrect variable");
    }
    return res;
}

function parseNum() {
    const posCopy = pos;
    const resStr = getSymbols();
    if (resStr === "") {
        throw new ParsingError("Position '" + posCopy + "' - expected more arguments");
    } else if (isNaN(Number(resStr))) {
        throw new ParsingError("Position '" + posCopy + "' - number '" + resStr + "' is incorrect one");
    }
    return Number(resStr);
}

function getZnak() {
    skipWhiteSpace();
    const posCopy = pos;
    const znak = getSymbols();
    if (znak === "") {
        throw new ParsingError("Position '" + posCopy + "' - empty expression in ()");
    }
    if (!(znak in mapUn || znak in mapBin)) {
        throw new ParsingError("Position '" + posCopy + "' - znak '" + znak + "' is an incorrect one");
    }
    return znak;
}

function parsePrefixImpl() {
    skipWhiteSpace();
    if (str[pos] === '(') {
        pos++;
        const znak = getZnak();
        let i = 2;
        if (znak.length > 1) {
            i = 1;
        }
        let mas = new Array();
        while (i > 0) {
            mas.push(parsePrefixImpl());
            i--;
        }
        skipWhiteSpace();
        if (pos === str.length) {
            throw new ParsingError("Where is ')'?");
        }
        if (str[pos] !== ')') {
            throw new ParsingError("Position '" + pos + "' : Extra arguments : '" + getSymbols() + "'");
        }
        pos++;
        if (znak in mapBin) {
            return new mapBin[znak](mas[0], mas[1]);
        } else /*(mapBin.has(znak))*/ {
            return new mapUn[znak](mas[0]);
        }
    } else {
        let exp;
        if (str[pos] >= 'x' && str[pos] <= 'z') {
            exp = new Variable(parseVar());
        } else if (str[pos] === "-" || (str[pos] >= '0' && str[pos] <= '9')){
            exp = new Const(parseNum());
        } else {
            const argument = getSymbols();
            if (argument.length === 0) {
                throw new ParsingError("Position '" + pos + "' - not enough arguments");
            }
            throw new ParsingError("Position '" + pos + "' - argument : '" + argument + "' is incorrect one");
        }
        return exp;
    }
}

function parsePrefix(expressionString) {
    str = expressionString;
    pos = 0;
    skipWhiteSpace();
    if (pos === str.length) {
        throw new ParsingError("Empty expression");
    } //
    const res = parsePrefixImpl();
    skipWhiteSpace();
    if (pos !== str.length) {
        throw new ParsingError("Position '" + pos + "' : " + "Extra characters : '" + str.substring(pos, str.length) + "'");
    }
    return res;
}

/*let expr = parsePrefix('(+ x 2)');
console.log(expr.prefix());*/
