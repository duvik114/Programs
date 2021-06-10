node(XKEY, YKEY, VALUE, NLEFT, NRIGHT).

split(null, _, null, null) :- !.
split(node(NXK, NYK, NV, NL, NR), K, node(NXK, NYK, NV, NL, NRR), T2) :-
    K > NXK,
    split(NR, K, NRR, T2).
split(node(NXK, NYK, NV, NL, NR), K, T1, node(NXK, NYK, NV, NLL, NR)) :-
    not(K > NXK),
    split(NL, K, T1, NLL).

merge(A, null, A) :- !.
merge(null, A, A) :- !.
merge(node(LXKEY, LYKEY, LVALUE, LLEFT, LRIGHT),
      node(RXKEY, RYKEY, RVALUE, RLEFT, RRIGHT),
      node(LXKEY, LYKEY, LVALUE, LLEFT, LLRIGHT)) :-
    LYKEY > RYKEY,
    merge(LRIGHT, node(RXKEY, RYKEY, RVALUE, RLEFT, RRIGHT), LLRIGHT).
merge(node(LXKEY, LYKEY, LVALUE, LLEFT, LRIGHT),
      node(RXKEY, RYKEY, RVALUE, RLEFT, RRIGHT),
      node(RXKEY, RYKEY, RVALUE, RRLEFT, RRIGHT)) :-
    not(LYKEY > RYKEY),
    merge(node(LXKEY, LYKEY, LVALUE, LLEFT, LRIGHT), RLEFT, RRLEFT).

map_put(node(K, Y, V, null, null), T, RESS) :-
    split(T, K, T1, T2),
    merge(T1, node(K, Y, V, null, null), RES),
    merge(RES, T2, RESS).

map_containsValue(node(_, _, VAL, _, _), VAL) :- !.
map_containsValue(node(_, _, _, NL, NR), VAL) :-
    map_containsValue(NL, VAL);
    map_containsValue(NR, VAL).

map_containsKey(T, K) :-
    split(T, K, _, T2),
    KK is K + 1,
    split(T2, KK, node(K, _, _, null, null), _).

map_get(T, K, V) :-
    split(T, K, _, T2),
    KK is K + 1,
    split(T2, KK, node(K, _, V, null, null), _).

div(L, L1, T, M) :-
    length(L, N),
    append(L1, [M | T], L),
    N1 is div(N, 2),
    length(L1, N1),
    N2 is N - N1,
    length([M | T], N2).

map_build_Y([], _, null) :- !.
map_build_Y(L, Y, node(X, YR, V, NL, NR)) :-
    div(L, L1, L2, (X, V)),
    YY is Y - 1,
    map_build_Y(L1, YY, NL),
    map_build_Y(L2, YY, NR).

map_build(L, RES) :- map_build_Y(L, 1000000, RES).
