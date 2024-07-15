from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.switch_multiple_branching_checker import SwitchMultipleBranchingChecker

from .tests import assert_properties, msg


class TestSwitchMultipleBranchingChecker(CheckerTestCase):
    CHECKER_CLASS = SwitchMultipleBranchingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_multiple_switch_branches(self):
        node1 = \
            extract_node(
                '''
                match foo:  #@
                    case bar:
                        baz()
                    case _:
                        baz()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_match(node1)

    def test_separated_switch_case_branches(self):
        node1 = \
            extract_node(
                '''
                match foo:  #@
                    case bar:
                        baz()
                ''',
            )
        with self.assertAddsMessages(msg(SwitchMultipleBranchingChecker.MSG, (2, 0, 4, 13), node1)):
            self.checker.visit_match(node1)


if __name__ == '__main__':
    main()
