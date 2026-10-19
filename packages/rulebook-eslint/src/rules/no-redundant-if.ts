import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule, SourceCode } from 'eslint';
import type { Expression, IfStatement, Statement } from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#no-redundant-if|See detail} */
class NoRedundantIfRule extends RulebookRule {
    constructor() {
        super('no-redundant-if', {
            [NoRedundantIfRule.MSG]: messages.get(NoRedundantIfRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        function isThenConstant(statement: Statement): boolean {
            if (statement.type === 'BlockStatement') {
                const body: Statement[] = statement.body.filter(s => s.type !== 'EmptyStatement');
                return body.length === 1 && isStatementConstant(body[0]);
            }
            return isStatementConstant(statement);
        }

        function isStatementConstant(statement: Statement | null): boolean {
            if (!statement) {
                return false;
            }
            if (statement.type === 'ReturnStatement') {
                const arg: Expression | null | undefined = statement.argument;
                return !!arg &&
                    arg.type === 'Literal' &&
                    (arg.value === true || arg.value === false);
            }
            if (statement.type === 'ExpressionStatement') {
                const expr: Expression = statement.expression;
                return expr.type === 'Literal' &&
                    (expr.value === true || expr.value === false);
            }
            return false;
        }

        return {
            IfStatement(node: IfStatement) {
                // checks for violation
                if (!isThenConstant(node.consequent)) {
                    return;
                }
                if (node.alternate) {
                    if (node.alternate.type !== 'IfStatement' && isThenConstant(node.alternate)) {
                        context.report({
                            node: node,
                            messageId: NoRedundantIfRule.MSG,
                        });
                    }
                    return;
                }
                const sourceCode: SourceCode = context.sourceCode;
                const nextToken: AST.Token | null = sourceCode.getTokenAfter(node);
                if (!nextToken ||
                    nextToken.type !== 'Keyword' ||
                    nextToken.value !== 'return'
                ) {
                    return;
                }
                const nextNode: Statement =
                    sourceCode.getNodeByRangeIndex(nextToken.range[0]) as Statement;
                if (!isStatementConstant(nextNode)) {
                    return;
                }
                context.report({ node: node, messageId: NoRedundantIfRule.MSG });
            },
        };
    }

    private static MSG: string = 'no.redundant.if';
}

export default new NoRedundantIfRule();
