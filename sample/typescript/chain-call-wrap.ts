class Foo2 {
    bar() {
        return this;
    }
}

new Foo2()
    .bar().bar();
