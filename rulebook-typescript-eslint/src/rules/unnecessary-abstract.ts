import { TSESLint, TSESTree } from '@typescript-eslint/utils';
import messages from '../messages.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#unnecessary-abstract|See detail} */
class UnnecessaryAbstractRule extends RulebookRule {
    constructor() {
        super('unnecessary-abstract', {
            [UnnecessaryAbstractRule.MSG]: messages.get(UnnecessaryAbstractRule.MSG),
        });
    }

    create(context: TSESLint.RuleContext<string, any>) {
        return {
            ClassDeclaration(node: TSESTree.ClassDeclaration) {
                // skip non-abstract class
                if (!node.abstract || node.superClass || node.implements.length > 0) {
                    return;
                }

                // checks for violation
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
                    messageId: UnnecessaryAbstractRule.MSG,
                });
            },
        };
    }

    static MSG: string = 'unnecessary.abstract';
}

export default new UnnecessaryAbstractRule();
