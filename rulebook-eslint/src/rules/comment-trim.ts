import { Rule, SourceCode } from 'eslint';
import { Comment, Node, SourceLocation } from 'estree';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#comment-trim|See detail} */
class CommentTrimRule extends RulebookRule {
    constructor() {
        super('comment-trim', {
            [CommentTrimRule.MSG]: messages.get(CommentTrimRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
        return {
            'Program:exit'() {
                const sourceCode: SourceCode = context.sourceCode;
                const comments: Comment[] =
                    sourceCode
                        .getAllComments()
                        .filter(c => c.type === 'Line');
                const reported: Set<number> = new Set();
                for (let i = 0; i < comments.length; i++) {
                    const node: Comment = comments[i];
                    if (!node.loc || reported.has(node.loc.start.line)) {
                        continue;
                    }

                    // find the bounds of the comment block
                    let current: Comment = node;
                    const block: Comment[] = [node];
                    while (i + 1 < comments.length) {
                        const next: Comment = comments[i + 1];
                        const next_loc: SourceLocation | null | undefined = next.loc;
                        if (!next_loc || !next.range || !current.loc) {
                            continue;
                        }
                        const isStandalone =
                            sourceCode.getText(next as unknown as Node).trim() ===
                            sourceCode.text.slice(next.range[0], next.range[1]).trim() &&
                            sourceCode
                                .getTokensBefore(next)
                                .every(t => t.loc.end.line !== next_loc.start.line);
                        if (next_loc.start.line === current.loc.end.line + 1 && isStandalone) {
                            block.push(next);
                            current = next;
                            i++;
                        } else {
                            break;
                        }
                    }

                    // skip single-line comments
                    if (block.length <= 1) {
                        continue;
                    }

                    // checks for violation
                    const first: Comment = block[0];
                    const last: Comment = block[block.length - 1];
                    if (first.loc && first.value.trim() === '') {
                        context.report({
                            node: first,
                            messageId: CommentTrimRule.MSG,
                        });
                        reported.add(first.loc.start.line);
                    }
                    if (!(last.loc &&
                        last.value.trim() === '' && !reported.has(last.loc.start.line))) {
                        continue;
                    }
                    context.report({
                        node: last,
                        messageId: CommentTrimRule.MSG,
                    });
                    reported.add(last.loc.start.line);
                }
            },
        };
    }

    static MSG: string = 'comment.trim';
}

export default new CommentTrimRule();
