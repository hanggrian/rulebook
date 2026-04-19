import messages from '../messages.js';
import RulebookRule from './rulebook-rule.js';
import type { AST, Rule } from 'eslint';

/** {@link https://hanggrian.github.io/rulebook/rules/#file-name|See detail} */
class FileNameRule extends RulebookRule {
    constructor() {
        super('file-name', {
            [FileNameRule.MSG]: messages.get(FileNameRule.MSG),
        });
    }

    override create(context: Rule.RuleContext): Rule.RuleListener {
        return {
            Program(node: AST.Program) {
                // checks for violation
                const filename: string =
                    context.filename.substring(context.filename.lastIndexOf('/') + 1);
                if (FileNameRule.KEBAB_CASE_REGEX.test(filename)) {
                    return;
                }
                context.report({
                    node: node,
                    messageId: FileNameRule.MSG,
                    data: {
                        $0: filename.toLowerCase().replace('_', '-'),
                    },
                });
            },
        };
    }

    static MSG: string = 'file.name';

    static KEBAB_CASE_REGEX: RegExp = /^[a-z0-9-.]+\.(js|jsx|ts|tsx)$/;
}

export default new FileNameRule();
