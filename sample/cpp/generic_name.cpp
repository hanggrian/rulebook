template <typename XA> XA my_max(XA x, XA y) {
    return (x > y) ? x : y;
}

template <typename A_X, typename X_A> int my_max(A_X x, X_A y) {
    return 0;
}
