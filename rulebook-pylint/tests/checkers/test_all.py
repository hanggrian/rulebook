from contextlib import contextmanager
from os.path import join, dirname
from typing import Any, Iterator, Generator
from unittest import main

from astroid import parse
from astroid.nodes import NodeNG, ClassDef, Module, FunctionDef, Assign, Match, For, While, Call
from pylint.testutils import UnittestLinter, MessageTest, _tokenize_str
from pylint.testutils.global_test_linter import linter
from pylint.utils import ASTWalker
from rulebook_pylint.checkers.abstract_class_definition import AbstractClassDefinitionChecker
from rulebook_pylint.checkers.block_comment_trim import BlockCommentTrimChecker
from rulebook_pylint.checkers.built_in_function_position import BuiltInFunctionPositionChecker
from rulebook_pylint.checkers.case_separator import CaseSeparatorChecker
from rulebook_pylint.checkers.class_name_abbreviation import ClassNameAbbreviationChecker
from rulebook_pylint.checkers.comment_space import CommentSpaceChecker
from rulebook_pylint.checkers.comment_trim import CommentTrimChecker
from rulebook_pylint.checkers.duplicate_blank_line import DuplicateBlankLineChecker
from rulebook_pylint.checkers.duplicate_blank_line_in_block_comment import \
    DuplicateBlankLineInBlockCommentChecker
from rulebook_pylint.checkers.duplicate_blank_line_in_comment import \
    DuplicateBlankLineInCommentChecker
from rulebook_pylint.checkers.duplicate_space import DuplicateSpaceChecker
from rulebook_pylint.checkers.empty_parentheses_clip import EmptyParenthesesClipChecker
from rulebook_pylint.checkers.exception_inheritance import ExceptionInheritanceChecker
from rulebook_pylint.checkers.file_size import FileSizeChecker
from rulebook_pylint.checkers.illegal_class_name_suffix import IllegalClassNameSuffixChecker
from rulebook_pylint.checkers.inner_class_position import InnerClassPositionChecker
from rulebook_pylint.checkers.member_order import MemberOrderChecker
from rulebook_pylint.checkers.member_separator import MemberSeparatorChecker
from rulebook_pylint.checkers.nested_if_else import NestedIfElseChecker
from rulebook_pylint.checkers.parameter_wrap import ParameterWrapChecker
from rulebook_pylint.checkers.parentheses_trim import ParenthesesTrimChecker
from rulebook_pylint.checkers.redundant_default import RedundantDefaultChecker
from rulebook_pylint.checkers.required_generics_name import RequiredGenericsNameChecker
from rulebook_pylint.checkers.short_block_comment_clip import ShortBlockCommentClipChecker
from rulebook_pylint.checkers.string_quotes import StringQuotesChecker
from rulebook_pylint.checkers.todo_comment import TodoCommentChecker
from rulebook_pylint.checkers.trailing_comma import TrailingCommaChecker
from rulebook_pylint.checkers.unnecessary_blank_line_after_colon import \
    UnnecessaryBlankLineAfterColonChecker
from rulebook_pylint.checkers.unnecessary_blank_line_before_package import \
    UnnecessaryBlankLineBeforePackageChecker
from rulebook_pylint.checkers.unnecessary_switch import UnnecessarySwitchChecker

from ..tests import msg


