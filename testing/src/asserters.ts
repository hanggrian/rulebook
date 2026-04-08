import { RuleOptions } from '@stylistic/eslint-plugin';
import { NormalizedTestCase, RuleModule, RuleTester, TestExecutionResult, createRuleTester } from 'eslint-vitest-rule-tester';

class Asserter {
    nativeTester: RuleTester;
    code: string;
    filename: string | undefined = undefined;

    constructor(ruleTester: RuleTester, code: string) {
        this.nativeTester = ruleTester;
        this.code = code;
    }

    withFilename(filename: string): Asserter {
        this.filename = filename;
        return this;
    }

    hasNoError(): Promise<{
        testcase: NormalizedTestCase<RuleOptions>;
        result: TestExecutionResult;
    }> {
        return this.nativeTester.valid({
            code: this.code,
            filename: this.filename,
        });
    }

    hasErrorMessages(...messages: string[]): Promise<{
        testcase: NormalizedTestCase<RuleOptions>;
        result: TestExecutionResult;
    }> {
        const errors = [];
        for (const message of messages) {
            errors.push({
                message: message,
            });
        }
        return this.nativeTester.invalid({
            code: this.code,
            filename: this.filename,
            errors: errors,
        });
    }
}

type AssertThat = (code: string) => Asserter;

function assertThatRule(rule: RuleModule, name: string): AssertThat {
    return (code: string) =>
        new Asserter(
            createRuleTester({
                // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
                rule: rule,
                name: name,
            }),
            code,
        );
}

export { assertThatRule, AssertThat };
