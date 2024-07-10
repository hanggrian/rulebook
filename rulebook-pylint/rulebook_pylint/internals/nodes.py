from tokenize import TokenInfo, NL, COMMENT

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


def is_newline_single(token: TokenInfo) -> bool:
    return token.type == NL and token.string == '\n'


def is_comment_empty(token: TokenInfo) -> bool:
    return token.type == COMMENT and token.string == '#'
