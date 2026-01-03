### Block tag punctuation

Description of certain block tags, if present, should end with a period,
question mark or exclamation mark.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java title="Before" hl_lines="3"
    /**
    * @param num
    * @return the new size of the group
    */
    abstract int add(int num);
    ```
=== "Groovy"
    ```groovy title="Before" hl_lines="3"
    /**
    * @param num
    * @return the new size of the group
    */
    abstract def add(int num)
    ```
=== "Kotlin"
    ```kotlin title="Before" hl_lines="3"
    /**
    * @param num
    * @return the new size of the group
    */
    abstract fun add(int num): Int
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java title="After" hl_lines="3"
    /**
    * @param num
    * @return the new size of the group.
    */
    abstract int add(int num);
    ```
=== "Groovy"
    ```groovy title="After" hl_lines="3"
    /**
    * @param num
    * @return the new size of the group.
    */
    abstract def add(int num)
    ```
=== "Kotlin"
    ```kotlin title="After" hl_lines="3"
    /**
    * @param num
    * @return the new size of the group.
    */
    abstract fun add(int num): Int
    ```

??? Configuration
    :material-language-java:{ .lg .middle } Checkstyle | Default value
    --- | ---
    BlockTagPunctuation#tags | `@param, @return`
    **:material-language-kotlin:{ .lg .middle } Ktlint**
    rulebook_punctuate_block_tags | `@constructor, @receiver, @property, @param, @return`

### Built-in types

Prefer to use built-in types provided by the language.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1 4"
    import java.util.ArrayList

    val names = arrayListOf<String>()
    val people = java.util.ArrayList<Person>()
    ```
=== "Python"
    ```python hl_lines="1 3"
    from typing import Optional

    def get_name(person) -> Optional[str]:
        return person['name']
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="2"
    val names = arrayListOf<String>()
    val people = ArrayList<Person>()
    ```
=== "Python"
    ```python hl_lines="1"
    def get_name(person) -> str | None:
        return person['name']
    ```

### Confusing predicate

Use the positive form in a predicate call when it is a single expression and the
calling function can be inverted.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    person.takeIf { it.name != null }
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    person.takeUnless { it.name == null }
    ```

### Null equality

Use structural equality instead of referential equality when comparing objects
with `null`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    user.takeUnless { it.name === null }
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    user.takeUnless { it.name == null }
    ```

### Redundant qualifier

