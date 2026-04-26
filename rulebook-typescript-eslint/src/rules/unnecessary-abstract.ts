import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { TSESLint, TSESTree } from '@typescript-eslint/utils';

/** {@link https://hanggrian.github.io/rulebook/rules/#unnecessary-abstract|See detail} */
class UnnecessaryAbstractRule extends RulebookRule {
    constructor() {
        super('unnecessary-abstract', {
            [UnnecessaryAbstractRule.MSG]: messages.get(UnnecessaryAbstractRule.MSG),
        });
    }

    override create(
        context: TSESLint.RuleContext<string, readonly unknown[]>,
    ): TSESLint.RuleListener {
        return {
            ClassDeclaration(node: TSESTree.ClassDeclaration) {
                // skip non-abstract class
                if (!node.abstract || node.superClass || node.implements.length) {
                    return;
                }

                // checks for violation
                const hasAbstractMember: boolean =
                    node.body.body.some(
                        member => {
                            const type = member.type;
                            return (
                                ['MethodDefinition', 'PropertyDefinition']
                                    .includes(type) &&
                                ('abstract' in member && member.abstract)
                            ) ||
                            ['TSAbstractMethodDefinition', 'TSAbstractPropertyDefinition']
                                .includes(type);
                        },
                    );
                if (hasAbstractMember) {
                    return;
                }
                context.report({ node: node, messageId: UnnecessaryAbstractRule.MSG });
            },
        };
    }

    private static MSG: string = 'unnecessary.abstract';
}

export default new UnnecessaryAbstractRule();
