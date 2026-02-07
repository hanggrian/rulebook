class Foo:
    def bar(self):
        print()

    def __str__(self):
        return 'baz'

    def __hash__(self):
        return 0

    def __eq__(self, other):
        return False
