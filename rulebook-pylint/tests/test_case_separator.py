from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.case_separator import CaseSeparatorChecker

from .tests import assert_properties, msg


class TestCaseSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = CaseSeparatorChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_no_line_break_after_single_line_branch_and_line_break_after_multiline_branch(self):
        node1, node2, node3 = \
            extract_node(
                '''
                match foo:
                    case 0:  #@
                        bar()
                    case 1:  #@
                        bar()
                        bar()

                    case _:  #@
                        bar()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_matchcase(node1)
            self.checker.visit_matchcase(node2)
            self.checker.visit_matchcase(node3)

    def test_line_break_after_single_line_branch(self):
        node1, node2 = \
            extract_node(
                '''
                match foo:
                    case 0:  #@
                        bar()

                    case _:  #@
                        bar()
                ''',
            )
        with self.assertAddsMessages(msg(CaseSeparatorChecker.MSG_UNEXPECTED, 4, node1)):
            self.checker.visit_matchcase(node1)
            self.checker.visit_matchcase(node2)

    def test_no_line_break_after_multiline_branch(self):
        node1, node2 = \
            extract_node(
                '''
                match foo:
                    case 0:  #@
                        bar()
                        bar()
                    case _:  #@
                        bar()
                        bar()
                ''',
            )
        with self.assertAddsMessages(msg(CaseSeparatorChecker.MSG_MISSING, 5, node1)):
            self.checker.visit_matchcase(node1)
            self.checker.visit_matchcase(node2)


if __name__ == '__main__':
    main()
