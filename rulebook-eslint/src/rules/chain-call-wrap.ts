import { AST, Rule } from 'eslint';
import { Expression, Super } from 'estree';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';
import Token = AST.Token;

/** {@link https://hanggrian.github.io/rulebook/rules/#chain-call-wrap|See detail} */
class ChainCallWrapRule extends RulebookRule {
    constructor() {
        super('chain-call-wrap', {
            [ChainCallWrapRule.MSG_MISSING]: messages.get(ChainCallWrapRule.MSG_MISSING),
            [ChainCallWrapRule.MSG_UNEXPECTED]: messages.get(ChainCallWrapRule.MSG_UNEXPECTED),
        });
    }

    create(context: Rule.RuleContext) {
        return {
            'CallExpression, MemberExpression'(node: Rule.Node) {
                // target root multiline chain call
                if (!node.parent ||
                    node.parent.type === 'CallExpression' ||
                    node.parent.type === 'MemberExpression') {
                    return;
                }
                if (node.loc!.start.line === node.loc!.end.line) {
                    return;
                }

                // collect dots
                const dots: Token[] = [];
                let current: Rule.Node | Expression | Super | null = node;
                while (current) {
                    let obj: Expression | Super | null = null;
                    let dot: Token | null = null;
                    if (current.type === 'CallExpression' &&
                        current.callee.type === 'MemberExpression') {
                        obj = current.callee.object;
                        dot = context.sourceCode.getTokenBefore(current.callee.property);
                    } else if (current.type === 'MemberExpression') {
                        obj = current.object;
                        dot = context.sourceCode.getTokenBefore(current.property);
                    }

                    if (dot && dot.value === '.') {
                        dots.push(dot);
                    }

                    current = obj;
                }

                // skip dots in single-line
                if (dots.length < 2) {
                    return;
                }
                const firstDot: Token = dots[0];
                if (dots.every(node => node.loc.start.line === firstDot.loc.start.line)) {
                    return;
                }

                // checks for violation
                dots.forEach(dot => {
                    const prevToken = context.sourceCode.getTokenBefore(dot);
                    if (!prevToken) {
                        return;
                    }
                    if ([')', '}', ']'].includes(prevToken.value) &&
                        prevToken.loc.start.line !==
                        context.sourceCode.getTokenBefore(prevToken)?.loc.end.line
                    ) {
                        if (dot.loc.start.line !== prevToken.loc.start.line) {
                            context.report({
                                node: dot,
                                messageId: ChainCallWrapRule.MSG_UNEXPECTED,
                            });
                        }
                    } else {
                        if (dot.loc.start.line !== prevToken.loc.end.line + 1) {
                            context.report({
                                node: dot,
                                messageId: ChainCallWrapRule.MSG_MISSING,
                            });
                        }
                    }
                });
            },
        };
    }

    static MSG_MISSING: string = 'chain.call.wrap.missing';
    static MSG_UNEXPECTED: string = 'chain.call.wrap.unexpected';
}

export default new ChainCallWrapRule();
