import { Rule } from 'eslint';
import { ImportDeclaration } from 'estree';
import messages from '../messages.js';

const MSG: string = 'wildcard-import';

/** {@link https://hanggrian.github.io/rulebook/rules/#wildcard-import|See detail} */
const rule: Rule.RuleModule = {
    meta: {
        type: 'problem',
        docs: {
            description: "Disallow wildcard imports (import * as foo from 'bar').",
        },
        schema: [],
        messages: {
            [MSG]: messages.get(MSG),
        },
    },
    create(context: Rule.RuleContext) {
        return {
            ImportDeclaration(node: ImportDeclaration) {
                const hasWildcard: boolean =
                    node.specifiers.some(specifier =>
                        specifier.type === 'ImportNamespaceSpecifier',
                    );
                if (!hasWildcard) {
                    return;
                }
                context.report({
                    node: node,
                    messageId: MSG,
                });
            },
        };
    },
};

export { rule as default, MSG };
