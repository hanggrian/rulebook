import { ESLint } from 'eslint';
import assignmentWrap from './rules/assignment-wrap.js';
import chainCallWrap from './rules/chain-call-wrap.js';
import emptyFile from './rules/empty-file.js';
import todoComment from './rules/todo-comment.js';
import wildcardImport from './rules/wildcard-import.js';

const crockfordConfig: Record<string, any> = {
    'consistent-return': 'error',
    'no-undef': 'error',
    '@stylistic/semi': 'error',
    '@stylistic/semi-spacing': 'error',

    '@stylistic/comma-dangle': ['error', 'always-multiline'],
    'no-unused-vars': 'error',
    '@rulebook/todo-comment': 'error',
    '@rulebook/wildcard-import': 'error',
    // Clipping
    '@stylistic/object-curly-spacing': ['error', 'always'],
    '@stylistic/array-bracket-spacing': ['error', 'never'],
    '@stylistic/space-in-parens': 'error',
    '@jsdoc/multiline-blocks': [
        'error',
        {
            noMultilineBlocks: true,
            minimumLengthForMultiline: 100,
        },
    ],
    // Declaring
    '@stylistic/quotes': [
        'error',
        'single',
        {
            'avoidEscape': true,
            'allowTemplateLiterals': 'avoidEscape',
        },
    ],
    '@stylistic/arrow-parens': ['error', 'as-needed'],
    // Formatting
    '@rulebook/empty-file': 'error',
    'max-lines': ['error', 1000],
    '@stylistic/eol-last': 'error',
    'indent': ['error', 4],
    '@stylistic/max-len': [
        'error',
        {
            code: 100,
            tabWidth: 4,
            ignoreUrls: true,
            ignorePattern: '(module|require|import)',
        },
    ],
    // Naming
    'id-denylist': [
        'error',
        'object',
        'number',
        'string',
        'objects',
        'numbers',
        'strings',
    ],
    // Ordering
    '@jsdoc/sort-tags': 'error',
    '@sort-class-members/sort-class-members': [
        'error',
        {
            order: [
                '[properties]',
                'constructor',
                '[methods]',
                '[static-properties]',
                '[static-methods]',
                'InnerClass',
                'toString',
            ],
            groups: {
                InnerClass: [{
                    type: 'property',
                    propertyType: 'ClassExpression',
                }],
            },
        },
    ],
    'sort-imports': [
        'error',
        {
            ignoreDeclarationSort: true,
        },
    ],
    // Spacing
    '@stylistic/spaced-comment': 'error',
    '@jsdoc/check-indentation': 'error',
    '@stylistic/lines-between-class-members': [
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
            ],
        },
    ],
    '@jsdoc/tag-lines': [
        'error',
        'any',
        {
            startLines: 1,
        },
    ],
    // Stating
    'curly': ['error', 'multi-line'],
    'no-else-return': 'error',
    'no-restricted-syntax': [
        'error',
        {
            selector: 'SwitchStatement[cases.length<2]',
            message: 'Switch statements must have at least 2 cases.',
        },
    ],
    // Trimming
    '@jsdoc/no-blank-block-descriptions': 'error',
    '@stylistic/padded-blocks': ['error', 'never'],
    '@stylistic/no-multiple-empty-lines': [
        'error',
        {
            max: 1,
        },
    ],
    '@stylistic/no-multi-spaces': 'error',
    // Wrapping
    '@rulebook/assignment-wrap': 'error',
    '@rulebook/chain-call-wrap': 'error',
    'arrow-body-style': ['error', 'as-needed'],
    '@stylistic/operator-linebreak': 'error',
    '@stylistic/array-element-newline': ['error', 'consistent'],
    '@stylistic/function-call-argument-newline': ['error', 'consistent'],
    '@stylistic/function-paren-newline': ['error', 'multiline-arguments'],
    '@stylistic/max-statements-per-line': 'error',
};

