import { TSESLint } from '@typescript-eslint/utils';
import rulebookEslint, { crockfordConfig, googleConfig } from 'rulebook-eslint';
import genericNameRule from './rules/generic-name.js';
import unnecessaryAbstractRule from './rules/unnecessary-abstract.js';

const typescriptConfig: Record<string, any> = {
    '@typescript-eslint/consistent-return': 'error',
    '@typescript-eslint/no-explicit-any': 'off',

    // Clipping
    // Declaring
    '@rulebook/unnecessary-abstract': 'error',
    // Formatting
    // Naming
    '@typescript-eslint/naming-convention': [
        'error',
        {
            selector: 'class',
            format: ['PascalCase'],
            custom: {
                regex: '([A-Z]{3,})',
                match: false,
            },
        },
        {
            selector: 'property',
            format: ['camelCase'],
            leadingUnderscore: 'allow',
        },
        {
            selector: 'property',
            format: ['UPPER_CASE'],
            modifiers: ['static'],
            leadingUnderscore: 'allow',
        },
        {
            selector: 'property',
            format: null,
            modifiers: ['requiresQuotes'],
            leadingUnderscore: 'allow',
        },
        {
            selector: 'objectLiteralProperty',
            format: null,
            leadingUnderscore: 'allow',
        },
        {
            selector: 'method',
            format: ['camelCase'],
            leadingUnderscore: 'allow',
        },
        {
            selector: 'objectLiteralMethod',
            format: null,
            leadingUnderscore: 'allow',
        },
    ],
    '@rulebook/generic-name': 'error',
    // Ordering
    // Spacing
    // Stating
    '@typescript-eslint/switch-exhaustiveness-check': 'error',
    // Trimming
    // Wrapping
};

export default {
    rules: {
        ...rulebookEslint.rules,
        'generic-name': genericNameRule,
        'unnecessary-abstract': unnecessaryAbstractRule,
    },
    configs: {
        crockford: {
            rules: {
                ...crockfordConfig,
                ...typescriptConfig,
            },
        },
        google: {
            rules: {
                ...googleConfig,
                ...typescriptConfig,
            },
        },
    },
} as TSESLint.FlatConfig.Plugin;
