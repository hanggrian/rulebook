import { TSESLint } from '@typescript-eslint/utils';

abstract class RulebookRule implements TSESLint.RuleModule<string, any> {
    readonly meta: TSESLint.RuleMetaData<string, any, any>;

    protected constructor(name: string, messageIds: Record<string, string>) {
        this.meta = {
            type: 'problem',
            docs: {
                description: name,
                url: `https://hanggrian.github.io/rulebook/rules/#${name}`,
            },
            schema: [],
            messages: messageIds,
        };
    }

    abstract create(context: TSESLint.RuleContext<string, any>): TSESLint.RuleListener;
}

export default RulebookRule;
