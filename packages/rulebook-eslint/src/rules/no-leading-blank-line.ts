import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';

/** {@link https://hanggrian.github.io/rulebook/rules/#no-leading-blank-line|See detail} */
class NoLeadingBlankLineRule extends RulebookRule {
    constructor() {
        super('no-leading-blank-line', {
            [NoLeadingBlankLineRule.MSG]:
                messages.get(NoLeadingBlankLineRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Program() {
                const lines: string[] = context.sourceCode.lines;
                if (!lines.length) {
                    return;
                }
                const line: string = lines[0];
                if (line.trim().length) {
                    return;
                }
                context.report({
                    loc: {
                        line: 1,
                        column: 0,
                    },
                    messageId: NoLeadingBlankLineRule.MSG,
                });
            },
        };
    }

    private static MSG: string = 'no.leading.blank.line';
}

export default new NoLeadingBlankLineRule();
