## Features

!!! features1 "What is Rulebook?"
    <div class="grid cards" markdown>

    - <div style="text-align: center">:material-puzzle:{ .xxxl #primary }<br><b id="primary">Library extension</b><br>A set of additional rules for static code analysis tools</div>
    </div>
!!! features2 "Features"
    <div class="grid cards" markdown>

    - <div style="text-align: center">:material-translate:{ .xxxl #primary }<br><b id="primary">Multiple languages</b><br>Originally written for Kotlin, it now supports Java, Groovy and Python</div>
    - <div style="text-align: center">:material-creation:{ .xxxl #primary }<br><b id="primary">Follows guidelines</b><br>Works together with standard rules from linter tools and respects common coding convention</div>
    </div>

## Compatibility table

!!! legend
    :material-check-all:{ .xxxl } | :material-check:{ .xxxl } | [:material-check:{ .xxxl }](#) | :material-close:{ .xxxl } | [:material-close:{ .xxxl #accent }](#) | |
    :---: | :---: | :---: | :---: | :---: | :---:
    The rule is fully implemented | Functionality already exists in other rules | The rule is already supported by the linter | It is possible but currently not implemented | It will not be supported due to technical limitations | Not applicable to this language

Rule | Java | Groovy | Kotlin  | Python
--- | :---: | :---: | :---: | :---:
Block tag punctuation[^oracle-javadoc-tool] | :material-check-all:{ .xl } | :material-close:{ .xl } | :material-check-all:{ .xl }
Built-in types[^kotlin-mapped-types] | | | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/deprecated-typing-alias.html)
Confusing predicate[^kotlin-filter-by-predicate] | | | :material-check-all:{ .xl }
File size[^kotlin-source-file-organization] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/sizes/filelength.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-size.html#classsize-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Null equality[^kotlin-structural-equality] | | | :material-check-all:{ .xl }
Redundant qualifier[^jetbrains-remove-redundant-qualifier-name] | :material-check-all:{ .xl } | :material-check-all:{ .xl }
TODO comment[^jetbrains-using-todo] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/misc/todocomment.html) | :material-close:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Trailing comma in call[^kotlin-trailing-commas] | | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#trailing-comma-on-call-site) | :material-check-all:{ .xl }
Trailing comma in collection[^python-when-to-use-trailing-commas] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/arraytrailingcomma.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-convention.html#trailingcomma-rule) | | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/refactor/trailing-comma-tuple.html)
Trailing comma in declaration[^kotlin-trailing-commas] | | | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#trailing-comma-on-declaration-site) | :material-check-all:{ .xl }
Unused import[^common-imports-not-optimized] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/imports/unusedimports.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-imports.html#unusedimport-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-unused-imports) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/unused-import.html)
Wildcard import[^google-import-statements] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/imports/avoidstarimport.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-imports.html#nowildcardimports-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-wildcard-imports) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/wildcard-import.html)
**Declaring group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Abstract class definition[^common-abstraction-not-needed] | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-design.html#abstractclasswithoutabstractmethod-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Contract function definition[^kotlin-kotlin-contracts] | | | :material-check-all:{ .xl }
Exception inheritance[^oracle-throwing] | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#exceptionextendserror-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#exceptionextendsthrowable-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl } |
Number suffix for double[^groovy-number-type-suffixes] | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Number suffix for float[^groovy-number-type-suffixes] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Number suffix for integer[^groovy-number-type-suffixes] | | :material-check-all:{ .xl }
Number suffix for long[^groovy-number-type-suffixes] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/misc/upperell.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-convention.html#longliteralwithlowercasel-rule)
String quotes[^google-features-strings-use-single-quotes] | | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-unnecessary.html#unnecessarygstring-rule) | | :material-check-all:{ .xl }
Unnecessary parentheses in lambda[^oracle-lambdaexpressions] | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Utility class definition[^common-instance-not-allowed] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/design/hideutilityclassconstructor.html) | :material-check-all:{ .xl }
**Naming group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Class name acronym[^kotlin-choose-good-names] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/abbreviationaswordinname.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Class name[^oracle-codeconventions-namingconventions] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/typename.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#classname-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#interfacename-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#class-naming) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/convention/invalid-name.html)
Constant property name[^oracle-codeconventions-namingconventions] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/constantname.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#fieldname-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#propertyname-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#property-naming)
File name[^kotlin-source-file-names] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/misc/outertypefilename.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#classnamesameasfilename-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#file-name)
Identifier name[^oracle-codeconventions-namingconventions] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/catchparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/lambdaparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/localvariablename.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/localfinalvariablename.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/membername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/methodname.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/parametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/patternvariablename.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/recordcomponentname.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/staticvariablename.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#methodname-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#parametername-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#variablename-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#function-naming) | :material-check:{ .xl }
Illegal class final name[^kotlin-choose-good-names] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Illegal variable name[^common-purposeful-name] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/illegalidentifiername.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/convention/disallowed-name.html)
Package name[^oracle-codeconventions-namingconventions] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/packagename.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#packagename-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#package-name) | :material-check:{ .xl }
Property name interop[^kotlin-properties] | | | :material-check-all:{ .xl }
Required generic name[^oracle-types] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/classtypeparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/interfacetypeparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/methodtypeparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/recordtypeparametername.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
**Ordering group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Block tag order[^android-block-tags] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/javadoc/atclauseorder.html) | :material-close:{ .xl } | :material-check-all:{ .xl }
Built-in function position[^common-business-logic-first] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Import order[^google-import-ordering-and-spacing] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/imports/customimportorder.html) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#import-ordering) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/latest/user_guide/messages/convention/wrong-import-order.html)
Inner class position[^kotlin-class-layout] | [:material-check:{ .xl }](https://checkstyle.org/checks/design/innertypelast.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Member order[^kotlin-class-layout] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Overload function position[^kotlin-overload-layout] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/overloadmethodsdeclarationorder.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Static import position[^google-import-ordering-and-spacing] | :material-check:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-imports.html#misorderedstaticimports-rule)
**Spacing group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Block comment spaces[^oracle-javadoc] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/javadoc/javadocmissingwhitespaceafterasterisk.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#spaceaftercommentdelimiter-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#spacebeforecommentdelimiter-rule) | :material-check-all:{ .xl }
Block comment trim[^common-clean-comment] | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#javadocemptyfirstline-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#javadocemptylastline-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Block tag indentation[^google-javadoc-block-tags] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/javadoc/javadoctagcontinuationindentation.html) | :material-close:{ .xl } | :material-check-all:{ .xl }
Case separator[^kotlin-control-flow-statements] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Code block trim[^google-blocks-k-r-style] | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#blockendswithblankline-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#blockstartswithblankline-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#classendswithblankline-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#classstartswithblankline-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-empty-first-line-at-start-in-class-body)[:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-blank-lines-before) | :material-check-all:{ .xl }
Comment spaces[^android-horizontal] | :material-check-all:{ .xl } | :material-check:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#comment-spacing) | [:material-check:{ .xl }](https://peps.python.org/pep-0008/#inline-comments)
Comment trim[^common-clean-comment] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Duplicate blank line[^google-vertical-whitespace] | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#consecutiveblanklines-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-consecutive-blank-lines) | :material-check-all:{ .xl }
Duplicate blank line in block comment[^common-clean-comment] | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#javadocconsecutiveemptylines-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Duplicate blank line in comment[^common-clean-comment] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Member separator[^google-vertical-whitespace] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/whitespace/emptylineseparator.html) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#blank-line-before-declarations) | :material-check-all:{ .xl }
Missing blank line before block tags[^kotlin-kdoc-syntax] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/javadoc/requireemptylinebeforeblocktaggroup.html) | :material-close:{ .xl } | :material-check-all:{ .xl }
Unnecessary blank line before package[^google-source-file-structure] | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#blanklinebeforepackage-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl }
**Stating group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Illegal catch[^oracle-throwing] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/illegalcatch.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#catcherror-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#catchexception-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#catchthrowable-rule) | [:material-close:{ .xl #accent }](https://discuss.kotlinlang.org/t/does-kotlin-have-multi-catch/486/) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/bare-except.html)
Illegal throw[^oracle-throwing] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/illegalthrows.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#throwerror-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#throwexception-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#throwthrowable-rule) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/broad-exception-caught.html)
Missing braces[^android-braces] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/blocks/needbraces.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-braces.html#ifstatementbraces-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-braces.html#forstatementbraces-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-braces.html#whilestatementbraces-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-if-else)[:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-loop)
Nested if-else[^jetbrains-invert-if] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Redundant default[^jetbrains-redundant-if-else-block] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
Redundant else[^jetbrains-redundant-if-else-block] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/refactor/no-else-raise.html)[:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/refactor/no-else-return.html)
Unnecessary switch[^jetbrains-switch-statement-with-too-few-branches] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl }
**Wrapping group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Assignment wrap[^google-line-wrapping-where-to-break] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-expression-wrapping) | :material-close:{ .xl }
Chain call wrap[^kotlin-wrap-chained-calls] | :material-check-all:{ .xl } | :material-close:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#chain-method-continuation) | :material-close:{ .xl }
Elvis wrap[^common-trailing-elvis] | | | :material-check-all:{ .xl }
Empty code block join[^google-braces-empty-blocks] | [:material-check:{ .xl }](https://checkstyle.org/checks/regexp/regexpsinglelinejava.html) | :material-close:{ .xl } | :material-check-all:{ .xl }
Infix call wrap[^android-where-to-break] | | | :material-check-all:{ .xl }
Lambda wrap[^google-line-wrapping-where-to-break] | :material-check-all:{ .xl } | :material-close:{ .xl } | | :material-close:{ .xl }
Operator wrap[^android-where-to-break] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/whitespace/operatorwrap.html) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#condition-wrapping) | :material-close:{ .xl }
Parameter wrap[^kotlin-class-headers] | :material-check-all:{ .xl } | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#argument-list-wrapping)[:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#parameter-list-wrapping) | :material-check-all:{ .xl }
Short block comment join[^kotlin-documentation-comments] | :material-close:{ .xl } | :material-close:{ .xl } | :material-check-all:{ .xl } | :material-close:{ .xl }
Statement wrap[^codeconventions-statements] | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/onestatementperline.html) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#statement-wrapping) | [:material-check:{ .xl }](https://peps.python.org/pep-0008/#other-recommendations)

