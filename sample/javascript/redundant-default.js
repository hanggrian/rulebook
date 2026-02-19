function redundantDefault(foo) {
    for (let i = 0; i < 10; i++) {
        switch (foo) {
            case 'foo':
                continue;

            case 'bar':
                continue;
        }
        return;
    }
}

console.log(redundantDefault);
