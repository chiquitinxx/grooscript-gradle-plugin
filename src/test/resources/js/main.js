requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

// Start the main app logic.
requirejs(['jquery', 'grooscript', 'grooscript-binder', 'app/Item'], function($) {

    item = Item();
    console.log('Exists a variable item with a property text in main context: ' + item)
    var i, list = item.getMethods();
    list.each(function (item) {
        console.log('Method:' + item.name);
    });

    var binder = Binder();
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
        binder.bindAllMethods(item);
        console.log('Bind methods done.');
    });

});