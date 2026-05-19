#include <iostream>

using namespace std;

class Outer {
public:
    void show() {
        cout << "This is the Outer class" << endl;
    }

    class Inner {
    public:
        int foo = 0;

        void bar() {
            cout << "This is the Inner class" << endl;
        }
    };
};

int main() {
    Outer outer;
    outer.show();

    Outer::Inner inner;
    inner.bar();
    cout << "Value of foo: " << inner.foo << endl;

    return 0;
}
