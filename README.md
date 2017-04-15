# Simple example of a multilingual Geb project

This a simple example of a test automatisation project with Geb and
Spock. The [Spock](http://spockframework.org/) test opens an
[online dictionary](http://pons.com) and looks up a single word.
The test is localized by several languages, concretely german and
spanish. The browser automatisation is performed by
[Geb](http://www.gebish.org/). The Geb page representing the entry point
of the online dictionary is localized by its *contents* and `URL`.

This means that although Geb doesn't provide a direct mean to
parametrise a `Page` class it is nevertheless possible to localize one
or several `Page`s with several languages. The `Page` is implemented
here independently of its languages; the localizations are provided by
external helping classes and are therefore easily extensible.
