using namespace std;

int compare(int a, int b) {
    if (a < 0 || b < 0) {
        throw std::exception("received negative value");
    }
    return 0;
}
