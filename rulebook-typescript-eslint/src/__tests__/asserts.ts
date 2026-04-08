import { TSESLint } from '@typescript-eslint/utils';
import { RuleMetaData } from '@typescript-eslint/utils/ts-eslint';
import { expect } from 'vitest';

function assertProperties(module: TSESLint.RuleModule<string, readonly unknown[]>): void {
    const meta: RuleMetaData<string, unknown, readonly unknown[]> = module.meta;
    const ruleName: string = meta.docs!.description;
    expect(
        module.constructor.name
            .replace(/Rule$/, '')
            .replace(/([a-z])([A-Z])/g, '$1-$2')
            .toLowerCase(),
    ).toBe(ruleName);
    expect(meta.docs!.url)
        .toBe(`https://hanggrian.github.io/rulebook/rules/#${ruleName}`);
}

export default assertProperties;
