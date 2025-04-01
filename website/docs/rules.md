### Block tag punctuation

Description of certain block tags, if present, should end with a period,
question mark or exclamation mark.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin title="Before" hl_lines="3"
    /**
    * @param num
    * @return the new size of the group
    */
    abstract fun add(int num): Int
    ```
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

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin title="After" hl_lines="3"
    /**
    * @param num
    * @return the new size of the group.
    */
    abstract fun add(int num): Int
    ```
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
    abstract int add(int num)
    ```

??? Configuration
    - Ktlint

        Key | Default value
        --- | ---
        rulebook_punctuate_block_tags | `@constructor, @receiver, @property, @param, @return`
    - Checkstyle

        Key | Default value
        --- | ---
        BlockTagPunctuation#tags | `@param, @return`

### Built-in types

Do not use types from `java.*` when there are Kotlin equivalents.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1 4"
    import java.lang.String

    val names = arrayListOf<String>()
    val people = java.util.ArrayList<Person>()
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="2"
    val names = arrayListOf<String>()
    val people = ArrayList<Person>()
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

### Explicit import

Import directives must be single-type instead of wildcard imports.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1"
    import com.example.fruit.*

    val fruits = listOf(Apple(), Banana())
    ```
=== "Java"
    ```java hl_lines="1"
    import com.example.fruit.*;

    List<Fruit> fruits = Arrays.asList(new Apple(), new Banana());
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    import com.example.fruit.*

    ArrayList<Fruit> fruits = [new Apple(), new Banana()]
    ```
=== "Python"
    ```python hl_lines="1"
    from fruit import *

    fruits = [Apple(), Banana()]
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="1-2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    val fruits = listOf(Apple(), Banana())
    ```
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

    ArrayList<Fruit> fruits = [new Apple(), new Banana()]
    ```
=== "Python"
    ```python hl_lines="1"
    from fruit import Apple, Banana

    fruits = [Apple(), Banana()]
    ```

### File size

File length should not be longer than 1.000 lines of code. If a file exceeds the
limit, it should be split into multiple files.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2 4 6 8"
    object Articles {
        fun create(article: Article): Int { /*...*/ }

        fun read(articleId: Int): Article { /*...*/ }

        fun update(articleId: Int, article: Article) { /*...*/ }

        fun delete(articleId: Int) { /*...*/ }
    }
    ```
=== "Java"
    ```java hl_lines="2 4 6 8"
    final class Articles {
        static int create(Article article) { /*...*/ }

        static Article read(int articleId) { /*...*/ }

        static void update(int articleId, Article article) { /*...*/ }

        static void delete(int articleId) { /*...*/ }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2 4 6 8"
    final class Articles {
        static def create(Article article) { /*...*/ }

        static def read(int articleId) { /*...*/ }

        static def update(int articleId, Article article) { /*...*/ }

        static def delete(int articleId) { /*...*/ }
    }
    ```
=== "Python"
    ```python hl_lines="1 4 7 10"
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
    - Ktlint

        Key | Default value
        --- | ---
        rulebook_max_file_size | `1.000`
    - Checkstyle

        Key | Default value
        --- | ---
        FileLength#max | `1.000`
    - CodeNarc

        Key | Default value
        --- | ---
        ClassSize#maxLines | `1.000`
    - Pylint

        Key | Default value
        --- | ---
        rulebook-max-file-size | `1.000`

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

=== "Kotlin"
    ```kotlin
    // todo add tests
    //
    // FIXME: memory leak
    ```
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
=== "Python"
    ```python
    # todo add tests
    #
    # FIXME: memory leak
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    // TODO add tests
    //
    // FIXME memory leak
    ```
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
=== "Python"
    ```python
    # TODO add tests
    #
    # FIXME memory leak
    ```

### Trailing comma in call

Put a trailing comma in a multiline call site, omit when it is a single line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="4 7"
    val inventory =
        mapOf(
            "milks" to 2,
            "eggs" to 6
        )

    println(inventory,)
    ```
