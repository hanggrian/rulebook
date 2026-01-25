import { AST, Rule } from 'eslint';
import { Expression, VariableDeclarator } from 'estree';
import messages from '../messages.js';

const MSG: string = 'assignment-wrap';

/** {@link https://hanggrian.github.io/rulebook/rules/#assignment-wrap|See detail} */
const rule: Rule.RuleModule = {
    meta: {
        type: 'problem',
        docs: {
            description: 'Enforce a newline before multiline variable initializers.',
            category: 'Wrapping',
        },
        schema: [],
        messages: {
            [MSG]: messages.get(MSG),
        },
    },
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
                    messageId: MSG,
                });
            },
        };
    },
};

export { rule as default, MSG };
