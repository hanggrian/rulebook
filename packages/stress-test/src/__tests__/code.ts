import { readFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

function getCode(file): string {
    return readFileSync(
        join(dirname(fileURLToPath(import.meta.url)), `../../resources/${file}`),
        'utf8',
    );
}

export default getCode;
