import { AST, Rule } from 'eslint';
import { Expression, VariableDeclarator } from 'estree';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';

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
                if (init.loc!.start.line === init.loc!.end.line) {
                    return;
                }
                const loc: AST.SourceLocation | null | undefined = init.loc;
                if (!loc) {
                    return;
                }

                // checks for violation
                const operator: AST.Token | null =
                    context.sourceCode.getTokenAfter(node.id, token => token.value === '=');
                if (!operator || operator.loc.end.line !== loc.start.line) {
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
