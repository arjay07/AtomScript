/***
 *                 _                          _____                 _           _   
 *         /\     | |                        / ____|               (_)         | |  
 *        /  \    | |_    ___    _ __ ___   | (___     ___   _ __   _   _ __   | |_ 
 *       / /\ \   | __|  / _ \  | '_ ` _ \   \___ \   / __| | '__| | | | '_ \  | __|
 *      / ____ \  | |_  | (_) | | | | | | |  ____) | | (__  | |    | | | |_) | | |_ 
 *     /_/    \_\  \__|  \___/  |_| |_| |_| |_____/   \___| |_|    |_| | .__/   \__| v1.0
 *                                                                     | |          
 *                                                                     |_|          
 *
 *		©ZeroSeven Interactive 2016
 *		AtomScript is an interpreted programming language. The language is translated into JavaScript.
 * 
 */

var atomScript; 

// Main Function
window.addEventListener("load", function(){

	atomScript = new AtomScript();
	
	var url = window.location.href;
	if(AtomScript.src!=null){
		if(AtomScript.isAtomScriptFile(AtomScript.src))
			atomScript.run(AtomScript.src);
		else if(AtomScript.src.startsWith("#")){
			var id = AtomScript.src.substring(1, AtomScript.src.length);
			var script = document.getElementById(id);
			
			if(script.getAttribute("type") && script.getAttribute("type").toLowerCase().includes("atomscript")){
				var data = script.innerText;
				var blob = new Blob([data], {type: "text/plain"});
				atomScript.run(blob);
			}
		}

	}else if(url.includes("?")){
		var src = getParameterByName("atomscript.src", url);
		atomScript.run(src);
	}else{
		atomScript.start();
	}

});

// AtomScript Class
var AtomScript = function(){

	AtomScript.ATOM = ".atom";

	// Private Variables
	var WELCOME_MESSAGE = "";
	var YEAR = new Date().getFullYear();
	var ATOMSCRIPT_VERSION = "Lanthanum";
	var ATOMSCRIPT_VERSION_NUMBER = "1.0";
	var evaluator = null;
	var running = false;

	ASParser.generateKeywordReserves();

	this.init = function(evaluator){

		WELCOME_MESSAGE = "AtomScript v" + ATOMSCRIPT_VERSION_NUMBER + " (" + ATOMSCRIPT_VERSION + ")";

		evaluator.add("as", this);
		evaluator.add("AS", this);

	}

	this.start = function(){

		evaluator = new ASEvaluator();
		this.init(evaluator);

		running = true;
		AtomScript.data.console.enabled = true;

		console.log("%c" + WELCOME_MESSAGE, "color: #0355ff; font-family: arial; font-size: 20px;");
		console.log("%c©ZeroSeven Interactive " + YEAR, "color: #ff0330; font-family: arial;");

		this.startConsole();

		this.console.out(WELCOME_MESSAGE);
		this.console.out("©ZeroSeven Interactive " + YEAR);
		this.console.out("Welcome to AtomScript");

	}

	this.run = function(script){

		evaluator = new ASEvaluator();
		this.init(evaluator);

		var compiler = new ASCompiler(evaluator);

		this.startConsole();

		if(AtomScript.isAtomScriptFile(script)){
			compiler.cr(script);
		}else if(script instanceof Blob){
			var url = window.URL.createObjectURL(script);
			compiler.cr(url);
		}else{
			console.error("\"" + script + "\"  is not an AtomScript file.");
		}

	}

	this.startConsole = function(){
		this.console = new Console(function(str){
			if(running)evaluator.evaluate(str);
		});
		this.console.width = AtomScript.data.console.width;
		this.console.height = AtomScript.data.console.height-1;
		this.console.start();
		this.console.show();

		if(!AtomScript.data.console.show)this.console.hide();

		AtomScript.console = this.console;
		window.console.out = this.console.out;
		window.console.clr = this.console.clr;
		window.console.in = this.console.in;

	}

	this.setTitle = function(title){
		document.title = title;
	}

	this.getEvaluator = function(){

		return evaluator;

	}

	this.exit = function(){
		running = false;
	}

	this.getRunning = function(){
		return running;
	}

	this.setRunning = function(r){
		running = r;
	}

	AtomScript.isAtomScriptFile = function(name){
		if(name!=null && typeof name === "string")
			if(name.endsWith(AtomScript.ATOM)) 
				return true;
		return false;
	}

}

