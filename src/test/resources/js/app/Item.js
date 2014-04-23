function Item() {
  var gSobject = gs.inherit(gs.baseClass,'Item');
  gSobject.clazz = { name: 'groovy.Item', simpleName: 'Item'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.text = "";
  gSobject.area = "";
  gSobject.bike = false;
  gSobject.car = false;
  gSobject.group1 = "";
  gSobject.combo = "";
  gSobject['buttonClick'] = function(it) {
    return gs.println("Button clicked!");
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
