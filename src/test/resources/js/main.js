requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

// Start the main app logic.
requirejs(['jquery', 'grooscript', 'grooscript-binder'], function($) {

    item = {
        text: '',
        area: '',
        bike: false,
        car: false,
        radio: '',
        combo: ''
    };
    console.log('Exists a variable item with a property text in main context: ' + item)

    var binder = Binder();
    $(document).ready(function() {
        binder.bind('#text', item, 'text');
        binder.bind('#area', item, 'area');
        binder.bind('#bike', item, 'bike');
        binder.bind('#car', item, 'car');
        binder.bind('input:radio[name=group1]', item, 'radio');
        binder.bind('#combo', item, 'combo');
        console.log('Binds done.');
    });
});