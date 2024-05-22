public class StaticClassPosition {
    public class Foo {
        public static class Bar {
            public static int VALUE = 0;
        }

        public Foo(int a) {
        }

        public Foo() {
            this(VALUE);
        }

        public void baz() {
            System.out.println(VALUE);
        }
    }
}
