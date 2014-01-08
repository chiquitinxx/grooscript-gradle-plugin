grooscript-gradle-plugin 0.1
============================

Gradle plugin with grooscript tasks. To convert groovy files to javascript.

build.gradle example:

<pre>
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath group: 'org.grooscript', name: 'grooscript-gradle-plugin', version: '0.1'
    }
}

apply plugin: 'grooscript'

grooscript {
    source = ['src/main/groovy/presenters'] //Sources to be converted
    destination = 'js' //Target directory for js files
    classPath = ['src/main/groovy'] //Needed classpath's to compile source files
    convertDependencies = false //Convert dependencies in same file, by default is false
    customization = null //Customization in files, it's a closure, as for example { -> ast(groovy.transform.TypeChecked) }
}
</pre>

There are 2 tasks:

convert - to convert the files
daemon - to run daemon that detect file changes and convert the files

More info about grooscript in http://grooscript.org