import { expect } from 'vitest';
import type { Rule } from 'eslint';

function assertProperties(module: Rule.RuleModule): void {
    const meta = module.meta!;
    const ruleName: string | undefined = meta.docs!.description;
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
