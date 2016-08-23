function onLoad(){if(console.log("%cAtomScript v0.5.4.1","color: #0355ff; font-family: arial; font-size: 20px;"),console.log("%c©ZeroSeven Interactive 2015","color: #ff0330; font-family: arial;"),null!=AtomScript.src&&AtomScript.src.endsWith(".atom"))CURRENT_SRC=readFile(AtomScript.src).url,CURRENT_SRC_DIR=CURRENT_SRC.substring(0,CURRENT_SRC.lastIndexOf("/")),setScript(AtomScript.src),parseCode(),eval(AtomScript.CODE+"if(main)main();");else if(null!=AtomScript.src&&AtomScript.src.startsWith("#")){var id=AtomScript.src.substring(1,AtomScript.src.length),script=document.getElementById(id);"AtomScript"==script.getAttribute("type")?(AtomScript.CODE=script.innerHTML,parseCode(),eval(AtomScript.CODE+"if(main){ if(AtomScript.startConsole)Console.start(); main(); }")):console.error("Make sure the type of your script tag is 'AtomScript'...")}else if(null==AtomScript.src)for(var scripts=document.getElementsByTagName("script"),AtomScripts=[],i=0;i<scripts.length;i++){var script=scripts[i];"AtomScript"!=script.getAttribute("type")&&"text/AtomScript"!=script.getAttribute("type")||(AtomScript.CODE=script.innerHTML)}AtomScript.CODE=AtomScript.CODE}function parseCode(){removeComments(),includeFiles(),convertVariables(),convertMethods(),convertObjects(),convertObjectProperties(),convertNameSpaceSplitters(),convertObjectPropertyNameCaller(),convertObjectPropertyCaller(),convertEscapes(),removeComments(),convertColor()}function removeComments(){AtomScript.CODE=AtomScript.CODE.replace(/\B\#[^\n]+\n/g,"")}function convertVariables(){var a=AtomScript.CODE.match(/\B@\w+/g);if(null!=a)for(var b=0;b<a.length;b++)AtomScript.CODE=AtomScript.CODE.replace(a[b].substring(0,1),"var ")}function convertMethods(){var a=AtomScript.CODE.match(/\$\w+|\$\(/g);if(null!=a)for(var b=0;b<a.length;b++)AtomScript.CODE=AtomScript.CODE.replace(a[b].substring(0,1),"function ")}function convertObjects(){var a=AtomScript.CODE.match(/\*[^0-9;\s ]+/g);if(null!=a)for(var b=0;b<a.length;b++)AtomScript.CODE=AtomScript.CODE.replace(a[b].substring(0,1),"new ")}function convertObjectProperties(){AtomScript.CODE=AtomScript.CODE.replace(/this ->|this->|this-> |this -> /g,"this.")}function convertNameSpaceSplitters(){var a=AtomScript.CODE.match(/::/g);if(null!=a)for(var b=0;b<a.length;b++)AtomScript.CODE=AtomScript.CODE.replace(a[b],".")}function convertObjectPropertyCaller(){var a=AtomScript.CODE.match(/\b\<(.+?)\>/g);if(null!=a)for(var b=0;b<a.length;b++){var c=a[b],d=c.substring(1,c.length-1);AtomScript.CODE=AtomScript.CODE.replace(c,"."+d)}}function convertObjectPropertyNameCaller(){var a=AtomScript.CODE.match(/\b\<(.+?)\> \w+|\<(.+?)\>\w+/g);if(null!=a)for(var b=0;b<a.length;b++){var c=a[b],d=c.substring(1,c.indexOf(">")),e=c.split(/\>/g)[1];AtomScript.CODE=AtomScript.CODE.replace(c,"."+d+"."+e)}}function convertColor(){AtomScript.CODE=AtomScript.CODE.replace(/%c/g,"#")}function convertEscapes(){AtomScript.CODE=AtomScript.CODE.replace(/%evar /g,"@").replace(/%efunction /g,"$").replace(/%e#/g,"#")}function includeFiles(){var matches=AtomScript.CODE.match(/include[^;]+;/g);if(null!=matches)for(var i=0;i<matches.length;i++)if(AtomScript.src.endsWith(".atom")&&!AtomScript.src.startsWith("#")){var match=matches[i],includer=match.split(" ")[1],file=CURRENT_SRC_DIR+"/"+eval(includer);if(file.endsWith(".atom")){var read=readFile(file).text;AtomScript.CODE=AtomScript.CODE.replace(match,read)}else if(file.startsWith("http://")&&file.endsWith(".atom")){var read=readFile(file).text;AtomScript.CODE=AtomScript.CODE.replace(match,read)}}else if(AtomScript.src.startsWith("#")){var match=matches[i],file=eval(match.split(" ")[1]);if(file.endsWith(".atom")){var read=readFile(file).text;AtomScript.CODE=AtomScript.CODE.replace(match,read)}else if(file.startsWith("http://")&&file.endsWith(".atom")){var read=readFile(file).text;AtomScript.CODE=AtomScript.CODE.replace(match,read)}else{var read=readFile(file+".atom").text;AtomScript.CODE=AtomScript.CODE.replace(match,read)}}}function readFile(a){var b=new XMLHttpRequest;b.open("GET",a,!1);var c=null;return b.onload=function(a){4==b.readyState&&200===b.status&&(c=b.responseText)},b.onerror=function(a){console.error(b.statusText)},b.send(null),{text:c,url:b.responseURL,request:b}}function setScript(a){AtomScript.CODE=readFile(a).text}window.onload=onLoad;var AtomScript={src:null,consolePath:"AtomScript/console/main.html",startConsole:!1,CODE:"",Proton:!0},Console={},CURRENT_SRC,CURRENT_SRC_DIR;String.prototype.endsWith||Object.defineProperty(String.prototype,"endsWith",{value:function(a,b){var c=this.toString();(void 0===b||b>c.length)&&(b=c.length),b-=a.length;var d=c.indexOf(a,b);return d!==-1&&d===b}}),String.prototype.startsWith||Object.defineProperty(String.prototype,"startsWith",{enumerable:!1,configurable:!1,writable:!1,value:function(a,b){return b=b||0,this.lastIndexOf(a,b)===b}}),String.prototype.startsWith||Object.defineProperty(String.prototype,"startsWith",{enumerable:!1,configurable:!1,writable:!1,value:function(a,b){return b=b||0,this.lastIndexOf(a,b)===b}}),[].includes||(Array.prototype.includes=function(a){"use strict";var b=Object(this),c=parseInt(b.length)||0;if(0===c)return!1;var e,d=parseInt(arguments[1])||0;d>=0?e=d:(e=c+d,e<0&&(e=0));for(var f;e<c;){if(f=b[e],a===f||a!==a&&f!==f)return!0;e++}return!1}),Console.start=function(){Console.Window=window.open(AtomScript.consolePath,"AtomScriptConsole","width=650,height=440,menubar=no,statusbar=no,location=no"),Console.Document=Console.Window.document,Console.inputElement=Console.Document.getElementById("input"),Console.displayElement=Console.Document.getElementById("display"),Console.out("Welcome to the AtomScript Console!")},Console.out=function(a){var b=createText(a);b.style.display="block",append(b,Console.displayElement)};