import { Rule } from 'eslint';
import { Literal } from 'estree';
import messages from '../messages.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#lowercase-hexadecimal|See detail} */
class LowercaseHexadecimalRule extends RulebookRule {
    constructor() {
        super('lowercase-hexadecimal', {
            [LowercaseHexadecimalRule.MSG]: messages.get(LowercaseHexadecimalRule.MSG),
        });
    }

    override create(context: Rule.RuleContext) {
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
                    messageId: LowercaseHexadecimalRule.MSG,
                    data: {
                        $0: valueReplacement,
                    },
                });
            },
        };
    }

    static MSG: string = 'lowercase.hexadecimal';
}

export default new LowercaseHexadecimalRule();
