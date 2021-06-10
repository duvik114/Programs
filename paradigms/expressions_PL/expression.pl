lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

variable(Name, variable(Name)).
const(Value, const(Value)).

op_add(A, B, operation(op_add, A, B)).
op_subtract(A, B, operation(op_subtract, A, B)).
op_multiply(A, B, operation(op_multiply, A, B)).
op_divide(A, B, operation(op_divide, A, B)).
op_negate(A, operation(op_negate, A)).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is -A.

evaluate(const(V), _, V).
evaluate(variable(Name), Vars, R) :- lookup(Name, Vars, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R).

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

skip_wf([' '| T], TT) :- skip_wf(T, TT), !.
skip_wf([H | T], [H | T]) :- !.

skip_wb([], []) :- !.
skip_wb([' '], []) :- !.
skip_wb([H, ' '], [H]) :- !.
skip_wb([H | T], [H | TT]) :- skip_wb(T, TT).

skip_ww([], []) :- !.
skip_ww([E], [E]) :- !.
skip_ww([' ', ' ' | T], TT) :- skip_ww([' ' | T], TT), !.
skip_ww([H1, H2 | T], [H1 | TT]) :- skip_ww([H2 | T], TT), !.

skip_wh([], []) :- !.
skip_wh([E], [E]) :- !.
%skip_wh([' ', '(' | T], ['(' | TT]) :- skip_wh(T, TT), !.
skip_wh(['(', ' ' | T], ['(' | TT]) :- skip_wh(T, TT), !.
skip_wh([' ', ')' | T], [')' | TT]) :- skip_wh(T, TT), !.
skip_wh([H1, H2 | T], [H1 | TT]) :- skip_wh([H2 | T], TT), !.

:- load_library('alice.tuprolog.lib.DCGLibrary').

expr_p(variable(Name)) --> [Name], { member(Name, [x, y, z]) }.
expr_p(const(Value)) -->
  { nonvar(Value, number_chars(Value, Chars)) },
  digits_p(Chars),
  { Chars = [_ | _], number_chars(Value, Chars) }.
expr_p(operation(Op, A, B)) --> ['('], expr_p(A), [' '], expr_p(B), [' '], op_p(Op), [')'].
expr_p(operation(Op, A)) --> ['('], expr_p(A), [' '], op_p(Op), [')'].

digits_p([]) --> [].
digits_p_plus([]) --> [].
digits_p_point([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
  [H],
  digits_p_plus(T).
digits_p([H1, H2 | T]) -->
    { H1 = '-', member(H2, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
    [H1, H2],
    digits_p_plus(T).
digits_p_plus([H | T]) -->
      { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
      [H],
      digits_p_plus(T).
digits_p_plus([H1, H2 | T]) -->
  { H1 = '.', member(H2, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
  [H1, H2],
  digits_p_point(T).
digits_p_point([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
  [H],
  digits_p_point(T).

op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> ['n'], ['e'], ['g'], ['a'], ['t'], ['e'].

suffix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C).
suffix_str(E, A) :-   atom(A), atom_chars(A, C),
    skip_ww(C, C1),
    skip_wf(C1, C2),
    skip_wb(C2, C3),
    skip_wh(C3, C4),
    phrase(expr_p(E), C4).
