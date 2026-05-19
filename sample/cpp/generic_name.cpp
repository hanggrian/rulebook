template <typename X> X my_max(X x, X y) {
    return (x > y) ? x : y;
}

template <typename Single, typename Double> int my_max(Single x, Double y) {
    return 0;
}

int main() {
    int a = 5;
    int b = 10;
    double c = 3.14;

    return my_max(a, b) + my_max(a, c);
}
