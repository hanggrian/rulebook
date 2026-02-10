import { AST, Rule } from 'eslint';
import { BaseNode, ClassDeclaration } from 'estree';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';
import meaninglessWordsOptions from '../schema/meaningless-words.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#meaningless-word|See detail} */
class MeaninglessWordRule extends RulebookRule {
    constructor() {
        super(
            'meaningless-word',
            {
                [MeaninglessWordRule.MSG_ALL]: messages.get(MeaninglessWordRule.MSG_ALL),
                [MeaninglessWordRule.MSG_UTIL]: messages.get(MeaninglessWordRule.MSG_UTIL),
            },
            [meaninglessWordsOptions],
        );
    }

    create(context: Rule.RuleContext) {
        const options = context.options[0] as { words?: string[] } | undefined;
        const words = options?.words ?? ['Util', 'Utility', 'Helper', 'Manager', 'Wrapper'];

        function process(node: BaseNode, name: string): void {
            // checks for violation
            const matches: string[] = words.filter(word => name.endsWith(word));
            if (matches.length !== 1) {
                return;
            }
            const finalName: string = matches[0];
            if (['Util', 'Utility'].includes(finalName)) {
                context.report({
                    node,
                    messageId: MeaninglessWordRule.MSG_UTIL,
                    data: {
                        $0: `${name.substring(0, name.length - finalName.length)}s`,
                    },
                });
                return;
            }
            context.report({
                node,
                messageId: MeaninglessWordRule.MSG_ALL,
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
                );
            },
            ClassDeclaration(node: ClassDeclaration) {
                process(node.id, node.id.name);
            },
        };
    }

    static MSG_ALL: string = 'meaningless.word.all';
    static MSG_UTIL: string = 'meaningless.word.util';
}

export default new MeaninglessWordRule();