## Download

!!! download1 "Integrate the linter tools"
    <div class="grid cards" markdown>

    -   :material-language-java:{ .lg .middle } **Java**

        ---

        <a href="https://checkstyle.org/">
          <img
            width="45px"
            alt="Checkstyle Logo"
            title="Checkstyle Logo"
            src="images/checkstyle.png">
        </a>

        Checkstyle is a development tool to help programmers write Java code that
        adheres to a coding standard.

        [Maven Plugin :material-open-in-new:](https://maven.apache.org/plugins/maven-checkstyle-plugin/)&emsp;[Gradle Plugin :material-open-in-new:](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)

    -   :simple-apachegroovy:{ .lg .middle } **Groovy**

        ---

        <a href="https://codenarc.org/">
          <img
            width="100px"
            alt="CodeNarc Logo"
            title="CodeNarc Logo"
            src="images/codenarc.png">
        </a>

        CodeNarc is similar to popular static analysis tools such as PMD or Checkstyle.

        [Maven Plugin :material-open-in-new:](https://gleclaire.github.io/codenarc-maven-plugin/)&emsp;[Gradle Plugin :material-open-in-new:](https://docs.gradle.org/current/userguide/codenarc_plugin.html)

    -   :material-language-kotlin:{ .lg .middle } **Kotlin**

        ---

        <a href="https://pinterest.github.io/ktlint/">
          <img
            width="120px"
            alt="Ktlint Logo"
            title="Ktlint Logo"
            src="images/ktlint.svg">
        </a>

        An anti-bikeshedding Kotlin linter with built-in formatter.

        [Gradle Plugin (Unofficial) :material-open-in-new:](https://github.com/JLLeitschuh/ktlint-gradle/)

    -   :material-language-python:{ .lg .middle } **Python**

        ---

        <a href="https://www.pylint.org/">
            <img
              width="110px"
              alt="Pylint Logo"
              title="Pylint Logo"
              src="images/pylint.png">
        </a>

        It's not just a linter that annoys you!

        [PyPI :material-open-in-new:](https://pypi.org/project/pylint/)
    </div>
!!! download2 "Download the library"
    [:simple-apachemaven:{ .lg .middle } Maven](https://repo1.maven.org/maven2/com/hanggrian/rulebook/){ .md-button .md-button--primary }&emsp;[:simple-pypi:{ .lg .middle } PyPI](https://pypi.org/project/rulebook-pylint/){ .md-button }

[^common-abstraction-not-needed]: [Abstraction not needed](rationales/index.md#abstraction-not-needed)
[^common-business-logic-first]: [Business logic first](rationales/index.md#business-logic-first)
[^common-clean-comment]: [Clean comment](rationales/index.md#clean-comment)
[^common-imports-not-optimized]: [Imports not optimized](rationales/index.md#imports-not-optimized)
[^common-instance-not-allowed]: [Instance not allowed](rationales/index.md#instance-not-allowed)
[^common-purposeful-name]: [Purposeful name](rationales/index.md#purposeful-name)
[^common-trailing-elvis]: [Trailing elvis](rationales/index.md#trailing-elvis)
[^android-block-tags]: :simple-android:{ #android .lg } [Android: Block tags &nearr;](https://developer.android.com/kotlin/style-guide#block_tags)
[^android-braces]: :simple-android:{ #android .lg } [Android: Braces &nearr;](https://developer.android.com/kotlin/style-guide#braces)
[^android-horizontal]: :simple-android:{ #android .lg } [Android: Horizontal whitespace &nearr;](https://developer.android.com/kotlin/style-guide#horizontal)
[^android-where-to-break]: :simple-android:{ #android .lg } [Android: Where to break &nearr;](https://developer.android.com/kotlin/style-guide#where_to_break)
[^google-blocks-k-r-style]: :simple-google:{ #google .lg } [Google: Nonempty blocks &nearr;](https://google.github.io/styleguide/javaguide.html#s4.1.2-blocks-k-r-style)
[^google-braces-empty-blocks]: :simple-google:{ #google .lg } [Google: Empty blocks &nearr;](https://google.github.io/styleguide/javaguide.html#s4.1.3-braces-empty-blocks)
[^google-features-strings-use-single-quotes]: :simple-google:{ #google .lg } [Google: Use single quotes &nearr;](https://google.github.io/styleguide/jsguide.html#features-strings-use-single-quotes)
[^google-javadoc-block-tags]: :simple-google:{ #google .lg } [Google: Block tags &nearr;](https://checkstyle.sourceforge.io/styleguides/google-java-style-20220203/javaguide.html#s7.1.3-javadoc-block-tags)
[^google-line-wrapping-where-to-break]: :simple-google:{ #google .lg } [Google: Where to break &nearr;](https://google.github.io/styleguide/javaguide.html#s4.5.1-line-wrapping-where-to-break)
[^google-import-ordering-and-spacing]: :simple-google:{ #google .lg } [Google: Ordering and spacing &nearr;](https://checkstyle.sourceforge.io/styleguides/google-java-style-20220203/javaguide.html#s3.3.3-import-ordering-and-spacing)
[^google-import-statements]: :simple-google:{ #google .lg } [Google: Import statements &nearr;](https://google.github.io/styleguide/javaguide.html#s3.3-import-statements)
[^google-source-file-structure]: :simple-google:{ #google .lg } [Google: Source file structure &nearr;](https://google.github.io/styleguide/javaguide.html#s3-source-file-structure)
[^google-vertical-whitespace]: :simple-google:{ #google .lg } [Google: Vertical whitespace &nearr;](https://google.github.io/styleguide/jsguide.html#formatting-vertical-whitespace)
[^groovy-number-type-suffixes]: :simple-gradle:{ #groovy .lg } [Groovy: Number type suffixes &nearr;](https://groovy-lang.org/syntax.html#_number_type_suffixes)
[^jetbrains-invert-if]: :simple-jetbrains:{ #jetbrains .lg } [JetBrains: Invert if statement &nearr;](https://www.jetbrains.com/help/resharper/InvertIf.html)
[^jetbrains-redundant-if-else-block]: :simple-jetbrains:{ #jetbrains .lg } [JetBrains: Redundant else keyword &nearr;](https://www.jetbrains.com/help/resharper/RedundantIfElseBlock.html)
[^jetbrains-remove-redundant-qualifier-name]: :simple-jetbrains:{ #jetbrains .lg } [JetBrains: Redundant qualifier name &nearr;](https://www.jetbrains.com/help/inspectopedia/RemoveRedundantQualifierName.html)
[^jetbrains-switch-statement-with-too-few-branches]: :simple-jetbrains:{ #jetbrains .lg } [JetBrains: Minimum switch branches &nearr;](https://www.jetbrains.com/help/inspectopedia/SwitchStatementWithTooFewBranches.html)
[^jetbrains-using-todo]: :simple-jetbrains:{ #jetbrains .lg } [JetBrains: TODO comments &nearr;](https://www.jetbrains.com/help/idea/using-todo.html)
[^kotlin-choose-good-names]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Choose good names &nearr;](https://kotlinlang.org/docs/coding-conventions.html#choose-good-names)
[^kotlin-class-headers]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Class headers &nearr;](https://kotlinlang.org/docs/coding-conventions.html#class-headers)
[^kotlin-class-layout]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Class layout &nearr;](https://kotlinlang.org/docs/coding-conventions.html#class-layout)
[^kotlin-control-flow-statements]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Control flow statements &nearr;](https://kotlinlang.org/docs/coding-conventions.html#control-flow-statements)
[^kotlin-documentation-comments]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Documentation comments &nearr;](https://kotlinlang.org/docs/coding-conventions.html#documentation-comments)
[^kotlin-filter-by-predicate]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Filter by predicate &nearr;](https://kotlinlang.org/docs/collection-filtering.html#filter-by-predicate)
[^kotlin-kdoc-syntax]: :simple-kotlin:{ #kotlin .lg } [Kotlin: KDoc syntax &nearr;](https://kotlinlang.org/docs/kotlin-doc.html#kdoc-syntax)
[^kotlin-kotlin-contracts]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Kotlin contracts &nearr;](https://github.com/Kotlin/KEEP/blob/master/proposals/kotlin-contracts.md)
[^kotlin-mapped-types]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Mapped types &nearr;](https://kotlinlang.org/docs/java-interop.html#mapped-types)
[^kotlin-overload-layout]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Overload layout &nearr;](https://kotlinlang.org/docs/coding-conventions.html#overload-layout)
[^kotlin-properties]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Calling Kotlin from Java &nearr;](https://kotlinlang.org/docs/java-to-kotlin-interop.html#properties)
[^kotlin-source-file-names]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Source file names &nearr;](https://kotlinlang.org/docs/coding-conventions.html#source-file-names)
[^kotlin-source-file-organization]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Source file organization &nearr;](https://kotlinlang.org/docs/coding-conventions.html#source-file-organization)
[^kotlin-structural-equality]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Structural equality &nearr;](https://kotlinlang.org/docs/equality.html#structural-equality)
[^kotlin-trailing-commas]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Trailing commas](https://kotlinlang.org/docs/coding-conventions.html#trailing-commas)
[^kotlin-wrap-chained-calls]: :simple-kotlin:{ #kotlin .lg } [Kotlin: Wrap chained calls &nearr;](https://kotlinlang.org/docs/coding-conventions.html#wrap-chained-calls)
[^oracle-codeconventions-namingconventions]: :simple-openjdk:{ #oracle .lg } [Oracle: Naming conventions &nearr;](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)
[^codeconventions-statements]: :simple-openjdk:{ #oracle .lg } [Oracle: Simple statements &nearr;](https://www.oracle.com/java/technologies/javase/codeconventions-statements.html)
[^oracle-javadoc]: :simple-openjdk:{ #oracle .lg } [Oracle: Javadoc reference &nearr;](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html)
[^oracle-javadoc-tool]: :simple-openjdk:{ #oracle .lg } [Oracle: Javadoc tool &nearr;](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
[^oracle-lambdaexpressions]: :simple-openjdk:{ #oracle .lg } [Oracle: Lambda expressions &nearr;](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
[^oracle-throwing]: :simple-openjdk:{ #oracle .lg } [Oracle: How to Throw Exceptions &nearr;](https://docs.oracle.com/javase/tutorial/essential/exceptions/throwing.html)
[^oracle-types]: :simple-openjdk:{ #oracle .lg } [Oracle: Generic types &nearr;](https://docs.oracle.com/javase/tutorial/java/generics/types.html)
[^python-when-to-use-trailing-commas]: :simple-python:{ #python .lg } [Python: When to use trailing commas &nearr;](https://peps.python.org/pep-0008/#when-to-use-trailing-commas)
