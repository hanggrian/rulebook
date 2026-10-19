import assignmentWrappingRule from './rules/assignment-wrapping.js';
import bracesClippingRule from './rules/braces-clipping.js';
import caseSeparatorRule from './rules/case-separator.js';
import chainCallWrappingRule from './rules/chain-call-wrapping.js';
import commentTrimmingRule from './rules/comment-trimming.js';
import fileNameRule from './rules/file-name.js';
import lowercaseHexRule from './rules/lowercase-hex.js';
import noDuplicateBlankLineInBlockCommentRule from './rules/no-duplicate-blank-line-in-block-comment.js';
import noDuplicateBlankLineInCommentRule from './rules/no-duplicate-blank-line-in-comment.js';
import noEmptyFileRule from './rules/no-empty-file.js';
import noLeadingBlankLineRule from './rules/no-leading-blank-line.js';
import noMeaninglessWordRule from './rules/no-meaningless-word.js';
import noNestedIfElseRule from './rules/no-nested-if-else.js';
import noRedundantDefaultRule from './rules/no-redundant-default.js';
import noRedundantElseRule from './rules/no-redundant-else.js';
import noRedundantIfRule from './rules/no-redundant-if.js';
import noUselessContinueRule from './rules/no-useless-continue.js';
import parenthesesTrimmingRule from './rules/parentheses-trimming.js';
import todoCommentStyleRule from './rules/todo-comment-style.js';
import type { ESLint, Linter } from 'eslint';

const crockfordConfig: Linter.RulesRecord = {
    'consistent-return': 'error',
    'no-case-declarations': 'off',
    'no-undef': 'error',

    '@import/no-duplicates': 'error',
    'no-unused-vars': ['error', { args: 'none' }],
    '@import/no-namespace': 'error',
    '@rulebook/todo-comment-style': 'error',
    '@stylistic/comma-dangle': ['error', 'always-multiline'],
    // Clipping
    '@rulebook/braces-clipping': 'error',
    '@stylistic/array-bracket-spacing': 'error',
    '@stylistic/space-in-parens': 'error',
    '@jsdoc/multiline-blocks': [
        'error',
        {
            noMultilineBlocks: true,
            minimumLengthForMultiline: 97, // 100 - len(' * ')
        },
    ],
    // Declaration
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
    // Expression
    // Formatting
    '@stylistic/indent': ['error', 4],
    'max-lines': ['error', 1000],
    '@stylistic/max-len': [
        'error',
        {
            code: 100,
            tabWidth: 4,
            ignoreUrls: true,
            ignorePattern: '(module|require|import)',
        },
    ],
    '@rulebook/no-empty-file': 'error',
    '@stylistic/no-trailing-spaces': 'error',
    '@stylistic/eol-last': 'error',
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
    '@rulebook/no-meaningless-word': 'error',
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
    '@stylistic/object-curly-spacing': ['error', 'always'],
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
    // Statement
    'no-lonely-if': 'error',
    'no-restricted-syntax': [
        'error',
        {
            selector: 'SwitchStatement[cases.length<2]',
            message: "Replace 'when' with 'if' condition.",
        },
        {
            selector:
                'BinaryExpression[operator=/^(==|<|>)$/]' +
                '[left.property.name=/^(size|length)$/]' +
                '[right.value=0]',
            message: 'Replace comparison with truthy.',
        },
        {
            selector:
                'BinaryExpression[operator=/^(==|<|>)$/]' +
                '[left.value=0]' +
                '[right.property.name=/^(size|length)$/]',
            message: 'Replace comparison with truthy.',
        },
        {
            selector: "CallExpression[arguments.length=0][callee.property.name='isEmpty']",
            message: 'Replace comparison with truthy.',
        },
        {
            selector:
                'BinaryExpression[operator=/^(==|!=)$/] > ' +
                'Literal[value!=null]:not([value=/^.*$/])',
            message: "Replace equality with '==='.",
        },
        {
            selector: "BinaryExpression[operator=/^(==|!=)$/] > Identifier[name='undefined']",
            message: "Replace equality with '==='.",
        },
        {
            selector: 'BinaryExpression[operator=/^(==|!=)$/] > Literal[value=null]',
            message: "Replace equality with '==='.",
        },
    ],
    'curly': ['error', 'multi-line'],
    '@rulebook/no-nested-if-else': 'error',
    '@rulebook/no-redundant-default': 'error',
    '@rulebook/no-redundant-else': 'error',
    '@rulebook/no-redundant-if': 'error',
    '@rulebook/no-useless-continue': 'error',
    'no-useless-return': 'error',
    '@stylistic/semi': 'error',
    'operator-assignment': 'error',
    // Trimming
    '@jsdoc/no-blank-block-descriptions': 'error',
    '@stylistic/padded-blocks': ['error', 'never'],
    '@rulebook/comment-trimming': 'error',
    '@rulebook/no-duplicate-blank-line-in-comment': 'error',
    '@rulebook/no-duplicate-blank-line-in-block-comment': 'error',
    '@rulebook/no-leading-blank-line': 'error',
    '@stylistic/no-multiple-empty-lines': [
        'error',
        {
            max: 1,
        },
    ],
    '@stylistic/no-multi-spaces': 'error',
    '@rulebook/parentheses-trimming': 'error',
    // Wrapping
    '@rulebook/assignment-wrapping': 'error',
    '@rulebook/chain-call-wrapping': 'error',
    'arrow-body-style': ['error', 'as-needed'],
    '@stylistic/operator-linebreak': 'error',
    '@stylistic/array-element-newline': ['error', 'consistent'],
    '@stylistic/function-call-argument-newline': ['error', 'consistent'],
    '@stylistic/function-paren-newline': ['error', 'multiline-arguments'],
    '@stylistic/max-statements-per-line': 'error',
};

