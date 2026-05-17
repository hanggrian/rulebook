from unittest import main

from astroid import parse
from pylint.testutils import _tokenize_str

from rulebook_pylint.checkers import BlockCommentTrimChecker
from testing.messages import msg
from .all_checkers_case import AllCheckersTestCase
from ..code import get_code


class TestNumPy(AllCheckersTestCase):
    def test_numpy_matlib(self):
        code = get_code('pylint', 'numpy', 'matlib.py')
        node_all = parse(code)
        tokens = _tokenize_str(code)
        with self.assertAddsMessages(
            msg(BlockCommentTrimChecker._MSG_LAST, (62, 4, 7), node_all.body[8].doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (105, 4, 7), node_all.body[9].doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (149, 4, 7), node_all.body[10].doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (184, 4, 7), node_all.body[11].doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (229, 4, 7), node_all.body[12].doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (273, 4, 7), node_all.body[13].doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (326, 4, 7), node_all.body[14].doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (367, 4, 7), node_all.body[15].doc_node),
        ):
            for checker in self.checkers:
                self.assert_tokens(checker, tokens)
                self.assert_all(checker, node_all)


if __name__ == '__main__':
    main()
