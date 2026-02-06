class Foo<T> {
    type: T;

    constructor(type: T) {
        this.type = type;
    }
}

function bar<TX>(t: TX) {
    console.log(t);
}

new Foo<string>('');

bar<string>('');
