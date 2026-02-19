import { Expression, Node, SourceLocation, Statement, SwitchCase } from 'estree';

function hasJumpStatement(node: Statement): boolean {
    const type = node.type;
    if (type === 'BlockStatement') {
        return node.body.some(hasJumpStatement);
    }
    return type === 'ReturnStatement' ||
        type === 'ThrowStatement' ||
        type === 'BreakStatement' ||
        type === 'ContinueStatement';
}

function isMultiline(node: Node | Expression | SwitchCase): boolean {
    const loc: SourceLocation | null | undefined = node.loc;
    return !loc ? false : loc.start.line < loc.end.line;
}

export { hasJumpStatement, isMultiline };
