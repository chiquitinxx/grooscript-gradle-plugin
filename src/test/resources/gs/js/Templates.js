function Templates() {
  var gSobject = gs.inherit(gs.baseClass,'Templates');
  gSobject.clazz = { name: 'org.grooscript.gradle.template.Templates', simpleName: 'Templates'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  Object.defineProperty(gSobject, 'templates', { get: function() { return Templates.templates; }, set: function(gSval) { Templates.templates = gSval; }, enumerable: true });
  gSobject.applyTemplate = function(x0,x1) { return Templates.applyTemplate(x0,x1); }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
Templates.applyTemplate = function(name, model) {
  if (model === undefined) model = gs.map();
  var cl = Templates.templates[name];
  gs.sp(cl,"delegate",model);
  return gs.execCall(cl, this, [model]);
}
Templates.templates = gs.map().add("books.gtpl",function(model) {
  if (model === undefined) model = gs.map();
  return gs.mc(HtmlBuilder,"build",[function(it) {
    gs.mc(Templates,"h1",[gs.gp(model,"title")]);
    return gs.mc(Templates,"ul",[function(it) {
      return gs.mc(gs.gp(model,"books"),"each",[function(it) {
        return gs.mc(Templates,"p",["Title: " + (gs.gp(it,"title")) + " Author: " + (gs.gp(it,"author")) + ""]);
      }]);
    }]);
  }]);
}).add("numbers.gtpl",function(model) {
  if (model === undefined) model = gs.map();
  return gs.mc(HtmlBuilder,"build",[function(it) {
    var groovyTimes = function(number) {
      return gs.multiply("Groovy", number);
    };
    return gs.mc(model,"each",[function(it) {
      return gs.mc(Templates,"p",[gs.execCall(groovyTimes, this, [it])]);
    }]);
  }]);
});
