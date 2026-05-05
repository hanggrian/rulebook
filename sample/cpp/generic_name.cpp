template <typename X> X my_max(X x, X y) {
    return (x > y) ? x : y;
}

template <typename Single, typename Double> int my_max(Single x, Double y) {
    return 0;
}
