static int case_separator(int foo) {
    switch (foo) {
        case 0: return 0;
        case 1: return 1;
    }
    return -1;
}

int main() {
    case_separator(1);
    return 0;
}
