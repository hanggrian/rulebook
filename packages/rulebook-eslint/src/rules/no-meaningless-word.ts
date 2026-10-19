import messages from '../messages.js';
import meaninglessWordsOptions from '../schema/meaningless-words.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule } from 'eslint';
import type { BaseNode, ClassDeclaration } from 'estree';

/** {@link https://hanggrian.github.io/rulebook/rules/#no-meaningless-word|See detail} */
class NoMeaninglessWordRule extends RulebookRule {
    constructor() {
        super(
            'no-meaningless-word',
            {
                [NoMeaninglessWordRule.MSG_ALL]: messages.get(NoMeaninglessWordRule.MSG_ALL),
                [NoMeaninglessWordRule.MSG_UTIL]: messages.get(NoMeaninglessWordRule.MSG_UTIL),
            },
            [meaninglessWordsOptions],
        );
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        const options = context.options[0] as { words?: string[] } | undefined;
        const words = options?.words ?? ['Util', 'Utility', 'Helper', 'Manager', 'Wrapper'];

        function process(
            node: BaseNode,
            name: string,
            loc?: { line: number; column: number },
        ): void {
            // checks for violation
            const matches: string[] = words.filter(word => name.endsWith(word));
            if (matches.length !== 1) {
                return;
            }
            const finalName: string = matches[0];
            if (['Util', 'Utility'].includes(finalName)) {
                context.report({
                    node,
                    loc,
                    messageId: NoMeaninglessWordRule.MSG_UTIL,
                    data: {
                        $0: `${name.substring(0, name.length - finalName.length)}s`,
                    },
                });
                return;
            }
            context.report({
                node,
                loc,
                messageId: NoMeaninglessWordRule.MSG_ALL,
                data: {
                    $0: finalName,
                },
            });
        }

        return {
            Program(node: AST.Program) {
                process(
                    node,
                    context
                        .filename
                        .substring(0, context.filename.lastIndexOf('.'))
                        .replace(/(^\w|-\w)/g, m => m.replace(/-/, '').toUpperCase()),
                    { line: 1, column: 0 },
                );
            },
            ClassDeclaration(node: ClassDeclaration) {
                process(node.id, node.id.name);
            },
        };
    }

    private static MSG_ALL: string = 'no.meaningless.word.all';
    private static MSG_UTIL: string = 'no.meaningless.word.util';
}

export default new NoMeaninglessWordRule();
