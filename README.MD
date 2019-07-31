Malayalam Spell Checker
--------------------------------
Malayalam, the native language of Kerala, India is [agglutinative](https://en.wikipedia.org/wiki/Agglutinative_language). This makes it particularly challenging to write a spell checker for it. Currently, there is no open-source spell checker available.

This is a proof of concept for an algorithm for Malayalam spell checking. Given a *Joiner* that implements word compounding rules and a word, the *Splitter* splits the word into morphemes. These morphemes can then be checked against a dictionary.

Next Steps
--------------------------------
1. A Joiner that implements Malayalam compounding rules exhaustively need to be created
2. Splitter should be tested against a large corpus to check if it is viable for all scenarios
