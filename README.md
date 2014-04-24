# WikiBot
I'm trying to create tools to simplify creating articles for [Wikipedia][wk]. 
This tool called **wiki bot** and nowadays it can create article stubs.

[wk]: http://en.wikipedia.org

## Theory
- **Target file** - XML file, where you write parameters for bot.
- **Template file** - file-skeleton for article stub.
- **Result file** - file with article stub.
- **Site parser** - converter for getting data from site.

## Usage
For creating stub for article, make these steps:
- get bot *.jar file (usually WikiBot_majorversion_minorversion.jar).
- edit template file and target files.
- Windows users can use WikiBot_majorversion_minorversion.bat file to start creating stubs.
- improve generated article.
- paste text to wikipedia.

## Thanks
To get information from site I use special library - [boilerpipe][bl]. 

Library [Xstream][xs] provide functionality to work with XML files.

[bl]: https://code.google.com/p/boilerpipe/
[xs]: http://xstream.codehaus.org
