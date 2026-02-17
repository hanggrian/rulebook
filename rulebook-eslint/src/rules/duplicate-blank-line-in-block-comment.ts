import { Rule } from 'eslint';
import { Comment } from 'estree';
import messages from '../messages.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-block-comment|See detail} */
class DuplicateBlankLineInBlockCommentRule extends RulebookRule {
    constructor() {
        super('duplicate-blank-line-in-block-comment', {
            [DuplicateBlankLineInBlockCommentRule.MSG]:
                messages.get(DuplicateBlankLineInBlockCommentRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
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
                        context.report({
                            node: comment,
                            loc: {
                                start: {
                                    line: comment.loc!.start.line + i + 1,
                                    column: 0,
                                },
                                end: {
                                    line: comment.loc!.start.line + i + 1,
                                    column: lines[i + 1].length,
                                },
                            },
                            messageId: DuplicateBlankLineInBlockCommentRule.MSG,
                        });
                    }
                }
            },
        };
    }

    static MSG: string = 'duplicate.blank.line.in.block.comment';
}

export default new DuplicateBlankLineInBlockCommentRule();
