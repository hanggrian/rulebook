function nestedIfElse(foo) {
    if (foo === 2) {
        console.log('foo');
        throw new Error();
    }
    if (foo === 3) {
        console.log('bar');
        return;
    }
    console.log('baz');
}

console.log(nestedIfElse);
