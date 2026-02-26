import { AST_NODE_TYPES, TSESLint, TSESTree } from '@typescript-eslint/utils';
import messages from '../messages.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#generic-name|See detail} */
class GenericNameRule extends RulebookRule {
    constructor() {
        super('generic-name', {
            [GenericNameRule.MSG]: messages.get(GenericNameRule.MSG),
        });
    }

    create(context: TSESLint.RuleContext<string, readonly unknown[]>) {
        return {
            TSTypeParameter(node: TSESTree.TSTypeParameter) {
                // filter out multiple generics
                const parent: TSESTree.TSInferType |
                    TSESTree.TSMappedType |
                    TSESTree.TSTypeParameterDeclaration =
                        node.parent;
                if (parent.type !== AST_NODE_TYPES.TSTypeParameterDeclaration ||
                    parent.params.length > 1) {
                    return;
                }

                // skip inner generics
                let current: TSESTree.Node | undefined = parent.parent;
                while (current) {
                    if (current.type === AST_NODE_TYPES.ClassBody ||
                        current.type === AST_NODE_TYPES.BlockStatement) {
                        return;
                    }
                    current = current.parent;
                }

                // checks for violation
                if (/^[A-Z]$/.test(node.name.name)) {
                    return;
                }
                context.report({
                    node,
                    messageId: GenericNameRule.MSG,
                });
            },
        };
    }

    static MSG: string = 'generic.name';
}

export default new GenericNameRule();
