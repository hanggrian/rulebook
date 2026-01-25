class Foo {
    bar() {
        return this;
    }
}

new Foo()
    .bar()
    .bar();
