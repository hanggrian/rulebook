import { BlockStatement, ClassBody } from 'estree';
import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule } from 'eslint';

/** {@link https://hanggrian.github.io/rulebook/rules/#braces-clip|See detail} */
class BracesClipRule extends RulebookRule {
    constructor() {
        super('braces-clip', {
            [BracesClipRule.MSG]: messages.get(BracesClipRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        const sourceCode = context.sourceCode;

        function process(node: BlockStatement | ClassBody): void {
            // only target empty blocks
            if (node.body.length) {
                return;
            }

            // obtain corresponding braces
            const openBrace: AST.Token = sourceCode.getFirstToken(node)!;
            const closeBrace: AST.Token = sourceCode.getLastToken(node)!;

            // checks for violation
            if (sourceCode.getCommentsInside(node).length) {
                return;
            }
            if (!sourceCode
                .getText()
                .slice(openBrace.range[1], closeBrace.range[0])
                .length) {
                return;
            }
            context.report({
                node: node,
                messageId: BracesClipRule.MSG,
            });
        }

        return {
            BlockStatement(node: BlockStatement) {
                // skip control flows that can have multi-blocks
                const parent = (node as unknown as Rule.NodeParentExtension).parent;
                if (!parent) {
                    return;
                }
                if ((parent.type === 'TryStatement' &&
                        parent.block === node) ||
                    parent.type === 'CatchClause' ||
                    (parent.type === 'IfStatement' &&
                        (parent.consequent === node ||
                            parent.alternate === node)) ||
                    (parent.type === 'DoWhileStatement' &&
                        parent.body === node)) {
                    return;
                }

                process(node);
            },
            ClassBody(node: ClassBody) {
                process(node);
            },
        };
    }

    private static MSG: string = 'braces.clip';
}

export default new BracesClipRule();
