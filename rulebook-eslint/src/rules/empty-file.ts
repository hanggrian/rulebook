import { AST, Rule } from 'eslint';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#empty-file|See detail} */
class EmptyFileRule extends RulebookRule {
    constructor() {
        super('empty-file', {
            [EmptyFileRule.MSG]: messages.get(EmptyFileRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
        return {
            Program(node: AST.Program) {
                if (context.sourceCode.getText().trim().length > 0) {
                    return;
                }
                context.report({
                    node: node,
                    messageId: EmptyFileRule.MSG,
                });
            },
        };
    }

    static MSG: string = 'empty.file';
}

export default new EmptyFileRule();
