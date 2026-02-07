class Foo<T> {
    type: T;

    constructor(type: T) {
        this.type = type;
    }
}

function bar<T>(t: T) {
    console.log(t);
}

new Foo<string>('');

bar<string>('');
