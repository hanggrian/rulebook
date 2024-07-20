from tokenize import TokenInfo, COMMENT

from astroid import NodeNG, Name, Assign, AnnAssign, AssignName, FunctionDef, ClassDef


def has_decorator(node: FunctionDef | ClassDef, name: str) -> bool:
    if node.decorators:
        decorator: NodeNG
        for decorator in node.decorators.nodes:
            if isinstance(decorator, Name) and decorator.name == name:
                return True
    return False


def get_assignname(node: Assign) -> AssignName | None:
    target: NodeNG
    if isinstance(node, AnnAssign):
        target = node.target
    elif isinstance(node.targets[0], AssignName):
        target = node.targets[0]
    else:
        return None

    if isinstance(target, AssignName):
        return target
    return None


def is_multiline(node: NodeNG) -> bool:
    return node.end_lineno > node.lineno


def is_comment_empty(token: TokenInfo) -> bool:
    return token.type == COMMENT and token.string == '#'
