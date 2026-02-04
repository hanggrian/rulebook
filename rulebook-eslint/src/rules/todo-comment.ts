import { Rule } from 'eslint';
import messages from '../messages.js';

const MSG_KEYWORD: string = 'todo.comment.keyword';
const MSG_SEPARATOR: string = 'todo.comment.separator';

const KEYWORD_REGEX =
    new RegExp('\\b(?:[Ff][Ii][Xx][Mm][Ee]|[Tt][Oo][Dd][Oo])\\b(?<!FIXME|TODO)\\b', 'g');
const SEPARATOR_REGEX = new RegExp('\\b(todo|fixme)\\S', 'gi');

/** {@link https://hanggrian.github.io/rulebook/rules/#todo-comment|See detail} */
export default {
    meta: {
        type: 'problem',
        docs: {
            description: 'Detect TODO comments.',
        },
        schema: [],
        messages: {
            [MSG_KEYWORD]: messages.get(MSG_KEYWORD),
            [MSG_SEPARATOR]: messages.get(MSG_SEPARATOR),
        },
    },
    create(context: Rule.RuleContext) {
        return {
            Program() {
                const sourceCode = context.sourceCode;
                sourceCode.getAllComments().forEach(comment => {
                    for (const match of comment.value.matchAll(KEYWORD_REGEX)) {
                        context.report({
                            loc: comment.loc!,
                            messageId: MSG_KEYWORD,
                            data: {
                                $0: match[0],
                            },
                        });
                    }
                    for (const match of comment.value.matchAll(SEPARATOR_REGEX)) {
                        context.report({
                            loc: comment.loc!,
                            messageId: MSG_SEPARATOR,
                            data: {
                                $0: match[0].slice(-1),
                            },
                        });
                    }
                });
            },
        };
    },
} as Rule.RuleModule;
