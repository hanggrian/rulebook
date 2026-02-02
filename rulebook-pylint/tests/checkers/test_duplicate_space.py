from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str

from rulebook_pylint.checkers.duplicate_space import DuplicateSpaceChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestDuplicateSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateSpaceChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_space_between_token(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(bar, baz):
                    qux = 1 + 2
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_multiple_spaces_between_token(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(bar,  baz):
                    qux  =  1  +  2
                ''',
            )
        with self.assertAddsMessages(
            msg(DuplicateSpaceChecker.MSG, (2, 27)),
            msg(DuplicateSpaceChecker.MSG, (3, 20)),
            msg(DuplicateSpaceChecker.MSG, (3, 25)),
            msg(DuplicateSpaceChecker.MSG, (3, 28)),
            msg(DuplicateSpaceChecker.MSG, (3, 31)),
        ):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
