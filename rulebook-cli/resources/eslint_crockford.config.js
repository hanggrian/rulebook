import globals from 'globals';
import eslint from '@eslint/js';
import typescriptEslint from 'typescript-eslint';
import eslintPluginJsdoc from 'eslint-plugin-jsdoc';
import eslintPluginStylistic from '@stylistic/eslint-plugin';
import eslintPluginSortClassMembers from 'eslint-plugin-sort-class-members';
import rulebookEslint from 'rulebook-eslint';

export default typescriptEslint.config(
    { ignores: ['dist'] },
    {
        files: ['**/*.{js,jsx}'],
        plugins: {
            '@jsdoc': eslintPluginJsdoc,
            '@stylistic': eslintPluginStylistic,
            '@sort-class-members': eslintPluginSortClassMembers,
            '@rulebook': rulebookEslint,
        },
        extends: [
            eslint.configs.recommended,
            rulebookEslint.configs.crockford,
        ],
        languageOptions: {
            ecmaVersion: 2022,
            sourceType: 'module',
            globals: { ...globals.node },
            parserOptions: {
                ecmaFeatures: { jsx: true },
            },
        },
    },
);
