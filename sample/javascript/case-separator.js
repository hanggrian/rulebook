function caseSeparator(item) {
    for (let i = 0; i < 10; i++) {
        switch (item) {
            case 'foo':
                continue;

            case 'bar':
                return;
        }
    }
}

caseSeparator('foo');
