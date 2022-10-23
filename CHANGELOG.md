# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [3-alpha4-SNAPSHOT]
- default recognition of :kind/var, :kind/buffered-image
- disabled default recognition of :kind/hiccup
- slightly extended API

## [3-alpha3] - 2022-10-14
- support for general predicates in default advice

## [3-alpha2] - 2022-10-09
- default recognition of :kind/hiccup

## [3-alpha1] - 2022-10-05
a revised conceptual model:
- a tiny general api giving advice to tools
- default behavior is only one kind of advice
  - :kind/pprint is the default 
  - looking into the form rather than the code

## [2-alpha2] - 2022-04-05
- extended the functionality of the kind namespaces -- now its members are multiarity functions for user convenience
- regorganized implementation namespaces

## [2-alpha1] - 2022-03-28
- various API changes, in particular the Kindness protocol

## [1-alpha3] - 2021-11-12
- added a `to-hiccup` function to the api

## [1-alpha2] - 2021-10-14
- changing the kind and behaviour resolution api

## [1-alpha1] - 2021-10-09
Initial version
