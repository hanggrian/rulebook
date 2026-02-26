from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.unnecessary_continue import UnnecessaryContinueChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestUnnecessaryContinueChecker(CheckerTestCase):
    CHECKER_CLASS = UnnecessaryContinueChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_loops_dont_end_with_continue(self):
        node1, node2 = \
            extract_node(
                '''
                def foo(items):
                    for item in items:  #@
                        print()

                def bar(items):
                    while True:  #@
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_for(node1)
            self.checker.visit_for(node2)

    def test_loops_end_with_continue(self):
        node1, node2 = \
            extract_node(
                '''
                def foo(items):
                    for item in items:  #@
                        print()
                        continue

                def bar(items):
                    while True:  #@
                        print()
                        continue
                ''',
            )
        with self.assertAddsMessages(
            msg(UnnecessaryContinueChecker._MSG, (5, 8, 5, 16), node1.body[-1]),
            msg(UnnecessaryContinueChecker._MSG, (10, 8, 10, 16), node2.body[-1]),
        ):
            self.checker.visit_for(node1)
            self.checker.visit_while(node2)

    def test_capture_trailing_non_continue(self):
        node1, node2 = \
            extract_node(
                '''
                def foo(items):
                    for item in items:  #@
                        print()
                        continue

                        # Lorem ipsum.

                def bar(items):
                    while True:  #@
                        print()
                        continue

                        # Lorem ipsum.
                ''',
            )
        with self.assertAddsMessages(
            msg(UnnecessaryContinueChecker._MSG, (5, 8, 5, 16), node1.body[-1]),
            msg(UnnecessaryContinueChecker._MSG, (12, 8, 12, 16), node2.body[-1]),
        ):
            self.checker.visit_for(node1)
            self.checker.visit_while(node2)


if __name__ == '__main__':
    main()
