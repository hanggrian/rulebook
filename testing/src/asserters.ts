import {
    type NormalizedTestCase,
    type RuleTester,
    type TestExecutionResult,
    createRuleTester,
} from 'eslint-vitest-rule-tester';
import RulebookRule from 'rulebook-eslint/dist/rules/rulebook-rule';
import type { RuleOptions } from '@stylistic/eslint-plugin';

class Asserter {
    private readonly nativeTesters: RuleTester[];
    private readonly code: string;
    filename: string | undefined = undefined;

    constructor(code: string, ...ruleTesters: RuleTester[]) {
        this.nativeTesters = ruleTesters;
        this.code = code;
    }

    withFilename(filename: string): Asserter {
        this.filename = filename;
        return this;
    }

    hasNoError(): Promise<{
        testcase: NormalizedTestCase<RuleOptions>;
        result: TestExecutionResult;
    }[]> {
        return Promise.all(
            this.nativeTesters.map(tester =>
                tester.valid({
                    code: this.code,
                    filename: this.filename,
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
                    filename: this.filename,
                    errors,
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
            }),
        );
}

export { assertThatRule, Asserter, AssertThat };
