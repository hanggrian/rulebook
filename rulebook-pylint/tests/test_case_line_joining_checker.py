from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.case_line_joining_checker import CaseLineJoiningChecker

from .tests import assert_properties, msg


class TestCaseLineJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = CaseLineJoiningChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

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
        with self.assertAddsMessages(msg(CaseLineJoiningChecker.MSG, (5, 0))):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
