import eslint from '@eslint/js';
import eslintPluginStylistic from '@stylistic/eslint-plugin';
import eslintPluginImport from 'eslint-plugin-import';
import eslintPluginJsdoc from 'eslint-plugin-jsdoc';
import eslintPluginSortClassMembers from 'eslint-plugin-sort-class-members';
import globals from 'globals';
import rulebookEslint from 'rulebook-eslint';
import rulebookTypescriptEslint from 'rulebook-typescript-eslint';
import typescriptEslint from 'typescript-eslint';

export default typescriptEslint.config(
    { ignores: ['dist'] },
    {
        files: ['**/*.{js,jsx}'],
        plugins: {
            '@import': eslintPluginImport,
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
    {
        files: ['**/*.{ts,tsx}'],
        plugins: {
            '@import': eslintPluginImport,
            '@jsdoc': eslintPluginJsdoc,
            '@stylistic': eslintPluginStylistic,
            '@sort-class-members': eslintPluginSortClassMembers,
            '@rulebook': rulebookTypescriptEslint,
        },
        extends: [
            eslint.configs.recommended,
            typescriptEslint.configs.eslintRecommended,
            ...typescriptEslint.configs.recommendedTypeChecked,
            rulebookTypescriptEslint.configs.crockford,
        ],
        languageOptions: {
            ecmaVersion: 2020,
            globals: globals.browser,
            parserOptions: {
                projectService: true,
            },
        },
    },
);
