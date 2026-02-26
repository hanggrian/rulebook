import { ESLint, Linter } from 'eslint';
import assignmentWrapRule from './rules/assignment-wrap.js';
import caseSeparatorRule from './rules/case-separator.js';
import chainCallWrapRule from './rules/chain-call-wrap.js';
import commentTrimRule from './rules/comment-trim.js';
import duplicateBlankLineInBlockCommentRule from './rules/duplicate-blank-line-in-block-comment.js';
import duplicateBlankLineInCommentRule from './rules/duplicate-blank-line-in-comment.js';
import emptyFileRule from './rules/empty-file.js';
import fileNameRule from './rules/file-name.js';
import lowercaseHexRule from './rules/lowercase-hex.js';
import meaninglessWordRule from './rules/meaningless-word.js';
import nestedIfElseRule from './rules/nested-if-else.js';
import parenthesesTrimRule from './rules/parentheses-trim.js';
import redundantDefaultRule from './rules/redundant-default.js';
import redundantElseRule from './rules/redundant-else.js';
import redundantIfRule from './rules/redundant-if.js';
import todoCommentRule from './rules/todo-comment.js';
import unnecessaryContinueRule from './rules/unnecessary-continue.js';
import unnecessaryInitialBlankLineRule from './rules/unnecessary-initial-blank-line.js';

const crockfordConfig: Linter.RulesRecord = {
    'consistent-return': 'error',
    'no-undef': 'error',
    '@stylistic/semi': 'error',
    '@stylistic/semi-spacing': 'error',

    '@stylistic/comma-dangle': ['error', 'always-multiline'],
    'no-unused-vars': 'error',
    '@rulebook/todo-comment': 'error',
    '@import/no-namespace': 'error',
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
    '@rulebook/lowercase-hex': 'error',
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
    '@stylistic/indent': ['error', 4],
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
    '@rulebook/file-name': 'error',
    '@rulebook/meaningless-word': 'error',
    // Ordering
    '@jsdoc/sort-tags': 'error',
    '@import/order': [
        'error',
        {
            'groups': [
                'builtin',
                'external',
                'internal',
                'unknown',
                'parent',
                'sibling',
                'index',
                'object',
                'type',
            ],
            'alphabetize': {
                'order': 'asc',
                'caseInsensitive': false,
            },
        },
    ],
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
    '@rulebook/case-separator': 'error',
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
    'no-lonely-if': 'error',
    'no-restricted-syntax': [
        'error',
        {
            selector: 'SwitchStatement[cases.length<2]',
            message: 'Switch statements must have at least 2 cases.',
        },
    ],
    'curly': ['error', 'multi-line'],
    '@rulebook/nested-if-else': 'error',
    '@rulebook/redundant-default': 'error',
    '@rulebook/redundant-else': 'error',
    '@rulebook/redundant-if': 'error',
    '@rulebook/unnecessary-continue': 'error',
    'no-useless-return': 'error',
    // Trimming
    '@jsdoc/no-blank-block-descriptions': 'error',
    '@stylistic/padded-blocks': ['error', 'never'],
    '@rulebook/comment-trim': 'error',
    '@rulebook/duplicate-blank-line-in-comment': 'error',
    '@rulebook/duplicate-blank-line-in-block-comment': 'error',
    '@rulebook/parentheses-trim': 'error',
    '@stylistic/no-multiple-empty-lines': [
        'error',
        {
            max: 1,
        },
    ],
    '@stylistic/no-multi-spaces': 'error',
    '@rulebook/unnecessary-initial-blank-line': 'error',
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

const googleConfig: Linter.RulesRecord = {
    'consistent-return': 'error',
    'no-undef': 'error',
    '@stylistic/semi': 'error',
    '@stylistic/semi-spacing': 'error',

    '@stylistic/comma-dangle': ['error', 'always-multiline'],
    'no-unused-vars': ['error', { args: 'none' }],
    '@rulebook/todo-comment': 'error',
    '@import/no-namespace': 'error',
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
    '@rulebook/lowercase-hex': 'error',
    '@stylistic/quotes': ['error', 'single', { allowTemplateLiterals: true }],
    '@stylistic/arrow-parens': ['error', 'always'],
    // Formatting
    '@rulebook/empty-file': 'error',
    'max-lines': ['error', 1000],
    '@stylistic/eol-last': 'error',
    '@stylistic/indent': [
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
    '@rulebook/file-name': 'error',
    '@rulebook/meaningless-word': 'error',
    // Ordering
    '@jsdoc/sort-tags': 'error',
    '@import/order': [
        'error',
        {
            'groups': [
                'builtin',
                'external',
                'internal',
                'unknown',
                'parent',
                'sibling',
                'index',
                'object',
                'type',
            ],
            'alphabetize': {
                'order': 'asc',
                'caseInsensitive': false,
            },
        },
    ],
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
    '@rulebook/case-separator': 'error',
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
    'no-lonely-if': 'error',
    'no-restricted-syntax': [
        'error',
        {
            selector: 'SwitchStatement[cases.length<2]',
            message: 'Switch statements must have at least 2 cases.',
        },
    ],
    'curly': ['error', 'multi-line'],
    '@rulebook/nested-if-else': 'error',
    '@rulebook/redundant-default': 'error',
    '@rulebook/redundant-else': 'error',
    '@rulebook/redundant-if': 'error',
    '@rulebook/unnecessary-continue': 'error',
    'no-useless-return': 'error',
    // Trimming
    '@jsdoc/no-blank-block-descriptions': 'error',
    '@stylistic/padded-blocks': ['error', 'never'],
    '@rulebook/comment-trim': 'error',
    '@rulebook/duplicate-blank-line-in-block-comment': 'error',
    '@rulebook/duplicate-blank-line-in-comment': 'error',
    '@rulebook/parentheses-trim': 'error',
    '@stylistic/no-multiple-empty-lines': [
        'error',
        {
            max: 1,
        },
    ],
    '@stylistic/no-multi-spaces': 'error',
    '@rulebook/unnecessary-initial-blank-line': 'error',
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
        'assignment-wrap': assignmentWrapRule,
        'case-separator': caseSeparatorRule,
        'comment-trim': commentTrimRule,
        'chain-call-wrap': chainCallWrapRule,
        'duplicate-blank-line-in-block-comment': duplicateBlankLineInBlockCommentRule,
        'duplicate-blank-line-in-comment': duplicateBlankLineInCommentRule,
        'empty-file': emptyFileRule,
        'file-name': fileNameRule,
        'lowercase-hex': lowercaseHexRule,
        'meaningless-word': meaninglessWordRule,
        'nested-if-else': nestedIfElseRule,
        'redundant-default': redundantDefaultRule,
        'redundant-else': redundantElseRule,
        'redundant-if': redundantIfRule,
        'parentheses-trim': parenthesesTrimRule,
        'todo-comment': todoCommentRule,
        'unnecessary-continue': unnecessaryContinueRule,
        'unnecessary-initial-blank-line': unnecessaryInitialBlankLineRule,
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
