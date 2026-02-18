import { AST, Rule, SourceCode } from 'eslint';
import { Comment, SourceLocation } from 'estree';
import messages from '../messages.js';
import RulebookRule from './rulebook-rules.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#parentheses-trim|See detail} */
class ParenthesesTrimRule extends RulebookRule {
    constructor() {
        super('parentheses-trim', {
            [ParenthesesTrimRule.MSG_FIRST]: messages.get(ParenthesesTrimRule.MSG_FIRST),
            [ParenthesesTrimRule.MSG_LAST]: messages.get(ParenthesesTrimRule.MSG_LAST),
        });
    }

    create(context: Rule.RuleContext) {
        return {
            'Program:exit'() {
                const sourceCode: SourceCode = context.sourceCode;
                const tokens: AST.Token[] = sourceCode.getTokens(sourceCode.ast);
                for (let i = 0; i < tokens.length; i++) {
                    // find opening and closing parentheses
                    const openToken: AST.Token = tokens[i];
                    const pair: [string, string] | undefined =
                        ParenthesesTrimRule.PAIRS.find(p => p[0] === openToken.value);
                    if (!pair) {
                        continue;
                    }
                    let count: number = 0;
                    let closeTokenIndex: number = -1;
                    for (let j = i; j < tokens.length; j++) {
                        if (tokens[j].value === pair[0]) {
                            count++;
                        } else if (tokens[j].value === pair[1]) {
                            count--;
                        }
                        if (count === 0) {
                            closeTokenIndex = j;
                            break;
                        }
                    }

                    // checks for violation
                    if (closeTokenIndex === -1) {
                        continue;
                    }
                    const closeToken: AST.Token = tokens[closeTokenIndex];
                    if (openToken.loc.end.line === closeToken.loc.start.line) {
                        continue;
                    }
                    const firstContentToken: AST.Token | Comment | null =
                        sourceCode.getTokenAfter(openToken, { includeComments: true });
                    const lastContentToken: AST.Token | Comment | null =
                        sourceCode.getTokenBefore(closeToken, { includeComments: true });
                    if (firstContentToken) {
                        const firstContentTokenLoc: SourceLocation | null | undefined =
                            firstContentToken.loc;
                        if (firstContentTokenLoc &&
                            firstContentTokenLoc.start.line > openToken.loc.end.line + 1) {
                            context.report({
                                node: openToken,
                                messageId: ParenthesesTrimRule.MSG_FIRST,
                                data: {
                                    $0: openToken.value,
                                },
                            });
                        }
                    }
                    if (!lastContentToken) {
                        continue;
                    }
                    const lastContentTokenLoc: SourceLocation | null | undefined =
                        lastContentToken.loc;
                    if (!lastContentTokenLoc ||
                        lastContentTokenLoc.end.line >= closeToken.loc.start.line - 1) {
                        continue;
                    }
                    context.report({
                        node: closeToken,
                        messageId: ParenthesesTrimRule.MSG_LAST,
                        data: {
                            $0: closeToken.value,
                        },
                    });
                }
            },
        };
    }

    static PAIRS: [string, string][] = [
        ['(', ')'],
        ['[', ']'],
        ['<', '>'],
    ];

    static MSG_FIRST: string = 'parentheses.trim.first';
    static MSG_LAST: string = 'parentheses.trim.last';
}

export default new ParenthesesTrimRule();
