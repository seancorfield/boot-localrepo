# boot-localrepo

A Boot task that wraps lein-localrepo, so you can work with your local Maven repository.

[![Clojars Project](https://img.shields.io/clojars/v/seancorfield/boot-localrepo.svg)](https://clojars.org/seancorfield/boot-localrepo)

## Usage

To guess the Boot (Maven) coords of a filename:

    $ boot -d seancorfield/boot-localrepo coords -f local/jars/foo-bar-1.0.6.jar

That produces:

    local/jars/foo-bar-1.0.6.jar foo-bar/foo-bar 1.0.6

To install a local JAR file into the repository:

    $ boot -d seancorfield/boot-localrepo install-artifact -f foobar.jar -P bar/foo -v 1.0

That will install `foobar.jar` as `bar/foo/1.0/foo-1.0.jar` with a generated POM.

To see what files are in the local repository:

    $ boot -d seancorfield/boot-localrepo list-artifacts | fgrep foo

Each task has a `-h` / `--help` option to display more details

## License

Copyright © 2017 Sean Corfield, all rights reserved.

Distributed under the Eclipse Public License version 1.0.
