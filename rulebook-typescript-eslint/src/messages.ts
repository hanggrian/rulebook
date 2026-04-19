import { readFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

class Messages {
    private readonly record: Record<string, string> =
        JSON.parse(
            readFileSync(
                join(
                    dirname(fileURLToPath(import.meta.url)),
                    '../resources/typescript_eslint_messages.json',
                ),
                'utf-8',
            ),
        ) as Record<string, string>;

    get(key: string): string {
        return this.record[key];
    }
}

export default new Messages();
