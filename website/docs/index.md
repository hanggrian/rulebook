Rulebook is an aggresive additional linter ruleset for Java, Groovy, Kotlin and
Python.

[Download JAR](https://repo1.maven.org/maven2/com/hanggrian/rulebook/){ .md-button .md-button--primary }

[Download Egg](https://pypi.org/project/rulebook-pylint/){ .md-button }

## Linter tools

It uses the most popular linter tools for each programming language.

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

    [:material-arrow-right: Maven Plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/)&emsp;[:material-arrow-right: Gradle Plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)

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

    [:material-arrow-right: Maven Plugin](https://gleclaire.github.io/codenarc-maven-plugin/)&emsp;[:material-arrow-right: Gradle Plugin](https://docs.gradle.org/current/userguide/codenarc_plugin.html)

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

    [:material-arrow-right: Gradle Plugin (Unofficial)](https://github.com/JLLeitschuh/ktlint-gradle/)

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

    [:material-arrow-right: PyPI](https://pypi.org/project/pylint/)
</div>

## Compatibility table

List of rules and their current progression in this library.

!!! Legend
    :material-check-all:{ .xxxl } | :material-check:{ .xxxl } | [:material-check:{ .xxxl }](#) | :material-close:{ .xxxl } | |
    :---: | :---: | :---: | :---: | :---:
    Rule is fully supported | Functionality already exists in other rules | Rule is natively available by the linter | Possible but currently not implemented | Not applicable for this language

Rule | Java | Groovy | Kotlin  | Python | Rationale
--- | :---: | :---: | :---: | :---: | ---:
Block tag punctuation | :material-check-all:{ .xl } | :material-close:{ .xl } | :material-check-all:{ .xl } | | :simple-openjdk:{ #oracle .lg } [Javadoc tool](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
Built-in types | | | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/deprecated-typing-alias.html) | :simple-kotlin:{ #kotlin .lg } [Mapped types](https://kotlinlang.org/docs/java-interop.html#mapped-types)
Confusing predicate | | | :material-check-all:{ .xl } | | :simple-kotlin:{ #kotlin .lg } [Filter by predicate](https://kotlinlang.org/docs/collection-filtering.html#filter-by-predicate)
File size | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/sizes/filelength.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-size.html#classsize-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } [Source file organization](https://kotlinlang.org/docs/coding-conventions.html#source-file-organization)
Null equality | | | :material-check-all:{ .xl } | | :simple-kotlin:{ #kotlin .lg } [Structural equality](https://kotlinlang.org/docs/equality.html#structural-equality)
Redundant qualifier | :material-check-all:{ .xl } | :material-check-all:{ .xl } | | | :simple-jetbrains:{ #jetbrains .lg } [Redundant qualifier name](https://www.jetbrains.com/help/inspectopedia/RemoveRedundantQualifierName.html)
TODO comment | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/misc/todocomment.html) | :material-close:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-jetbrains:{ #jetbrains .lg } [TODO comments](https://www.jetbrains.com/help/idea/using-todo.html)
Trailing comma in call | | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#trailing-comma-on-call-site) | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } [Trailing commas](https://kotlinlang.org/docs/coding-conventions.html#trailing-commas)
Trailing comma in collection | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/arraytrailingcomma.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-convention.html#trailingcomma-rule) | | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/refactor/trailing-comma-tuple.html) | :simple-python:{ #python .lg } [When to use trailing commas](https://peps.python.org/pep-0008/#when-to-use-trailing-commas)
Trailing comma in declaration | | | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#trailing-comma-on-declaration-site) | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } Trailing commas
Unused import | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/imports/unusedimports.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-imports.html#unusedimport-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-unused-imports) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/unused-import.html) | :simple-gitbook:{ #common .lg } [Imports not optimized](rationales/#imports-not-optimized)
Wildcard import | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/imports/avoidstarimport.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-imports.html#nowildcardimports-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-wildcard-imports) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/wildcard-import.html) | :simple-google:{ #google .lg } [Import statements](https://google.github.io/styleguide/javaguide.html#s3.3-import-statements)
**Declaring** | :material-language-java:{ .xl } | :simple-apachegroovy:{ .xl } | :material-language-kotlin:{ .xl } | :material-language-python:{ .xl }
Abstract class definition | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-design.html#abstractclasswithoutabstractmethod-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-gitbook:{ #common .lg } [Abstraction not needed](rationales/#abstraction-not-needed)
Contract function definition | | | :material-check-all:{ .xl } | | :simple-kotlin:{ #kotlin .lg } [Kotlin contracts](https://github.com/Kotlin/KEEP/blob/master/proposals/kotlin-contracts.md)
Exception inheritance | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#exceptionextendserror-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#exceptionextendsthrowable-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-openjdk:{ #oracle .lg } [Creating exceptions](https://docs.oracle.com/javase/tutorial/essential/exceptions/throwing.html)
Number suffix for double | :material-check-all:{ .xl } | :material-check-all:{ .xl } | | | :simple-gradle:{ #groovy .lg } [Number type suffixes](https://groovy-lang.org/syntax.html#_number_type_suffixes)
Number suffix for float | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | | :simple-gradle:{ #groovy .lg } Number type suffixes
Number suffix for integer| | :material-check-all:{ .xl } | | | :simple-gradle:{ #groovy .lg } Number type suffixes
Number suffix for long| [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/misc/upperell.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-convention.html#longliteralwithlowercasel-rule) | | | :simple-gradle:{ #groovy .lg } Number type suffixes
String quotes | | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-unnecessary.html#unnecessarygstring-rule) | | :material-check-all:{ .xl } | :simple-google:{ #google .lg } [Use single quotes](https://google.github.io/styleguide/jsguide.html#features-strings-use-single-quotes)
Unnecessary parentheses in lambda | :material-check-all:{ .xl } | :material-check-all:{ .xl } | | | :simple-openjdk:{ #oracle .lg } [Lambda expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
Utility class definition | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/design/hideutilityclassconstructor.html) | :material-check-all:{ .xl } | | | :simple-kotlin:{ #kotlin .lg } [Object declarations](https://kotlinlang.org/docs/object-declarations.html#object-declarations-overview)
**Naming** | :material-language-java:{ .xl } | :simple-apachegroovy:{ .xl } | :material-language-kotlin:{ .xl } | :material-language-python:{ .xl }
Class name acronym | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/abbreviationaswordinname.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } [Choose good names](https://kotlinlang.org/docs/coding-conventions.html#choose-good-names)
Class name | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/typename.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#classname-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#interfacename-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#class-naming) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/convention/invalid-name.html) | :simple-openjdk:{ #oracle .lg } [Naming conventions](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)
Constant property name | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/constantname.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#fieldname-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#propertyname-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#property-naming) | | :simple-openjdk:{ #oracle .lg } Naming conventions
File name | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/misc/outertypefilename.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#classnamesameasfilename-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#file-name) | | :simple-kotlin:{ #kotlin .lg } [Source file names](https://kotlinlang.org/docs/coding-conventions.html#source-file-names)
Identifier name | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/catchparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/lambdaparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/localvariablename.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/localfinalvariablename.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/membername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/methodname.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/parametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/patternvariablename.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/recordcomponentname.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/staticvariablename.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#methodname-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#parametername-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#variablename-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#function-naming) | :material-check:{ .xl } | :simple-openjdk:{ #oracle .lg } Naming conventions
Illegal class final name | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } Choose good names
Illegal variable name | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/illegalidentifiername.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/convention/disallowed-name.html) | :simple-gitbook:{ #common .lg } [Purposeful name](rationales/#purposeful-name)
Package name | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/packagename.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-naming.html#packagename-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#package-name) | :material-check:{ .xl } | :simple-openjdk:{ #oracle .lg } Naming conventions
Property name interop | | | :material-check-all:{ .xl } | | :simple-kotlin:{ #kotlin .lg } [Calling Kotlin from Java](https://kotlinlang.org/docs/java-to-kotlin-interop.html#properties)
Required generic name  | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/classtypeparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/interfacetypeparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/methodtypeparametername.html)[:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/naming/recordtypeparametername.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-openjdk:{ #oracle .lg } [Generic types](https://docs.oracle.com/javase/tutorial/java/generics/types.html)
**Ordering** | :material-language-java:{ .xl } | :simple-apachegroovy:{ .xl } | :material-language-kotlin:{ .xl } | :material-language-python:{ .xl }
Block tag order | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/javadoc/atclauseorder.html) | :material-close:{ .xl } | :material-check-all:{ .xl } | | :simple-android:{ #android .lg } [Block tags](https://developer.android.com/kotlin/style-guide#block_tags)
Built-in function position | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-gitbook:{ #common .lg } [Business logic first](rationales/#business-logic-first)
Import order | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/imports/customimportorder.html) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#import-ordering) | [:material-check:{ .xl }](https://pylint.pycqa.org/en/latest/user_guide/messages/convention/wrong-import-order.html) | :simple-google:{ #google .lg } [Ordering and spacing](https://checkstyle.sourceforge.io/styleguides/google-java-style-20220203/javaguide.html#s3.3.3-import-ordering-and-spacing)
Inner class position | [:material-check:{ .xl }](https://checkstyle.org/checks/design/innertypelast.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } [Class layout](https://kotlinlang.org/docs/coding-conventions.html#class-layout)
Member order | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } Class layout
Overload function position | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/overloadmethodsdeclarationorder.html) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | | :simple-kotlin:{ #kotlin .lg } [Overload layout](https://kotlinlang.org/docs/coding-conventions.html#overload-layout)
Static import position | :material-check:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-imports.html#misorderedstaticimports-rule) | | | :simple-google:{ #google .lg } Ordering and spacing
**Spacing** | :material-language-java:{ .xl } | :simple-apachegroovy:{ .xl } | :material-language-kotlin:{ .xl } | :material-language-python:{ .xl }
Block comment spaces | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/javadoc/javadocmissingwhitespaceafterasterisk.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#spaceaftercommentdelimiter-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#spacebeforecommentdelimiter-rule) | :material-check-all:{ .xl } | | :simple-openjdk:{ #oracle .lg } [Javadoc reference](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html)
Block comment trim | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#javadocemptyfirstline-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#javadocemptylastline-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-gitbook:{ #common .lg } [Clean comment](rationales/#clean-comment)
Block tag indentation | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/javadoc/javadoctagcontinuationindentation.html) | :material-close:{ .xl } | :material-check-all:{ .xl } | | :simple-google:{ #google .lg } [Block tags](https://checkstyle.sourceforge.io/styleguides/google-java-style-20220203/javaguide.html#s7.1.3-javadoc-block-tags)
Case separator | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } [Control flow statements](https://kotlinlang.org/docs/coding-conventions.html#control-flow-statements)
Code block trim | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#blockendswithblankline-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#blockstartswithblankline-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#classendswithblankline-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#classstartswithblankline-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-empty-first-line-at-start-in-class-body)[:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-blank-lines-before) | :material-check-all:{ .xl } | :simple-google:{ #google .lg } [Nonempty blocks](https://google.github.io/styleguide/javaguide.html#s4.1.2-blocks-k-r-style)
Comment spaces | :material-check-all:{ .xl } | :material-check:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#comment-spacing) | [:material-check:{ .xl }](https://peps.python.org/pep-0008/#inline-comments) | :simple-android:{ #android .lg } [Horizontal whitespace](https://developer.android.com/kotlin/style-guide#horizontal)
Comment trim | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-gitbook:{ #common .lg } Clean comment
Duplicate blank line | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#consecutiveblanklines-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-consecutive-blank-lines) | :material-check-all:{ .xl } | :simple-google:{ #google .lg } [Vertical whitespace](https://google.github.io/styleguide/jsguide.html#formatting-vertical-whitespace)
Duplicate blank line in block comment | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-comments.html#javadocconsecutiveemptylines-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-gitbook:{ #common .lg } Clean comment
Duplicate blank line in comment | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-gitbook:{ #common .lg } Clean comment
Member separator | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/whitespace/emptylineseparator.html) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#blank-line-before-declarations) | :material-check-all:{ .xl } | :simple-google:{ #google .lg } Vertical whitespace
Missing blank line before block tags | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/javadoc/requireemptylinebeforeblocktaggroup.html) | :material-close:{ .xl } | :material-check-all:{ .xl } | | :simple-kotlin:{ #kotlin .lg } [KDoc syntax](https://kotlinlang.org/docs/kotlin-doc.html#kdoc-syntax)
Unnecessary blank line before package | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-formatting.html#blanklinebeforepackage-rule) | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-google:{ #google .lg } [Source file structure](https://google.github.io/styleguide/javaguide.html#s3-source-file-structure)
**Stating** | :material-language-java:{ .xl } | :simple-apachegroovy:{ .xl } | :material-language-kotlin:{ .xl } | :material-language-python:{ .xl }
Illegal catch| [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/illegalcatch.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#catcherror-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#catchexception-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#catchthrowable-rule) | | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/bare-except.html) | :simple-openjdk:{ #oracle .lg } [Throwing exceptions](https://docs.oracle.com/javase/tutorial/essential/exceptions/throwing.html)
Illegal throw | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/illegalthrows.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#throwerror-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#throwexception-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-exceptions.html#throwthrowable-rule) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/broad-exception-caught.html) | :simple-openjdk:{ #oracle .lg } Throwing exceptions
Missing braces | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/blocks/needbraces.html) | [:material-check:{ .xl }](https://codenarc.org/codenarc-rules-braces.html#ifstatementbraces-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-braces.html#forstatementbraces-rule)[:material-check:{ .xl }](https://codenarc.org/codenarc-rules-braces.html#whilestatementbraces-rule) | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-if-else)[:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-loop) | | :simple-android:{ #android .lg } [Braces](https://developer.android.com/kotlin/style-guide#braces)
Nested if-else | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-jetbrains:{ #jetbrains .lg } [Invert if statement](https://www.jetbrains.com/help/resharper/InvertIf.html)
Redundant default | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-jetbrains:{ #jetbrains .lg } [Redundant else keyword](https://www.jetbrains.com/help/resharper/RedundantIfElseBlock.html)
Redundant else | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/refactor/no-else-raise.html)[:material-check:{ .xl }](https://pylint.pycqa.org/en/stable/user_guide/messages/refactor/no-else-return.html) | :simple-jetbrains:{ #jetbrains .lg } Redundant else keyword
Unnecessary switch | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :material-check-all:{ .xl } | :simple-jetbrains:{ #jetbrains .lg } [Minimum switch branches](https://www.jetbrains.com/help/inspectopedia/SwitchStatementWithTooFewBranches.html)
**Wrapping** | :material-language-java:{ .xl } | :simple-apachegroovy:{ .xl } | :material-language-kotlin:{ .xl } | :material-language-python:{ .xl }
Assignment wrap | :material-check-all:{ .xl } | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-expression-wrapping) | :material-close:{ .xl } | :simple-google:{ #google .lg } [Where to break](https://google.github.io/styleguide/javaguide.html#s4.5.1-line-wrapping-where-to-break)
Chain call wrap | :material-check-all:{ .xl } | :material-close:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#chain-method-continuation) | :material-close:{ .xl } | :simple-kotlin:{ #kotlin .lg } [Wrap chained calls](https://kotlinlang.org/docs/coding-conventions.html#wrap-chained-calls)
Elvis wrap | | | :material-check-all:{ .xl } | | :simple-gitbook:{ #common .lg } [Trailing elvis](rationales/#trailing-elvis)
Empty code block join | [:material-check:{ .xl }](https://checkstyle.org/checks/regexp/regexpsinglelinejava.html) | :material-close:{ .xl } | :material-check-all:{ .xl } | | :simple-google:{ #google .lg } [Empty blocks](https://google.github.io/styleguide/javaguide.html#s4.1.3-braces-empty-blocks)
Infix call wrap | | | :material-check-all:{ .xl } | | :simple-android:{ #android .lg } [Where to break](https://developer.android.com/kotlin/style-guide#where_to_break)
Lambda wrap | :material-check-all:{ .xl } | :material-close:{ .xl } | | :material-close:{ .xl } | :simple-google:{ #google .lg } Where to break
Operator wrap | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/whitespace/operatorwrap.html) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#condition-wrapping) | :material-close:{ .xl } | :simple-android:{ #android .lg } Where to break
Parameter wrap | :material-check-all:{ .xl } | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#argument-list-wrapping)[:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#parameter-list-wrapping) | :material-check-all:{ .xl } | :simple-kotlin:{ #kotlin .lg } [Class headers](https://kotlinlang.org/docs/coding-conventions.html#class-headers)
Short block comment join | :material-close:{ .xl } | :material-close:{ .xl } | :material-check-all:{ .xl } | :material-close:{ .xl } | :simple-kotlin:{ #kotlin .lg } [Documentation comments](https://kotlinlang.org/docs/coding-conventions.html#documentation-comments)
Statement wrap | [:material-check:{ .xl }](https://checkstyle.sourceforge.io/checks/coding/onestatementperline.html) | :material-check-all:{ .xl } | [:material-check:{ .xl }](https://pinterest.github.io/ktlint/latest/rules/standard/#statement-wrapping) | [:material-check:{ .xl }](https://peps.python.org/pep-0008/#other-recommendations) | :simple-openjdk:{ #oracle .lg } [Simple statements](https://www.oracle.com/java/technologies/javase/codeconventions-statements.html)

## FAQ

### Why is it necessary?

When working on a project with multiple programming languages, we often forget
to apply the same coding style and leave the validation to a linter tool.
However, the default behavior of these linters are not always consistent.
Consider the example below:

<table>
  <thead>
    <tr>
      <th>Java</th>
      <th>Groovy</th>
      <th>Kotlin</th>
      <th>Python</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        Java does not allow trailing commas except in array initializers.
      </td>
      <td>
        Groovy allows trailing commas in call sites, but CodeNarc does not
        natively support it.
      </td>
      <td>
        Trailing commas can be placed in call and declaration sites in Kotlin,
        the rule is provided by Ktlint.
      </td>
      <td>
        Python allows trailing commas but Pylint considers it optional in PEP.
        <i>Note that the comment spacing rule is different in Python.</i>
      </td>
    </tr>
    <tr>
      <td>
```java
void foo(
    int a,
    int b
) {
    bar(
        a,
        b
    )
}
```
      </td>
      <td>
```groovy
def foo(
    int a,
    int b
) {
    bar(
        a,
        b // no!
    )
}
```
      </td>
      <td>
```kotlin
fun foo(
    a: Int,
    b: Int // no!
) =
    bar(
        a,
        b // no!
    )
```
      </td>
      <td>
        <pre>
```python
def foo(
    a: int,
    b: int  # no!
):
    bar(
        a,
        b  # no!
    )
```
      </td>
    </tr>
  </tbody>
</table>

### How stable is it?

The rules are mostly work in progress and have not been tested against a large
codebase. Disable the rules individually if they behave unexpectedly.

=== "checkstyle.xml"
    ```xml
    <!--module name="CommentSpaces"/-->
    ```
=== "codenarc.xml"
    ```xml
    <!--rule class="com.hanggrian.rulebook.codenarc.CommentSpacesRule"/-->
    ```
=== ".editorconfig"
    ```ini
    ktlint_rulebook_comment-spaces = disabled
    ```
=== "pylintrc"
    ```python
    # rulebook_pylint.comment_spaces,
    ```

### What's next?

Although there is no timeline for the roadmap, the following features are
planned:

- More languages:
    - [X] Java
    - [X] Groovy
    - [X] Kotlin
    - [X] Python
    - [ ] JavaScript
    - [ ] TypeScript
