import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule } from 'eslint';

/** {@link https://hanggrian.github.io/rulebook/rules/#empty-file|See detail} */
class EmptyFileRule extends RulebookRule {
    constructor() {
        super('empty-file', {
            [EmptyFileRule.MSG]: messages.get(EmptyFileRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Program(node: AST.Program) {
                // checks for violation
                if (context.sourceCode.getText().trim().length) {
                    return;
                }
                context.report({ node: node, messageId: EmptyFileRule.MSG });
            },
        };
    }

    static MSG: string = 'empty.file';
}

export default new EmptyFileRule();
