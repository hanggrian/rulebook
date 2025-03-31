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
        node1 = \
            extract_node(
                '''
                match foo:  #@
                    case 0: bar()
                    case 1:
                        bar()
                        bar()

                    case _: bar()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_match(node1)

    def test_line_break_after_single_line_branch(self):
        node1 = \
            extract_node(
                '''
                match foo:  #@
                    case 0: bar()

                    case _: bar()
                ''',
            )
        with self.assertAddsMessages(
            msg(CaseSeparatorChecker.MSG_UNEXPECTED, (3, 12, 17), node1.cases[0].body[-1]),
        ):
            self.checker.visit_match(node1)

    def test_no_line_break_after_multiline_branch(self):
        node1 = \
            extract_node(
                '''
                match foo:  #@
                    case 0:
                        bar()
                        bar()
                    case _:
                        bar()
                        bar()
                ''',
            )
        with self.assertAddsMessages(
            msg(CaseSeparatorChecker.MSG_MISSING, (5, 8, 13), node1.cases[0].body[-1]),
        ):
            self.checker.visit_match(node1)

    def test_branches_with_comment_are_always_mutliline(self):
        node1 = \
            extract_node(
                '''
                match foo:  #@
                    # Comment
                    case 0: bar()
                    # Comment
                    case 1: bar()
                    # Comment
                    case 2: bar()
                    case _: bar()
                ''',
            )
        with self.assertAddsMessages(
            msg(CaseSeparatorChecker.MSG_MISSING, (4, 12, 17), node1.cases[0].body[-1]),
            msg(CaseSeparatorChecker.MSG_MISSING, (6, 12, 17), node1.cases[1].body[-1]),
            msg(CaseSeparatorChecker.MSG_MISSING, (8, 12, 17), node1.cases[2].body[-1]),
        ):
            self.checker.visit_match(node1)


if __name__ == '__main__':
    main()
