#include <iostream>

using namespace std;

class Outer {
public:
    void show() {
        cout << "outer" << endl;
    }

    class Inner {
    public:
        void my_function() {
            cout << "inner" << endl;
        }

        int my_variable = 0;
    };
};
