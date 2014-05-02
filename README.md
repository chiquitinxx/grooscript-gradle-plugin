grooscript-gradle-plugin 0.3-SNAPSHOT
=====================================

Gradle plugin with grooscript tasks. To convert groovy files to javascript.

build.gradle example:

<pre>
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'org.grooscript:grooscript-gradle-plugin:0.3'
    }
}

apply plugin: 'grooscript'

//If you need to change any conversion option, can do this way:
grooscript {
    source = ['src/main/groovy/presenters'] //Sources to be converted(List<String>), default is ['src/main/groovy']
    destination = 'js' //Target directory for js files, default is 'src/main/webapp/js/app'
    classPath = ['src/main/groovy'] //Needed classpath's to compile source files(List<String>), default is ['src/main/groovy']
    convertDependencies = true //Convert dependencies in same file, by default is false
    customization = null //Customization in files, it's a closure, as for example { -> ast(groovy.transform.TypeChecked) }
    initialText = '//Grooscript converted file'
    initialText = '//End converted file'
    recursive = true //Default is false
}
</pre>

There are 3 tasks:

convert - to convert groovy files

daemon - to run daemon that detect file changes and convert the files

initStaticWeb - create a static web project with index.htlm in src/main/webabb, to work with require.js and grooscript

More info about grooscript in http://grooscript.org