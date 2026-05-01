import { createRuleTester } from 'eslint-vitest-rule-tester';
import rulebook from 'rulebook-eslint';
import { type AssertThat, Asserter } from 'testing/dist/asserters';
import type RulebookRule from 'rulebook-eslint/dist/rules/rulebook-rule';

function assertThatAllRules(): AssertThat {
    return (code: string) =>
        new Asserter(
            code,
            ...Object.values(rulebook.rules).map(rule =>
                createRuleTester({
                    rule: rule,
                    name: (rule as RulebookRule).meta.docs.description,
                })),
        );
}

export default assertThatAllRules;
