from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.conditional_branch_line_joining_checker \
    import ConditionalBranchLineJoiningChecker

from .tests import msg


class TestConditionalBranchLineJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = ConditionalBranchLineJoiningChecker

    def test_joined_switch_case_branches(self):
        tokens = \
            _tokenize_str(
                '''
                match foo:
                    case 0:
                        print()
                    case _:
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_separated_switch_case_branches(self):
        tokens = \
            _tokenize_str(
                '''
                match foo:
                    case 0:
                        print()

                    case _:
                        print()
                ''',
            )
        with self.assertAddsMessages(msg(ConditionalBranchLineJoiningChecker.MSG, (5, 0))):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
