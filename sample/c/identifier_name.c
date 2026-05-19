static int identifier_name() {
    constexpr int foo_bar = 0;
    return foo_bar;
}

static int foo_bar() {
    return 0;
}

int main() {
    return identifier_name() + foo_bar();
}
