import eslintPluginJs from '@stylistic/eslint-plugin-js';
import eslintPluginJsdoc from 'eslint-plugin-jsdoc';
import { sortClassMembersRule } from 'eslint-plugin-sort-class-members/dist/rules/sort-class-members.js';
import assignmentWrap from './rules/assignment-wrap.js';
import chainCallWrap from './rules/chain-call-wrap.js';
import todoComment from './rules/todo-comment.js';
import wildcardImport from './rules/wildcard-import.js';

const plugin = {
    rules: {
        ...eslintPluginJs.rules,
        ...eslintPluginJsdoc.rules,
        'sort-class-members': sortClassMembersRule,
        'assignment-wrap': assignmentWrap,
        'wildcard-import': wildcardImport,
        'todo-comment': todoComment,
        'chain-call-wrap': chainCallWrap,
    },
};

const proxmoxJavaScriptStyleNamed = (plugin: string) => ({
    'consistent-return': 'error',
    'no-undef': 'error',
    [`${plugin}/semi`]: 'error',
    [`${plugin}/semi-spacing`]: 'error',

    [`${plugin}/comma-dangle`]: ['error', 'always-multiline'],
    'no-unused-vars': 'error',
    [`${plugin}/todo-comment`]: 'error',
    [`${plugin}/wildcard-import`]: 'error',
    // Clipping
    [`${plugin}/object-curly-spacing`]: ['error', 'always'],
    [`${plugin}/array-bracket-spacing`]: ['error', 'never'],
    [`${plugin}/space-in-parens`]: 'error',
    [`${plugin}/multiline-blocks`]: [
        'error',
        {
            noMultilineBlocks: true,
            minimumLengthForMultiline: 100,
        },
    ],
    // Declaring
    [`${plugin}/quotes`]: [
        'error',
        'single',
        {
            'avoidEscape': true,
            'allowTemplateLiterals': 'avoidEscape',
        },
    ],
    [`${plugin}/arrow-parens`]: ['error', 'as-needed'],
    // Formatting
    'max-lines': ['error', 1000],
    [`${plugin}/eol-last`]: 'error',
    'indent': ['error', 4],
    [`${plugin}/max-len`]: [
        'error',
        {
            code: 100,
            tabWidth: 4,
            ignoreUrls: true,
            ignorePattern: '(module|require|import)',
        },
    ],
    'no-empty': ['error'],
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
    [`${plugin}/sort-tags`]: 'error',
    [`${plugin}/sort-class-members`]: [
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
    [`${plugin}/spaced-comment`]: 'error',
    [`${plugin}/check-indentation`]: 'error',
    [`${plugin}/tag-lines`]: [
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
    [`${plugin}/no-blank-block-descriptions`]: 'error',
    [`${plugin}/padded-blocks`]: ['error', 'never'],
    [`${plugin}/no-multiple-empty-lines`]: [
        'error',
        {
            max: 1,
        },
    ],
    [`${plugin}/no-multi-spaces`]: 'error',
    // Wrapping
    [`${plugin}/assignment-wrap`]: 'error',
    [`${plugin}/chain-call-wrap`]: 'error',
    'arrow-body-style': ['error', 'as-needed'],
    [`${plugin}/operator-linebreak`]: 'error',
    [`${plugin}/array-element-newline`]: ['error', 'consistent'],
    [`${plugin}/function-call-argument-newline`]: ['error', 'consistent'],
    [`${plugin}/function-paren-newline`]: ['error', 'multiline-arguments'],
    [`${plugin}/max-statements-per-line`]: 'error',
});

const googleJavaScriptStyleNamed = (plugin: string) => ({
    'consistent-return': 'error',
    'no-undef': 'error',
    [`${plugin}/semi`]: 'error',
    [`${plugin}/semi-spacing`]: 'error',

    [`${plugin}/comma-dangle`]: ['error', 'always-multiline'],
    'no-unused-vars': ['error', { args: 'none' }],
    [`${plugin}/todo-comment`]: 'error',
    [`${plugin}/wildcard-import`]: 'error',
    // Clipping
    [`${plugin}/object-curly-spacing`]: ['error', 'never'],
    [`${plugin}/array-bracket-spacing`]: ['error', 'never'],
    [`${plugin}/space-in-parens`]: 'error',
    [`${plugin}/multiline-blocks`]: [
        'error',
        {
            noMultilineBlocks: true,
            minimumLengthForMultiline: 100,
        },
    ],
    // Declaring
    [`${plugin}/quotes`]: ['error', 'single', { allowTemplateLiterals: true }],
    [`${plugin}/arrow-parens`]: ['error', 'always'],
    // Formatting
    'max-lines': ['error', 1000],
    [`${plugin}/eol-last`]: 'error',
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
    [`${plugin}/max-len`]: [
        'error',
        {
            code: 80,
            tabWidth: 2,
            ignoreUrls: true,
            ignorePattern: 'goog.(module|require)',
        },
    ],
    'no-empty': ['error'],
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
    [`${plugin}/sort-tags`]: 'error',
    [`${plugin}/sort-class-members`]: [
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
    [`${plugin}/spaced-comment`]: 'error',
    [`${plugin}/check-indentation`]: 'error',
    [`${plugin}/tag-lines`]: [
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
    [`${plugin}/no-blank-block-descriptions`]: 'error',
    [`${plugin}/padded-blocks`]: ['error', 'never'],
    [`${plugin}/no-multiple-empty-lines`]: [
        'error',
        {
            max: 1,
        },
    ],
    [`${plugin}/no-multi-spaces`]: 'error',
    // Wrapping
    [`${plugin}/assignment-wrap`]: 'error',
    [`${plugin}/chain-call-wrap`]: 'error',
    'arrow-body-style': ['error', 'as-needed'],
    [`${plugin}/operator-linebreak`]: 'error',
    [`${plugin}/array-element-newline`]: ['error', 'consistent'],
    [`${plugin}/function-call-argument-newline`]: ['error', 'consistent'],
    [`${plugin}/function-paren-newline`]: ['error', 'multiline-arguments'],
    [`${plugin}/max-statements-per-line`]: 'error',

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
});

export { plugin as default, proxmoxJavaScriptStyleNamed, googleJavaScriptStyleNamed };