=== "Groovy"
    ```groovy hl_lines="3 6"
    def inventory = [
        'milks': 2,
        'eggs': 6
    ]

    println(inventory,)
    ```
=== "Python"
    ```python hl_lines="3 6"
    inventory = {
        'milks': 2,
        'eggs': 6
    }

    print(inventory,)
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="4 7"
    val inventory =
        mapOf(
            "milks" to 2,
            "eggs" to 6,
        )

    println(inventory)
    ```
=== "Groovy"
    ```groovy hl_lines="3 6"
    def inventory = [
        'milks': 2,
        'eggs': 6,
    ]

    println(inventory)
    ```
=== "Python"
    ```python hl_lines="3 6"
    inventory = {
        'milks': 2,
        'eggs': 6,
    }

    print(inventory)
    ```

### Trailing comma in collection

Put a trailing comma in a multiline collection site, omit when it is a single
line. In Java and Groovy, this rule applies to array initializers. In Python,
this rule applies to tuples.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java hl_lines="1 6"
    String[] games = {"chess", "checkers",};

    int[][] ticTacToe = {
        {0, 0, 0,},
        {0, 0, 0,},
        {0, 0, 0,}
    };
    ```
=== "Groovy"
    ```groovy hl_lines="1 6"
    var games = ['chess', 'checkers',]

    var ticTacToe = [
        [0, 0, 0,],
        [0, 0, 0,],
        [0, 0, 0,]
    ]
    ```
=== "Python"
    ```python hl_lines="1 6"
    games = ('chess', 'checkers',)

    tic_tac_toe = (
        (0, 0, 0,),
        (0, 0, 0,),
        (0, 0, 0,)
    )
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="1 6"
    String[] games = {"chess", "checkers"};

    int[][] ticTacToe = {
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0},
    };
    ```
=== "Groovy"
    ```groovy hl_lines="1 6"
    var games = ['chess', 'checkers']

    var ticTacToe = [
        [0, 0, 0],
        [0, 0, 0],
        [0, 0, 0],
    ]
    ```
=== "Python"
    ```python hl_lines="1 6"
    games = ('chess', 'checkers')

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
    fun updateInventory(item: String,) = TODO()

    fun createInventory(
        item: String,
        quantity: Int
    ) = TODO()
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
    fun updateInventory(item: String) = TODO()

    fun createInventory(
        item: String,
        quantity: Int,
    )
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

=== "Kotlin"
    ```kotlin hl_lines="2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    val apple = Apple()
    ```
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
=== "Python"
    ```python hl_lines="1"
    from fruit import Apple, Banana

    apple = Apple()
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    import com.example.fruit.Apple

    val apple = Apple()
    ```
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
=== "Python"
    ```python hl_lines="1"
    from fruit import Apple

    apple = Apple()
    ```

## Declaring

### Abstract class definition

