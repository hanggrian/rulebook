import { TSESLint, TSESTree } from '@typescript-eslint/utils';
import messages from '../messages.js';
import createRule from '../rules.js';

const MSG: string = 'abstract.class.definition';

/** {@link https://hanggrian.github.io/rulebook/rules/#abstract-class-definition|See detail} */
export default createRule({
    name: 'abstract-class-definition',
    defaultOptions: [],
    meta: {
        type: 'suggestion',
        docs: {
            description:
                'Enforce that abstract classes must contain at least one abstract method.',
        },
        schema: [],
        messages: {
            [MSG]: messages.get(MSG),
        },
    },
    create(context: TSESLint.RuleContext<string, any>) {
        return {
            ClassDeclaration(node: TSESTree.ClassDeclaration) {
                if (!node.abstract || node.superClass) {
                    return;
                }
                const hasAbstractMember: boolean =
                    node.body.body.some(
                        member =>
                            (
                                ['MethodDefinition', 'PropertyDefinition']
                                    .includes(member.type) &&
                                ('abstract' in member && member.abstract)
                            ) ||
                            ['TSAbstractMethodDefinition', 'TSAbstractPropertyDefinition']
                                .includes(member.type),
                    );
                if (hasAbstractMember) {
                    return;
                }
                context.report({
                    node: node,
                    messageId: MSG,
                });
            },
        };
    },
}) as TSESLint.RuleModule<string, any, any, TSESLint.RuleListener> & { name: string };
