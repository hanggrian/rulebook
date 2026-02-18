import { Rule } from 'eslint';
import { Expression, SourceLocation, SwitchCase } from 'estree';

function isMultiline(node: Rule.Node | Expression | SwitchCase): boolean {
    const loc: SourceLocation | null | undefined = node.loc;
    return !loc? false : loc.start.line < loc.end.line;
}

export { isMultiline };
