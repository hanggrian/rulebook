void parentheses_clip() {}

auto vglambda =
    [](auto printer) {
        return 9;
    };

template <typename X> int my_max() {
    return 0;
}

int main() {
    parentheses_clip();
    vglambda([](auto x) { return x; });
    my_max<int>();
    return 0;
}
