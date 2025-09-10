from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.checkers.redundant_default import RedundantDefaultChecker

from ..tests import assert_properties, msg


class TestRedundantDefaultChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantDefaultChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_no_throw_or_return_in_case(self):
        node1 = \
            extract_node(
                '''
                def foo(bar: int):  #@
                    match bar:
                        case 0:
                            baz()
                        case 1:
                            baz()
                        case _:
                            baz()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_match(node1.body[0])

    def test_lift_else_when_case_has_return(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    match bar:
                        case 0:
                            raise ValueError()
                        case 1:
                            return
                        case _:
                            baz()
                ''',
            )
        with self.assertAddsMessages(
            msg(RedundantDefaultChecker.MSG, 8, node1.body[0].cases[-1]),
        ):
            self.checker.visit_match(node1.body[0])

    def test_skip_if_not_all_case_blocks_have_return_or_throw(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    match bar:
                        case 0:
                            raise ValueError()
                        case 1:
                            baz()
                        case _:
                            baz()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_match(node1.body[0])


if __name__ == '__main__':
    main()
