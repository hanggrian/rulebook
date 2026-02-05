import { TSESLint, TSESTree } from '@typescript-eslint/utils';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#abstract-class-definition|See detail} */
class AbstractClassDefinitionRule extends RulebookRule {
    constructor() {
        super('abstract-class-definition', {
            [AbstractClassDefinitionRule.MSG]: messages.get(AbstractClassDefinitionRule.MSG),
        });
    }

    create(context: TSESLint.RuleContext<string, any>) {
        return {
            ClassDeclaration(node: TSESTree.ClassDeclaration) {
                if (!node.abstract || node.superClass || node.implements.length > 0) {
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
                    messageId: AbstractClassDefinitionRule.MSG,
                });
            },
        };
    }

    static MSG: string = 'abstract.class.definition';
}

export default new AbstractClassDefinitionRule();