Strip fully qualified names when they are already imported.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="3"
    import java.io.FileInputStream;

    void read(java.io.FileInputStream stream) {}
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    import java.io.FileInputStream

    def read(java.io.FileInputStream stream) {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="3"
    import java.io.FileInputStream;

    void read(FileInputStream stream) {}
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    import java.io.FileInputStream

    def read(FileInputStream stream) {}
    ```

### TODO comment

TODO comment keywords should be uppercase and followed by exactly one space.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    // todo add tests
    //
    // FIXME: memory leak
    ```
=== "Groovy"
    ```groovy
    // todo add tests
    //
    // FIXME: memory leak
    ```
=== "Kotlin"
    ```kotlin
    // todo add tests
    //
    // FIXME: memory leak
    ```
=== "Python"
    ```python
    # todo add tests
    #
    # FIXME: memory leak
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    // TODO add tests
    //
    // FIXME memory leak
    ```
=== "Groovy"
    ```groovy
    // TODO add tests
    //
    // FIXME memory leak
    ```
=== "Kotlin"
    ```kotlin
    // TODO add tests
    //
    // FIXME memory leak
    ```
=== "Python"
    ```python
    # TODO add tests
    #
    # FIXME memory leak
    ```

### Trailing comma in call

Put a trailing comma in a multiline call site, omit when it is a single line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"
    ```groovy hl_lines="4 7"
    var items =
        Arrays.asList(
            'milks',
            'eggs'
        )

    println(items,)
    ```
=== "Kotlin"
    ```kotlin hl_lines="4 7"
    val items =
        listOf(
            "milks",
            "eggs"
        )

    println(items,)
    ```
=== "Python"
    ```python hl_lines="4 7"
    items = \
        set(
            'milks',
            'eggs'
        )

    print(items,)
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"
    ```groovy hl_lines="4 7"
    var items =
        Arrays.asList(
            'milks',
            'eggs',
        )

    println(items)
    ```
=== "Kotlin"
    ```kotlin hl_lines="4 7"
    val items =
        listOf(
            "milks",
            "eggs",
        )

    println(items)
    ```
=== "Python"
    ```python hl_lines="4 7"
    items = \
        set(
            'milks',
            'eggs',
        )

    print(items)
    ```

### Trailing comma in collection

Put a trailing comma in a multiline collection site, omit when it is a single
line. In Java and Groovy, this rule applies to array initializers. In Python,
this rule applies to tuples.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2-4"
    int[][] ticTacToe = {
        {0, 0, 0,},
        {0, 0, 0,},
        {0, 0, 0,}
    };
    ```
=== "Groovy"
    ```groovy hl_lines="2-4"
    var ticTacToe = [
        [0, 0, 0,],
        [0, 0, 0,],
        [0, 0, 0,]
    ]
    ```
=== "Python"
    ```python hl_lines="2-4"
    tic_tac_toe = (
        (0, 0, 0,),
        (0, 0, 0,),
        (0, 0, 0,)
    )
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2-4"
    int[][] ticTacToe = {
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0},
    };
    ```
=== "Groovy"
    ```groovy hl_lines="2-4"
    var ticTacToe = [
        [0, 0, 0],
        [0, 0, 0],
        [0, 0, 0],
    ]
    ```
=== "Python"
    ```python hl_lines="2-4"
    tic_tac_toe = (
        (0, 0, 0),
        (0, 0, 0),
        (0, 0, 0),
    )
    ```

### Trailing comma in declaration

Put a trailing comma in a multiline declaration site, omit when it is a single
line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1 5"
    fun updateInventory(item: String,) {}

    fun createInventory(
        item: String,
        quantity: Int
    ) {}
    ```
=== "Python"
    ```python hl_lines="1 7"
    def update_inventory(item: str,):
        pass


    def create_inventory(
        item: str,
        quantity: int
    ):
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="1 5"
    fun updateInventory(item: String) {}

    fun createInventory(
        item: String,
        quantity: Int,
    ) {}
    ```
=== "Python"
    ```python hl_lines="1 7"
    def update_inventory(item: str):
        pass


    def create_inventory(
        item: str,
        quantity: int,
    )
    ```

### Unused import

Remove unused import statements.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2"
    import com.example.fruit.Apple;
    import com.example.fruit.Banana;

    Apple apple = new Apple();
    ```
=== "Groovy"
    ```groovy hl_lines="2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    var apple = new Apple()
    ```
=== "Kotlin"
    ```kotlin hl_lines="2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    val apple = Apple()
    ```
=== "Python"
    ```python hl_lines="1"
    from fruit import Apple, Banana

    apple = Apple()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    import com.example.fruit.Apple;

    Apple apple = new Apple();
    ```
=== "Groovy"
    ```groovy
    import com.example.fruit.Apple

    var apple = new Apple()
    ```
=== "Kotlin"
    ```kotlin
    import com.example.fruit.Apple

    val apple = Apple()
    ```
=== "Python"
    ```python hl_lines="1"
    from fruit import Apple

    apple = Apple()
    ```

### Wildcard import

Import directives must be single-type instead of wildcard imports.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1"
    import com.example.fruit.*;

    List<Fruit> fruits = Arrays.asList(new Apple(), new Banana());
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    import com.example.fruit.*

    var fruits = [new Apple(), new Banana()]
    ```
=== "Kotlin"
    ```kotlin hl_lines="1"
    import com.example.fruit.*

    val fruits = listOf(Apple(), Banana())
    ```
=== "Python"
    ```python hl_lines="1"
    from fruit import *

    fruits = [Apple(), Banana()]
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1-2"
    import com.example.fruit.Apple;
    import com.example.fruit.Banana;

    List<Fruit> fruits = Arrays.asList(new Apple(), new Banana());
    ```
=== "Groovy"
    ```groovy hl_lines="1-2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    var fruits = [new Apple(), new Banana()]
    ```
=== "Kotlin"
    ```kotlin hl_lines="1-2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    val fruits = listOf(Apple(), Banana())
    ```
=== "Python"
    ```python hl_lines="1"
    from fruit import Apple, Banana

    fruits = [Apple(), Banana()]
    ```

## Clipping

### Empty braces clip

Empty code blocks should be joined with the preceding code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2"
    void main() {
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2"
    def main() {
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2"
    fun main() {
    }
    ```
=== "Python"
    ```python hl_lines="2"
    foo = {
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1"
    void main() {}
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    def main() {}
    ```
=== "Kotlin"
    ```kotlin hl_lines="1"
    fun main() {}
    ```
=== "Python"
    ```python hl_lines="1"
    foo = {}
    ```

### Empty brackets clip

Empty collection initializers should be joined with the preceding code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"
    ```groovy hl_lines="2"
    var items = [
    ]
    ```
=== "Python"
    ```python hl_lines="2"
    items = [
    ]
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"
    ```groovy hl_lines="1"
    var items = []
    ```
=== "Python"
    ```python hl_lines="1"

### Empty parentheses clip

Empty method declarations and calls should be joined with the preceding code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2 4"
    void recurse(
    ) {
        recurse(
        );
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2 4"
    def recurse(
    ) {
        recurse(
        )
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2 4"
    fun recurse(
    ) {
        recurse(
        )
    }
    ```
=== "Python"
    ```python hl_lines="2 4"
    def recurse(
    ):
        recurse(
        )
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1-2"
    void recurse() {
        recurse();
    }
    ```
=== "Groovy"
    ```groovy hl_lines="1-2"
    def recurse() {
        recurse()
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="1-2"
    fun recurse() {
        recurse()
    }
    ```
=== "Python"
    ```python hl_lines="1-2"
    def recurse():
        recurse()
    ```

### Empty tags clip

Empty generic types should be joined with the preceding code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2-3"
    List<Float> points =
        new ArrayList<
          >();
    ```
=== "Groovy"
    ```groovy hl_lines="2"
    var points =
        new ArrayList< >()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2"
    List<Float> points =
        new ArrayList<>();
    ```
=== "Groovy"
    ```groovy hl_lines="2"
    var points =
        new ArrayList<>()
    ```

### Short block comment clip

Short block comments should be written in a single line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```
=== "Groovy"
    ```groovy
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```
=== "Kotlin"
    ```kotlin
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```
=== "Python"
    ```python
    """
    The quick brown fox jumps over the lazy dog.
    """
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    /** The quick brown fox jumps over the lazy dog. */
    ```
=== "Groovy"
    ```groovy
    /** The quick brown fox jumps over the lazy dog. */
    ```
=== "Kotlin"
    ```kotlin
    /** The quick brown fox jumps over the lazy dog. */
    ```
=== "Python"
    ```python
    """The quick brown fox jumps over the lazy dog."""
    ```

## Declaring

### Abstract class definition

Abstract classes need at least one abstract function.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1"
    abstract class Vehicle {
        void start() {}
    }
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    abstract class Vehicle {
        def start() {}
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="1"
    abstract class Vehicle {
        fun start() {}
    }
    ```
=== "Python"
    ```python hl_lines="3"
    from abc import ABC

    class Vehicle(ABC):
        def start(self):
            pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1"
    class Vehicle {
        void start() {}
    }
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    class Vehicle {
        def start() {}
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="1"
    class Vehicle {
        fun start() {}
    }
    ```
=== "Python"
    ```python hl_lines="1"
    class Vehicle:
        def start(self):
            pass
    ```

### Contract function definition

Kotlin contract functions that carry a runnable parameter should have `inline`
modifier. Without the modifier, user cannot assign a global variable within the
code block.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1"
    fun action(block: () -> Unit) {
        contract { callsInPlace(block, EXACTLY_ONCE) }
        block()
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="1"
    inline fun action(block: () -> Unit) {
        contract { callsInPlace(block, EXACTLY_ONCE) }
        block()
    }
    ```

### Exception inheritance

Use `Exception` as superclass of custom exceptions. Most applications should not
extend `Error` or `Throwable`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    class PurchaseException extends Error {}
    ```
=== "Groovy"
    ```groovy
    class PurchaseException extends Error {}
    ```
=== "Kotlin"
    ```kotlin
    class PurchaseException : Error()
    ```
=== "Python"
    ```python
    class PurchaseException(BaseException):
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    class PurchaseException extends Exception {}
    ```
=== "Groovy"
    ```groovy
    class PurchaseException extends Exception {}
    ```
=== "Kotlin"
    ```kotlin
    class PurchaseException : Exception()
    ```
=== "Python"
    ```python
    class PurchaseException(Exception):
        pass
    ```

### Number suffix for double

Double floating point literals should be suffixed with lowercase `d`, which is
more readable than `D`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    double quarter = 0.25D;
    ```
=== "Groovy"
    ```groovy
    var quarter = 0.25D
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    double quarter = 0.25d;
    ```
=== "Groovy"
    ```groovy
    var quarter = 0.25d
    ```

### Number suffix for float

Floating point literals should be suffixed with lowercase `f`, which is more
readable than `F`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    float half = 0.5F;
    ```
=== "Groovy"
    ```groovy
    var quarter = 0.25F
    ```
=== "Kotlin"
    ```kotlin
    val half = 0.5F
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    float half = 0.5f;
    ```
=== "Groovy"
    ```groovy
    var quarter = 0.25f
    ```
=== "Kotlin"
    ```kotlin
    val half = 0.5f
    ```

### Number suffix for integer

Integer literals should be suffixed with lowercase `i`, which is more readable
than `I`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"
    ```groovy
    var ten = 10I
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"
    ```groovy
    var ten = 10i
    ```

### Number suffix for long

Long integer literals should be suffixed with uppercase `L`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    long tenMillion = 10_000_000l;
    ```
=== "Groovy"
    ```groovy
    var tenMillion = 10_000_000l
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    long tenMillion = 10_000_000L;
    ```
=== "Groovy"
    ```groovy
    var tenMillion = 10_000_000L
    ```

### String quotes

Wrap string in single quotes, unless there is a template or a single quote.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"
    ```groovy
    var name = "John Doe"

    println('G\'day, ' + name)
    ```
=== "Python"
    ```python
    name = "John Doe"

    print('G\'day, ' + name)
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"
    ```groovy
    var name = 'John Doe'

    println("G'day, " + name)
    ```
=== "Python"
    ```python
    name = 'John Doe'

    print("G'day, " + name)
    ```

### Unnecessary parentheses in lambda

Single parameter lambdas should not have parentheses.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    files.forEach((file) -> System.out.println(file));
    ```
=== "Groovy"
    ```groovy
    files.forEach((file) -> System.out.println(file))
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    files.forEach(file -> System.out.println(file));
    ```
=== "Groovy"
    ```groovy
    files.forEach(file -> System.out.println(file))
    ```

!!! tip
    Parentheses on lambda parameters is a syntax error in:

    - Kotlin if parameter is single
    - Groovy closures, not lambdas

Use single quotes for string literals, unless the string contains single quotes.

### Utility class definition

Utility classes should have a final modifier and a private constructor to
prevent instantiation.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1"
    class Lists {
        static List<String> of(String... elements) {
            return Arrays.asList(elements);
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    class Lists {
        static List<String> of(String... elements) {
            return Arrays.asList(elements)
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1-2"
    final class Lists {
        private Lists() {}

        static List<String> of(String... elements) {
            return Arrays.asList(elements);
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="1-2"
    final class Lists {
        private Lists() {}

        static List<String> of(String... elements) {
            return Arrays.asList(elements)
        }
    }
    ```

## Formatting

### File size

File length should not be longer than 1.000 lines of code. If a file exceeds the
limit, it should be split into multiple files.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    final class Articles {
        static int create(Article article) { /*...*/ }

        static Article read(int articleId) { /*...*/ }

        static void update(int articleId, Article article) { /*...*/ }

        static void delete(int articleId) { /*...*/ }
    }
    ```
=== "Groovy"
    ```groovy
    final class Articles {
        static def create(Article article) { /*...*/ }

        static def read(int articleId) { /*...*/ }

        static def update(int articleId, Article article) { /*...*/ }

        static def delete(int articleId) { /*...*/ }
    }
    ```
=== "Kotlin"
    ```kotlin
    object Articles {
        fun create(article: Article): Int { /*...*/ }

        fun read(articleId: Int): Article { /*...*/ }

        fun update(articleId: Int, article: Article) { /*...*/ }

        fun delete(articleId: Int) { /*...*/ }
    }
    ```
=== "Python"
    ```python
    def create_article(article: Article) -> int:
        // ...

    def read_article(article_id: int) -> Article:
        // ...

    def update_article(article_id: int, article: Article):
        // ...

    def delete_article(article_id: int):
        // ...
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    final class ArticleCreator {
        static int create(Article article) { /*...*/ }
    }
    ```
    ```java
    final class ArticleReader {
        static Article read(int articleId) { /*...*/ }
    }
    ```
    ```java
    final class ArticleUpdater {
        static void update(int articleId, Article article) { /*...*/ }
    }
    ```
    ```java
    final class ArticleDeleter {
        static void delete(int articleId) { /*...*/ }
    }
    ```
=== "Groovy"
    ```groovy
    final class ArticleCreator {
        static def create(Article article) { /*...*/ }
    }
    ```
    ```groovy
    final class ArticleReader {
        static def read(int articleId) { /*...*/ }
    }
    ```
    ```groovy
    final class ArticleUpdater {
        static def update(int articleId, Article article) { /*...*/ }
    }
    ```
    ```groovy
    final class ArticleDeleter {
        static def delete(int articleId) { /*...*/ }
    }
    ```
=== "Kotlin"
    ```kotlin
    fun Articles.create(article: Article): Int { /*...*/ }
    ```
    ```kotlin
    fun Articles.read(articleId: Int): Article { /*...*/ }
    ```
    ```kotlin
    fun Articles.update(articleId: Int, article: Article) { /*...*/ }
    ```
    ```kotlin
    fun Articles.delete(articleId: Int) { /*...*/ }
    ```
=== "Python"
    ```python
    def create_article(article: Article) -> int:
        // ...
    ```
    ```python
    def read_article(article_id: int) -> Article:
        // ...
    ```
    ```python
    def update_article(article_id: int, article: Article):
        // ...
    ```
    ```python
    def delete_article(article_id: int):
        // ...
    ```

??? Configuration
    :material-language-java:{ .lg .middle } Checkstyle | Default value
    --- | ---
    FileLength#max | `1.000`
    **:simple-apachegroovy:{ .lg .middle } CodeNarc**
    ClassSize#maxLines | `1.000`
    **:material-language-kotlin:{ .lg .middle } Ktlint**
    rulebook_max_file_size | `1.000`
    **:material-language-python: Pylint**
    rulebook-max-file-size | `1.000`

### Final newline

End files with a newline character.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    class AwesomeImplementation { /*...*/ }
    ```
=== "Groovy"
    ```groovy
    class AwesomeImplementation { /*...*/ }
    ```
=== "Kotlin"
    ```kotlin
    class AwesomeImplementation { /*...*/ }
    ```
=== "Python"
    ```python
    class AwesomeImplementation:
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```
=== "Groovy"
    ```groovy hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```
=== "Kotlin"
    ```kotlin hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```
=== "Python"
    ```python hl_lines="3"
    class AwesomeImplementation:
        pass
    \n
    ```

### Indent style

Use spaces instead of tabs for indentation.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2-4"
    class AwesomeImplementation {
    \t  void doSomething() {
    \t\t    expectCoolness();
    \t  }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2-4"
    class AwesomeImplementation {
    \t  def doSomething() {
    \t\t    expectCoolness()
    \t  }
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2-4"
    class AwesomeImplementation {
    \t  fun doSomething() {
    \t\t    expectCoolness()
    \t  }
    }
    ```
=== "Python"
    ```python hl_lines="2-3"
    class AwesomeImplementation:
    \t  def do_something(self):
    \t\t    expect_coolness()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2-4"
    class AwesomeImplementation {
        void doSomething() {
            expectCoolness();
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2-4"
    class AwesomeImplementation {
        def doSomething() {
            expectCoolness()
        }
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2-4"
    class AwesomeImplementation {
        fun doSomething() {
            expectCoolness()
        }
    }
    ```
=== "Python"
    ```python hl_lines="2-3"
    class AwesomeImplementation:
        def do_something(self):
            expect_coolness()
    ```

### Line length

Length of a line should not exceed 100 characters.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    StringBuilder builder = new StringBuilder("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    ```
=== "Groovy"
    ```groovy
    var builder = new StringBuilder('Lorem ipsum dolor sit amet, consectetur adipiscing elit.')
    ```
=== "Kotlin"
    ```kotlin
    val builder = StringBuilder("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    ```
=== "Python"
    ```python
    builder = ('Lorem ipsum dolor sit amet, consectetur adipiscing elit.')
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    StringBuilder builder =
        new StringBuilder(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        );
    ```
=== "Groovy"
    ```groovy
    var builder =
        new StringBuilder(
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
        )
    ```
=== "Kotlin"
    ```kotlin
    val builder =
        StringBuilder(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        )
    ```
=== "Python"
    ```python
    builder = (
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
    )
    ```

??? Configuration
    :material-language-java:{ .lg .middle } Checkstyle | Default value
    --- | ---
    LineLength#max | `100`
    **:simple-apachegroovy:{ .lg .middle } CodeNarc**
    LineLength#length | `100`
    **:material-language-kotlin:{ .lg .middle } Ktlint**
    rulebook_max_line_length | `100`
    **:material-language-python: Pylint**
    rulebook-max-line-length | `100`

## Naming

### Class name abbreviation

Ensures that the first letter of acronyms longer than three characters are
always capitalized.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    class RestAPI {
        String httpURL = "https://example.com";
    }
    ```
=== "Groovy"
    ```groovy
    class RestAPI {
        var httpURL = 'https://example.com'
    }
    ```
=== "Python"
    ```python
    class RestAPI:
        http_url = 'https://example.com'
    ```
=== "Kotlin"
    ```kotlin
    class RestAPI {
        val httpURL = "https://example.com"
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    class RestApi {
        String httpUrl = "https://example.com";
    }
    ```
=== "Groovy"
    ```groovy
    class RestApi {
        var httpUrl = 'https://example.com'
    }
    ```
=== "Python"
    ```python
    class RestApi:
        http_url = 'https://example.com'
    ```
=== "Kotlin"
    ```kotlin
    class RestApi {
        val httpUrl = "https://example.com"
    }
    ```

### Class name

Class, interface and object names are written in `PascalCase`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    class train_station {}
    ```
=== "Groovy"
    ```groovy
    class train_station {}
    ```
=== "Python"
    ```python
    class train_station:
        pass
    ```
=== "Kotlin"
    ```kotlin
    class train_station
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    class TrainStation {}
    ```
=== "Groovy"
    ```groovy
    class TrainStation {}
    ```
=== "Python"
    ```python
    class TrainStation:
        pass
    ```
=== "Kotlin"
    ```kotlin
    class TrainStation
    ```

### Constant property name

Constant fields should be written in `SCREAMING_SNAKE_CASE`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    static final int maxValue = 99;
    ```
=== "Groovy"
    ```groovy
    static final var maxValue = 99
    ```
=== "Kotlin"
    ```kotlin
    const val maxValue = 99
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    static final int MAX_VALUE = 99;
    ```
=== "Groovy"
    ```groovy
    static final var MAX_VALUE = 99
    ```
=== "Kotlin"
    ```kotlin
    const val MAX_VALUE = 99
    ```

### File name

If the file contains a single class, the file name should be the same as the
root class name.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```
    └─ com.example
       ├─ UserObject.java
       └─ class User {}
    ```
=== "Groovy"
    ```
    └─ com.example
       ├─ UserObject.groovy
       └─ class User {}
    ```
=== "Kotlin"
    ```
    └─ com.example
       ├─ UserObject.kt
       └─ class User
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```
    └─ com.example
       ├─ User.java
       └─ class User {}
    ```
=== "Groovy"
    ```
    └─ com.example
       ├─ User.groovy
       └─ class User {}
    ```
=== "Kotlin"
    ```
    └─ com.example
       ├─ User.kt
       └─ class User
    ```

### Identifier name

Non-constant fields, functions and parameters should be written in
**camelCase.**

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    void DebugUser(User User) {
        User AnotherUser = User;
    }
    ```
=== "Groovy"
    ```groovy
    def DebugUser(User User) {
        var AnotherUser = User
    }
    ```
=== "Kotlin"
    ```kotlin
    fun DebugUser(User: User) {
        val AnotherUser = User
    }
    ```
=== "Python"
    ```python
    def DebugUser(User):
        AnotherUser = User
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    void debugUser(User user) {
        User anotherUser = user;
    }
    ```
=== "Groovy"
    ```groovy
    def debugUser(User user) {
        var anotherUser = user
    }
    ```
=== "Kotlin"
    ```kotlin
    fun debugUser(user: User) {
        val anotherUser = user
    }
    ```
=== "Python"
    ```python
    def debug_user(user):
        another_user = user
    ```

### Illegal class name suffix

Prohibits meaningless source names in class, interface, object and files. The
name of utility classes (or files) should be the plural form of the extended
class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    class SpaceshipWrapper {}
    ```
=== "Groovy"
    ```groovy
    class SpaceshipWrapper {}
    ```
=== "Kotlin"
    ```kotlin
    class SpaceshipWrapper
    ```
=== "Python"
    ```python
    class SpaceshipWrapper():
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    class Spaceship {}
    ```
=== "Groovy"
    ```groovy
    class Spaceship {}
    ```
=== "Kotlin"
    ```kotlin
    class Spaceship
    ```
=== "Python"
    ```python
    class Spaceship():
        pass
    ```

??? Configuration
    :material-language-java:{ .lg .middle } Checkstyle | Default value
    --- | ---
    IllegalClassNameSuffix#names | `Util, Utility, Helper, Manager, Wrapper`
    **:simple-apachegroovy:{ .lg .middle } CodeNarc**
    IllegalClassNameSuffix#names | `Util, Utility, Helper, Manager, Wrapper`
    **:material-language-kotlin:{ .lg .middle } Ktlint**
    rulebook_illegal_class_name_suffixes | `Util, Utility, Helper, Manager, Wrapper`
    **:material-language-python: Pylint**
    rulebook-illegal-class-name-suffixes | `Util, Utility, Helper, Manager, Wrapper`

### Illegal variable name

Prohibits primitive type names, base `Object` type names and their plural forms
as identifiers of properties, parameters and local variables. The name of the
identifier should be descriptive and meaningful.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    String string;

    List<Person> list;
    ```
=== "Groovy"
    ```groovy
    var string

    var list
    ```
=== "Kotlin"
    ```kotlin
    val string: String

    val list: List<Person>
    ```
=== "Python"
    ```python
    string: str

    list: list[Person]
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    String name;

    List<Person> people;
    ```
=== "Groovy"
    ```groovy
    var name

    var people
    ```
=== "Kotlin"
    ```kotlin
    val name: String

    val people: List<Person>
    ```
=== "Python"
    ```python
    name: str

    people: list[Person]
    ```

??? Configuration
    :material-language-java:{ .lg .middle } Checkstyle | Default value
    --- | ---
    IllegalIdentifierName#format | `object, integer, string, objects, integers, strings`
    **:simple-apachegroovy:{ .lg .middle } CodeNarc**
    IllegalVariableName#names | `object, integer, string, object, integers, strings`
    **:material-language-kotlin:{ .lg .middle } Ktlint**
    rulebook_illegal_variable_names | `any, boolean, byte, char, double, float, int, long, short, string, many, booleans, bytes, chars, doubles, floats, ints, longs, shorts`
    **:material-language-python: Pylint**
    bad-names | `objs, ints, strs`

### Package name

Package names should be written in lowercase with no separators.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    package com.example.user_management;
    ```
=== "Groovy"
    ```groovy
    package com.example.user_management
    ```
=== "Kotlin"
    ```kotlin
    package com.example.user_management
    ```
=== "Python"
    ```
    └─ user_management
       └─ UserConfig.py
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    package com.example.usermanagement;
    ```
=== "Groovy"
    ```groovy
    package com.example.usermanagement
    ```
=== "Kotlin"
    ```kotlin
    package com.example.usermanagement
    ```
=== "Python"
    ```python
    └─ user_management
       └─ user_config.py
    ```

### Property name interop

Kotlin field definitions that are Boolean types should be prefixed with `is`.
Otherwise, the compiler will generate a getter method with `get` prefix.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    val active: Boolean
    ```
    ```java
    boolean getActive() {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    val isActive: Boolean
    ```
    ```java
    boolean isActive() {}
    ```

### Required generics name

Only use common generic type names according to Oracle. Multiple generic types
declaration is ignored.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    class Box<A> {}

    void <X> rotate(Box<X> box) {}
    ```
=== "Groovy"
    ```groovy
    class Box<A> {}

    void <X> rotate(Box<X> box) {}
    ```
=== "Kotlin"
    ```kotlin
    class Box<A>() {}

    fun <X> rotate(box: Box<X>) {}
    ```
=== "Python"
    ```python
    from typing import TypeVar

    A = TypeVar('A')
    X = TypeVar('X')


    class Box(A):
        pass


    def rotate(box: Box[X]):
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    class Box<E> {}

    void <T> rotate(Box<T> box) {}
    ```
=== "Groovy"
    ```groovy
    class Box<E> {}

    void <T> rotate(Box<T> box) {}
    ```
=== "Kotlin"
    ```kotlin
    class Box<E>() {}

    fun <T> rotate(box: Box<T>) {}
    ```
=== "Python"
    ```python
    from typing import TypeVar

    E = TypeVar('E')
    T = TypeVar('T')


    class Box(E):
        pass


    def rotate(box: Box[T]):
        pass
    ```

??? Configuration
    :material-language-java:{ .lg .middle } Checkstyle | Default value
    --- | ---
    ClassTypeParameterName#format | `E, K, N, T, V`
    InterfaceTypeParameterName#format | `E, K, N, T, V`
    MethodTypeParameterName#format | `E, K, N, T, V`
    RecordTypeParameterName#format | `E, K, N, T, V`
    TypeParameterName#format | `E, K, N, T, V`
    **:simple-apachegroovy:{ .lg .middle } CodeNarc**
    RequiredGenericName#names | `E, K, N, T, V`
    **:material-language-kotlin:{ .lg .middle } Ktlint**
    rulebook_required_generic_names | `E, K, N, T, V`
    **:material-language-python: Pylint**
    rulebook-required-generic-names | `E, K, N, T, V`

## Ordering

### Block tag order

Block tags should be ordered in the following sequence: `@constructor`,
`@receiver`, `@param`, `@property`, `@return`, `@throws`, `@see`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    abstract User createUser(String name);
    ```
=== "Groovy"
    ```groovy hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    abstract def createUser(String name)
    ```
=== "Kotlin"
    ```kotlin hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    abstract fun createUser(name: String): User
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    abstract User createUser(String name);
    ```
=== "Groovy"
    ```groovy hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    abstract def createUser(String name)
    ```
=== "Kotlin"
    ```kotlin hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    abstract fun createUser(name: String): User
    ```

### Built-in function position

Place Object built-in methods such as `toString()`, `hashCode()` and `equals()`
at the end of the class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="8-11"
    class Notification {
        private String message;

        public Notification(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }

        public void notify() {
            return System.out.println(message);
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="8-11"
    class Notification {
        private var message

        public Notification(String message) {
            this.message = message
        }

        @Override
        public def toString() {
            return message
        }

        public def notify() {
            println(message)
        }
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2"
    class Notification(private val message: String) {
        public override fun toString(): String = message

        public fun notify() {
            println(message)
        }
    }
    ```
=== "Python"
    ```python hl_lines="6-7"
    class Notification:
        def __init__(self, message):
            self.message = message
            self.id = randomize()

        def __str__(self):
            return self.message

        def notify(self):
            print(self.message)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="12-15"
    class Notification {
        private String message;

        public Notification(String message) {
            this.message = message;
        }

        public void notify() {
            return System.out.println(message);
        }

        @Override
        public String toString() {
            return message;
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="12-15"
    class Notification {
        private var message

        public Notification(String message) {
            this.message = message
        }

        public def notify() {
            println(message)
        }

        @Override
        public def toString() {
            return message
        }
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="6"
    class Notification(private val message: String) {
        public fun notify() {
            println(message)
        }

        public override fun toString(): String = message
    }
    ```
=== "Python"
    ```python hl_lines="9-10"
    class Notification:
        def __init__(self, message):
            self.message = message
            self.id = randomize()

        def notify(self):
            print(self.message)

        def __str__(self):
            return self.message
    ```

### Import order

Import directives should be ordered alphabetically without any blank lines.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    import java.util.List;

    import com.example.User;
    ```
=== "Groovy"
    ```groovy
    import java.util.List

    import com.example.User
    ```
=== "Kotlin"
    ```kotlin
    import java.util.List

    import com.example.User
    ```
=== "Python"
    ```python
    import utils

    import user
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    import com.example.User;
    import java.util.List;
    ```
=== "Groovy"
    ```groovy
    import com.example.User
    import java.util.List
    ```
=== "Kotlin"
    ```kotlin
    import com.example.User
    import java.util.List
    ```
=== "Python"
    ```python
    import user
    import utils
    ```

### Inner class position

Place inner classes at the end of the class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2"
    class Article {
        class Author {}

        Article(String content, Author author) {}

        Article(String content) {}
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2"
    class Article {
        class Author {}

        Article(String content, Author author) {}

        Article(String content) {}
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2"
    class Article(content: String, author: Author) {
        class Author(name: String)

        constructor(content: String) : this(content, null)
    }
    ```
=== "Python"
    ```python hl_lines="2"
    class Article:
        class Author:
            pass

        def __init__(self, content, author = None):
            pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="6"
    class Article {
        Article(String content, Author author) {}

        Article(String content) {}

        class Author {}
    }
    ```
=== "Groovy"
    ```groovy hl_lines="6"
    class Article {
        Article(String content, Author author) {}

        Article(String content) {}

        class Author {}
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="4"
    class Article(content: String, author: Author) {
        constructor(content: String) : this(content, null)

        class Author(name: String)
    }
    ```
=== "Python"
    ```python hl_lines="5"
    class Article:
        def __init__(self, content, author = None):
            pass

        class Author:
            pass
    ```

### Member order

The class should be organized as follows: properties, initializer block,
constructors, methods and static members. Inner classes should be declared next
to the last member that uses them.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    class Car {
        static void log(String message) {
            System.out.println(message);
        }

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, "Unknown");
        }

        int wheels = 4;

        void start() {
            log("Car created");
        }
    }
    ```
=== "Groovy"
    ```groovy
    class Car {
        static def log(String message) {
            System.out.println(message)
        }

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, 'Unknown')
        }

        var wheels = 4

        def start() {
            log('Car created')
        }
    }
    ```
=== "Kotlin"
    ```kotlin
    class Car(brand: String, model: String) {
        companion object {
            fun log(message: String) {
                println(message)
            }
        }

        init {
            log("Car created")
        }

        constructor(brand: String) : this(brand, "Unknown")

        val wheels = 4

        fun start() {
            log("Car started")
        }
    }
    ```
=== "Python"
    ```python
    class Car:
        @staticmethod
        def log(message: str):
            print(message)

        def __init__(self, brand, model = 'Unknown'):
            pass

        wheels = 4

        def start(self):
            log('Car started')
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    class Car {
        int wheels = 4;

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, "Unknown");
        }

        void start() {
            log("Car created");
        }

        static void log(String message) {
            System.out.println(message);
        }
    }
    ```
=== "Groovy"
    ```groovy
    class Car {
        var wheels = 4

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, 'Unknown')
        }

        def start() {
            log('Car created')
        }

        static def log(String message) {
            System.out.println(message)
        }
    }
    ```
=== "Kotlin"
    ```kotlin
    class Car(brand: String, model: String) {
        override val wheels = 4

        init {
            log("Car created")
        }

        constructor(brand: String) : this(brand, "Unknown")

        fun start() {
            log("Car started")
        }

        companion object {
            fun log(message: String) {
                println(message)
            }
        }
    }
    ```
=== "Python"
    ```python
    class Car:
        wheels = 4

        def __init__(self, brand, model = 'Unknown'):
            pass

        def start(self):
            log('Car started')

        @staticmethod
        def log(message: str):
            print(message)
    ```

??? Configuration
    :material-language-java:{ .lg .middle } Checkstyle | Default value
    --- | ---
    MemberOrder#order | `property, constructor, method, static`
    **:simple-apachegroovy:{ .lg .middle } CodeNarc**
    MemberOrder#order | `property, constructor, method, static`
    **:material-language-kotlin:{ .lg .middle } Ktlint**
    rulebook_member_order | `property, initializer, constructor, method, companion`
    **:material-language-python: Pylint**
    rulebook-member-order | `property, constructor, method, static`

### Overload function position

Place overloaded functions next to each other.

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="9-11"
    int sum(int a, int b) {
        return a + b;
    }

    int times(int a, int b) {
        return a * b;
    }

    int sum(int a, int b, int c) {
        return a + b + c;
    }
    ```
=== "Groovy"
    ```groovy hl_lines="9-11"
    def sum(int a, int b) {
        return a + b
    }

    def times(int a, int b) {
        return a * b
    }

    def sum(int a, int b, int c) {
        return a + b + c
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="5"
    fun sum(a: Int, b: Int): Int = a + b

    fun times(a: Int, b: Int): Int = a * b

    fun sum(a: Int, b: Int, c: Int): Int = a + b + c
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="5-7"
    int sum(int a, int b) {
        return a + b;
    }

    int sum(int a, int b, int c) {
        return a + b + c;
    }

    int times(int a, int b) {
        return a * b;
    }
    ```
=== "Groovy"
    ```groovy hl_lines="5-7"
    def sum(int a, int b) {
        return a + b
    }

    def sum(int a, int b, int c) {
        return a + b + c
    }

    def times(int a, int b) {
        return a * b
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="3"
    fun sum(a: Int, b: Int): Int = a + b

    fun sum(a: Int, b: Int, c: Int): Int = a + b + c

    fun times(a: Int, b: Int): Int = a * b
    ```

### Static import position

Static import directives are to be placed before non-static imports, separated
by a blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="3"
    import java.util.List;

    import static java.lang.Math.PI;
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    import java.util.List

    import static java.lang.Math.PI
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1"
    import static java.lang.Math.PI;

    import java.util.List;
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    import static java.lang.Math.PI

    import java.util.List
    ```

## Spacing

### Block comment spaces

Ensures that block comments starts and ends with a whitespace. In multiline
comments, each line after the asterisk should be indented by a whitespace.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1"
    /**Pass on user behavior.*/
    void report() {}
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    /**Pass on user behavior.*/
    def report() {}
    ```
=== "Kotlin"
    ```kotlin hl_lines="1"
    /**Pass on user behavior.*/
    fun report()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1"
    /** Pass on user behavior. */
    void report() {}
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    /** Pass on user behavior. */
    def report() {}
    ```
=== "Kotlin"
    ```kotlin hl_lines="1"
    /** Pass on user behavior. */
    fun report()
    ```

### Block tag indentation

Multi-line block tag descriptions should be indented by four spaces, or five
spaces from the leading asterisk.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    void abs(int num) {}
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    def abs(int num) {}
    ```
=== "Kotlin"
    ```kotlin hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    fun abs(num: Int): Int
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    void abs(int num) {}
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    def abs(int num) {}
    ```
=== "Kotlin"
    ```kotlin hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    fun abs(num: Int): Int
    ```

### Case separator

Multiline switch-case entries end with a blank line while short entries are
joined.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="8"
    switch (event) {
        case CANCELLED:
            return;

        case PAST:
            String message = "Event is in the past";
            throw new IllegalStateException(message);
        default:
            createEvent(event);
    }
    ```
=== "Groovy"
    ```groovy hl_lines="8"
    switch (event) {
        case CANCELLED:
            return

        case PAST:
            var message = 'Event is in the past'
            throw new IllegalStateException(message)
        default:
            createEvent(event)
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="8"
    when {
        event.isCancelled() -> return

        event.date < now -> {
            val message = "Event is in the past"
            throw IllegalStateException(message)
        }
        else -> createEvent(event)
    }
    ```
=== "Python"
    ```python hl_lines="8"
    match event:
        case CANCELLED:
            return

        case PAST:
            message = 'Event is in the past'
            raise ValueError(message)
        case _:
            create_event(event)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    switch (event) {
        case CANCELLED:
            return;

        case PAST:
            String message = "Event is in the past";
            throw new IllegalStateException(message);

        default:
            createEvent(event);
    }
    ```
=== "Groovy"
    ```groovy
    switch (event) {
        case CANCELLED:
            return

        case PAST:
            var message = 'Event is in the past'
            throw new IllegalStateException(message)

        default:
            createEvent(event)
    }
    ```
=== "Kotlin"
    ```kotlin
    when {
        event.isCancelled() -> return

        event.date < now -> {
            val message = "Event is in the past"
            throw IllegalStateException(message)
        }

        else -> createEvent(event)
    }
    ```
=== "Python"
    ```python
    match event:
        case CANCELLED:
            return

        case PAST:
            message = 'Event is in the past'
            raise ValueError(message)

        case _:
            create_event(event)
    ```

### Comment space

End-of-file comments should be separated by a single whitespace from the
preceding code, and start with a single whitespace.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    System.out.println("This is a code");//This is a comment
    ```
=== "Groovy"
    ```groovy
    println('This is a code')//This is a comment
    ```
=== "Kotlin"
    ```kotlin
    println("This is a code")//This is a comment
    ```
=== "Python"
    ```python
    print('This is a code')#This is a comment
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    System.out.println("This is a code"); // This is a comment
    ```
=== "Groovy"
    ```groovy
    println('This is a code') // This is a comment
    ```
=== "Kotlin"
    ```kotlin
    println("This is a code") // This is a comment
    ```
=== "Python"
    ```python
    print('This is a code')  # This is a comment
    ```

!!! warning
    PEP8 requires leading two spaces for comments.

### Member separator

Class, function and property declarations should be separated by a blank line.
There is an exception for single-line properties.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    interface Vehicle {
        int getWheels();
        void start();
    }
    ```
=== "Groovy"
    ```groovy
    interface Vehicle {
        def getWheels();
        def start();
    }
    ```
=== "Kotlin"
    ```kotlin
    interface Vehicle {
        val wheels: Int
        fun start()
    }
    ```
=== "Python"
    ```python
    class Vehicle:
        wheels: int
        def start(self):
            pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="3"
    interface Vehicle {
        int getWheels();

        void start();
    }
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    interface Vehicle {
        def getWheels();

        void start();
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="3"
    interface Vehicle {
        val wheels: Int

        fun start()
    }
    ```
=== "Python"
    ```python hl_lines="3"
    class Vehicle:
        wheels: int

        def start(self):
            pass
    ```

### Missing blank line before block tags

Separate block tag group from the summary with a blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    void abs(int number) {}
    ```
=== "Groovy"
    ```groovy
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    def abs(int number) {}
    ```
=== "Kotlin"
    ```kotlin
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    fun abs(number: Int): Int
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="3"
    /**
     * Returns the absolute value of the given number.
     *
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    void abs(int number) {}
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    /**
     * Returns the absolute value of the given number.
     *
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    def abs(int number) {}
    ```
=== "Kotlin"
    ```kotlin hl_lines="3"
    /**
     * Returns the absolute value of the given number.
     *
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    fun abs(number: Int): Int
    ```

## Stating

### Illegal catch

Catch specific exception subclass instead of the generic `Throwable`,
`Exception` or `Error`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="3"
    try {
        unsafeOperation();
    } catch (Throwable e) {
        e.printStackTrace();
    }
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    try {
        unsafeOperation()
    } catch (Throwable e) {
        e.printStackTrace()
    }
    ```
=== "Python"
    ```python hl_lines="3"
    try:
        unsafe_operation()
    except Exception as e:
        print(e)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="3"
    try {
        unsafeOperation();
    } catch (IOException | SQLException e) {
        e.printStackTrace();
    }
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    try {
        unsafeOperation()
    } catch (IOException | SQLException e) {
        e.printStackTrace()
    }
    ```
=== "Python"
    ```python hl_lines="3"
    try:
        unsafe_operation()
    except (IOError, OSError) as e:
        print(e)
    ```

### Illegal throw

Throw a narrower exception type instead of `Exception`, `Error` or `Throwable`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    throw new Exception();
    ```
=== "Groovy"
    ```groovy
    throw new Exception()
    ```
=== "Kotlin"
    ```kotlin
    throw Exception()
    ```
=== "Python"
    ```python
    raise Exception()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    throw new IllegalStateException();
    ```
=== "Groovy"
    ```groovy
    throw new IllegalStateException()
    ```
=== "Kotlin"
    ```kotlin
    throw IllegalStateException()
    ```
=== "Python"
    ```python
    raise ValueError()
    ```

### Missing braces

Enforces the use of braces for multiline `if`, `else`, `for`, `while` and `do`
statements.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials);
    else
        showError();
    ```
=== "Groovy"
    ```groovy hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials)
    else
        showError()
    ```
=== "Kotlin"
    ```kotlin hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials)
    else
        showError()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else {
        showError();
    }
    ```
=== "Groovy"
    ```groovy hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else {
        showError()
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else {
        showError()
    }
    ```

### Nested if-else

If a block ends with an if statement without else and the body is at least 2
lines, it should be inverted to avoid nesting and unnecessary indentation.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2 3"
    void login(User user) {
        if (user.isValid()) {
            if (!isLoggedIn(user)) {
                updateProfile(user);
                displayDashboard();
            }
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2 3"
    def login(User user) {
        if (user.isValid()) {
            if (!isLoggedIn(user)) {
                updateProfile(user)
                displayDashboard()
            }
        }
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2 3"
    fun login(user: User) {
        if (user.isValid()) {
            if (!isLoggedIn(user)) {
                updateProfile(user)
                displayDashboard()
            }
        }
    }
    ```
=== "Python"
    ```python hl_lines="2 3"
    def login(user: User):
        if user.is_valid():
            if not is_logged_in(user):
                update_profile(user)
                display_dashboard()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2 5"
    void login(User user) {
        if (!user.isValid()) {
            return;
        }
        if (isLoggedIn(user)) {
            return;
        }
        updateProfile(user);
        displayDashboard();
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2 5"
    def login(User user) {
        if (!user.isValid()) {
            return
        }
        if (isLoggedIn(user)) {
            return
        }
        updateProfile(user)
        displayDashboard()
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2 5"
    fun login(user: User) {
        if (!user.isValid()) {
            return
        }
        if (isLoggedIn(user)) {
            return
        }
        updateProfile(user)
        displayDashboard()
    }
    ```
=== "Python"
    ```python hl_lines="2 5"
    def login(user: User):
        if not user.is_valid():
            return
        if is_logged_in(user):
            return
        update_profile(user)
        display_dashboard()
    ```

### Redundant default

If every branch of a switch statement has a return or throw statement, the
default branch can be lifted.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="7-8"
    void park(Car car) {
        switch (car) {
            case MOVING:
                throw new IllegalStateException();
            case PARKED:
                return;
            default:
                findParking(car);
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="7-8"
    def park(Car car) {
        switch (car) {
            case MOVING:
                throw new IllegalStateException()
            case PARKED:
                return
            default:
                findParking(car)
        }
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="5"
    fun park(car: Car) {
        when {
            car.isMoving() -> throw IllegalStateException()
            car.isParked() -> return
            else -> findParking(car)
        }
    }
    ```
=== "Python"
    ```python hl_lines="7-8"
    def park(car: Car):
        match car:
            case Car.MOVING:
                raise ValueError()
            case Car.PARKED:
                return
            case _:
                find_parking(car)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="8"
    void park(Car car) {
        switch (car) {
            case MOVING:
                throw new IllegalStateException();
            case PARKED:
                return;
        }
        findParking(car);
    }
    ```
=== "Groovy"
    ```groovy hl_lines="8"
    def park(Car car) {
        switch (car) {
            case MOVING:
                throw new IllegalStateException()
            case PARKED:
                return
        }
        findParking(car)
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="6"
    fun park(car: Car) {
        when {
            car.isMoving() -> throw IllegalStateException()
            car.isParked() -> return
        }
        findParking(car)
    }
    ```
=== "Python"
    ```python hl_lines="7"
    def park(car: Car):
        match car:
            case Car.MOVING:
                raise ValueError()
            case Car.PARKED:
                return
        find_parking(car)
    ```

### Redundant else

When every if and else-if block has a return or throw statement, the else block
can be lifted.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="4 6"
    void park(Car car) {
        if (car.isMoving()) {
            throw new IllegalStateException();
        } else if (car.isParked()) {
            return;
        } else {
            findParking(car);
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="4 6"
    def park(Car car) {
        if (car.isMoving()) {
            throw new IllegalStateException()
        } else if (car.isParked()) {
            return
        } else {
            findParking(car)
        }
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="4 6"
    fun park(car: Car) {
        if (car.isMoving()) {
            throw IllegalStateException()
        } else if (car.isParked()) {
            return
        } else {
            findParking(car)
        }
    }
    ```
=== "Python"
    ```python hl_lines="4 6"
    def park(car: Car):
        if car.is_moving():
            raise ValueError()
        elif car.is_parked():
            return
        else:
            find_parking(car)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="5 8"
    void park(Car car) {
        if (car.isMoving()) {
            throw new IllegalStateException();
        }
        if (car.isParked()) {
            return;
        }
        findParking(car);
    }
    ```
=== "Groovy"
    ```groovy hl_lines="5 8"
    def park(Car car) {
        if (car.isMoving()) {
            throw new IllegalStateException()
        }
        if (car.isParked()) {
            return
        }
        findParking(car)
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="5 8"
    fun park(car: Car) {
        if (car.isMoving()) {
            throw IllegalStateException()
        }
        if (car.isParked()) {
            return
        }
        findParking(car)
    }
    ```
=== "Python"
    ```python hl_lines="4 6"
    def park(car: Car):
        if car.is_moving():
            raise ValueError()
        if car.is_parked():
            return
        find_parking(car)
    ```

### Unnecessary switch

If a switch statement has single branch, it should be replaced with an if
statement.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    switch (token) {
        case VALUE_TOKEN:
            callback(token);
    }
    ```
=== "Groovy"
    ```groovy
    switch (token) {
        case VALUE_TOKEN:
            callback(token)
    }
    ```
=== "Kotlin"
    ```kotlin
    when (token) {
        is Token.ValueToken -> callback(token)
    }
    ```
=== "Python"
    ```python
    match token:
        case Token.VALUE_TOKEN:
            callback(token)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    if (token == Token.VALUE_TOKEN) {
        callback(token);
    }
    ```
=== "Groovy"
    ```groovy
    if (token == Token.VALUE_TOKEN) {
        callback(token)
    }
    ```
=== "Kotlin"
    ```kotlin
    if (token is Token.ValueToken) {
        callback(token)
    }
    ```
=== "Python"
    ```python
    if token == Token.VALUE_TOKEN:
        callback(token)
    ```

## Trimming

### Block comment trim

Do not start or end block comments with whitespaces.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
=== "Groovy"
    ```groovy hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
=== "Kotlin"
    ```kotlin hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
=== "Python"
    ```python hl_lines="2 5"
    """

    AUTHOR: John Doe
    LICENSE: Apache 2.0

    """
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
=== "Groovy"
    ```groovy
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
=== "Kotlin"
    ```kotlin
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
=== "Python"
    ```python
    """
    AUTHOR: John Doe
    LICENSE: Apache 2.0
    """
    ```

### Braces trim

Prohibits empty first and last lines in code blocks.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2 5"
    void onReceive(Integer value) {

        if (value != null) {
            total += value;

        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2 5"
    def onReceive(Integer value) {

        if (value != null) {
            total += value

        }
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2 5"
    fun onReceive(value: Int?) {

        if (value != null) {
            total += value

        }
    }
    ```
=== "Python"
    ```python hl_lines="2 6"
    foo = {

        'bar',
        'baz',
        'qux',

    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    void onReceive(Integer value) {
        if (value != null) {
            total += value;
        }
    }
    ```
=== "Groovy"
    ```groovy
    def onReceive(Integer value) {
        if (value != null) {
            total += value
        }
    }
    ```
=== "Kotlin"
    ```kotlin
    fun onReceive(value: Int?) {
        if (value != null) {
            total += value
        }
    }
    ```
=== "Python"
    ```python
    foo = {
        'bar',
        'baz',
        'qux',
    }
    ```

### Brackets trim

Prohibits empty first and last lines in collection initializers.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"
    ```groovy hl_lines="2 6"
    var pond = [

        Fish('Nemo'),
        Fish('Dory'),
        Fish('Marlin'),

    ]
    ```
=== "Python"
    ```python hl_lines="2 6"
    pond = [

        Fish('Nemo'),
        Fish('Dory'),
        Fish('Marlin'),

    ]
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"
    ```groovy
    var pond = [
        Fish('Nemo'),
        Fish('Dory'),
        Fish('Marlin'),
    ]
    ```
=== "Python"
    ```python
    pond = [
        Fish('Nemo'),
        Fish('Dory'),
        Fish('Marlin'),
    ]
    ```

### Comment trim

Prohibits empty first and last lines in EOL comments.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
=== "Groovy"
    ```groovy hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
=== "Kotlin"
    ```kotlin hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
=== "Python"
    ```python hl_lines="1 4"
    #
    # This is a
    # multiline comment
    #
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    // This is a
    // multiline comment
    ```
=== "Groovy"
    ```groovy
    // This is a
    // multiline comment
    ```
=== "Kotlin"
    ```kotlin
    // This is a
    // multiline comment
    ```
=== "Python"
    ```python
    # This is a
    # multiline comment
    ```

### Duplicate blank line

Prohibits consecutive blank lines in the code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="3"
    String message = "Hello";


    System.out.println(message);
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    var message = 'Hello'


    println(message)
    ```
=== "Kotlin"
    ```kotlin hl_lines="3"
    val message = "Hello"


    println(message)
    ```
=== "Python"
    ```python hl_lines="3"
    message = 'Hello'


    print(message)
    ```

!!! warning
    PEP8 allows two blank lines between top-level functions and class
    definitions.

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    String message = "Hello";

    System.out.println(message);
    ```
=== "Groovy"
    ```groovy
    var message = 'Hello'

    println(message)
    ```
=== "Kotlin"
    ```kotlin
    val message = "Hello"

    println(message)
    ```
=== "Python"
    ```python
    message = 'Hello'

    print(message)
    ```

### Duplicate blank line in block comment

Prohibits consecutive blank lines in block comments.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
=== "Groovy"
    ```groovy hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
=== "Kotlin"
    ```kotlin hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
=== "Python"
    ```python hl_lines="4"
    """
    This is a


    very long comment
    """
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    /**
     * This is a
     *
     * very long comment
     */
    ```
=== "Groovy"
    ```groovy
    /**
     * This is a
     *
     * very long comment
     */
    ```
=== "Kotlin"
    ```kotlin
    /**
     * This is a
     *
     * very long comment
     */
    ```
=== "Python"
    ```python
    """
    This is a

    very long comment
    """
    ```

### Duplicate blank line in comment

Prohibits consecutive blank lines in comments.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
=== "Groovy"
    ```groovy hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
=== "Kotlin"
    ```kotlin hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
=== "Python"
    ```python hl_lines="3"
    # This is a
    #
    #
    # very long comment
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    // This is a
    //
    // very long comment
    ```
=== "Groovy"
    ```groovy
    // This is a
    //
    // very long comment
    ```
=== "Kotlin"
    ```kotlin
    // This is a
    //
    // very long comment
    ```
=== "Python"
    ```python
    # This is a
    #
    # very long comment
    ```

### Duplicate space

Prohibits consecutive spaces in the code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    double tax      = 0.2;
    double subtotal = bill     * tax;
    double total    = subtotal + bill;
    ```
=== "Groovy"
    ```groovy
    var tax      = 0.2d
    var subtotal = bill     * tax
    var total    = subtotal + bill
    ```
=== "Kotlin"
    ```kotlin
    val tax      = 0.2d
    val subtotal = bill     * tax
    val total    = subtotal + bill
    ```
=== "Python"
    ```python
    tax      = 0.2
    subtotal = bill     * tax
    total    = subtotal + bill
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    double tax = 0.2;
    double subtotal = bill * tax;
    double total = subtotal + bill;
    ```
=== "Groovy"
    ```groovy
    var tax = 0.2d
    var subtotal = bill * tax
    var total = subtotal + bill
    ```
=== "Kotlin"
    ```kotlin
    val tax = 0.2d
    val subtotal = bill * tax
    val total = subtotal + bill
    ```
=== "Python"
    ```python
    tax = 0.2
    subtotal = bill * tax
    total = subtotal + bill
    ```

### Parentheses trim

Prohibits empty first and last lines in method declarations and calls.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2 8"
    void swim(

        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish

        );
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2 8"
    def swim(

        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish,

        )
    }
    ```
=== "Kotlin"
    ```kotlin hl_lines="2 8"
    fun swim(

        Fish fish,
        Pond pond,
    ) {
        pond.release(
            fish,

        )
    }
    ```
=== "Python"
    ```python hl_lines="3 12 15 17"
    def swim(

        fish: Fish,
        pond: Pond,
    ):
        pond.release(
            fish,

        )
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    void swim(
        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish
        );
    }
    ```
=== "Groovy"
    ```groovy
    def swim(
        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish,
        )
    }
    ```
=== "Kotlin"
    ```kotlin
    fun swim(
        fish: Fish,
        pond: Pond,
    ) {
        pond.release(
            fish,
        )
    }
    ```
=== "Python"
    ```python
    def swim(
        fish: Fish,
        pond: Pond,
    ):
        pond.release(
            fish,
        )
    ```

### Tags trim

Prohibits empty first and last lines in generic type parameters.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2 6"
    Triple<

        String,
        Integer,
        Boolean

    > userAgeMarried = new Triple<>("Hank Hill", 41, true);
    ```
=== "Groovy"
    ```groovy hl_lines="2 6"
    Triple<

        String,
        Integer,
        Boolean

    > userAgeMarried = new Triple<>('Hank Hill', 41, true)
    ```
=== "Kotlin"
    ```kotlin hl_lines="3 7"
    val userAgeMarried =
        new Triple<

            String,
            Integer,
            Boolean

        >('Hank Hill', 41, true)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    Triple<
        String,
        Integer,
        Boolean
    > userAgeMarried = new Triple<>("Hank Hill", 41, true);
    ```
=== "Groovy"
    ```groovy
    Triple<
        String,
        Integer,
        Boolean
    > userAgeMarried = new Triple<>('Hank Hill', 41, true)
    ```
=== "Kotlin"
    ```kotlin
    val userAgeMarried =
        Triple<

            String,
            Integer,
            Boolean

        >('Hank Hill', 41, true)
    ```

### Unnecessary blank line after colon

Prohibits first empty line in Python function and class definitions.

**:material-star-four-points-outline:{ #accent } Before**

=== "Python"
    ```python hl_lines="2"
    def on_receive(value):

        if value is not None:
            total += value
    ```

**:material-star-four-points:{ #accent } After**

=== "Python"
    ```python
    def on_receive(value):
        if value is not None:
            total += value
    ```

### Unnecessary blank line before package

The first line of a file cannot be a blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1"
    \n

    package com.example;

    void execute() {}
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    \n

    package com.example

    def execute() {}
    ```
=== "Kotlin"
    ```kotlin hl_lines="1"
    \n

    package com.example

    fun execute() {}
    ```
=== "Python"
    ```python hl_lines="1"
    \n

    def execute():
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    package com.example;

    void execute() {}
    ```
=== "Groovy"
    ```groovy
    package com.example

    def execute() {}
    ```
=== "Kotlin"
    ```kotlin
    package com.example

    fun execute() {}
    ```
=== "Python"
    ```python
    def execute():
        pass
    ```

## Wrapping

### Assignment wrap

Assignee and the value of assignment spanning multiple lines should be separated
by a newline.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1"
    String message = new StringBuilder()
        .append("Hello")
        .toString();
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    var message = new StringBuilder()
        .append('Hello')
        .toString()
    ```
=== "Kotlin"
    ```kotlin hl_lines="1"
    val message = buildString {
        append("Hello")
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1-2"
    String message =
        new StringBuilder()
            .append("Hello")
            .toString();
    ```
=== "Groovy"
    ```groovy hl_lines="1-2"
    var message =
        new StringBuilder()
            .append('Hello')
            .toString()
    ```
=== "Kotlin"
    ```kotlin hl_lines="1-2"
    val message =
        buildString {
            append("Hello")
        }
    ```

### Chain call wrap

Each method call in a chain should be aligned with the dot operator.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2"
    int senderId =
        notification.getSender()
            .getId();
    ```
=== "Groovy"
    ```groovy hl_lines="2"
    var senderId =
        notification.getSender()
            .getId()
    ```
=== "Kotlin"
    ```kotlin hl_lines="2"
    val senderId =
        notification.getSender()
            .id.takeIf { it.isNotBlank() }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2-3"
    int senderId =
        notification
            .getSender()
            .getId();
    ```
=== "Groovy"
    ```groovy hl_lines="2-3"
    var senderId =
        notification
            .getSender()
            .getId()
    ```
=== "Kotlin"
    ```kotlin hl_lines="2-3"
    val senderId =
        notification
            .getSender()
            .id
    ```

### Elvis wrap

In a multiline statement, the elvis operator should be separated into a new line
instead of trailing the statement.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2"
    user.name
        .takeIf { it.isNotBlank() } ?: "Unknown"
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="3"
    user.name
        .takeIf { it.isNotBlank() }
        ?: "Unknown"
    ```

### Infix call wrap

When breaking an infix function call, the operator should be placed at the end
of the line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="4 6"
    val ages =
        mapOf(
            "Alice"
                to 25,
            "Bob"
                to 30,
        )
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="3 5"
    val ages =
        mapOf(
            "Alice" to
                25,
            "Bob" to
                30,
        )
    ```

### Lambda wrap

When breaking a multiline lambda expression, the body should be placed on a new
line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="4"
    int sum =
        IntStream
            .range(0, 10)
            .map(i -> i
                * 2
            ).sum();
    ```
=== "Groovy"
    ```groovy hl_lines="4"
    var sum =
        IntStream
            .range(0, 10)
            .map { i -> i
                * 2
            }.sum()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="5"
    int sum =
        IntStream
            .range(0, 10)
            .map(i ->
                i * 2
            ).sum();
    ```
=== "Groovy"
    ```groovy hl_lines="5"
    var sum =
        IntStream
            .range(0, 10)
            .map { i ->
                i * 2
            }.sum()
    ```

### Operator wrap

A line break should be placed after the operator in a binary expression.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="3-4"
    int total =
        subtotal
        + tax
        - discount;
    ```
=== "Groovy"
    ```groovy hl_lines="3-4"
    var total =
        subtotal
        + tax
        - discount
    ```
=== "Kotlin"
    ```kotlin hl_lines="3-4"
    val total =
        subtotal
        + tax
        - discount
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2-3"
    int total =
        subtotal +
        tax -
        discount;
    ```
=== "Groovy"
    ```groovy hl_lines="2-3"
    var total =
        subtotal +
        tax -
        discount
    ```
=== "Kotlin"
    ```kotlin hl_lines="2-3"
    val total =
        subtotal +
        tax -
        discount
    ```

### Parameter wrap

When breaking a multiline parameter list, each parameter should be placed on a
new line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="2"
    void createUser(
        String name, String email, int age
    )
    ```
=== "Groovy"
    ```groovy hl_lines="2"
    def createUser(
        String name, String email, int age
    )
    ```
=== "Kotlin"
    ```kotlin hl_lines="2"
    fun createUser(
        name: String, email: String, age: Int
    )
    ```
=== "Python"
    ```python hl_lines="2"
    def create_user(
        name: str, email: str, age: int
    )
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="2-4"
    void createUser(
        String name,
        String email,
        int age
    )
    ```
=== "Groovy"
    ```groovy hl_lines="2-4"
    def createUser(
        String name,
        String email,
        int age,
    )
    ```
=== "Kotlin"
    ```kotlin hl_lines="2-4"
    fun createUser(
        name: String,
        email: String,
        age: Int,
    )
    ```
=== "Python"
    ```python
    def create_user(
        name: str,
        email: str,
        age: int,
    )
    ```

### Statement wrap

Compound statements are not allowed.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    int x = 0; int y = 0;
    ```
=== "Groovy"
    ```groovy
    var x = 0; var y = 0
    ```
=== "Kotlin"
    ```kotlin
    val x = 0; val y = 0
    ```
=== "Python"
    ```python
    x = 0; y = 0
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    int x = 0;
    int y = 0;
    ```
=== "Groovy"
    ```groovy
    var x = 0
    var y = 0
    ```
=== "Kotlin"
    ```kotlin
    val x = 0
    val y = 0
    ```
=== "Python"
    ```python
    x = 0
    y = 0
    ```
