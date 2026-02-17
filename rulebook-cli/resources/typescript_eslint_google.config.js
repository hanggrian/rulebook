import eslint from '@eslint/js';
import eslintPluginStylistic from '@stylistic/eslint-plugin';
import eslintPluginJsdoc from 'eslint-plugin-jsdoc';
import eslintPluginSortClassMembers from 'eslint-plugin-sort-class-members';
import globals from 'globals';
import rulebookTypescriptEslint from 'rulebook-typescript-eslint';
import typescriptEslint from 'typescript-eslint';

export default typescriptEslint.config(
    { ignores: ['dist'] },
    {
        files: ['**/*.{ts,tsx}'],
        plugins: {
            '@jsdoc': eslintPluginJsdoc,
            '@stylistic': eslintPluginStylistic,
            '@sort-class-members': eslintPluginSortClassMembers,
            '@rulebook': rulebookTypescriptEslint,
        },
        extends: [
            eslint.configs.recommended,
            typescriptEslint.configs.eslintRecommended,
            ...typescriptEslint.configs.recommendedTypeChecked,
            rulebookTypescriptEslint.configs.google,
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
