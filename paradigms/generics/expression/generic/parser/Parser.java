package expression.generic.parser;

import expression.generic.GeneralExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    GeneralExpression parse(String expression);
}