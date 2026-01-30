import js from '@eslint/js';
import globals from 'globals';
import typescriptEslint from 'typescript-eslint';
import rulebookTypescriptEslint, { googleTypeScriptStyleNamed } from 'rulebook-typescript-eslint';

export default typescriptEslint.config(
    { ignores: ['dist'] },
    {
        files: ['**/*.{ts,tsx}'],
        extends: [
            js.configs.recommended,
            ...typescriptEslint.configs.recommendedTypeChecked,
        ],
        languageOptions: {
            ecmaVersion: 2020,
            globals: globals.browser,
            parserOptions: {
                projectService: true,
            },
        },
        plugins: {
            'rulebook': rulebookTypescriptEslint,
        },
        rules: googleTypeScriptStyleNamed('rulebook'),
    },
);
