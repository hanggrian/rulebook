import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type { Literal } from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#lowercase-hex|See detail} */
class LowercaseHexRule extends RulebookRule {
    constructor() {
        super('lowercase-hex', {
            [LowercaseHexRule.MSG]: messages.get(LowercaseHexRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Literal(node: Literal) {
                // checks for violation
                if (typeof node.value !== 'number') {
                    return;
                }
                const value = node.raw;
                if (value === undefined || !value.toLowerCase().startsWith('0x')) {
                    return;
                }
                const valueReplacement = value.toLowerCase();
                if (value === valueReplacement) {
                    return;
                }
                context.report({
                    node,
                    messageId: LowercaseHexRule.MSG,
                    data: {
                        $0: valueReplacement,
                    },
                });
            },
        };
    }

    private static MSG: string = 'lowercase.hex';
}

export default new LowercaseHexRule();
