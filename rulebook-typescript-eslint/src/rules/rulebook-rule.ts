import { TSESLint } from '@typescript-eslint/utils';
import { JSONSchema4 } from '@typescript-eslint/utils/json-schema';

abstract class RulebookRule implements TSESLint.RuleModule<string, readonly unknown[]> {
    readonly meta: TSESLint.RuleMetaData<string, unknown, readonly unknown[]>;

    protected constructor(
        description: string,
        messages: Record<string, string>,
        schema: JSONSchema4[] = [],
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

    abstract create(
        context: TSESLint.RuleContext<string, readonly unknown[]>,
    ): TSESLint.RuleListener;
}

export default RulebookRule;
