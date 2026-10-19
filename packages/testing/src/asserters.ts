import { Linter } from 'eslint';
import { expect } from 'vitest';
import type RulebookRule from 'rulebook-eslint/src/rules/rulebook-rule';

class Asserter {
    private readonly rules: RulebookRule[];
    private readonly code: string;
    filename: string | undefined = undefined;

    constructor(rules: RulebookRule[], code: string) {
        this.rules = rules;
        this.code = code;
    }

    withFilename(filename: string): Asserter {
        this.filename = filename;
        return this;
    }

    hasNoError(): void {
        expect(this.verify())
            .toHaveLength(0);
    }

    hasErrorMessages(...messages: string[]): void {
        expect(this.verify().map(m => `${m.line}:${m.column} ${m.message}`))
            .toEqual(messages);
    }

    private verify(): Linter.LintMessage[] {
        return new Linter({ configType: 'flat' }).verify(
            this.code,
            [{
                files: ['**/*.js'],
                plugins: {
                    custom: {
                        rules:
                            Object.fromEntries(
                                // eslint-disable-next-line
                                this.rules.map(rule => [rule.meta!.docs!.description!, rule])
                            ),
                    },
                },
                rules:
                    Object.fromEntries(
                        this.rules.map(rule =>
                            // eslint-disable-next-line
                            [`custom/${rule.meta!.docs!.description!}`, 'error'],),
                    ),
            }],
            this.filename,
        );
    }

    private static ERROR_MESSAGE_REGEX: RegExp = /^(\d+):(\d+)\s+(.+)$/;
}

type AssertThat = (code: string) => Asserter;

function assertThatRule(...rules: RulebookRule[]): AssertThat {
    return (code: string) => new Asserter(rules, code);
}

export { assertThatRule, Asserter, AssertThat };
