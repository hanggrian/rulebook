from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.case_separator import CaseSeparatorChecker
from ..tests import assert_properties, msg


class TestCaseSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = CaseSeparatorChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_line_branches_without_line_break(self):
        node_all = \
            parse(
                '''
                match foo:
                    case 0: bar()
                    case 1: bar(); bar()
                    case _: bar()
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_match(node_all.body[0])

    def test_multiline_branches_with_line_break(self):
        node_all = \
            parse(
                '''
                match foo:
                    case 0:
                        bar()

                    case 1:
                        bar()
                        bar()

                    case _:
                        bar()
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_match(node_all.body[0])

    def test_single_line_branches_with_line_break(self):
        node_all = \
            parse(
                '''
                match foo:
                    case 0: bar()

                    case 1: bar(); bar()

                    case _: bar()
                ''',
            )
        with self.assertAddsMessages(
            msg(CaseSeparatorChecker.MSG_UNEXPECTED, (3, 12, 17)),
            msg(CaseSeparatorChecker.MSG_UNEXPECTED, (5, 19, 24)),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_match(node_all.body[0])

    def test_multiline_branches_without_line_break(self):
        node_all = \
            parse(
                '''
                match foo:
                    case 0:
                        bar()
                    case 1:
                        bar()
                        break
                    case _:
                        bar()
                ''',
            )
        with self.assertAddsMessages(
            msg(CaseSeparatorChecker.MSG_MISSING, (4, 8, 13)),
            msg(CaseSeparatorChecker.MSG_MISSING, (7, 8, 13)),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_match(node_all.body[0])

    def test_branches_with_comment_are_always_mutliline(self):
        node_all = \
            parse(
                '''
                match foo:
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
            msg(CaseSeparatorChecker.MSG_MISSING, (4, 12, 17)),
            msg(CaseSeparatorChecker.MSG_MISSING, (6, 12, 17)),
            msg(CaseSeparatorChecker.MSG_MISSING, (8, 12, 17)),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_match(node_all.body[0])


if __name__ == '__main__':
    main()
