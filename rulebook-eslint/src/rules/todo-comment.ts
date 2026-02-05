import { Rule } from 'eslint';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#todo-comment|See detail} */
class TodoCommentRule extends RulebookRule {
    constructor() {
        super('todo-comment', {
            [TodoCommentRule.MSG_KEYWORD]: messages.get(TodoCommentRule.MSG_KEYWORD),
            [TodoCommentRule.MSG_SEPARATOR]: messages.get(TodoCommentRule.MSG_SEPARATOR),
        });
    }

    create(context: Rule.RuleContext) {
        return {
            Program() {
                const sourceCode = context.sourceCode;
                sourceCode.getAllComments().forEach(comment => {
                    for (const match of comment.value.matchAll(TodoCommentRule.KEYWORD_REGEX)) {
                        context.report({
                            loc: comment.loc!,
                            messageId: TodoCommentRule.MSG_KEYWORD,
                            data: {
                                $0: match[0],
                            },
                        });
                    }
                    for (const match of comment.value.matchAll(TodoCommentRule.SEPARATOR_REGEX)) {
                        context.report({
                            loc: comment.loc!,
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

    static MSG_KEYWORD: string = 'todo.comment.keyword';
    static MSG_SEPARATOR: string = 'todo.comment.separator';

    static KEYWORD_REGEX =
        new RegExp('\\b(?:[Ff][Ii][Xx][Mm][Ee]|[Tt][Oo][Dd][Oo])\\b(?<!FIXME|TODO)\\b', 'g');
    static SEPARATOR_REGEX = new RegExp('\\b(todo|fixme)\\S', 'gi');
}

export default new TodoCommentRule();
