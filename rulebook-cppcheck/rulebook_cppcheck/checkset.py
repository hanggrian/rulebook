from sys import argv

from rulebook_cppcheck.checkers import (
    AbbreviationAsWordChecker,
    AssignmentWrappingChecker,
    BlockCommentSpacingChecker,
    BlockCommentTrimmingChecker,
    BlockTagIndentationChecker,
    BlockTagPunctuationChecker,
    BracesSpacingChecker,
    CaseSeparatorChecker,
    ChainCallWrappingChecker,
    ClassNameChecker,
    CommentSpacingChecker,
    CommentTrimmingChecker,
    FileNameChecker,
    GenericNameChecker,
    IdentifierNameChecker,
    IllegalVariableNameChecker,
    ImportOrderChecker,
    IndentStyleChecker,
    InnerClassPositionChecker,
    LowercaseFChecker,
    LowercaseHexChecker,
    MaxFileSizeChecker,
    MaxLineLengthChecker,
    MemberOrderChecker,
    MemberSeparatorChecker,
    NoBroadCatchChecker,
    NoBroadThrowChecker,
    NoDuplicateBlankLineChecker,
    NoDuplicateBlankLineInBlockCommentChecker,
    NoDuplicateBlankLineInCommentChecker,
    NoDuplicateSpaceChecker,
    NoLonelyCaseChecker,
    NoMeaninglessWordChecker,
    NoRedundantDefaultChecker,
    NoRedundantElseChecker,
    NoRedundantIfChecker,
    NoTrailingSpaceChecker,
    NoUselessReturnChecker,
    OperatorWrappingChecker,
    PackageNameChecker,
    ParameterWrappingChecker,
    ParenthesesClippingChecker,
    ParenthesesTrimmingChecker,
    ShorthandAssignmentChecker,
    TodoCommentStyleChecker,
    TrailingNewlineChecker,
    UppercaseLChecker,
)
from rulebook_cppcheck.checkers.rulebook_checkers import BaseChecker

try:
    from cppcheckdata import CppcheckData
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import CppcheckData

if __name__ == '__main__':
    checkers: list[BaseChecker] = [
        BlockTagPunctuationChecker(),
        TodoCommentStyleChecker(),
        # Clipping
        ParenthesesClippingChecker(),
        # Declaration
        LowercaseFChecker(),
        LowercaseHexChecker(),
        UppercaseLChecker(),
        # Expression
        # Formatting
        IndentStyleChecker(),
        MaxFileSizeChecker(),
        MaxLineLengthChecker(),
        NoTrailingSpaceChecker(),
        TrailingNewlineChecker(),
        # Naming
        AbbreviationAsWordChecker(),
        ClassNameChecker(),
        FileNameChecker(),
        IdentifierNameChecker(),
        IllegalVariableNameChecker(),
        GenericNameChecker(),
        NoMeaninglessWordChecker(),
        PackageNameChecker(),
        # Ordering
        ImportOrderChecker(),
        InnerClassPositionChecker(),
        MemberOrderChecker(),
        # Spacing
        BlockCommentSpacingChecker(),
        BlockTagIndentationChecker(),
        BracesSpacingChecker(),
        CaseSeparatorChecker(),
        CommentSpacingChecker(),
        MemberSeparatorChecker(),
        # Statement
        ShorthandAssignmentChecker(),
        NoBroadCatchChecker(),
        NoBroadThrowChecker(),
        NoLonelyCaseChecker(),
        NoRedundantDefaultChecker(),
        NoRedundantElseChecker(),
        NoRedundantIfChecker(),
        NoUselessReturnChecker(),
        # Trimming
        BlockCommentTrimmingChecker(),
        CommentTrimmingChecker(),
        NoDuplicateBlankLineChecker(),
        NoDuplicateBlankLineInBlockCommentChecker(),
        NoDuplicateBlankLineInCommentChecker(),
        NoDuplicateSpaceChecker(),
        ParenthesesTrimmingChecker(),
        # Wrapping
        AssignmentWrappingChecker(),
        ChainCallWrappingChecker(),
        OperatorWrappingChecker(),
        ParameterWrappingChecker(),
    ]

    system_args: list[str] = argv
    for checker in checkers:
        mapped_args: dict[str, str] = {}
        must_continue: bool = False
        for arg in checker.ARGS:
            for system_arg in system_args.copy():
                if not system_arg.startswith(f'--{arg}='):
                    continue
                mapped_args[arg] = system_arg.split('=')[1]
                system_args.remove(system_arg)
                must_continue = True
            if must_continue:
                continue
        if mapped_args:
            checker.before_run(mapped_args)

    for dump_file in [arg for arg in argv[1:] if arg.endswith('.dump')]:
        for config in CppcheckData(dump_file).iterconfigurations():
            [checker.run_check(config) for checker in checkers]
