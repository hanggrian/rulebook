from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.switch_statement_wrapping_checker import SwitchStatementWrappingChecker

from .testing import msg

class TestSwitchStatementWrappingChecker(CheckerTestCase):
    CHECKER_CLASS = SwitchStatementWrappingChecker

    def test_joined_switch_case_branches(self):
        def_all = \
            parse(
                '''
                match foo:
                    case 0:
                        print()
                    case _:
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(def_all)

    def test_separated_switch_case_branches(self):
        def_all = \
            parse(
                '''
                match foo:
                    case 0:
                        print()

                    case _:
                        print()
                ''',
            )
        with self.assertAddsMessages(msg(SwitchStatementWrappingChecker.MSG, 6)):
            self.checker.process_module(def_all)

if __name__ == '__main__':
    main()
