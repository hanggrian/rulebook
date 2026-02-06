import { TSESLint } from '@typescript-eslint/utils';

abstract class RulebookRule implements TSESLint.RuleModule<string, any> {
    readonly meta: TSESLint.RuleMetaData<string, any, any>;

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

    abstract create(context: TSESLint.RuleContext<string, any>): TSESLint.RuleListener;
}

export default RulebookRule;