const googleConfig: Record<string, any> = {
    'consistent-return': 'error',
    'no-undef': 'error',
    '@stylistic/semi': 'error',
    '@stylistic/semi-spacing': 'error',

    '@stylistic/comma-dangle': ['error', 'always-multiline'],
    'no-unused-vars': ['error', { args: 'none' }],
    '@rulebook/todo-comment': 'error',
    '@rulebook/wildcard-import': 'error',
    // Clipping
    '@stylistic/object-curly-spacing': ['error', 'never'],
    '@stylistic/array-bracket-spacing': ['error', 'never'],
    '@stylistic/space-in-parens': 'error',
    '@jsdoc/multiline-blocks': [
        'error',
        {
            noMultilineBlocks: true,
            minimumLengthForMultiline: 100,
        },
    ],
    // Declaring
    '@stylistic/quotes': ['error', 'single', { allowTemplateLiterals: true }],
    '@stylistic/arrow-parens': ['error', 'always'],
    // Formatting
    '@rulebook/empty-file': 'error',
    'max-lines': ['error', 1000],
    '@stylistic/eol-last': 'error',
    'indent': [
        'error',
        2,
        {
            'CallExpression': {
                'arguments': 2,
            },
            'FunctionDeclaration': {
                'body': 1,
                'parameters': 2,
            },
            'FunctionExpression': {
                'body': 1,
                'parameters': 2,
            },
            'MemberExpression': 2,
            'ObjectExpression': 1,
            'SwitchCase': 1,
            'ignoredNodes': [
                'ConditionalExpression',
            ],
        },
    ],
    '@stylistic/max-len': [
        'error',
        {
            code: 80,
            tabWidth: 2,
            ignoreUrls: true,
            ignorePattern: 'goog.(module|require)',
        },
    ],
    // Naming
    'id-denylist': [
        'error',
        'object',
        'number',
        'string',
        'objects',
        'numbers',
        'strings',
    ],
    // Ordering
    '@jsdoc/sort-tags': 'error',
    '@sort-class-members/sort-class-members': [
        'error',
        {
            order: [
                '[properties]',
                'constructor',
                '[methods]',
                '[static-properties]',
                '[static-methods]',
                'InnerClass',
                'toString',
            ],
            groups: {
                InnerClass: [{
                    type: 'property',
                    propertyType: 'ClassExpression',
                }],
            },
        },
    ],
    'sort-imports': [
        'error',
        {
            ignoreDeclarationSort: true,
        },
    ],
    // Spacing
    '@stylistic/spaced-comment': 'error',
    '@jsdoc/check-indentation': 'error',
    '@stylistic/lines-between-class-members': [
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
            ],
        },
    ],
    '@jsdoc/tag-lines': [
        'error',
        'any',
        {
            startLines: 1,
        },
    ],
    // Stating
    'curly': ['error', 'multi-line'],
    'no-else-return': 'error',
    'no-restricted-syntax': [
        'error',
        {
            selector: 'SwitchStatement[cases.length<2]',
            message: 'Switch statements must have at least 2 cases.',
        },
    ],
    // Trimming
    '@jsdoc/no-blank-block-descriptions': 'error',
    '@stylistic/padded-blocks': ['error', 'never'],
    '@stylistic/no-multiple-empty-lines': [
        'error',
        {
            max: 1,
        },
    ],
    '@stylistic/no-multi-spaces': 'error',
    // Wrapping
    '@rulebook/assignment-wrap': 'error',
    '@rulebook/chain-call-wrap': 'error',
    'arrow-body-style': ['error', 'as-needed'],
    '@stylistic/operator-linebreak': 'error',
    '@stylistic/array-element-newline': ['error', 'consistent'],
    '@stylistic/function-call-argument-newline': ['error', 'consistent'],
    '@stylistic/function-paren-newline': ['error', 'multiline-arguments'],
    '@stylistic/max-statements-per-line': 'error',

    'no-cond-assign': 'off',
    'no-irregular-whitespace': 'error',
    'no-unexpected-multiline': 'error',
    'guard-for-in': 'error',
    'no-caller': 'error',
    'no-extend-native': 'error',
    'no-extra-bind': 'error',
    'no-invalid-this': 'error',
    'no-multi-str': 'error',
    'no-new-wrappers': 'error',
    'no-throw-literal': 'error',
    'no-with': 'error',
    'prefer-promise-reject-errors': 'error',
    'array-bracket-newline': 'off',
    'block-spacing': ['error', 'never'],
    'brace-style': 'error',
    'camelcase': ['error', { properties: 'never' }],
    'comma-spacing': 'error',
    'comma-style': 'error',
    'computed-property-spacing': 'error',
    'func-call-spacing': 'error',
    'key-spacing': 'error',
    'keyword-spacing': 'error',
    'linebreak-style': 'error',
    'new-cap': 'error',
    'no-array-constructor': 'error',
    'no-mixed-spaces-and-tabs': 'error',
    'no-new-object': 'error',
    'no-tabs': 'error',
    'no-trailing-spaces': 'error',
    'one-var': [
        'error',
        {
            var: 'never',
            let: 'never',
            const: 'never',
        },
    ],
    'quote-props': ['error', 'consistent'],
    'space-before-blocks': 'error',
    'space-before-function-paren': [
        'error',
        {
            asyncArrow: 'always',
            anonymous: 'never',
            named: 'never',
        },
    ],
    'switch-colon-spacing': 'error',
    'constructor-super': 'error',
    'generator-star-spacing': ['error', 'after'],
    'no-new-symbol': 'error',
    'no-this-before-super': 'error',
    'no-var': 'error',
    'prefer-const': ['error', { destructuring: 'all' }],
    'prefer-rest-params': 'error',
    'prefer-spread': 'error',
    'rest-spread-spacing': 'error',
    'yield-star-spacing': ['error', 'after'],
};

const plugin: ESLint.Plugin = {
    rules: {
        'assignment-wrap': assignmentWrap,
        'chain-call-wrap': chainCallWrap,
        'empty-file': emptyFile,
        'todo-comment': todoComment,
        'wildcard-import': wildcardImport,
    },
    configs: {
        crockford: {
            rules: crockfordConfig,
        },
        google: {
            rules: googleConfig,
        },
    },
};

export { plugin as default, crockfordConfig, googleConfig };
