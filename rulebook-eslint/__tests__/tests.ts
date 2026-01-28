import { createRuleTester, NormalizedTestCase, RuleModule, RuleTester, TestExecutionResult } from 'eslint-vitest-rule-tester';
import { RuleOptions } from '@stylistic/eslint-plugin-js';

class Asserter {
    nativeTester: RuleTester;
    code: string;

    constructor(ruleTester: RuleTester, code: string) {
        this.nativeTester = ruleTester;
        this.code = code;
    }

    hasNoError(): Promise<{
        testcase: NormalizedTestCase<RuleOptions>;
        result: TestExecutionResult;
    }> {
        return this.nativeTester.valid({
            code: this.code,
        });
    }

    hasErrorMessages(...messages: string[]): Promise<{
        testcase: NormalizedTestCase<RuleOptions>;
        result: TestExecutionResult;
    }> {
        let errors = [];
        for (const message of messages) {
            errors.push({
                message: message,
            })
        }
        return this.nativeTester.invalid({
            code: this.code,
            errors: errors,
        })
    }
}

type AssertThat = (code: string) => Asserter;

function assertThatRule(rule: RuleModule, name: string): AssertThat {
    return (code: string) =>
        new Asserter(
            createRuleTester({
                rule: rule,
                name: name,
            }),
            code,
        );
}

export { assertThatRule, AssertThat };
