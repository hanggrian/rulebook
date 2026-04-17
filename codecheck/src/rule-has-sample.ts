import { existsSync } from 'fs';
import { AST, Rule } from 'eslint';

const ESLINT_PATH: string = '/rulebook-eslint/src/rules/';
const TYPESCRIPT_ESLINT_PATH: string = '/rulebook-typescript-eslint/src/rules/';

export default {
    create(context: Rule.RuleContext) {
        return {
            Program(node: AST.Program) {
                const file: string = context.filename;
                if (file.endsWith('rulebook-rule.ts')) {
                    return;
                }
                if (file.includes(ESLINT_PATH)) {
                    if (existsSync(
                        file
                            .replace(ESLINT_PATH, '/sample/javascript/')
                            .replace('.ts', '.js'),
                    )) {
                        return;
                    }
                    context.report({ node, message: 'Missing sample.' });
                } else if (file.includes(TYPESCRIPT_ESLINT_PATH)) {
                    if (existsSync(file.replace(TYPESCRIPT_ESLINT_PATH, '/sample/typescript/'))) {
                        return;
                    }
                    context.report({ node, message: 'Missing sample.' });
                }
            },
        };
    },
};
