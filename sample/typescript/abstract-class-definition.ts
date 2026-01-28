abstract class Foo {
    bar(): void {
    }
}

class Bar extends Foo {
    bar(): void {
    }
}

new Bar().bar();
