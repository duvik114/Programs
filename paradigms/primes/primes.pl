%is_prime(N, N) :- !.
is_prime(N, DEL) :- DEL > sqrt(N), !.
is_prime(N, DEL) :-
    not(DEL > sqrt(N)),
    not(0 is mod(N, DEL)),
    DELL is DEL + 1,
    is_prime(N, DELL).
prime(N) :-
    is_prime(N, 2).
composite(N) :- not(prime(N)).

next_prime(N, P, P) :-
    prime(P),
    (0 is mod(N, P); N < P).
next_prime(N, P, PPP) :-
    composite(P),
    PP is P + 1,
    next_prime(N, PP, PPP).
next_prime(N, P, PPP) :-
    prime(P),
    not(0 is mod(N, P); N < P),
    PP is P + 1,
    next_prime(N, PP, PPP).

make_list(1, _, []) :- !.
make_list(N, P, [P | R]) :-
    0 is mod(div(N, P), P),
    NN is div(N, P),
    make_list(NN, P, R).
make_list(N, P, [P | R]) :-
    not(0 is mod(div(N, P), P)),
    P1 is P + 1,
    next_prime(N, P1, PP),
    NN is div(N, P),
    make_list(NN, PP, R).

prime_divisors(N, R) :-
    next_prime(N, 2, P),
    make_list(N, P, R).

square_divisors(N, R) :-
    NN is N * N,
    next_prime(NN, 2, P),
    make_list(NN, P, R).
