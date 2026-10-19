import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type {
    DoWhileStatement,
    ForInStatement,
    ForOfStatement,
    ForStatement,
    Statement,
    WhileStatement,
} from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#no-useless-continue|See detail} */
class NoUselessContinueRule extends RulebookRule {
    constructor() {
        super('no-useless-continue', {
            [NoUselessContinueRule.MSG]: messages.get(NoUselessContinueRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        function process(node: Statement) {
            let last: Statement | null = null;
            if (node.type !== 'BlockStatement') {
                last = node;
            } else {
                const body: Statement[] = node.body;
                if (body.length) {
                    last = body[body.length - 1];
                }
            }
            if (!last || last.type !== 'ContinueStatement') {
                return;
            }
            context.report({ node: last, messageId: NoUselessContinueRule.MSG });
        }

        return {
            ForStatement(node: ForStatement) {
                process(node.body);
            },
            ForInStatement(node: ForInStatement) {
                process(node.body);
            },
            ForOfStatement(node: ForOfStatement) {
                process(node.body);
            },
            WhileStatement(node: WhileStatement) {
                process(node.body);
            },
            DoWhileStatement(node: DoWhileStatement) {
                process(node.body);
            },
        };
    }

    private static MSG: string = 'no.useless.continue';
}

export default new NoUselessContinueRule();
