public class GenericsNameWhitelisting {
    public class Foo<T> {
        public class Bar<X> {}

        public <Y> void bar() {}
    }
}
