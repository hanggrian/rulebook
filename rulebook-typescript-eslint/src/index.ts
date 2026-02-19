import { TSESLint } from '@typescript-eslint/utils';
import rulebookEslint, { crockfordConfig, googleConfig } from 'rulebook-eslint';
import genericNameRule from './rules/generic-name.js';
import unnecessaryAbstractRule from './rules/unnecessary-abstract.js';

const typescriptConfig: TSESLint.SharedConfig.RulesRecord = {
    '@typescript-eslint/consistent-return': 'error',

    '@typescript-eslint/no-restricted-types': [
        'error',
        {
            types: {
                any:
                    "Use 'unknown' instead, as it is type-safe and requires a type check before " +
                    'use.',
                Function: {
                    'message':
                        "The 'Function' type accepts any function-like object, which is unsafe.",
                    'suggest': ['(...args: any[]) => any'],
                },
                Object: {
                    message: "The 'Object' type actually means 'any non-nullish value.'",
                    fixWith: 'Record<string, unknown>',
                },
                '{}': {
                    'message': 'Empty object types allow any non-nullish value.',
                    'fixWith': 'Record<string, unknown>',
                },
            },
        },
    ],
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