AtomScript.data = {

	console: {
		show: false,
		width: 100,
		height: 28
	}

};

AtomScript.src = null;

// ASCompiler ANCHOR: ASCompiler
var ASCompiler = function(evaluator){

	var compiledScript = "";
	var scriptFilePath = "";

	this.compile = function(file){
		compiledScript = new ASIO().readFile(file);
	}

	this.run = function(){
		evaluator.evaluate(compiledScript + "\nif(typeof(main) == \"function\")main();");
	}

	this.cr = function(file){
		this.compile(file);
		this.run();
	}

}

// ASEvaluator ANCHOR: ASEvaluator
var ASEvaluator = function(source){

	this.SRC = null;
	this.showErrorDialog = false;
	var parser = null;

	if(source!=null){
		this.SRC = source;
	}

	this.evaluate = function(code){

		var evaluated;

		try{

			parser = new ASParser(code);
			evaluated = window.eval(parser.parse());

		}catch(e){

			console.error(e);
			atomScript.console.out(e);

		}

		return evaluated;

	}

	this.getParser = function(){

		return parser;

	}

	this.setShowErrorDialog = function(showErrorDialog){

		this.showErrorDialog = showErrorDialog;

	}

	this.put = function(key, value){

		this.evaluate("@" + key + " = " + value);

	}

	this.eval = function(value){

		this.evaluate(value);

	}

	this.add = function(key, value){

		window[key] = value;

	}

}

// ASParser ANCHOR: ASParser

