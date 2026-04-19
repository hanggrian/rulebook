import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule } from 'eslint';

/** {@link https://hanggrian.github.io/rulebook/rules/#unnecessary-initial-blank-line|See detail} */
class UnnecessaryInitialBlankLineRule extends RulebookRule {
    constructor() {
        super('unnecessary-initial-blank-line', {
            [UnnecessaryInitialBlankLineRule.MSG]:
                messages.get(UnnecessaryInitialBlankLineRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Program(node: AST.Program) {
                const lines: string[] = context.sourceCode.lines;
                if (lines.length === 0) {
                    return;
                }
                const line: string = lines[0];
                if (line.trim().length !== 0) {
                    return;
                }
                context.report({ node: node, messageId: UnnecessaryInitialBlankLineRule.MSG });
            },
        };
    }

    static MSG: string = 'unnecessary.initial.blank.line';
}

export default new UnnecessaryInitialBlankLineRule();
