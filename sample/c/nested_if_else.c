void nested_if_else() {
    if (1) {
        if (0) {
            int x = 1;
            int y = 2;
        }
    }
}

void nested_if_else2() {
    if (0) {
        int x = 1;
        int y = 2;
    } else {
        int x = 1;
        int y = 2;
    }
}
