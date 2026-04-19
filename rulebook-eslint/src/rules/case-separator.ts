import messages from '../messages.js';
import { isMultiline } from '../nodes.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule } from 'eslint';
import type { Comment, SwitchCase, SwitchStatement } from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#case-separator|See detail} */
class CaseSeparatorRule extends RulebookRule {
    constructor() {
        super('case-separator', {
            [CaseSeparatorRule.MSG_MISSING]: messages.get(CaseSeparatorRule.MSG_MISSING),
            [CaseSeparatorRule.MSG_UNEXPECTED]: messages.get(CaseSeparatorRule.MSG_UNEXPECTED),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        const sourceCode = context.sourceCode;
        return {
            SwitchStatement(node: SwitchStatement) {
                // collect cases
                const cases: SwitchCase[] = node.cases;
                if (!cases.length) {
                    return;
                }

                // checks for violation
                const hasMultiline: boolean =
                    cases.some(c => {
                        if (isMultiline(c)) {
                            return true;
                        }
                        return sourceCode.getCommentsBefore(c).length;
                    });
                for (let i = 1; i < cases.length; i++) {
                    const prevCase: SwitchCase = cases[i - 1];
                    const currentCase: SwitchCase = cases[i];
                    const prevLastToken: AST.Token | null = sourceCode.getLastToken(prevCase);
                    const currentFirstToken: AST.Token | null =
                        sourceCode.getFirstToken(currentCase);
                    if (!prevLastToken || !currentFirstToken) {
                        continue;
                    }
                    const commentsBetween: Comment[] = sourceCode.getCommentsBefore(currentCase);
                    const actualStartLine: number =
                        commentsBetween.length
                            ? commentsBetween[0].loc!.start.line
                            : currentFirstToken.loc.start.line;
                    const lineDiff: number = actualStartLine - prevLastToken.loc.end.line;
                    if (hasMultiline) {
                        if (lineDiff !== 2) {
                            context.report({
                                node: prevLastToken,
                                messageId: CaseSeparatorRule.MSG_MISSING,
                            });
                        }
                    } else if (lineDiff !== 1) {
                        context.report({
                            node: prevLastToken,
                            messageId: CaseSeparatorRule.MSG_UNEXPECTED,
                        });
                    }
                }
            },
        };
    }

    static MSG_MISSING: string = 'case.separator.missing';
    static MSG_UNEXPECTED: string = 'case.separator.unexpected';
}

export default new CaseSeparatorRule();
