#include <iostream>

using namespace std;

class Outer2 {
public:
    int foo = 0;

    void show() {
        cout << "This is the Outer class: " << foo << endl;
    }

    class Inner2 {
    public:
        int foo = 0;

        void bar() {
            cout << "This is the Inner class: " << foo << endl;
        }
    };
};
