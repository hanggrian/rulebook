import { readFileSync } from 'fs';
import { dirname, join } from 'path';
import { fileURLToPath } from 'url';

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
};
