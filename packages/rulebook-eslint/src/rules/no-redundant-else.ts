import messages from '../messages.js';
import { hasJumpStatement } from '../nodes.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type { IfStatement, Node, Statement } from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#no-redundant-else|See detail} */
class NoRedundantElseRule extends RulebookRule {
    constructor() {
        super('no-redundant-else', {
            [NoRedundantElseRule.MSG]: messages.get(NoRedundantElseRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            IfStatement(node: IfStatement) {
                // target root if
                const parent: Node | null = (node as Rule.Node).parent;
                if (parent?.type === 'IfStatement' && parent.alternate === node) {
                    return;
                }

                // checks for violation
                let ifStatement: Statement | null | undefined = node;
                while (ifStatement.type === 'IfStatement') {
                    if (!hasJumpStatement(ifStatement.consequent)) {
                        return;
                    }
                    const elseStatement: Statement | null | undefined = ifStatement.alternate;
                    if (!elseStatement) {
                        return;
                    }
                    context.report({ node: elseStatement, messageId: NoRedundantElseRule.MSG });
                    ifStatement = elseStatement;
                }
            },
        };
    }

    private static MSG: string = 'no.redundant.else';
}

export default new NoRedundantElseRule();
