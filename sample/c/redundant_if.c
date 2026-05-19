static bool redundant_if(int i) {
    return i == 0;
}

int main() {
    redundant_if(0);
    return 0;
}
