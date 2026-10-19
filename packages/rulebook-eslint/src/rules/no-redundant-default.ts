import messages from '../messages.js';
import { hasJumpStatement } from '../nodes.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type { SwitchCase, SwitchStatement } from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#no-redundant-default|See detail} */
class NoRedundantDefaultRule extends RulebookRule {
    constructor() {
        super('no-redundant-default', {
            [NoRedundantDefaultRule.MSG]: messages.get(NoRedundantDefaultRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
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
                context.report({ node: lastCase, messageId: NoRedundantDefaultRule.MSG });
            },
        };
    }

    private static MSG: string = 'no.redundant.default';
}

export default new NoRedundantDefaultRule();
