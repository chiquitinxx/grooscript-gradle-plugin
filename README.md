grooscript-gradle-plugin 0.6
============================

Gradle plugin to convert your groovy files to javascript using grooscript.

__Important: plugin requires Gradle 2, grooscript requires Groovy 2__

build.gradle to use the plugin with any gradle version:

<pre>
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'org.grooscript:grooscript-gradle-plugin:0.6'
    }
}

apply plugin: 'org.grooscript.conversion'
</pre>

build.gradle to use the plugin with gradle 2.1+:

<pre>
plugins {
  id "org.grooscript.conversion" version "0.6"
}
</pre>

To change any conversion option:

<pre>
//If you need to change any conversion option, can do this way, optional
grooscript {
    source = ['src/main/groovy/presenters'] //Sources to be converted(List<String>), default is ['src/main/groovy']
    destination = 'js' //Target directory for js files, default is 'src/main/webapp/js/app'
    classPath = ['src/main/groovy'] //Needed classpath's to compile source files(List<String>), default is ['src/main/groovy']
    convertDependencies = true //Convert dependencies in same file, by default is false
    customization = null //Customization in files, it's a closure, as for example { -> ast(groovy.transform.TypeChecked) }
    initialText = '//Grooscript converted file'
    initialText = '//End converted file'
    recursive = true //Default is false
    includeJsLib = 'grooscript' //Include a grooscript js lib in the result, default is null
}
</pre>

There are 4 tasks:

__convert__ - to convert groovy files to javascript

__daemon__ - to run daemon that detect file changes and convert the files. This task blocks execution, so use it alone.

__initStaticWeb__ - create a static web project with index.html in src/main/webabb, to work with grooscript

__thread__ - run daemon to convert files to javascript. Executes daemon in a thread, so is perfect to use with other task

Guide about using this plugin [here](http://grooscript.org/starting_gradle.html)