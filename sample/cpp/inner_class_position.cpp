#include <iostream>

using namespace std;

class Outer {
public:
    class Inner {
    public:
        int foo = 0;

        void bar() {
            cout << "This is the Inner class" << endl;
        }
    };

    void show() {
        cout << "This is the Outer class" << endl;
    }
};
