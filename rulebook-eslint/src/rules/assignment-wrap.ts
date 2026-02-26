import { Rule } from 'eslint';
import { Expression, VariableDeclarator } from 'estree';
import messages from '../messages.js';
import { isMultiline } from '../nodes.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#assignment-wrap|See detail} */
class AssignmentWrapRule extends RulebookRule {
    constructor() {
        super('assignment-wrap', {
            [AssignmentWrapRule.MSG]: messages.get(AssignmentWrapRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
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
                context.report({
                    node: init,
                    messageId: AssignmentWrapRule.MSG,
                });
            },
        };
    }

    static MSG: string = 'assignment.wrap';
}

export default new AssignmentWrapRule();
