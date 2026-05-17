import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule } from 'eslint';

/** {@link https://hanggrian.github.io/rulebook/rules/#unnecessary-leading-blank-line|See detail} */
class UnnecessaryLeadingBlankLineRule extends RulebookRule {
    constructor() {
        super('unnecessary-leading-blank-line', {
            [UnnecessaryLeadingBlankLineRule.MSG]:
                messages.get(UnnecessaryLeadingBlankLineRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Program(node: AST.Program) {
                const lines: string[] = context.sourceCode.lines;
                if (!lines.length) {
                    return;
                }
                const line: string = lines[0];
                if (line.trim().length) {
                    return;
                }
                context.report({ node: node, messageId: UnnecessaryLeadingBlankLineRule.MSG });
            },
        };
    }

    private static MSG: string = 'unnecessary.leading.blank.line';
}

export default new UnnecessaryLeadingBlankLineRule();
