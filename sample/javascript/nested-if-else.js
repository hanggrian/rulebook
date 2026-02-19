function nestedIfElse(foo) {
    if (foo === 2) {
        console.log('foo');
        return;
    }
    if (foo === 3) {
        console.log('bar');
    } else {
        console.log('baz');
    }
}

console.log(nestedIfElse);
