public class StaticClassPosition {
    public class Foo {
        public int bar = VALUE;

        public Foo(int a) {
        }

        public Foo() {
            this(VALUE);
        }

        public void baz() {
            System.out.println(VALUE);
        }

        public static class Bar {
            public static int VALUE = 0;
        }
    }
}
