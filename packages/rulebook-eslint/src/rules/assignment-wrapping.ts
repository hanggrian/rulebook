import messages from '../messages.js';
import { isMultiline } from '../nodes.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type { Expression, VariableDeclarator } from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#assignment-wrapping|See detail} */
class AssignmentWrappingRule extends RulebookRule {
    constructor() {
        super('assignment-wrapping', {
            [AssignmentWrappingRule.MSG]: messages.get(AssignmentWrappingRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            VariableDeclarator(node: VariableDeclarator) {
                // skip lambda and collection initializers
                const init: Expression | null | undefined = node.init;
                if (!init || [
                    'ArrayExpression',
                    'ArrowFunctionExpression',
                    'BlockStatement',
                    'ObjectExpression',
                ].includes(init.type)) {
                    return;
                }

                // find multiline assignee
                if (!isMultiline(init)) {
                    return;
                }

                // checks for violation
                if (context
                    .sourceCode
                    .getTokenAfter(node.id, token => token.value === '=')
                    ?.loc
                    .end
                    .line !== init.loc!.start.line
                ) {
                    return;
                }
                context.report({ node: init, messageId: AssignmentWrappingRule.MSG });
            },
        };
    }

    private static MSG: string = 'assignment.wrapping';
}

export default new AssignmentWrappingRule();
