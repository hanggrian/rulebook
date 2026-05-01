import {
    type NormalizedTestCase,
    type RuleTester,
    type TestExecutionResult,
    createRuleTester,
} from 'eslint-vitest-rule-tester';
import typescriptEslint from 'typescript-eslint';
import type { RuleOptions } from '@stylistic/eslint-plugin';
import type RulebookRule from 'rulebook-typescript-eslint/dist/rules/rulebook-rule';

class Asserter {
    private readonly nativeTesters: RuleTester[];
    private readonly code: string;

    constructor(code: string, ...ruleTesters: RuleTester[]) {
        this.nativeTesters = ruleTesters;
        this.code = code;
    }

    hasNoError(): Promise<{
        testcase: NormalizedTestCase<RuleOptions>;
        result: TestExecutionResult;
    }[]> {
        return Promise.all(
            this.nativeTesters.map(tester =>
                tester.valid({
                    code: this.code,
                })),
        );
    }

    hasErrorMessages(...messages: string[]): Promise<{
        testcase: NormalizedTestCase<RuleOptions>;
        result: TestExecutionResult;
    }[]> {
        const errors = messages.map(message => ({ message }));
        return Promise.all(
            this.nativeTesters.map(tester =>
                tester.invalid({
                    code: this.code,
                    errors: errors,
                })),
        );
    }
}

type AssertThat = (code: string) => Asserter;

function assertThatRule(rule: RulebookRule): AssertThat {
    return (code: string) =>
        new Asserter(
            code,
            createRuleTester({
                rule: rule,
                // eslint-disable-next-line @stylistic/max-len
                // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion, @typescript-eslint/no-extra-non-null-assertion
                name: rule.meta.docs!!.description,
                configs: {
                    languageOptions: {
                        parser: typescriptEslint.parser,
                        parserOptions: {
                            ecmaVersion: 'latest',
                            sourceType: 'module',
                        },
                    },
                },
            }),
        );
}

export { assertThatRule, Asserter, AssertThat };
