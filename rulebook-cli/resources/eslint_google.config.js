import js from '@eslint/js';
import globals from 'globals';
import typescriptEslint from 'typescript-eslint';
import rulebookEslint, { googleJavaScriptStyleNamed } from 'rulebook-eslint';

export default typescriptEslint.config(
    { ignores: ['dist'] },
    {
        files: ['**/*.{js,jsx}'],
        extends: [js.configs.recommended],
        languageOptions: {
            ecmaVersion: 2022,
            sourceType: 'module',
            globals: { ...globals.node },
            parserOptions: {
                ecmaFeatures: { jsx: true },
            },
        },
        plugins: {
            'rulebook': rulebookEslint,
        },
        rules: googleJavaScriptStyleNamed('rulebook'),
    },
);
