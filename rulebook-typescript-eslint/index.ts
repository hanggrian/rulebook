import typescriptEslint, { FlatConfig } from 'typescript-eslint';
import rulebookEslint, { googleJavaScriptStyleNamed, proxmoxJavaScriptStyleNamed } from 'rulebook-eslint';
import abstractClassDefinition from './rules/abstract-class-definition.js';

const typescriptPlugin = {
    rules: {
        ...rulebookEslint.rules,
        ...(typescriptEslint.plugin as FlatConfig.Plugin).rules,
        'abstract-class-definition': abstractClassDefinition,
    },
};

const microsoftTypeScriptStyleNamed = (plugin: string) => ({
    ...proxmoxJavaScriptStyleNamed(plugin),

    'consistent-return': 'off',
    [`${plugin}/consistent-return`]: 'error',

    [`${plugin}/abstract-class-definition`]: 'error',
    // Clipping
    // Declaring
    // Formatting
    // Naming
    [`${plugin}/naming-convention`]: [
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
    'lines-between-class-members': 'off',
    [`${plugin}/lines-between-class-members`]: [
        'error',
        {
            enforce: [
                {
                    blankLine: 'always',
                    prev: 'method',
                    next: '*',
                },
                {
                    blankLine: 'always',
                    prev: '*',
                    next: 'method',
                },
                {
                    blankLine: 'never',
                    prev: 'field',
                    next: 'field',
                },
            ],
        },
    ],
    // Stating
    [`${plugin}/switch-exhaustiveness-check`]: 'error',
    // Trimming
    // Wrapping
});

const googleTypeScriptStyleNamed = (plugin: string) => ({
    ...googleJavaScriptStyleNamed(plugin),

    'consistent-return': 'off',
    [`${plugin}/consistent-return`]: 'error',

    [`${plugin}/abstract-class-definition`]: 'error',
    // Clipping
    // Declaring
    // Formatting
    // Naming
    [`${plugin}/naming-convention`]: [
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
    'lines-between-class-members': 'off',
    [`${plugin}/lines-between-class-members`]: [
        'error',
        {
            enforce: [
                {
                    blankLine: 'always',
                    prev: 'method',
                    next: '*',
                },
                {
                    blankLine: 'always',
                    prev: '*',
                    next: 'method',
                },
                {
                    blankLine: 'never',
                    prev: 'field',
                    next: 'field',
                },
            ],
        },
    ],
    // Stating
    [`${plugin}/switch-exhaustiveness-check`]: 'error',
    // Trimming
    // Wrapping
});

export { typescriptPlugin as default, microsoftTypeScriptStyleNamed, googleTypeScriptStyleNamed };
