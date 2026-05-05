import { type TSESLint, type TSESTree } from '@typescript-eslint/utils';
import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#generic-name|See detail} */
class GenericNameRule extends RulebookRule {
    constructor() {
        super('generic-name', {
            [GenericNameRule.MSG]: messages.get(GenericNameRule.MSG),
        });
    }

    override create(
        context: TSESLint.RuleContext<string, readonly unknown[]>,
    ): TSESLint.RuleListener {
        return {
            TSTypeParameter(node: TSESTree.TSTypeParameter) {
                // checks for violation
                if (GenericNameRule.PASCAL_CASE_REGEX.test(node.name.name)) {
                    return;
                }
                context.report({ node, messageId: GenericNameRule.MSG });
            },
        };
    }

    private static MSG: string = 'generic.name';

    private static PASCAL_CASE_REGEX = /^[A-Z]([a-z][a-zA-Z0-9]*)?$/;
}

export default new GenericNameRule();
