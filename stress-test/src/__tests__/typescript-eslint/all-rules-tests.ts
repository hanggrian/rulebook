import { createRuleTester } from 'eslint-vitest-rule-tester';
import rulebook from 'rulebook-typescript-eslint';
import { type AssertThat, Asserter } from 'testing/dist/asserters';
import typescriptEslint from 'typescript-eslint';
import type RulebookRule from 'rulebook-typescript-eslint/dist/rules/rulebook-rule';

function assertThatAllRules(): AssertThat {
    return (code: string) =>
        new Asserter(
            code,
            ...Object.values(rulebook.rules).map(rule =>
                createRuleTester({
                    rule: rule,
                    name: (rule as RulebookRule).meta.docs.description,
                    configs: {
                        languageOptions: {
                            parser: typescriptEslint.parser,
                            parserOptions: {
                                ecmaVersion: 'latest',
                                sourceType: 'module',
                            },
                        },
                    },
                })),
        );
}

export default assertThatAllRules;
