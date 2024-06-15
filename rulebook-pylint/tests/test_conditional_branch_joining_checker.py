from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.conditional_branch_joining_checker import ConditionalBranchJoiningChecker

from .tests import msg


class TestConditionalBranchJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = ConditionalBranchJoiningChecker

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
        with self.assertAddsMessages(msg(ConditionalBranchJoiningChecker.MSG, 6)):
            self.checker.process_tokens(_tokenize_str(code))


if __name__ == '__main__':
    main()
