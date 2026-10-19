import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule } from 'eslint';

/** {@link https://hanggrian.github.io/rulebook/rules/#no-empty-file|See detail} */
class NoEmptyFileRule extends RulebookRule {
    constructor() {
        super('no-empty-file', {
            [NoEmptyFileRule.MSG]: messages.get(NoEmptyFileRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Program(node: AST.Program) {
                // checks for violation
                if (context.sourceCode.getText().trim().length) {
                    return;
                }
                context.report({ node: node, messageId: NoEmptyFileRule.MSG });
            },
        };
    }

    private static MSG: string = 'no.empty.file';
}

export default new NoEmptyFileRule();
