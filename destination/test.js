//Need mocha installed to run this test, use sudo if fails
var fs = require('fs');
var gs = require('grooscript');

eval(fs.readFileSync('Item.js')+'');

var assert = require("assert");

describe('initial tests on Item', function(){
    it('initializated', function(){
        assert.equal(Item().clazz.name, 'Item');
    })
})
