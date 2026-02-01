#include <iostream>

using namespace std;

class Outer {
public:
    void show() {
        cout << "This is the Outer class" << endl;
    }

    class Inner {
    public:
        int my_variable = 0;

        void my_function() {
            cout << "This is the Inner class" << endl;
        }
    };
};
