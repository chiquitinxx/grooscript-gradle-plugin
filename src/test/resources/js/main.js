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
        /*binder.bind('#text', item, 'text', function(newValue) { console.log('Change in text to: '+newValue);});
        binder.bind('#area', item, 'area');
        binder.bind('#bike', item, 'bike');
        binder.bind('#car', item, 'car');
        binder.bind('input:radio[name=group1]', item, 'radio');
        binder.bind('#combo', item, 'combo');
        console.log('Binds done.');*/
        binder.bindAllProperties(item);
        console.log('Bind properties done.');
        console.log('2');
        binder.bindAllMethods(item);
        console.log('Bind methods done.');
    });

});