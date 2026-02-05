import { Rule } from 'eslint';
import { ImportDeclaration } from 'estree';
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
                const hasWildcard: boolean =
                    node.specifiers.some(
                        specifier => specifier.type === 'ImportNamespaceSpecifier',
                    );
                if (!hasWildcard) {
                    return;
                }
                context.report({
                    node: node,
                    messageId: WildcardImportRule.MSG,
                });
            },
        };
    }

    static MSG: string = 'wildcard.import';
}

export default new WildcardImportRule();
