import { ESLintUtils } from '@typescript-eslint/utils';

export default ESLintUtils.RuleCreator(
    name => `https://hanggrian.github.io/rulebook/rules/#${name}`,
);
