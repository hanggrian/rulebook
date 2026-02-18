import { readFileSync } from 'fs';
import { dirname, join } from 'path';
import { fileURLToPath } from 'url';

class Messages {
    private readonly record: Record<string, string> =
        JSON.parse(
            readFileSync(
                join(dirname(fileURLToPath(import.meta.url)), '../resources/messages.json'),
                'utf-8',
            ),
        ) as Record<string, string>;

    get(key: string): string {
        return this.record[key];
    }
}

export default new Messages();