const googleConfig: Linter.RulesRecord = {
    'consistent-return': 'error',
    'no-case-declarations': 'off',
    'no-undef': 'error',

    '@import/no-duplicates': 'error',
    'no-unused-vars': ['error', { args: 'none' }],
    '@import/no-namespace': 'error',
    '@rulebook/todo-comment-style': 'error',
    '@stylistic/comma-dangle': ['error', 'always-multiline'],
    // Clipping
    '@rulebook/braces-clipping': 'error',
    '@stylistic/array-bracket-spacing': 'error',
    '@stylistic/space-in-parens': 'error',
    '@jsdoc/multiline-blocks': [
        'error',
        {
            noMultilineBlocks: true,
            minimumLengthForMultiline: 97, // 100 - len(' * ')
        },
    ],
    // Declaration
    '@rulebook/lowercase-hex': 'error',
    '@stylistic/quotes': ['error', 'single', { allowTemplateLiterals: true }],
    '@stylistic/arrow-parens': ['error', 'always'],
    // Expression
    // Formatting
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
    'max-lines': ['error', 1000],
    '@stylistic/max-len': [
        'error',
        {
            code: 80,
            tabWidth: 2,
            ignoreUrls: true,
            ignorePattern: 'goog.(module|require)',
        },
    ],
    '@rulebook/no-empty-file': 'error',
    '@stylistic/no-trailing-spaces': 'error',
    '@stylistic/eol-last': 'error',
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
    '@rulebook/no-meaningless-word': 'error',
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
    '@stylistic/object-curly-spacing': ['error', 'never'],
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
    // Statement
    'no-lonely-if': 'error',
    'no-restricted-syntax': [
        'error',
        {
            selector: 'SwitchStatement[cases.length<2]',
            message: "Replace 'when' with 'if' condition.",
        },
        {
            selector:
                'BinaryExpression[operator=/^(==|<|>)$/]' +
                '[left.property.name=/^(size|length)$/]' +
                '[right.value=0]',
            message: 'Replace comparison with truthy.',
        },
        {
            selector:
                'BinaryExpression[operator=/^(==|<|>)$/]' +
                '[left.value=0]' +
                '[right.property.name=/^(size|length)$/]',
            message: 'Replace comparison with truthy.',
        },
        {
            selector: "CallExpression[arguments.length=0][callee.property.name='isEmpty']",
            message: 'Replace comparison with truthy.',
        },
        {
            selector:
                'BinaryExpression[operator=/^(==|!=)$/] > ' +
                'Literal[value!=null]:not([value=/^.*$/])',
            message: "Replace equality with '==='.",
        },
        {
            selector: "BinaryExpression[operator=/^(==|!=)$/] > Identifier[name='undefined']",
            message: "Replace equality with '==='.",
        },
        {
            selector: 'BinaryExpression[operator=/^(==|!=)$/] > Literal[value=null]',
            message: "Replace equality with '==='.",
        },
    ],
    'curly': ['error', 'multi-line'],
    '@rulebook/no-nested-if-else': 'error',
    '@rulebook/no-redundant-default': 'error',
    '@rulebook/no-redundant-else': 'error',
    '@rulebook/no-redundant-if': 'error',
    '@rulebook/no-useless-continue': 'error',
    'no-useless-return': 'error',
    '@stylistic/semi': 'error',
    'operator-assignment': 'error',
    // Trimming
    '@jsdoc/no-blank-block-descriptions': 'error',
    '@stylistic/padded-blocks': ['error', 'never'],
    '@rulebook/comment-trimming': 'error',
    '@rulebook/no-duplicate-blank-line-in-block-comment': 'error',
    '@rulebook/no-duplicate-blank-line-in-comment': 'error',
    '@rulebook/no-leading-blank-line': 'error',
    '@stylistic/no-multiple-empty-lines': [
        'error',
        {
            max: 1,
        },
    ],
    '@stylistic/no-multi-spaces': 'error',
    '@rulebook/parentheses-trimming': 'error',
    // Wrapping
    '@rulebook/assignment-wrapping': 'error',
    '@rulebook/chain-call-wrapping': 'error',
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
        'assignment-wrapping': assignmentWrappingRule,
        'braces-clipping': bracesClippingRule,
        'case-separator': caseSeparatorRule,
        'comment-trimming': commentTrimmingRule,
        'chain-call-wrapping': chainCallWrappingRule,
        'file-name': fileNameRule,
        'lowercase-hex': lowercaseHexRule,
        'no-duplicate-blank-line-in-block-comment': noDuplicateBlankLineInBlockCommentRule,
        'no-duplicate-blank-line-in-comment': noDuplicateBlankLineInCommentRule,
        'no-empty-file': noEmptyFileRule,
        'no-leading-blank-line': noLeadingBlankLineRule,
        'no-meaningless-word': noMeaninglessWordRule,
        'no-nested-if-else': noNestedIfElseRule,
        'no-redundant-default': noRedundantDefaultRule,
        'no-redundant-else': noRedundantElseRule,
        'no-redundant-if': noRedundantIfRule,
        'no-useless-continue': noUselessContinueRule,
        'parentheses-trimming': parenthesesTrimmingRule,
        'todo-comment-style': todoCommentStyleRule,
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
