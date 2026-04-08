from unittest import main

from astroid import parse
from pylint.testutils import _tokenize_str

from rulebook_pylint.checkers import \
    NestedIfElseChecker, \
    TrailingCommaChecker
from testing.messages import msg
from .all_checkers_case import AllCheckersTestCase
from ..code import get_code


class TestPylint(AllCheckersTestCase):
    def test_pylint_pylint_messages(self):
        code = get_code('pylint', 'pylint', 'messages.py')
        node_all = parse(code)
        tokens = _tokenize_str(code)
        with self.assertAddsMessages(
            msg(NestedIfElseChecker._MSG_INVERT, (172, 4, 180, 75), node_all.body[33].body[-1]),
            msg(
                NestedIfElseChecker._MSG_INVERT,
                (266, 8, 276, 21),
                node_all.body[38].body[-2].body[-1],
            ),
            msg(TrailingCommaChecker._MSG_MULTI, (94, 50)),
            msg(TrailingCommaChecker._MSG_MULTI, (101, 83)),
            msg(TrailingCommaChecker._MSG_MULTI, (104, 78)),
            msg(TrailingCommaChecker._MSG_MULTI, (114, 84)),
            msg(TrailingCommaChecker._MSG_MULTI, (125, 43)),
            msg(TrailingCommaChecker._MSG_MULTI, (134, 72)),
            msg(TrailingCommaChecker._MSG_MULTI, (140, 71)),
            msg(TrailingCommaChecker._MSG_MULTI, (156, 3)),
            msg(TrailingCommaChecker._MSG_MULTI, (160, 73)),
            msg(TrailingCommaChecker._MSG_MULTI, (165, 62)),
            msg(TrailingCommaChecker._MSG_MULTI, (178, 58)),
            msg(TrailingCommaChecker._MSG_MULTI, (250, 54)),
            msg(TrailingCommaChecker._MSG_MULTI, (272, 46)),
            msg(TrailingCommaChecker._MSG_MULTI, (275, 50)),
            msg(TrailingCommaChecker._MSG_MULTI, (318, 87)),
            msg(TrailingCommaChecker._MSG_MULTI, (370, 53)),
            msg(TrailingCommaChecker._MSG_MULTI, (376, 51)),
            msg(TrailingCommaChecker._MSG_MULTI, (385, 13)),
            msg(TrailingCommaChecker._MSG_MULTI, (398, 67)),
            msg(TrailingCommaChecker._MSG_MULTI, (417, 3)),
            msg(TrailingCommaChecker._MSG_MULTI, (460, 24)),
        ):
            for checker in self.checkers:
                self.assert_tokens(checker, tokens)
                self.assert_all(checker, node_all)


if __name__ == '__main__':
    main()
