function unnecessaryContinue(foo) {
    for (let item of foo) {
        console.log(item);
        // continue;
    }
}

console.log(unnecessaryContinue);
