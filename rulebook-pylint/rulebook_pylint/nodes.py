from tokenize import TokenInfo, COMMENT

from astroid import NodeNG, Name, Assign, AnnAssign, AssignName, FunctionDef, ClassDef, If, \
    MatchCase, Return, Raise, Break, Continue, Attribute


def _get_assignname(node: Assign) -> AssignName | None:
    target: NodeNG
    if isinstance(node, AnnAssign):
        target = node.target
    elif isinstance(node.targets[0], AssignName):
        target = node.targets[0]
    else:
        return None
    return target if isinstance(target, AssignName) else None


def _has_decorator(node: FunctionDef | ClassDef, name: str) -> bool:
    return any(
        ((isinstance(n, Name) and n.name == name) or \
         (isinstance(n, Attribute) and n.attrname == name) \
         for n in node.decorators.nodes),
    ) if node.decorators \
        else False


def _has_jump_statement(node: NodeNG) -> bool:
    return any(isinstance(n, (Return, Raise, Break, Continue)) for n in node.body) \
        if isinstance(node, (If, MatchCase)) \
        else False


def _is_multiline(node: NodeNG) -> bool:
    if isinstance(node, If):
        return node.body[-1].end_lineno > node.body[0].lineno or \
            _is_multiline(node.test)
    if isinstance(node, MatchCase):
        return node.body[-1].end_lineno > node.body[0].lineno or \
            _is_multiline(node.pattern)
    if isinstance(node, ClassDef):
        return node.body[-1].end_lineno > node.body[0].lineno or \
            _is_multiline(node.bases)
    return node.end_lineno > node.lineno


def _is_comment_empty(token: TokenInfo) -> bool:
    return token.type == COMMENT and token.string == '#'
