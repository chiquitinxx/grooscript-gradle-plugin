requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

// Start the main app logic.
requirejs(['jquery', 'grooscript', 'JQueryUtils', 'grooscript-binder', 'app/Item'], function($) {
    var jQueryUtils = JQueryUtils();
    item = Item();
    console.log('Exists a variable item with a property text in main context: ' + item)

    var binder = Binder();
    binder.jQueryUtils = jQueryUtils;
    $(document).ready(function() {
        binder.bindAllProperties(item);
        console.log('Bind properties done.');
        console.log('2');
        binder.bindAllMethods(item);
        console.log('Bind methods done.');
    });

});