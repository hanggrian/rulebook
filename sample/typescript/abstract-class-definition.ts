abstract class Abstracted {
    abstract foo(): void;
}

class NonAbstract extends Abstracted {
    foo(): void {
    }
}

new NonAbstract().foo();
