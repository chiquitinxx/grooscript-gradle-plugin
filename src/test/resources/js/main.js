requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

// Start the main app logic.
requirejs(['jquery', 'grooscript', 'gQueryImpl', 'grooscript-binder', 'app/Item'], function($) {
    var jQueryUtils = GQueryImpl();
    item = Item();
    console.log('Exists a variable item with a property text in main context: ' + item)

    var binder = Binder();
    binder.gQuery = jQueryUtils;
    $(document).ready(function() {
        binder.call(item);
        console.log('Bind all done.');
    });

});