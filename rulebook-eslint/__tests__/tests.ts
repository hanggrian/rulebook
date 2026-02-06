import { Rule } from 'eslint';
import { RuleOptions } from '@stylistic/eslint-plugin';
import { createRuleTester, NormalizedTestCase, RuleModule, RuleTester, TestExecutionResult } from 'eslint-vitest-rule-tester';
import { expect } from 'vitest';

function assertProperties(module: Rule.RuleModule): void {
    const ruleName: string = module.meta.docs.description;
    expect(
        module.constructor.name
            .replace(/Rule$/, '')
            .replace(/([a-z])([A-Z])/g, '$1-$2')
            .toLowerCase(),
    ).toBe(ruleName);
    expect(module.meta.docs.url)
        .toBe(`https://hanggrian.github.io/rulebook/rules/#${ruleName}`);
}

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
        let errors = [];
        for (const message of messages) {
            errors.push({
                message: message,
            })
        }
        return this.nativeTester.invalid({
            code: this.code,
            filename: this.filename,
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

export { assertProperties, assertThatRule, AssertThat };
