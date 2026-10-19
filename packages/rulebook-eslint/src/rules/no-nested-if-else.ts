import messages from '../messages.js';
import { hasJumpStatement, isMultiline } from '../nodes.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type { BlockStatement, Node, Statement } from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#no-nested-if-else|See detail} */
class NoNestedIfElseRule extends RulebookRule {
    constructor() {
        super('no-nested-if-else', {
            [NoNestedIfElseRule.MSG_INVERT]: messages.get(NoNestedIfElseRule.MSG_INVERT),
            [NoNestedIfElseRule.MSG_LIFT]: messages.get(NoNestedIfElseRule.MSG_LIFT),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            BlockStatement(node: BlockStatement) {
                // skip blocks without exit path
                const parent: Node | null = (node as Rule.Node).parent;
                if (!parent ||
                    parent.type !== 'FunctionDeclaration' &&
                    parent.type !== 'FunctionExpression' &&
                    parent.type !== 'ArrowFunctionExpression') {
                    return;
                }
                if (context.sourceCode.getAncestors(node).some(a =>
                    (a.type === 'MethodDefinition' && a.kind === 'constructor') ||
                    a.type === 'TryStatement' ||
                    a.type === 'CatchClause')
                ) {
                    return;
                }

                // get last if
                if (!node.body.length) {
                    return;
                }
                const ifStatement: Node = node.body[node.body.length - 1];
                if (ifStatement.type !== 'IfStatement') {
                    return;
                }

                // checks for violation
                const elseStatement: Statement | null | undefined = ifStatement.alternate;
                if (elseStatement) {
                    if (elseStatement.type === 'IfStatement') {
                        return;
                    }
                    if (!NoNestedIfElseRule.hasMultipleLines(elseStatement)) {
                        return;
                    }
                    context.report({
                        node: elseStatement,
                        messageId: NoNestedIfElseRule.MSG_LIFT,
                    });
                }
                const expression: Statement = ifStatement.consequent;
                if (hasJumpStatement(expression) ||
                    !NoNestedIfElseRule.hasMultipleLines(expression)) {
                    return;
                }
                context.report({ node: ifStatement, messageId: NoNestedIfElseRule.MSG_INVERT });
            },
        };
    }

    private static MSG_INVERT: string = 'no.nested.if.else.invert';
    private static MSG_LIFT: string = 'no.nested.if.else.lift';

    private static hasMultipleLines(node: Statement): boolean {
        if (node.type !== 'BlockStatement') {
            return isMultiline(node);
        }
        const statements: Statement[] = node.body;
        if (!statements.length) {
            return false;
        }
        return statements.length === 1
            ? isMultiline(statements[0])
            : true;
    }
}

export default new NoNestedIfElseRule();
