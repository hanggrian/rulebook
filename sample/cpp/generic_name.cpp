template <typename X> X my_max(X x, X y) {
    return (x > y) ? x : y;
}

template <typename X, typename XX> int my_max(X x, XX y) {
    return 0;
}
