abstract class Abstracted {
    foo(): void {
    }
}

class NonAbstract extends Abstracted {
    foo(): void {
    }
}

new NonAbstract().foo();
