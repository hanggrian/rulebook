import { Rule } from 'eslint';
import { Literal } from 'estree';
import messages from '../messages.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#lowercase-hex|See detail} */
class LowercaseHexRule extends RulebookRule {
    constructor() {
        super('lowercase-hex', {
            [LowercaseHexRule.MSG]: messages.get(LowercaseHexRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
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

    static MSG: string = 'lowercase.hex';
}

export default new LowercaseHexRule();