var ASParser = function(scriptcode){

	// Private variables
	var code = scriptcode;

	// Static variables
	ASParser.VAR;
	ASParser.FUNCTION;
	ASParser.NEW;

	var evaluator = atomScript.getEvaluator();

	this.parse = function(){

		removeComments();
		includeFiles();
		includeLibs();
		avoidKeywords();
		convertVariables();
		convertMethods();
		convertObjects();
		convertObjectProperties();
		convertNamespaceSplitters();
		convertObjectPropertyNameCaller();
		convertObjectPropertyCaller();
		exponents();
		multiplication();
		removeComments();
		convertEscapes();

		return code;

	}

	function removeComments(){

		var pattern = /\#(.+)/g

		var matcher;
		if(matcher = pattern.exec(code)){

			var match = matcher[0];
			if(!inString(code, matcher.index)){

				code = code.replace(match, "");
				removeComments();

			}

		}

	}

	function avoidKeywords(){

		var pattern = /\b(var|function|new)\b/g;

		var matcher;
		if(matcher = pattern.exec(code)){

			var match = matcher[0];
			if(!inString(code, matcher.index)){

				switch(match){
				
				case "var":
					code = code.replaceBetween(matcher.index, pattern.lastIndex, ASParser.VAR);
					avoidKeywords();
					break;
					
				case "function":
					code = code.replaceBetween(matcher.index, pattern.lastIndex, ASParser.FUNCTION);
					avoidKeywords();
					break;
					
				case "new":
					code = code.replaceBetween(matcher.index, pattern.lastIndex, ASParser.NEW);
					avoidKeywords();
					break;
				
				}

			}

		}

	}

	function convertVariables(){

		var pattern = /\B@\w+/g;

		var matcher;
		if(matcher = pattern.exec(code)){

			if(inString(code, matcher.index)){
				code = code.replaceBetween(matcher.index, matcher.index+1, "%e@");
				convertVariables();
			}else{
				code = code.replaceBetween(matcher.index, matcher.index+1, "var ");
				convertVariables();
			}

		}

	}

	function convertMethods(){

		var pattern = /\B\$\w+|\B\$\(/g;

		var matcher;
		if(matcher = pattern.exec(code)){

			if(inString(code, matcher.index)){
				code = code.replaceBetween(matcher.index, matcher.index+1, "%e$");
				convertMethods();
			}else{
				code = code.replaceBetween(matcher.index, matcher.index+1, "function ");
				convertMethods();
			}

		}

	}

	function convertObjects(){

		var pattern = /\B\*[^0-9;\s ]+/g;

		var matcher;
		if(matcher = pattern.exec(code)){

			if(inString(code, matcher.index)){
				code = code.replaceBetween(matcher.index, matcher.index+1, "%e*");
				convertObjects();
			}else{
				code = code.replaceBetween(matcher.index, matcher.index+1, "new ");
				convertObjects();
			}

		}

	}

	function convertObjectProperties(){

		code = code.replace(/this\s*->\s*/g, "this.");

	}

	function convertNamespaceSplitters(){

		code = code.replace(/\:\:/g, ".");

	}

	function convertObjectPropertyCaller(){

		var pattern = /\b\<([A-Za-z_][A-Za-z0-9_$]*?)\>/g;

		var matcher;
		while(matcher = pattern.exec(code)){

			var match = matcher[0];

			code = code.replace(match, "." + matcher[1]);

		}

	}

	function convertObjectPropertyNameCaller(){

		var pattern = /\b\<([A-Za-z_][A-Za-z0-9_$]*?)\>(\w+)/g;

		var matcher;
		while(matcher = pattern.exec(code)){

			var match = matcher[0];
			var propertyName = matcher[1];
			var other = matcher[2];

			code = code.replace(match, "." + propertyName + "." + other);

		}

	}
	
	function multiplication(){

		var pattern = /(\b\d+|\))\((.+?)\)/g;

		var matcher;
		while(matcher = pattern.exec(code)){

			var match = matcher[0];
			var multiplier = matcher[1];
			var expression = matcher[2];

			code = code.replace(match, multiplier + "*(" + expression + ")");

			multiplication();

		}

	}

	function exponents(){

		var pattern = /(\b\d+|\(.+?\)|\w+)\^\^(\b\d+|\(.+?\)|\w+)/g;

		var matcher;
		while(matcher = pattern.exec(code)){

			var match = matcher[0];
			var expression = matcher[1];
			var exponent = matcher[2];

			code = code.replace(match, "Math.pow(parseFloat(eval("+expression+")), parseFloat(eval("+exponent+")))");

			exponents();

		}

	}

	function convertEscapes(){

		code = code
				.replace(/%e\@/g, "@")
				.replace(/%e\$/g, "$")
				.replace(/%e\*/g, "*")
				.replace(/%e#/g, "#")
				.replace(/%c/g, "#");

	}

	function includeFiles(){

		var pattern = /\b(include)\b\s"((.+?).atom)"/g;

		var matcher;
		while(matcher = pattern.exec(code)){

			var match = matcher[0];
			var file = matcher[2];

			try{
				var read = new ASIO().readFile(file);
				code = code.replace(match, "");
				evaluator.evaluate(read);
				includeFiles();
			}catch(e){
				console.error(e);
				console.out("The module \"" + file + "\" does not exist...");
			}

		}

	}

	function includeLibs(){

		var pattern = /\b(include)\b\s\<(.+?)\>/g;

		var matcher;
		while(matcher = pattern.exec(code)){

			var match = matcher[0];
			var lib = matcher[2];

			var io = new ASIO();

			switch(lib){

				case "io":
				case "IO":
					code = code.replace(match, "");
					evaluator.add("io", io);
					evaluator.add("IO", io);
					includeLibs();
					break;

				default:
					code = code.replace(match, "");
					console.out("The library \"" + lib + "\" does not exist...");
					break;

			}

		}

	}

	this.getParsedCode = function(){

		return code;

	}

	String.prototype.replaceBetween = function(start, end, replacement){

		return this.substring(0, start) + replacement + this.substring(end);

	}

	function inString(str, start){

		var quotes = 0;

		for(var i = 0; i < start; i++){

			var c = "" + str.charAt(i);
			var pc = "";
			if(i > 0)pc = "" + str.charAt(i-1);
			if(c === "\"" && pc !== "\\"){

				quotes = quotes+1;

			}

		}

		if((quotes%2)==0)
			return false;
		else
			return true;

	}

	function fileExists(url){

		var http;
		if(window.XMLHttpRequest)
			http = new XMLHttpRequest();
		else
			http = new ActiveXObject();

		http.open("HEAD", url, false);
		http.send();
		return (http.status!=404);

	}

}

ASParser.generateKeywordReserves = function(){

	var chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz123456789_$";
	var random = "";

	for(var i = 0; i < 7; i++){

		random += chars.charAt(Math.floor(Math.random() * chars.length));

	}

	ASParser.VAR = "var_" + random;
	ASParser.FUNCTION = "function_" + random;
	ASParser.NEW = "new_" + random;

}

// ASIO Class (io, IO) ANCHOR: ASIO
var ASIO = function(){

	this.readFile = function(url){

		return this.getRequest(url, "text/plain").text;

	}

	this.getRequest = function(url, mimeType){

		var request;
		if(window.XMLHttpRequest)
			request = new XMLHttpRequest();
		else
			request = new ActiveXObject();
		request.open("GET", url, false);
		if(mimeType!=null||mimeType!=undefined) request.overrideMimeType(mimeType);
		var text = null;
		request.addEventListener("load", function(e){

			if(request.readyState == 4){

				if(request.status == 200){

					text = request.responseText;

				}

			}

		});

		request.addEventListener("error", function(e){

			console.error(request.statusText);
			console.out(request.statusText);

		});

		request.send(null); 

		return {text: text, url: request.responseURL, request: request};

	}

	this.out = function(output){

		console.out(output);

		return output;

	}

	this.print = function(){

		var str = "";

		for(var i = 0; i < arguments; i++){

			str += arguments + " ";

		}

		console.out(str);

	}

	this.clr = function(){

		console.clr();

	}

}

/***
 *              _                   _____           _       _   
 *         /\  | |                 / ____|         (_)     | |  
 *        /  \ | |_ ___  _ __ ___ | (___   ___ _ __ _ _ __ | |_ 
 *       / /\ \| __/ _ \| '_ ` _ \ \___ \ / __| '__| | '_ \| __|
 *      / ____ \ || (_) | | | | | |____) | (__| |  | | |_) | |_ 
 *     /_/    \_\__\___/|_| |_| |_|_____/ \___|_|  |_| .__/ \__|
 *                / ____|                    | |     | |        
 *               | |     ___  _ __  ___  ___ | | ___ |_|        
 *               | |    / _ \| '_ \/ __|/ _ \| |/ _ \           
 *               | |___| (_) | | | \__ \ (_) | |  __/           
 *                \_____\___/|_| |_|___/\___/|_|\___|           
 *                                                              
 *                                                              
 */

// AtomScript Console ANCHOR: CONSOLE
var Console = function(inCall){

	var view = document.createElement("div");
	var displayView = document.createElement("div");
	var inputView = document.createElement("input");

	var inputs = [];
	var input = "";

	var doc = document;

	var style = document.createElement("style");
	style.appendChild(document.createTextNode(""));
	document.head.appendChild(style);
	var styleSheet = style.sheet;

	this.showing = false;
	this.width = 100;
	this.height = 28;

	var inputCallback = null;

	function consoleIn(str){

		if(typeof inCall === "function"){
			if(inputCallback==null)
				inCall(str);
			else{
				inputCallback(str);
				inputCallback = null;
			}
			
		}

	}

	function out(str){

		var line = document.createElement("p");
		line.setAttribute("class", "atomscript_console_display_line");
		line.style.margin = 0;
		line.style.padding = 0;
		line.innerText = str;
		line.textContent = str;
		displayView.appendChild(line);
		displayView.scrollTop = displayView.scrollHeight;

	}

	this.in = function(callback){
		inputCallback = callback;
	}

	this.out = function(str){

		out(str);

	}

	this.clr = function(){

		displayView.innerHTML = "";

	}

	this.start = function(){

		// Main View
		view.id = "atomscript_console";
		view.style.width = this.width + "vw";
		view.style.height = this.height + "vh";
		view.style.visibility = "hidden";
		view.style.position = "fixed";
		view.style.bottom = "0vh";
		view.style.left = "0vw";
		view.style.margin = "0";
		view.style.zIndex = findHighestZIndex() + 1;
		view.style.padding = "5px";
		view.addEventListener("click", function(e){

			inputView.focus();

		});

		doc.body.appendChild(view);
		var viewStyles = [

			"font-family: consolas, sans-serif",
			"background-color: #000",
			"color: #c0c0c0",

		];

		// Input View
		inputView.id = "atomscript_console_input";
		inputView.type = "text";
		inputView.style.fontFamily = "inherit";
		inputView.style.fontSize = "inherit"
		inputView.style.color = "inherit";
		inputView.style.backgroundColor = "inherit";
		inputView.style.zIndex = "inherit";
		inputView.style.width = "100%";
		inputView.style.paddingLeft = "5px";
		inputView.style.paddingRight = "5px";
		inputView.style.position = "fixed";
		inputView.style.bottom = "0vh";
		inputView.style.left = "0";
		inputView.style.border = "0";
		inputView.style.overflowX = "auto";
		var hoveredCommand = 0;
		inputView.addEventListener("keydown", function(e){

			switch(e.keyCode){

				case 13:
					out(inputView.value);
					consoleIn(inputView.value);
					inputs.push(inputView.value);
					input = inputView.value;
					hoveredCommand = inputs.length;
					inputView.value = "";
					break;

				case 38:
					if(hoveredCommand > 0){
						hoveredCommand--;
						inputView.value = inputs[hoveredCommand];
					}
					break;

				case 40:
					if(hoveredCommand < inputs.length){
						if(hoveredCommand < inputs.length-1){
							hoveredCommand++;
							inputView.value = inputs[hoveredCommand];
						}else{
							hoveredCommand = inputs.length;
							inputView.value = "";
						}
					}
					break;

				default:
					return;
					break;

			}

			e.preventDefault();

		});

		view.appendChild(inputView);

		// Display View
		displayView.id = "atomscript_console_display";
		displayView.style.width = "100%";
		displayView.style.height = view.offsetHeight - inputView.offsetHeight - 5 + "px";
		displayView.style.position = "relative";
		displayView.style.top = "0";
		displayView.style.left = "0";
		displayView.style.bottom = inputView.style.top;
		displayView.style.overflow = "auto";
		displayView.style.zIndex = "inherit";
		displayView.addEventListener("click", function(e){

			inputView.focus();

		});

		view.appendChild(displayView);
		addCSSRule(styleSheet, "#atomscript_console", viewStyles.join(";"), 0);
		addCSSRule(styleSheet, "#atomscript_console_input:focus", "outline: 0", 0);

	}

	this.show = function(){

		this.showing = true;
		view.style.visibility = "visible";
		view.style.zIndex = findHighestZIndex() + 1;
		inputView.focus();

	}

	this.hide = function(){

		this.showing = false;
		view.style.visibility = "hidden";
		view.style.zIndex = findHighestZIndex() + 1;

	}

	this.toggle = function(){

		if(!this.showing){
			this.show();
		}else{
			this.hide();
		}

	}

	this.getDOM = function(){

		return view;

	}

	window.addEventListener("resize", function(){

		displayView.style.height = view.offsetHeight - inputView.offsetHeight - 5 + "px";

	});

	function findHighestZIndex(){
		var elems = document.body.children;
		var highest = 0;
		for(var i = 0; i < elems.length; i++){
			var zindex=document.defaultView.getComputedStyle(elems[i],null).getPropertyValue("z-index");
			if ((zindex > highest) && (zindex != 'auto')){
		  		highest = zindex;
			}
		}
		return highest;
	}

	function addCSSRule(sheet, selector, rules, index) {
		if("insertRule" in sheet) {
			sheet.insertRule(selector + "{" + rules + "}", index);
		}
		else if("addRule" in sheet) {
			sheet.addRule(selector, rules, index);
		}
	}

}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    url = url.toLowerCase();
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}