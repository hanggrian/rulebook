import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type { Comment, SourceLocation } from 'estree';

/**
 * {@link https://hanggrian.github.io/rulebook/rules/#no-duplicate-blank-line-in-block-comment|See detail}
 */
class NoDuplicateBlankLineInBlockCommentRule extends RulebookRule {
    constructor() {
        super('no-duplicate-blank-line-in-block-comment', {
            [NoDuplicateBlankLineInBlockCommentRule.MSG]:
                messages.get(NoDuplicateBlankLineInBlockCommentRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            'Program:exit'() {
                const comments: Comment[] =
                    context.sourceCode.getAllComments().filter(c => c.type === 'Block');
                // checks for violation
                for (const comment of comments) {
                    const lines: string[] = comment.value.split('\n');
                    for (let i = 0; i < lines.length - 1; i++) {
                        const current: string = lines[i].trim();
                        const next: string = lines[i + 1].trim();
                        if (current !== '*' || next !== '*') {
                            continue;
                        }
                        const loc: SourceLocation | null | undefined = comment.loc;
                        if (!loc) {
                            continue;
                        }
                        context.report({
                            node: comment,
                            loc: {
                                start: {
                                    line: loc.start.line + i + 1,
                                    column: 0,
                                },
                                end: {
                                    line: loc.start.line + i + 1,
                                    column: lines[i + 1].length,
                                },
                            },
                            messageId: NoDuplicateBlankLineInBlockCommentRule.MSG,
                        });
                    }
                }
            },
        };
    }

    private static MSG: string = 'no.duplicate.blank.line.in.block.comment';
}

export default new NoDuplicateBlankLineInBlockCommentRule();
