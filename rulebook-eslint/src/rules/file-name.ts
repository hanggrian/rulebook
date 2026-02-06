import { AST, Rule } from 'eslint';
import RulebookRule from './rulebook-rules.js';
import messages from '../messages.js';

/** {@link https://hanggrian.github.io/rulebook/rules/#file-name|See detail} */
class FileNameRule extends RulebookRule {
    constructor() {
        super('file-name', {
            [FileNameRule.MSG]: messages.get(FileNameRule.MSG),
        });
    }

    create(context: Rule.RuleContext) {
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
