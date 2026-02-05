import { TSESLint } from '@typescript-eslint/utils';
import rulebookEslint, { crockfordConfig, googleConfig } from 'rulebook-eslint';
import abstractClassDefinition from './rules/abstract-class-definition.js';

const typescriptCrockfordConfig: Record<string, any> = {
    '@typescript-eslint/consistent-return': 'error',
    '@typescript-eslint/no-explicit-any': 'off',

    '@rulebook/abstract-class-definition': 'error',
    // Clipping
    // Declaring
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
    // Ordering
    // Spacing
    // Stating
    '@typescript-eslint/switch-exhaustiveness-check': 'error',
    // Trimming
    // Wrapping
};

const typescriptGoogleConfig: Record<string, any> = {
    '@typescript-eslint/consistent-return': 'error',
    '@typescript-eslint/no-explicit-any': 'off',

    '@rulebook/abstract-class-definition': 'error',
    // Clipping
    // Declaring
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
    // Ordering
    // Spacing
    // Stating
    '@typescript-eslint/switch-exhaustiveness-check': 'error',
    // Trimming
    // Wrapping
};

/* eslint-disable @typescript-eslint/no-unsafe-assignment */
export default {
    rules: {
        ...rulebookEslint.rules,
        'abstract-class-definition': abstractClassDefinition,
    },
    configs: {
        crockford: {
            rules: {
                ...crockfordConfig,
                ...typescriptCrockfordConfig,
            },
        },
        google: {
            rules: {
                ...googleConfig,
                ...typescriptGoogleConfig,
            },
        },
    },
} as TSESLint.FlatConfig.Plugin;
