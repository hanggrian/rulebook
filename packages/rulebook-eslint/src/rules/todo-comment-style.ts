import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type { SourceLocation } from 'estree';

/* eslint-disable @rulebook/todo-comment-style */
/** {@link https://hanggrian.github.io/rulebook/rules/#todo-comment-style|See detail} */
class TodoCommentStyleRule extends RulebookRule {
    constructor() {
        super('todo-comment-style', {
            [TodoCommentStyleRule.MSG_KEYWORD]: messages.get(TodoCommentStyleRule.MSG_KEYWORD),
            [TodoCommentStyleRule.MSG_SEPARATOR]: messages.get(TodoCommentStyleRule.MSG_SEPARATOR),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Program() {
                context.sourceCode.getAllComments().forEach(comment => {
                    for (const match of
                        comment.value.matchAll(TodoCommentStyleRule.KEYWORD_REGEX)) {
                        const loc: SourceLocation | null | undefined = comment.loc;
                        if (!loc) {
                            continue;
                        }
                        context.report({
                            loc: loc,
                            messageId: TodoCommentStyleRule.MSG_KEYWORD,
                            data: {
                                $0: match[0],
                            },
                        });
                    }
                    for (const match of
                        comment.value.matchAll(TodoCommentStyleRule.SEPARATOR_REGEX)) {
                        const loc: SourceLocation | null | undefined = comment.loc;
                        if (!loc) {
                            continue;
                        }
                        context.report({
                            loc: loc,
                            messageId: TodoCommentStyleRule.MSG_SEPARATOR,
                            data: {
                                $0: match[0].slice(-1),
                            },
                        });
                    }
                });
            },
        };
    }

    private static MSG_KEYWORD: string = 'todo.comment.style.keyword';
    private static MSG_SEPARATOR: string = 'todo.comment.style.separator';

    private static KEYWORD_REGEX: RegExp =
        new RegExp('\\b(?:[Ff][Ii][Xx][Mm][Ee]|[Tt][Oo][Dd][Oo])\\b(?<!FIXME|TODO)\\b', 'g');
    private static SEPARATOR_REGEX: RegExp = new RegExp('\\b(todo|fixme)\\S', 'gi');
}

export default new TodoCommentStyleRule();
