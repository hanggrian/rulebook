import { Rule } from 'eslint';
import { DoWhileStatement, ForInStatement, ForOfStatement, ForStatement, Statement, WhileStatement } from 'estree';
import messages from '../messages.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#unnecessary-continue|See detail} */
class UnnecessaryContinueRule extends RulebookRule {
    constructor() {
        super('unnecessary-continue', {
            [UnnecessaryContinueRule.MSG]: messages.get(UnnecessaryContinueRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
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
            context.report({ node: last, messageId: UnnecessaryContinueRule.MSG });
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

    static MSG: string = 'unnecessary.continue';
}

export default new UnnecessaryContinueRule();
