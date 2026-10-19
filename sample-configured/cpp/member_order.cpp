#include <iostream>

using namespace std;

class Outer {
public:
  static void show() {
    cout << "outer" << endl;
  }

  class Inner {
  public:
    static void my_function() {
        cout << "inner" << endl;
    }

    int my_variable = 0;
  };
};

int main() {
  Outer::show();

  Outer::Inner inner;
  Outer::Inner::my_function();
  cout << inner.my_variable << endl;

  return 0;
}
