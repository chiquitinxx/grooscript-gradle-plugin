function Item() {
    var gSobject = gs.inherit(gs.baseClass,'Item');
    gSobject.clazz = { name: 'Item', simpleName: 'Item'};
    gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
    gSobject.text = "";
    gSobject.area = "";
    gSobject.bike = false;
    gSobject.car = false;
    gSobject.group1 = "";
    gSobject.combo = "";
    gSobject.buttonClick = function() {
        console.log('Button clicked!');
    };
    gSobject.Item1 = function(map) { gs.passMapToObject(map,this); return this;};
    if (arguments.length==1) {gSobject.Item1(arguments[0]); }

    return gSobject;
};