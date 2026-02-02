void redundant_else() {
    for (int i = 0; i < 10; i++) {
        if (true) {
            return;
        } else if (true) {
            continue;
        } else {
            int j = i;
        }
    }
}
