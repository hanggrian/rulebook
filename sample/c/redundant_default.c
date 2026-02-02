void redundant_default() {
    for (int i = 0; i < 10; i++) {
        switch (i) {
            case 0: return;
            case 1: continue;
            default: break;
        }
    }
}
