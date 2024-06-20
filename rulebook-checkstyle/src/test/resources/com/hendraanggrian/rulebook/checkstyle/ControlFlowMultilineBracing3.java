public class ControlFlowMultilineBracing {
    public void foo() {
        if (true) {
            bar();
        }
        if (true) {
            bar();
        } else if (false) {
            baz();
        } else {
            bar();
        }

        for (int i = 0; i < 0; i++) {
            bar();
        }
        while (true) {
            bar();
        }
        do {
            bar();
        } while (true);
    }

    public void bar() {
    }

    public void baz() {
    }
}
