import { Rule } from 'eslint';

abstract class RulebookRule implements Rule.RuleModule {
    readonly meta: Rule.RuleMetaData;

    protected constructor(
        description: string,
        messages: Record<string, string>,
        schema: any[] = [],
    ) {
        this.meta = {
            type: 'problem',
            docs: {
                description,
                url: `https://hanggrian.github.io/rulebook/rules/#${description}`,
            },
            messages,
            schema,
        };
    }

    abstract create(context: Rule.RuleContext): Rule.RuleListener;
}

export default RulebookRule;
