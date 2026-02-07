template <typename X> X my_max(X x, X y) {
    return (x > y) ? x : y;
}

template <typename X, typename X> int my_max(X x, X y) {
    return 0;
}
