import { RuleOptions } from '@stylistic/eslint-plugin';
import { NormalizedTestCase, RuleModule, RuleTester, TestExecutionResult, createRuleTester } from 'eslint-vitest-rule-tester';
import typescriptEslint from 'typescript-eslint';

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
        const errors = [];
        for (const message of messages) {
            errors.push({
                message: message,
            });
        }
        return this.nativeTester.invalid({
            code: this.code,
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
            code,
        );
}

export { assertThatRule, AssertThat };
