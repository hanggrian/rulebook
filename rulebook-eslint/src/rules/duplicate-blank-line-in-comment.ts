import { Rule } from 'eslint';
import { Comment, SourceLocation } from 'estree';
import messages from '../messages.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-comment|See detail} */
class DuplicateBlankLineInCommentRule extends RulebookRule {
    constructor() {
        super('duplicate-blank-line-in-comment', {
            [DuplicateBlankLineInCommentRule.MSG]:
                messages.get(DuplicateBlankLineInCommentRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
        return {
            'Program:exit'() {
                // checks for violation
                const comments: Comment[] =
                    context.sourceCode.getAllComments().filter(c => c.type === 'Line');
                for (let i = 0; i < comments.length - 1; i++) {
                    const current: Comment = comments[i];
                    const next: Comment = comments[i + 1];
                    const current_loc: SourceLocation | null | undefined = current.loc;
                    const next_loc: SourceLocation | null | undefined = next.loc;
                    if (!current_loc ||
                        !next_loc ||
                        current.value.trim() !== '' ||
                        next.value.trim() !== '') {
                        continue;
                    }
                    if (next_loc.start.line !== current_loc.end.line + 1) {
                        continue;
                    }
                    context.report({
                        node: next,
                        messageId: DuplicateBlankLineInCommentRule.MSG,
                    });
                }
            },
        };
    }

    static MSG: string = 'duplicate.blank.line.in.comment';
}

export default new DuplicateBlankLineInCommentRule();
