import { readFileSync } from 'fs';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';

const messages =
    JSON.parse(
        readFileSync(
            join(dirname(fileURLToPath(import.meta.url)), '../resources/messages.json'),
            'utf-8',
        ),
    ) as Record<string, string>;

export default {
    get(key: string): string {
        return messages[key];
    },

    of(key: string, ...args: string[]): string {
        let result = messages[key];
        let count = 0;
        args.forEach(arg => result = result.replace(`{{ $${count++} }}`, String(arg)));
        return result;
    },
};
