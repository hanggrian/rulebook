import { Expression, Node, SourceLocation, Statement, SwitchCase } from 'estree';

function hasJumpStatement(node: Statement): boolean {
    if (node.type === 'BlockStatement') {
        return node.body.some(hasJumpStatement);
    }
    return node.type === 'ReturnStatement' ||
        node.type === 'ThrowStatement' ||
        node.type === 'BreakStatement' ||
        node.type === 'ContinueStatement';
}

function isMultiline(node: Node | Expression | SwitchCase): boolean {
    const loc: SourceLocation | null | undefined = node.loc;
    return !loc ? false : loc.start.line < loc.end.line;
}

export { hasJumpStatement, isMultiline };
