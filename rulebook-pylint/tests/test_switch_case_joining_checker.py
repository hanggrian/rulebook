from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.switch_case_joining_checker import SwitchCaseJoiningChecker

from .tests import msg

class TestSwitchCaseJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = SwitchCaseJoiningChecker

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
        with self.assertAddsMessages(msg(SwitchCaseJoiningChecker.MSG, 6)):
            self.checker.process_tokens(_tokenize_str(code))

if __name__ == '__main__':
    main()
