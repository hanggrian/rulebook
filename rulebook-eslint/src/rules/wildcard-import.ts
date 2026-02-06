import { Rule } from 'eslint';
import { ImportDeclaration, ImportNamespaceSpecifier } from 'estree';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#wildcard-import|See detail} */
class WildcardImportRule extends RulebookRule {
    constructor() {
        super('wildcard-import', {
            [WildcardImportRule.MSG]: messages.get(WildcardImportRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
        return {
            ImportDeclaration(node: ImportDeclaration) {
                // checks for violation
                const wildcard: ImportNamespaceSpecifier | undefined =
                    node.specifiers.find(
                        specifier => specifier.type === 'ImportNamespaceSpecifier',
                    );
                if (!wildcard) {
                    return;
                }
                context.report({
                    node: wildcard,
                    messageId: WildcardImportRule.MSG,
                });
            },
        };
    }

    static MSG: string = 'wildcard.import';
}

export default new WildcardImportRule();
