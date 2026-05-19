#include <iostream>

using namespace std;

class Outer3 {
public:
    int foo = 0;
    int bar = 0;

    void baz() {
        cout << "This is the Outer class" << endl;
    }
};

int main() {
    Outer3 outer;
    outer.baz();
    cout << "Value of foo: " << outer.foo << endl;
    cout << "Value of bar: " << outer.bar << endl;

    return 0;
}
