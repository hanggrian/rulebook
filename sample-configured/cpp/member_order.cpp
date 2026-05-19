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

int main() {
  Outer outer;
  outer.show();

  Outer::Inner inner;
  inner.my_function();
  cout << inner.my_variable << endl;

  return 0;
}
