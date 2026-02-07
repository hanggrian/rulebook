void redundant_else() {
    for (int i = 0; i < 10; i++) {
        if (true) {
            return;
        }
        if (false) {
            continue;
        }
        int j = i;
    }
}
