import { Rule } from 'eslint';
import { SwitchCase, SwitchStatement } from 'estree';
import messages from '../messages.js';
import { hasJumpStatement } from '../nodes.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#redundant-default|See detail} */
class RedundantDefaultRule extends RulebookRule {
    constructor() {
        super('redundant-default', {
            [RedundantDefaultRule.MSG]: messages.get(RedundantDefaultRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
        return {
            SwitchStatement(node: SwitchStatement) {
                // find default
                const cases: SwitchCase[] = node.cases;
                const lastCase = cases[cases.length - 1];
                const test = lastCase.test;
                if (test !== null && test !== undefined) {
                    return;
                }

                // checks for violation
                if (!cases.slice(0, -1).every(c => c.consequent.some(hasJumpStatement))) {
                    return;
                }
                context.report({
                    node: lastCase,
                    messageId: RedundantDefaultRule.MSG,
                });
            },
        };
    }

    static MSG: string = 'redundant.default';
}

export default new RedundantDefaultRule();
