package expression.parser;

import expression.GeneralExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    GeneralExpression parse(String expression);
}