Abstract classes need at least one abstract function.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1"
    abstract class Vehicle {
        fun start() {}
    }
    ```
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
=== "Python"
    ```python hl_lines="3"
    from abc import ABC

    class Vehicle(ABC):
        def start(self):
            pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="1"
    class Vehicle {
        fun start()
    }
    ```
=== "Java"
    ```java hl_lines="1"
    class Vehicle {
        void start();
    }
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    class Vehicle {
        def start()
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

=== "Kotlin"
    ```kotlin
    class PurchaseException : Error()
    ```
=== "Java"
    ```java
    class PurchaseException extends Error {}
    ```
=== "Groovy"
    ```groovy
    class PurchaseException extends Error {}
    ```
=== "Python"
    ```python
    class PurchaseException(BaseException):
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    class PurchaseException : Exception()
    ```
=== "Java"
    ```java
    class PurchaseException extends Exception {}
    ```
=== "Groovy"
    ```groovy
    class PurchaseException extends Exception {}
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

=== "Kotlin"
    ```kotlin
    val half = 0.5F
    ```
=== "Java"
    ```java
    float half = 0.5F;
    ```
=== "Groovy"
    ```groovy
    var quarter = 0.25F
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    val half = 0.5f
    ```
=== "Java"
    ```java
    float half = 0.5f;
    ```
=== "Groovy"
    ```groovy
    var quarter = 0.25f
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

Utility classes should have a final modifier and a private constructor to prevent
instantiation.

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

## Naming

### Class name acronym

Ensures that the first letter of acronyms longer than three characters are
always capitalized.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    class RestAPI {
        val httpURL = "https://example.com"
    }
    ```
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

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    class RestApi {
        val httpUrl = "https://example.com"
    }
    ```
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

### Class name

Class, interface and object names are written in `PascalCase`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    class train_station
    ```
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

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    class TrainStation
    ```
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

### Constant property name

Constant fields should be written in `SCREAMING_SNAKE_CASE`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    const val maxValue = 99
    ```
=== "Java"
    ```java
    static final int maxValue = 99;
    ```
=== "Groovy"
    ```groovy
    static final int maxValue = 99
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    const val MAX_VALUE = 99
    ```
=== "Java"
    ```java
    static final int MAX_VALUE = 99;
    ```
=== "Groovy"
    ```groovy
    static final int MAX_VALUE = 99
    ```

### File name

If the file contains a single class, the file name should be the same as the
root class name.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```
    └ com.example
      └ UserObject.kt
        └ class User
    ```
=== "Java"
    ```
    └ com.example
      └ UserObject.java
        └ class User {}
    ```
=== "Groovy"
    ```
    └ com.example
      └ UserObject.groovy
        └ class User {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```
    └ com.example
      └ User.kt
        └ class User
    ```
=== "Java"
    ```
    └ com.example
      └ User.java
        └ class User {}
    ```
=== "Groovy"
    ```
    └ com.example
      └ User.groovy
        └ class User {}
    ```

### Identifier name

Non-constant fields, functions and parameters should be written in
**camelCase.**

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    fun DebugUser(User: User) {
        AnotherUser = User
    }
    ```
=== "Java"
    ```java
    void DebugUser(User User) {
        AnotherUser = User;
    }
    ```
=== "Groovy"
    ```groovy
    def DebugUser(User User) {
        AnotherUser = User
    }
    ```
=== "Python"
    ```python
    def DebugUser(User):
        AnotherUser = User
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    fun debugUser(user: User) {
        anotherUser = user
    }
    ```
=== "Java"
    ```java
    void debugUser(User user) {
        anotherUser = user;
    }
    ```
=== "Groovy"
    ```groovy
    def debugUser(User user) {
        anotherUser = user
    }
    ```
=== "Python"
    ```python
    def debug_user(user):
        another_user = user
    ```

### Illegal class final name

Prohibits meaningless source names in class, interface, object and files. The
name of utility classes (or files) should be the plural form of the extended
class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    interface AbstractRocket

    class SpaceshipWrapper : AbstractRocket
    ```
=== "Java"
    ```java
    interface AbstractRocket {}

    class SpaceshipWrapper implements AbstractRocket {}
    ```
=== "Groovy"
    ```groovy
    interface AbstractRocket {}

    class SpaceshipWrapper implements AbstractRocket {}
    ```
=== "Python"
    ```python
    class AbstractRocket:
        pass


    class SpaceshipWrapper(AbstractRocket):
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    interface Rocket

    class Spaceship : Rocket
    ```
=== "Java"
    ```java
    interface Rocket {}

    class Spaceship implements Rocket {}
    ```
=== "Groovy"
    ```groovy
    interface Rocket {}

    class Spaceship implements Rocket {}
    ```
=== "Python"
    ```python
    class Rocket:
        pass


    class Spaceship(Rocket):
        pass
    ```

??? Configuration
    - Ktlint

        Key | Default value
        --- | ---
        rulebook_illegal_class_final_names | `Util, Utility, Helper, Manager, Wrapper`
    - Checkstyle

        Key | Default value
        --- | ---
        IllegalClassFinalName#names | `Util, Utility, Helper, Manager, Wrapper`
    - CodeNarc

        Key | Default value
        --- | ---
        IllegalClassFinalName#names | `Util, Utility, Helper, Manager, Wrapper`
    - Pylint

        Key | Default value
        --- | ---
        rulebook-illegal-class-final-names | `Util, Utility, Helper, Manager, Wrapper`

### Illegal variable name

Prohibitprimitive type names, base `Object` type names and their plural forms as
identifiers of properties, parameters and local variables. The name of the
identifier should be descriptive and meaningful.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    val string: String

    val list: List<Person>
    ```
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
=== "Python"
    ```python
    string: str

    list: list[Person]
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    val name: String

    val people: List<Person>
    ```
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
=== "Python"
    ```python
    name: str

    people: list[Person]
    ```

??? Configuration
    - Ktlint

        Key | Default value
        --- | ---
        rulebook_illegal_variable_names | `any, boolean, byte, char, double, float, int, long, short, string, many, booleans, bytes, chars, doubles, floats, ints, longs, shorts`
    - Checkstyle

        Key | Default value
        --- | ---
        IllegalIdentifierName#format | `object, integer, string, objects, integers, strings`
    - CodeNarc

        Key | Default value
        --- | ---
        IllegalVariableName#names | `object, integer, string, object, integers, strings`
    - Pylint

        Key | Default value
        --- | ---
        bad-names | `objs, ints, strs`

### Package name

Package names should be written in lowercase with no separators.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    package com.example.user_management
    ```
=== "Java"
    ```java
    package com.example.user_management;
    ```
=== "Groovy"
    ```groovy
    package com.example.user_management
    ```
=== "Python"
    ```
    └ user_management
      └ UserConfig.py
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    package com.example.usermanagement
    ```
=== "Java"
    ```java
    package com.example.usermanagement;
    ```
=== "Groovy"
    ```groovy
    package com.example.usermanagement
    ```
=== "Python"
    ```python
    └ user_management
      └ user_config.py
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

### Required generic name

Only use common generic type names according to Oracle. Multiple generic types
declaration is ignored.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    class Box<A>() {}

    fun <X> rotate(box: Box<X>) {}
    ```
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

=== "Kotlin"
    ```kotlin
    class Box<E>() {}

    fun <T> rotate(box: Box<T>) {}
    ```
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
    - Ktlint

        Key | Default value
        --- | ---
        rulebook_required_generic_names | `E, K, N, T, V`
    - Checkstyle

        Key | Default value
        --- | ---
        ClassTypeParameterName#format | `E, K, N, T, V`
        InterfaceTypeParameterName#format | `E, K, N, T, V`
        MethodTypeParameterName#format | `E, K, N, T, V`
        RecordTypeParameterName#format | `E, K, N, T, V`
        TypeParameterName#format | `E, K, N, T, V`
    - CodeNarc

        Key | Default value
        --- | ---
        RequiredGenericName#names | `E, K, N, T, V`
    - Pylint

        Key | Default value
        --- | ---
        rulebook-required-generic-names | `E, K, N, T, V`

## Ordering

### Block tag order

Block tags should be ordered in the following sequence: `@constructor`,
`@receiver`, `@param`, `@property`, `@return`, `@throws`, `@see`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    abstract fun createUser(name: String): User
    ```
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

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    abstract fun createUser(name: String): User
    ```
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

### Built-in function position

Place Object built-in methods such as `toString()`, `hashCode()` and `equals()`
at the end of the class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2 4"
    class Notification(val message: String) {
        override fun toString(): String = "$id: $message"

        val id: Int = randomize()
    }
    ```
=== "Java"
    ```java hl_lines="2-5 7-8 10-13"
    class Notification {
        @Override
        public String toString() {
            return String.format("%d: %s", id, message);
        }

        final String message;
        final int id;

        Notification(String message) {
            this.message = message;
            this.id = randomize();
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2-4 6-7 9-12"
    class Notification {
        String toString() {
            return "${id}: ${message}"
        }

        final String message
        final int id

        Notification(String message) {
            this.message = message
            this.id = randomize()
        }
    }
    ```
=== "Python"
    ```python hl_lines="2-3 5-7"
    class Notification:
        def __str__(self):
            return f'{self.id}: {self.message}'

        def __init__(self, message):
            self.message = message
            self.id = randomize()
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="2 4"
    class Notification(val message: String) {
        val id: Int = randomize()

        override fun toString(): String = "$id: $message"
    }
    ```
=== "Java"
    ```java hl_lines="2-3 5-8 10-13"
    class Notification {
        final String message;
        final int id;

        Notification(String message) {
            this.message = message;
            this.id = randomize();
        }

        @Override
        public String toString() {
            return String.format("%d: %s", id, message);
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2-3 5-8 10-12"
    class Notification {
        final String message
        final int id

        Notification(String message) {
            this.message = message
            this.id = randomize()
        }

        String toString() {
            return "${id}: ${message}"
        }
    }
    ```
=== "Python"
    ```python hl_lines="2-4 6-7"
    class Notification:
        def __init__(self, message):
            self.message = message
            self.id = randomize()

        def __str__(self):
            return f'{self.id}: {self.message}'
    ```

### Import order

Import directives should be ordered alphabetically without any blank lines.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    import java.util.List

    import com.example.User
    ```
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
=== "Python"
    ```python
    import utils

    import user
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    import com.example.User
    import java.util.List
    ```
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
=== "Python"
    ```python
    import user
    import utils
    ```

### Inner class position

Place inner classes at the end of the class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2"
    class Article(content: String, author: Author) {
        class Author(name: String)

        constructor(content: String) : this(content, null)
    }
    ```
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
=== "Python"
    ```python hl_lines="2"
    class Article:
        class Author:
            pass

        def __init__(self, content, author = None):
            pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="4"
    class Article(content: String, author: Author) {
        constructor(content: String) : this(content, null)

        class Author(name: String)
    }
    ```
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
constructors and methods.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2-4 6 8 10-12"
    class Car(brand: String, model: String) {
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
=== "Java"
    ```java hl_lines="2 4-6 8 10-12"
    class Car {
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
    ```groovy hl_lines="2 4-6 8 10-12"
    class Car {
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
=== "Python"
    ```python hl_lines="2-3 5 7-8"
    class Car:
        def __init__(self, brand, model = 'Unknown'):
            pass

        wheels = 4

        def start(self):
            pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="2 4-6 8 10-12"
    class Car(brand: String, model: String): Vehicle {
        override val wheels = 4

        init {
            log("Car created")
        }

        constructor(brand: String) : this(brand, "Unknown")

        fun start() {
            log("Car started")
        }
    }
    ```
=== "Java"
    ```java hl_lines="2 4 6-8 10-12"
    class Car implements Vehicle {
        int wheels = 4;

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, "Unknown");
        }

        void start() {
            log("Car created");
        }
    }
    ```
=== "Groovy"
    ```groovy hl_lines="2 4 6-8 10-12"
    class Car implements Vehicle {
        var wheels = 4

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, 'Unknown')
        }

        def start() {
            log('Car created')
        }
    }
    ```
=== "Python"
    ```python hl_lines="2 4-5 7-8"
    class Car(Vehicle):
        wheels = 4

        def __init__(self, brand, model = 'Unknown'):
            pass

        def start(self):
            pass
    ```

### Overload function position

Place overloaded functions next to each other.

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="3 5"
    fun sum(a: Int, b: Int): Int = a + b

    fun times(a: Int, b: Int): Int = a * b

    fun sum(a: Int, b: Int, c: Int): Int = a + b + c
    ```
=== "Java"
    ```java hl_lines="5-7 9-11"
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
    ```groovy hl_lines="5-7 9-11"
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

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="3 5"
    fun sum(a: Int, b: Int): Int = a + b

    fun sum(a: Int, b: Int, c: Int): Int = a + b + c

    fun times(a: Int, b: Int): Int = a * b
    ```
=== "Java"
    ```java hl_lines="5-7 9-11"
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
    ```groovy hl_lines="5-7 9-11"
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

### Static import position

Static import directives are to be placed after normal imports, separated by a
blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"
    ```java
    import static java.lang.Math.PI;

    import java.util.List;
    ```
=== "Groovy"
    ```groovy
    import static java.lang.Math.PI

    import java.util.List
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java
    import java.util.List;

    import static java.lang.Math.PI;
    ```
=== "Groovy"
    ```groovy
    import java.util.List

    import static java.lang.Math.PI
    ```

## Spacing

### Block comment spaces

Ensures that block comments starts and ends with a whitespace. In multiline
comments, each line after the asterisk should be indented by a whitespace.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    /**Pass on user behavior.*/
    fun report()
    ```
=== "Java"
    ```java
    /**Pass on user behavior.*/
    void report() {}
    ```
=== "Groovy"
    ```groovy
    /**Pass on user behavior.*/
    def report() {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    /** Pass on user behavior. */
    fun report()
    ```
=== "Java"
    ```java
    /** Pass on user behavior. */
    void report() {}
    ```
=== "Groovy"
    ```groovy
    /** Pass on user behavior. */
    def report() {}
    ```

### Block comment trim

Do not start or end block comments with whitespaces.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
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
=== "Python"
    ```python hl_lines="2 5"
    """

    AUTHOR: John Doe
    LICENSE: Apache 2.0

    """
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
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
=== "Python"
    ```python
    """
    AUTHOR: John Doe
    LICENSE: Apache 2.0
    """
    ```

### Block tag indentation

Multi-line block tag descriptions should be indented by four spaces, or five
spaces from the leading asterisk.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    fun abs(num: Int): Int
    ```
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

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    fun abs(num: Int): Int
    ```
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

### Block tag separator

Separate block tag group from the summary with a blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    fun abs(number: Int): Int
    ```
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

**:material-star-four-points:{ #accent } After**

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

### Case separator

Multiline switch-case entries end with a blank line while short entries are
joined.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="3"
    when {
        event.isCancelled() -> return

        event.date < now -> {
            val message = "Event is in the past"
            throw IllegalStateException(message)
        }
        else -> createEvent(event)
    }
    ```
=== "Java"
    ```java hl_lines="4"
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
    ```groovy hl_lines="4"
    switch (event) {
        case CANCELLED:
            return;

        case PAST:
            var message = 'Event is in the past';
            throw new IllegalStateException(message);
        default:
            createEvent(event);
    }
    ```
=== "Python"
    ```python hl_lines="4"
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

=== "Kotlin"
    ```kotlin hl_lines="7"
    when {
        event.isCancelled() -> return
        event.date < now -> {
            val message = "Event is in the past"
            throw IllegalStateException(message)
        }

        else -> createEvent(event)
    }
    ```
=== "Java"
    ```java hl_lines="7"
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
    ```groovy hl_lines="7"
    switch (event) {
        case CANCELLED:
            return;
        case PAST:
            var message = 'Event is in the past';
            throw new IllegalStateException(message);

        default:
            createEvent(event);
    }
    ```
=== "Python"
    ```python hl_lines="7"
    match event:
        case CANCELLED:
            return
        case PAST:
            message = 'Event is in the past'
            raise ValueError(message)

        case _:
            create_event(event)
    ```

### Code block trim

Prohibits empty first and last lines in code blocks.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2 5"
    fun onReceive(value: Int?) {

        if (value != null) {
            total += value

        }
    }
    ```
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
=== "Python"
    ```python hl_lines="2"
    def on_receive(value):

        if value is not None:
            total += value
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    fun onReceive(value: Int?) {
        if (value != null) {
            total += value
        }
    }
    ```
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
=== "Python"
    ```python
    def on_receive(value):
        if value is not None:
            total += value
    ```

### Comment spaces

End-of-file comments should be separated by a single whitespace from the
preceding code, and start with a single whitespace.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    println("This is a code")//This is a comment
    ```
=== "Java"
    ```java
    System.out.println("This is a code");//This is a comment
    ```
=== "Groovy"
    ```groovy
    println('This is a code')//This is a comment
    ```
=== "Python"
    ```python
    print('This is a code')#This is a comment
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    println("This is a code") // This is a comment
    ```
=== "Java"
    ```java
    System.out.println("This is a code"); // This is a comment
    ```
=== "Groovy"
    ```groovy
    println('This is a code') // This is a comment
    ```
=== "Python"
    ```python
    print('This is a code')  # This is a comment
    ```

!!! warning
    PEP8 requires leading two spaces for comments.

### Comment trim

Prohibits empty first and last lines in EOL comments.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
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
=== "Python"
    ```python hl_lines="1 4"
    #
    # This is a
    # multiline comment
    #
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    // This is a
    // multiline comment
    ```
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
=== "Python"
    ```python
    # This is a
    # multiline comment
    ```

### Duplicate blank line

Prohibits consecutive blank lines in the code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="3"
    val message = "Hello"


    println(message)
    ```
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
=== "Python"
    ```python hl_lines="3"
    message = 'Hello'


    print(message)
    ```

!!! warning
    PEP8 allows two blank lines between top-level functions and class
    definitions.

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    val message = "Hello"

    println(message)
    ```
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
=== "Python"
    ```python
    message = 'Hello'

    print(message)
    ```

### Duplicate blank line in block comment

Prohibits consecutive blank lines in block comments.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
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
=== "Python"
    ```python hl_lines="4"
    """
    This is a


    very long comment
    """
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    /**
     * This is a
     *
     * very long comment
     */
    ```
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

=== "Kotlin"
    ```kotlin hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
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
=== "Python"
    ```python hl_lines="3"
    # This is a
    #
    #
    # very long comment
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    // This is a
    //
    // very long comment
    ```
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
=== "Python"
    ```python
    # This is a
    #
    # very long comment
    ```

### Member separator

Class, function and property declarations should be separated by a blank line.
There is an exception for single-line properties.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    interface Vehicle {
        val wheels: Int
        fun start()
    }
    ```
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
        int getWheels();
        def start();
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

=== "Kotlin"
    ```kotlin hl_lines="3"
    interface Vehicle {
        val wheels: Int

        fun start()
    }
    ```
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
        int getWheels();

        void start();
    }
    ```
=== "Python"
    ```python hl_lines="3"
    class Vehicle:
        wheels: int

        def start(self):
            pass
    ```

### Unexpected blank line before package

The first line of a file cannot be a blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1"
    \n

    package com.example

    fun execute() {}
    ```
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
=== "Python"
    ```python hl_lines="1"
    \n

    def execute():
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    package com.example

    fun execute() {}
    ```
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
=== "Python"
    ```python
    def execute():
        pass
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

=== "Kotlin"
    ```kotlin
    throw Exception()
    ```
=== "Java"
    ```java
    throw new Exception();
    ```
=== "Groovy"
    ```groovy
    throw new Exception()
    ```
=== "Python"
    ```python
    raise Exception()
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    throw IllegalStateException()
    ```
=== "Java"
    ```java
    throw new IllegalStateException();
    ```
=== "Groovy"
    ```groovy
    throw new IllegalStateException()
    ```
=== "Python"
    ```python
    raise ValueError()
    ```

### Missing braces

Enforces the use of braces for multiline `if`, `else`, `for`, `while` and `do`
statements.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials)
    else
        showError()
    ```
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

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else {
        showError()
    }
    ```
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

### Nested if-else

If a block ends with an if statement without else and the body is at least 2
lines, it should be inverted to avoid nesting and unnecessary indentation.

**:material-star-four-points-outline:{ #accent } Before**

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
=== "Python"
    ```python hl_lines="2 3"
    def login(user: User):
        if user.is_valid():
            if not is_logged_in(user):
                update_profile(user)
                display_dashboard()
    ```

**:material-star-four-points:{ #accent } After**

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

=== "Kotlin"
    ```kotlin
    when (token) {
        is Token.ValueToken -> callback(token)
    }
    ```
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
=== "Python"
    ```python
    match token:
        case Token.VALUE_TOKEN:
            callback(token)
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    if (token is Token.ValueToken) {
        callback(token)
    }
    ```
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
=== "Python"
    ```python
    if token == Token.VALUE_TOKEN:
        callback(token)
    ```

## Wrapping

### Assignment wrap

Assignee and the value of assignment spanning multiple lines should be separated
by a newline.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="1"
    val message = buildString {
        append("Hello")
    }
    ```
=== "Java"
    ```java hl_lines="1"
    String message = new StringBuilder()
        .append("Hello")
        .toString();
    ```
=== "Groovy"
    ```groovy hl_lines="1"
    val message = new StringBuilder()
        .append('Hello')
        .toString()
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="1-2"
    val message =
        buildString {
            append("Hello")
        }
    ```
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

### Block comment unwrap

Short block comments should be written in a single line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    /** The quick brown fox jumps over the lazy dog. */
    ```

### Chain call wrap

Each method call in a chain should be aligned with the dot operator.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2-3"
    val senderId =
        notification.getSender()
            .id.takeIf { it.isNotBlank() }
    ```
=== "Java"
    ```java hl_lines="2-3"
    int senderId =
        notification.getSender()
            .getId();
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="2-4"
    val senderId =
        notification
            .getSender()
            .id
    ```
=== "Java"
    ```java hl_lines="2-4"
    int senderId =
        notification
            .getSender()
            .getId();
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

### Empty code block unwrap

Empty code blocks should not contain any other characters in between the braces.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin
    fun main() {
    }
    ```
=== "Java"
    ```java
    void main() {
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    fun main() {}
    ```
=== "Java"
    ```java
    void main() {}
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
    ```java hl_lines="5"
    int sum =
        IntStream
            .range(0, 10)
            .map(
              i -> i
                * 2
            ).sum();
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"
    ```java hl_lines="6"
    int sum =
        IntStream
            .range(0, 10)
            .map(
                i ->
                  i * 2
            ).sum();
    ```

### Operator wrap

A line break should be placed after the operator in a binary expression.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="3-4"
    val total =
        subtotal
        + tax
        - discount
    ```
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

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="2-3"
    val total =
        subtotal +
        tax -
        discount
    ```
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

### Parameter wrap

When breaking a multiline parameter list, each parameter should be placed on a
new line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"
    ```kotlin hl_lines="2"
    fun createUser(
        name: String, email: String, age: Int
    )
    ```
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
=== "Python"
    ```python hl_lines="2"
    def create_user(
        name: str, email: str, age: int
    )
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin hl_lines="2-4"
    fun createUser(
        name: String,
        email: String,
        age: Int,
    )
    ```
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

=== "Kotlin"
    ```kotlin
    val x = 0; val y = 0
    ```
=== "Java"
    ```java
    int x = 0; int y = 0;
    ```
=== "Groovy"
    ```groovy
    var x = 0; var y = 0
    ```
=== "Python"
    ```python
    x = 0; y = 0
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"
    ```kotlin
    val x = 0
    val y = 0
    ```
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
=== "Python"
    ```python
    x = 0
    y = 0
    ```
