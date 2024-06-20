from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.conditional_branch_line_wrapping_checker import ConditionalBranchLineWrappingChecker

from .tests import msg


class TestConditionalBranchLineWrappingChecker(CheckerTestCase):
    CHECKER_CLASS = ConditionalBranchLineWrappingChecker

    def test_joined_switch_case_branches(self):
        code = \
            '''
            match foo:
                case 0:
                    print()
                case _:
                    print()
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_separated_switch_case_branches(self):
        code = \
            '''
            match foo:
                case 0:
                    print()

                case _:
                    print()
            '''
        with self.assertAddsMessages(msg(ConditionalBranchLineWrappingChecker.MSG, 5)):
            self.checker.process_tokens(_tokenize_str(code))


if __name__ == '__main__':
    main()
