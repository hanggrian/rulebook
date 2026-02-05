import { Rule } from 'eslint';

abstract class RulebookRule implements Rule.RuleModule {
    readonly meta: Rule.RuleMetaData;

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

    abstract create(context: Rule.RuleContext): Rule.RuleListener;
}

export default RulebookRule;
