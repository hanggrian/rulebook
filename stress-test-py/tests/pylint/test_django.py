from unittest import main

from astroid import parse
from pylint.testutils import _tokenize_str

from rulebook_pylint.checkers import (
    BlockCommentClipChecker,
    TrailingCommaChecker,
)
from testing.messages import msg
from .all_checkers_case import AllCheckersTestCase
from ..code import get_code


class TestDjango(AllCheckersTestCase):
    def test_django_paginator(self):
        code = get_code('pylint', 'django', 'paginator.py')
        node_all = parse(code)
        tokens = _tokenize_str(code)
        with self.assertAddsMessages(
            msg(
                BlockCommentClipChecker._MSG,
                (73, 8, 75, 11),
                node_all.body[14].body[3].doc_node,
            ),
            msg(TrailingCommaChecker._MSG_MULTI, (57, 61)),
            msg(TrailingCommaChecker._MSG_MULTI, (68, 29)),
            msg(TrailingCommaChecker._MSG_MULTI, (80, 79)),
            msg(TrailingCommaChecker._MSG_MULTI, (83, 52)),
            msg(TrailingCommaChecker._MSG_MULTI, (93, 70)),
            msg(TrailingCommaChecker._MSG_MULTI, (211, 74)),
            msg(TrailingCommaChecker._MSG_MULTI, (225, 82)),
            msg(TrailingCommaChecker._MSG_MULTI, (271, 37)),
            msg(TrailingCommaChecker._MSG_MULTI, (304, 64)),
            msg(TrailingCommaChecker._MSG_MULTI, (325, 38)),
            msg(TrailingCommaChecker._MSG_MULTI, (389, 84)),
            msg(TrailingCommaChecker._MSG_MULTI, (397, 60)),
            msg(TrailingCommaChecker._MSG_MULTI, (406, 38)),
            msg(TrailingCommaChecker._MSG_MULTI, (411, 85)),
        ):
            for checker in self.checkers:
                self.assert_tokens(checker, tokens)
                self.assert_all(checker, node_all)


if __name__ == '__main__':
    main()
