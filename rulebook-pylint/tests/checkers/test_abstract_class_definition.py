from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.abstract_class_definition import AbstractClassDefinitionChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestAbstractClassDefinitionChecker(CheckerTestCase):
    CHECKER_CLASS = AbstractClassDefinitionChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_abstract_class_with_abstract_function(self):
        node1 = \
            extract_node(
                '''
                from abc import ABC, abstractmethod


                class Foo(ABC):  #@
                    @abstractmethod
                    def bar():
                        pass
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_abstract_class_without_abstract_function(self):
        node1 = \
            extract_node(
                '''
                from abc import ABC


                class Foo(ABC):  #@
                    def bar():
                        pass
                ''',
            )
        with self.assertAddsMessages(
            msg(AbstractClassDefinitionChecker._MSG, (5, 10, 13), node=node1.bases[0]),
        ):
            self.checker.visit_classdef(node1)

    def test_skip_class_with_inheritance(self):
        node1 = \
            extract_node(
                '''
                from abc import ABC


                class Foo(ABC, Bar):  #@
                    def bar():
                        pass
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
