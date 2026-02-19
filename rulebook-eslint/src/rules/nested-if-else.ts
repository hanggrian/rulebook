import { Rule } from 'eslint';
import { BlockStatement, Node, Statement } from 'estree';
import messages from '../messages.js';
import { hasJumpStatement, isMultiline } from '../nodes.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#nested-if-else|See detail} */
class NestedIfElseRule extends RulebookRule {
    constructor() {
        super('nested-if-else', {
            [NestedIfElseRule.MSG_INVERT]: messages.get(NestedIfElseRule.MSG_INVERT),
            [NestedIfElseRule.MSG_LIFT]: messages.get(NestedIfElseRule.MSG_LIFT),
        });
    }

    create(context: Rule.RuleContext) {
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
                if (node.body.length == 0) {
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
                    if (!NestedIfElseRule.hasMultipleLines(elseStatement)) {
                        return;
                    }
                    context.report({
                        node: elseStatement,
                        messageId: NestedIfElseRule.MSG_LIFT,
                    });
                }
                const expression: Statement = ifStatement.consequent;
                if (hasJumpStatement(expression) ||
                    !NestedIfElseRule.hasMultipleLines(expression)) {
                    return;
                }
                context.report({
                    node: ifStatement,
                    messageId: NestedIfElseRule.MSG_INVERT,
                });
            },
        };
    }

    static MSG_INVERT: string = 'nested.if.else.invert';
    static MSG_LIFT: string = 'nested.if.else.lift';

    static hasMultipleLines(node: Statement): boolean {
        if (node.type !== 'BlockStatement') {
            return isMultiline(node);
        }
        const statements: Statement[] = node.body;
        if (statements.length === 0) {
            return false;
        }
        return statements.length === 1
            ? isMultiline(statements[0])
            : true;
    }
}

export default new NestedIfElseRule();
