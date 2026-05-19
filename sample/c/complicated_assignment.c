static int complicated_assignment() {
    int bar = 0;
    bar += 1;
    bar -= 1;
    bar *= 1;
    bar /= 1;
    bar %= 1;
    return bar;
}

int main() {
    return complicated_assignment();
}
