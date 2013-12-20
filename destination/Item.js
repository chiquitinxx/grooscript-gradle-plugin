function Item() {
  var gSobject = gs.inherit(gs.baseClass,'Item');
  gSobject.clazz = { name: 'Item', simpleName: 'Item'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.name = null;
  gSobject.value = null;
  gSobject.Item1 = function(map) { gs.passMapToObject(map,this); return this;};
  if (arguments.length==1) {gSobject.Item1(arguments[0]); }
  
  return gSobject;
};
gs.assert(gs.equals(gs.gp(Item(gs.map().add("value",5)),"value"), 5), null);