class TestAllCheckers:
    CHECKER_CLASSES: tuple = (
        AbstractClassDefinitionChecker,
        BlockCommentTrimChecker,
        BuiltInFunctionPositionChecker,
        CaseSeparatorChecker,
        ClassNameAbbreviationChecker,
        CommentSpaceChecker,
        CommentTrimChecker,
        DuplicateBlankLineChecker,
        DuplicateBlankLineInBlockCommentChecker,
        DuplicateBlankLineInCommentChecker,
        DuplicateSpaceChecker,
        EmptyParenthesesClipChecker,
        ExceptionInheritanceChecker,
        # TODO find out why this test shares config (max 3 lines) with `test_file_size` when running
        #   command `pytest`.
        # FileSizeChecker,
        IllegalClassNameSuffixChecker,
        InnerClassPositionChecker,
        MemberOrderChecker,
        MemberSeparatorChecker,
        NestedIfElseChecker,
        ParameterWrapChecker,
        ParenthesesTrimChecker,
        RedundantDefaultChecker,
        RequiredGenericsNameChecker,
        ShortBlockCommentClipChecker,
        # skip string quotes, too many errors
        # StringQuotesChecker,
        TodoCommentChecker,
        TrailingCommaChecker,
        UnnecessaryBlankLineAfterColonChecker,
        UnnecessaryBlankLineBeforePackageChecker,
        UnnecessarySwitchChecker,
    )
    CONFIG: dict[str, Any] = {}

    def test_pylint_pylint_messages(self):
        with open(
            join(dirname(__file__), '../resources/pylint_messages.py'),
            'r',
            encoding='UTF-8',
        ) as file:
            s = file.read()
            node_all = parse(s)
            tokens = _tokenize_str(s)
        with self.assertAddsMessages(
            msg(NestedIfElseChecker.MSG_INVERT, (172, 4, 180, 75), node_all.body[33].body[-1]),
            msg(
                NestedIfElseChecker.MSG_INVERT,
                (266, 8, 276, 21),
                node_all.body[38].body[-2].body[-1],
            ),
            msg(TrailingCommaChecker.MSG_MULTI, (94, 50)),
            msg(TrailingCommaChecker.MSG_MULTI, (101, 83)),
            msg(TrailingCommaChecker.MSG_MULTI, (104, 78)),
            msg(TrailingCommaChecker.MSG_MULTI, (114, 84)),
            msg(TrailingCommaChecker.MSG_MULTI, (125, 43)),
            msg(TrailingCommaChecker.MSG_MULTI, (134, 72)),
            msg(TrailingCommaChecker.MSG_MULTI, (140, 71)),
            msg(TrailingCommaChecker.MSG_MULTI, (156, 3)),
            msg(TrailingCommaChecker.MSG_MULTI, (160, 73)),
            msg(TrailingCommaChecker.MSG_MULTI, (165, 62)),
            msg(TrailingCommaChecker.MSG_MULTI, (178, 58)),
            msg(TrailingCommaChecker.MSG_MULTI, (241, 44)),
            msg(TrailingCommaChecker.MSG_MULTI, (250, 54)),
            msg(TrailingCommaChecker.MSG_MULTI, (272, 46)),
            msg(TrailingCommaChecker.MSG_MULTI, (275, 50)),
            msg(TrailingCommaChecker.MSG_MULTI, (318, 87)),
            msg(TrailingCommaChecker.MSG_MULTI, (370, 53)),
            msg(TrailingCommaChecker.MSG_MULTI, (376, 51)),
            msg(TrailingCommaChecker.MSG_MULTI, (384, 39)),
            msg(TrailingCommaChecker.MSG_MULTI, (385, 13)),
            msg(TrailingCommaChecker.MSG_MULTI, (398, 67)),
            msg(TrailingCommaChecker.MSG_MULTI, (417, 3)),
            msg(TrailingCommaChecker.MSG_MULTI, (435, 73)),
            msg(TrailingCommaChecker.MSG_MULTI, (438, 83)),
            msg(TrailingCommaChecker.MSG_MULTI, (460, 24)),
            msg(TrailingCommaChecker.MSG_MULTI, (481, 76)),
        ):
            for checker in self.checkers:
                self._assert_tokens(checker, tokens)
                self._assert_all(checker, node_all)

    def test_django_paginator(self):
        with open(
            join(dirname(__file__), '../resources/paginator.py'),
            'r',
            encoding='UTF-8',
        ) as file:
            s = file.read()
            node_all = parse(s)
            tokens = _tokenize_str(s)
        with self.assertAddsMessages(
            msg(
                ShortBlockCommentClipChecker.MSG, (73, 8, 75, 11),
                node_all.body[14].body[3].doc_node,
            ),
            msg(TrailingCommaChecker.MSG_MULTI, (57, 61)),
            msg(TrailingCommaChecker.MSG_MULTI, (68, 29)),
            msg(TrailingCommaChecker.MSG_MULTI, (80, 79)),
            msg(TrailingCommaChecker.MSG_MULTI, (83, 52)),
            msg(TrailingCommaChecker.MSG_MULTI, (93, 70)),
            msg(TrailingCommaChecker.MSG_MULTI, (211, 74)),
            msg(TrailingCommaChecker.MSG_MULTI, (225, 82)),
            msg(TrailingCommaChecker.MSG_MULTI, (271, 37)),
            msg(TrailingCommaChecker.MSG_MULTI, (304, 64)),
            msg(TrailingCommaChecker.MSG_MULTI, (325, 38)),
            msg(TrailingCommaChecker.MSG_MULTI, (389, 84)),
            msg(TrailingCommaChecker.MSG_MULTI, (397, 60)),
            msg(TrailingCommaChecker.MSG_MULTI, (406, 38)),
            msg(TrailingCommaChecker.MSG_MULTI, (411, 85)),
        ):
            for checker in self.checkers:
                self._assert_tokens(checker, tokens)
                self._assert_all(checker, node_all)

    def test_numpy_matlib(self):
        with open(
            join(dirname(__file__), '../resources/matlib.py'),
            'r',
            encoding='UTF-8',
        ) as file:
            s = file.read()
            node_all = parse(s)
            tokens = _tokenize_str(s)
        with self.assertAddsMessages(
            msg(BlockCommentTrimChecker.MSG_LAST, (62, 4, 7), node_all.body[8].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (105, 4, 7), node_all.body[9].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (149, 4, 7), node_all.body[10].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (184, 4, 7), node_all.body[11].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (229, 4, 7), node_all.body[12].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (273, 4, 7), node_all.body[13].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (326, 4, 7), node_all.body[14].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (367, 4, 7), node_all.body[15].doc_node),
        ):
            for checker in self.checkers:
                self._assert_tokens(checker, tokens)
                self._assert_all(checker, node_all)

    def test_matplotlib_pyplot(self):
        with open(
            join(dirname(__file__), '../resources/pyplot.py'),
            'r',
            encoding='UTF-8',
        ) as file:
            s = file.read()
            node_all = parse(s)
            tokens = _tokenize_str(s)
        switch_backend = node_all.body[64]
        _warn_if_gui_out_of_main_thread = node_all.body[65]
        _auto_draw_if_interactive = node_all.body[81]
        delaxes = node_all.body[96]
        sca = node_all.body[97]
        subplot = node_all.body[99]
        last_subplots = node_all.body[103]
        last_subplot_mosaic = node_all.body[107]
        subplot2grid = node_all.body[108]
        xticks = node_all.body[115]
        cla = node_all.body[119]
        clim = node_all.body[122]
        matshow = node_all.body[127]
        with self.assertAddsMessages(
            msg(BlockCommentTrimChecker.MSG_LAST, (388, 4, 7), switch_backend.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (1516, 4, 7), subplot.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (1776, 4, 7), last_subplots.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (1949, 4, 7), last_subplot_mosaic.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (2551, 4, 7), clim.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (2668, 4, 7), matshow.doc_node),
            msg(CommentSpaceChecker.MSG, (268, 0)),
            msg(CommentSpaceChecker.MSG, (872, 0)),
            msg(CommentSpaceChecker.MSG, (1256, 0)),
            msg(CommentSpaceChecker.MSG, (1268, 0)),
            msg(CommentSpaceChecker.MSG, (1387, 0)),
            msg(CommentSpaceChecker.MSG, (2089, 0)),
            msg(CommentSpaceChecker.MSG, (2517, 0)),
            msg(CommentSpaceChecker.MSG, (2738, 0)),
            msg(DuplicateBlankLineInBlockCommentChecker.MSG, (2173, 4, 2229, 7), xticks.doc_node),
            # msg(FileSizeChecker.MSG, args=1000),
            msg(
                NestedIfElseChecker.MSG_INVERT,
                (545, 4, 548, 20),
                _warn_if_gui_out_of_main_thread.body[-1],
            ),
            msg(
                NestedIfElseChecker.MSG_INVERT,
                (1086, 4, 1094, 34),
                _auto_draw_if_interactive.body[-1],
            ),
            msg(ParameterWrapChecker.MSG, (1963, 28, 48), subplot2grid.args.args[1]),
            msg(ParameterWrapChecker.MSG, (1964, 22, 34), subplot2grid.args.args[3]),
            msg(ShortBlockCommentClipChecker.MSG, (1361, 4, 1363, 7), delaxes.doc_node),
            msg(ShortBlockCommentClipChecker.MSG, (1370, 4, 1372, 7), sca.doc_node),
            msg(ShortBlockCommentClipChecker.MSG, (2494, 4, 2496, 7), cla.doc_node),
            msg(TrailingCommaChecker.MSG_MULTI, (166, 30)),
            msg(TrailingCommaChecker.MSG_MULTI, (177, 40)),
            msg(TrailingCommaChecker.MSG_MULTI, (183, 31)),
            msg(TrailingCommaChecker.MSG_MULTI, (203, 85)),
            msg(TrailingCommaChecker.MSG_MULTI, (348, 29)),
            msg(TrailingCommaChecker.MSG_MULTI, (805, 64)),
            msg(TrailingCommaChecker.MSG_MULTI, (889, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (1130, 35)),
            msg(TrailingCommaChecker.MSG_MULTI, (1273, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (1527, 65)),
            msg(TrailingCommaChecker.MSG_MULTI, (1583, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (1600, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (1617, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (1631, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (1797, 17)),
            msg(TrailingCommaChecker.MSG_MULTI, (1813, 17)),
            msg(TrailingCommaChecker.MSG_MULTI, (1829, 17)),
            msg(TrailingCommaChecker.MSG_MULTI, (1846, 17)),
            msg(TrailingCommaChecker.MSG_MULTI, (1966, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (2171, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (2257, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (2342, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (2420, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (2525, 12)),
            msg(TrailingCommaChecker.MSG_MULTI, (2613, 71)),
            msg(TrailingCommaChecker.MSG_MULTI, (2620, 65)),
            msg(TrailingCommaChecker.MSG_MULTI, (2716, 60)),
            msg(TrailingCommaChecker.MSG_MULTI, (2732, 54)),
            msg(TrailingCommaChecker.MSG_MULTI, (2777, 80)),
            msg(TrailingCommaChecker.MSG_MULTI, (2825, 84)),
            msg(TrailingCommaChecker.MSG_MULTI, (2856, 40)),
            msg(TrailingCommaChecker.MSG_MULTI, (2935, 72)),
            msg(TrailingCommaChecker.MSG_MULTI, (2973, 72)),
            msg(TrailingCommaChecker.MSG_MULTI, (3130, 81)),
            msg(TrailingCommaChecker.MSG_MULTI, (3180, 71)),
            msg(TrailingCommaChecker.MSG_MULTI, (3191, 71)),
            msg(TrailingCommaChecker.MSG_MULTI, (3634, 81)),
            msg(TrailingCommaChecker.MSG_MULTI, (3888, 71)),
            msg(TrailingCommaChecker.MSG_MULTI, (3897, 65)),
            msg(TrailingCommaChecker.MSG_MULTI, (4034, 86)),
            msg(TrailingCommaChecker.MSG_MULTI, (4186, 80)),
            msg(TrailingCommaChecker.MSG_MULTI, (4393, 71)),
            msg(TrailingCommaChecker.MSG_MULTI, (4408, 71)),
            msg(UnnecessaryBlankLineAfterColonChecker.MSG, (456, 0)),
        ):
            for checker in self.checkers:
                self._assert_tokens(checker, tokens)
                self._assert_all(checker, node_all)

    @staticmethod
    def _assert_tokens(checker, tokens):
        if hasattr(checker, 'process_tokens'):
            checker.process_tokens(tokens)

    @staticmethod
    def _assert_all(checker, node):
        if hasattr(checker, 'process_module') and isinstance(node, Module):
            checker.process_module(node)
            if hasattr(checker, 'visit_module'):
                checker.visit_module(node)
        if not node.body:
            return
        for n in node.body:
            if hasattr(checker, 'visit_classdef') and isinstance(n, ClassDef):
                checker.visit_classdef(n)
                TestAllCheckers._assert_all(checker, n)
            if hasattr(checker, 'visit_functiondef') and isinstance(n, FunctionDef):
                checker.visit_functiondef(n)
                TestAllCheckers._assert_all(checker, n)
            if hasattr(checker, 'visit_assign') and isinstance(n, Assign):
                checker.visit_assign(n)
            if hasattr(checker, 'visit_match') and isinstance(n, Match):
                checker.visit_match(n)
                for case in n.cases:
                    TestAllCheckers._assert_all(checker, case)
            if hasattr(checker, 'visit_for') and isinstance(n, For):
                if hasattr(checker, 'visit_call') and isinstance(n.iter, Call):
                    checker.visit_call(n)
                checker.visit_for(n)
                TestAllCheckers._assert_all(checker, n)
            if hasattr(checker, 'visit_while') and isinstance(n, While):
                checker.visit_while(n)
                TestAllCheckers._assert_all(checker, n)

    def setup_method(self) -> None:
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.setup_method`"""
        self.linter = UnittestLinter()
        self.checkers = [c(self.linter) for c in self.CHECKER_CLASSES]
        for checker in self.checkers:
            for key, value in self.CONFIG.items():
                setattr(checker.linter.before_run, key, value)
            checker.open()

    @contextmanager
    def assertNoMessages(self) -> Iterator[None]:
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.assertNoMessages`"""
        with self.assertAddsMessages():
            yield

    @contextmanager
    def assertAddsMessages(
        self,
        *messages: MessageTest,
        ignore_position: bool = False,
    ) -> Generator[None]:
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.assertAddsMessages`"""
        yield
        got = self.linter.release_messages()
        no_msg = "No message."
        expected = "\n".join(repr(m) for m in messages) or no_msg
        got_str = "\n".join(repr(m) for m in got) or no_msg
        msg = (
            "Expected messages did not match actual.\n"
            f"\nExpected:\n{expected}\n\nGot:\n{got_str}\n"
        )

        assert len(messages) == len(got), msg

        for expected_msg, gotten_msg in zip(messages, got):
            assert expected_msg.msg_id == gotten_msg.msg_id, msg
            assert expected_msg.node == gotten_msg.node, msg
            assert expected_msg.args == gotten_msg.args, msg
            assert expected_msg.confidence == gotten_msg.confidence, msg

            if ignore_position:
                # Do not check for line, col_offset etc...
                continue

            assert expected_msg.line == gotten_msg.line, msg
            assert expected_msg.col_offset == gotten_msg.col_offset, msg
            assert expected_msg.end_line == gotten_msg.end_line, msg
            assert expected_msg.end_col_offset == gotten_msg.end_col_offset, msg

    def walk(self, node: NodeNG) -> None:
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.walk`"""
        walker = ASTWalker(linter)
        for checker in self.checkers:
            walker.add_checker(checker)
        walker.walk(node)


if __name__ == '__main__':
    main()
