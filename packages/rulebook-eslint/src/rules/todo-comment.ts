import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { Rule } from 'eslint';
import type { SourceLocation } from 'estree';

/* eslint-disable @rulebook/todo-comment */
/** {@link https://hanggrian.github.io/rulebook/rules/#todo-comment|See detail} */
class TodoCommentRule extends RulebookRule {
    constructor() {
        super('todo-comment', {
            [TodoCommentRule.MSG_KEYWORD]: messages.get(TodoCommentRule.MSG_KEYWORD),
            [TodoCommentRule.MSG_SEPARATOR]: messages.get(TodoCommentRule.MSG_SEPARATOR),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Program() {
                context.sourceCode.getAllComments().forEach(comment => {
                    for (const match of comment.value.matchAll(TodoCommentRule.KEYWORD_REGEX)) {
                        const loc: SourceLocation | null | undefined = comment.loc;
                        if (!loc) {
                            continue;
                        }
                        context.report({
                            loc: loc,
                            messageId: TodoCommentRule.MSG_KEYWORD,
                            data: {
                                $0: match[0],
                            },
                        });
                    }
                    for (const match of comment.value.matchAll(TodoCommentRule.SEPARATOR_REGEX)) {
                        const loc: SourceLocation | null | undefined = comment.loc;
                        if (!loc) {
                            continue;
                        }
                        context.report({
                            loc: loc,
                            messageId: TodoCommentRule.MSG_SEPARATOR,
                            data: {
                                $0: match[0].slice(-1),
                            },
                        });
                    }
                });
            },
        };
    }

    private static MSG_KEYWORD: string = 'todo.comment.keyword';
    private static MSG_SEPARATOR: string = 'todo.comment.separator';

    private static KEYWORD_REGEX: RegExp =
        new RegExp('\\b(?:[Ff][Ii][Xx][Mm][Ee]|[Tt][Oo][Dd][Oo])\\b(?<!FIXME|TODO)\\b', 'g');
    private static SEPARATOR_REGEX: RegExp = new RegExp('\\b(todo|fixme)\\S', 'gi');
}

export default new TodoCommentRule();
