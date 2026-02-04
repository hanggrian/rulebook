import globals from 'globals';
import eslint from '@eslint/js';
import typescriptEslint from 'typescript-eslint';
import eslintPluginJsdoc from 'eslint-plugin-jsdoc';
import eslintPluginStylistic from '@stylistic/eslint-plugin';
import eslintPluginSortClassMembers from 'eslint-plugin-sort-class-members';
import rulebookTypescriptEslint from 'rulebook-typescript-eslint';

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
