var _$_ee21=["load","href","location","src","isAtomScriptFile","run","#","startsWith","length","substring","getElementById","type","getAttribute","atomscript","includes","toLowerCase","innerText","text/plain","?","atomscript.src","start","addEventListener","ATOM",".atom","","getFullYear","Lanthanum","1.0","generateKeywordReserves","init","AtomScript v"," (",")","as","add","AS","enabled","console","data","%c","color: #0355ff; font-family: arial; font-size: 20px;","log","%c\xA9ZeroSeven Interactive ","color: #ff0330; font-family: arial;","startConsole","out","\xA9ZeroSeven Interactive ","Welcome to AtomScript","cr","createObjectURL","URL","\"","\"  is not an AtomScript file.","error","evaluate","width","height","show","hide","clr","in","setTitle","title","getEvaluator","exit","getRunning","setRunning","string","endsWith","compile","readFile","\x0Aif(typeof(main) == \"function\")main();","SRC","showErrorDialog","parse","eval","getParser","setShowErrorDialog","put","@"," = ","VAR","FUNCTION","NEW","exec","index","replace","lastIndex","replaceBetween","var","function","new","%e@","var ","%e$","function ","%e*","new ","this.",".","*(","Math.pow(parseFloat(eval(",")), parseFloat(eval(",")))","*","$","The module \"","\" does not exist...","io","IO","The library \"","getParsedCode","prototype","charAt","\\","XMLHttpRequest","HEAD","open","send","status","AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz123456789_$","random","floor","var_","function_","new_","text","getRequest","GET","overrideMimeType","readyState","responseText","statusText","responseURL","print"," ","div","createElement","input","style","createTextNode","appendChild","head","sheet","showing","p","class","atomscript_console_display_line","setAttribute","margin","padding","textContent","scrollTop","scrollHeight","innerHTML","id","atomscript_console","vw","vh","visibility","hidden","position","fixed","bottom","0vh","left","0vw","0","zIndex","5px","click","focus","body","font-family: consolas, sans-serif","background-color: #000","color: #c0c0c0","atomscript_console_input","fontFamily","inherit","fontSize","color","backgroundColor","100%","paddingLeft","paddingRight","border","overflowX","auto","keydown","value","push","keyCode","preventDefault","atomscript_console_display","offsetHeight","px","relative","top","overflow","#atomscript_console",";","join","#atomscript_console_input:focus","outline: 0","visible","toggle","getDOM","resize","children","z-index","getPropertyValue","getComputedStyle","defaultView","insertRule","{","}","addRule","\\$&","[?&]","(=([^&#]*)|&|#|$)"];var atomScript;window[_$_ee21[21]](_$_ee21[0],function(){atomScript=  new AtomScript();var g=window[_$_ee21[2]][_$_ee21[1]];if(AtomScript[_$_ee21[3]]!= null){if(AtomScript[_$_ee21[4]](AtomScript[_$_ee21[3]])){atomScript[_$_ee21[5]](AtomScript[_$_ee21[3]])}else {if(AtomScript[_$_ee21[3]][_$_ee21[7]](_$_ee21[6])){var c=AtomScript[_$_ee21[3]][_$_ee21[9]](1,AtomScript[_$_ee21[3]][_$_ee21[8]]);var d=document[_$_ee21[10]](c);if(d[_$_ee21[12]](_$_ee21[11])&& d[_$_ee21[12]](_$_ee21[11])[_$_ee21[15]]()[_$_ee21[14]](_$_ee21[13])){var b=d[_$_ee21[16]];var a= new Blob([b],{type:_$_ee21[17]});atomScript[_$_ee21[5]](a)}}}}else {if(g[_$_ee21[14]](_$_ee21[18])){var f=getParameterByName(_$_ee21[19],g);atomScript[_$_ee21[5]](f)}else {atomScript[_$_ee21[20]]()}}});var AtomScript=function(){AtomScript[_$_ee21[22]]= _$_ee21[23];var l=_$_ee21[24];var m= new Date()[_$_ee21[25]]();var h=_$_ee21[26];var i=_$_ee21[27];var j=null;var k=false;ASParser[_$_ee21[28]]();this[_$_ee21[29]]= function(j){l= _$_ee21[30]+ i+ _$_ee21[31]+ h+ _$_ee21[32];j[_$_ee21[34]](_$_ee21[33],this);j[_$_ee21[34]](_$_ee21[35],this)};this[_$_ee21[20]]= function(){j=  new ASEvaluator();this[_$_ee21[29]](j);k= true;AtomScript[_$_ee21[38]][_$_ee21[37]][_$_ee21[36]]= true;console[_$_ee21[41]](_$_ee21[39]+ l,_$_ee21[40]);console[_$_ee21[41]](_$_ee21[42]+ m,_$_ee21[43]);this[_$_ee21[44]]();this[_$_ee21[37]][_$_ee21[45]](l);this[_$_ee21[37]][_$_ee21[45]](_$_ee21[46]+ m);this[_$_ee21[37]][_$_ee21[45]](_$_ee21[47])};this[_$_ee21[5]]= function(d){j=  new ASEvaluator();this[_$_ee21[29]](j);var n= new ASCompiler(j);this[_$_ee21[44]]();if(AtomScript[_$_ee21[4]](d)){n[_$_ee21[48]](d)}else {if(d instanceof  Blob){var g=window[_$_ee21[50]][_$_ee21[49]](d);n[_$_ee21[48]](g)}else {console[_$_ee21[53]](_$_ee21[51]+ d+ _$_ee21[52])}}};this[_$_ee21[44]]= function(){this[_$_ee21[37]]=  new Console(function(o){if(k){j[_$_ee21[54]](o)}});this[_$_ee21[37]][_$_ee21[55]]= AtomScript[_$_ee21[38]][_$_ee21[37]][_$_ee21[55]];this[_$_ee21[37]][_$_ee21[56]]= AtomScript[_$_ee21[38]][_$_ee21[37]][_$_ee21[56]]- 1;this[_$_ee21[37]][_$_ee21[20]]();this[_$_ee21[37]][_$_ee21[57]]();if(!AtomScript[_$_ee21[38]][_$_ee21[37]][_$_ee21[57]]){this[_$_ee21[37]][_$_ee21[58]]()};AtomScript[_$_ee21[37]]= this[_$_ee21[37]];window[_$_ee21[37]][_$_ee21[45]]= this[_$_ee21[37]][_$_ee21[45]];window[_$_ee21[37]][_$_ee21[59]]= this[_$_ee21[37]][_$_ee21[59]];window[_$_ee21[37]][_$_ee21[60]]= this[_$_ee21[37]][_$_ee21[60]]};this[_$_ee21[61]]= function(p){document[_$_ee21[62]]= p};this[_$_ee21[63]]= function(){return j};this[_$_ee21[64]]= function(){k= false};this[_$_ee21[65]]= function(){return k};this[_$_ee21[66]]= function(q){k= q};AtomScript[_$_ee21[4]]= function(s){if(s!= null&&  typeof s=== _$_ee21[67]){if(s[_$_ee21[68]](AtomScript[_$_ee21[22]])){return true}};return false}};AtomScript[_$_ee21[38]]= {console:{show:false,width:100,height:28}};AtomScript[_$_ee21[3]]= null;var ASCompiler=function(j){var t=_$_ee21[24];var u=_$_ee21[24];this[_$_ee21[69]]= function(v){t=  new ASIO()[_$_ee21[70]](v)};this[_$_ee21[5]]= function(){j[_$_ee21[54]](t+ _$_ee21[71])};this[_$_ee21[48]]= function(v){this[_$_ee21[69]](v);this[_$_ee21[5]]()}};var ASEvaluator=function(x){this[_$_ee21[72]]= null;this[_$_ee21[73]]= false;var w=null;if(x!= null){this[_$_ee21[72]]= x};this[_$_ee21[54]]= function(y){var z;try{w=  new ASParser(y);z= window[_$_ee21[75]](w[_$_ee21[74]]())}catch(e){console[_$_ee21[53]](e);atomScript[_$_ee21[37]][_$_ee21[45]](e)};return z};this[_$_ee21[76]]= function(){return w};this[_$_ee21[77]]= function(A){this[_$_ee21[73]]= A};this[_$_ee21[78]]= function(B,C){this[_$_ee21[54]](_$_ee21[79]+ B+ _$_ee21[80]+ C)};this[_$_ee21[75]]= function(C){this[_$_ee21[54]](C)};this[_$_ee21[34]]= function(B,C){window[B]= C}};var ASParser=function(T){var y=T;ASParser[_$_ee21[81]];ASParser[_$_ee21[82]];ASParser[_$_ee21[83]];var j=atomScript[_$_ee21[63]]();this[_$_ee21[74]]= function(){S();O();P();D();L();F();K();H();G();J();I();M();R();S();E();return y};function S(){var Z=/\#(.+)/g;var Y;if(Y= Z[_$_ee21[84]](y)){var X=Y[0];if(!Q(y,Y[_$_ee21[85]])){y= y[_$_ee21[86]](X,_$_ee21[24]);S()}}}function D(){var Z=/\b(var|function|new)\b/g;var Y;if(Y= Z[_$_ee21[84]](y)){var X=Y[0];if(!Q(y,Y[_$_ee21[85]])){switch(X){case _$_ee21[89]:y= y[_$_ee21[88]](Y[_$_ee21[85]],Z[_$_ee21[87]],ASParser[_$_ee21[81]]);D();break;case _$_ee21[90]:y= y[_$_ee21[88]](Y[_$_ee21[85]],Z[_$_ee21[87]],ASParser[_$_ee21[82]]);D();break;case _$_ee21[91]:y= y[_$_ee21[88]](Y[_$_ee21[85]],Z[_$_ee21[87]],ASParser[_$_ee21[83]]);D();break}}}}function L(){var Z=/\B@\w+/g;var Y;if(Y= Z[_$_ee21[84]](y)){if(Q(y,Y[_$_ee21[85]])){y= y[_$_ee21[88]](Y[_$_ee21[85]],Y[_$_ee21[85]]+ 1,_$_ee21[92]);L()}else {y= y[_$_ee21[88]](Y[_$_ee21[85]],Y[_$_ee21[85]]+ 1,_$_ee21[93]);L()}}}function F(){var Z=/\B\$\w+|\B\$\(/g;var Y;if(Y= Z[_$_ee21[84]](y)){if(Q(y,Y[_$_ee21[85]])){y= y[_$_ee21[88]](Y[_$_ee21[85]],Y[_$_ee21[85]]+ 1,_$_ee21[94]);F()}else {y= y[_$_ee21[88]](Y[_$_ee21[85]],Y[_$_ee21[85]]+ 1,_$_ee21[95]);F()}}}function K(){var Z=/\B\*[^0-9;\s ]+/g;var Y;if(Y= Z[_$_ee21[84]](y)){if(Q(y,Y[_$_ee21[85]])){y= y[_$_ee21[88]](Y[_$_ee21[85]],Y[_$_ee21[85]]+ 1,_$_ee21[96]);K()}else {y= y[_$_ee21[88]](Y[_$_ee21[85]],Y[_$_ee21[85]]+ 1,_$_ee21[97]);K()}}}function H(){y= y[_$_ee21[86]](/this\s*->\s*/g,_$_ee21[98])}function G(){y= y[_$_ee21[86]](/\:\:/g,_$_ee21[99])}function I(){var Z=/\b\<([A-Za-z_][A-Za-z0-9_$]*?)\>/g;var Y;while(Y= Z[_$_ee21[84]](y)){var X=Y[0];y= y[_$_ee21[86]](X,_$_ee21[99]+ Y[1])}}function J(){var Z=/\b\<([A-Za-z_][A-Za-z0-9_$]*?)\>(\w+)/g;var Y;while(Y= Z[_$_ee21[84]](y)){var X=Y[0];var bb=Y[1];var ba=Y[2];y= y[_$_ee21[86]](X,_$_ee21[99]+ bb+ _$_ee21[99]+ ba)}}function R(){var Z=/(\b\d+|\))\((.+?)\)/g;var Y;while(Y= Z[_$_ee21[84]](y)){var X=Y[0];var bm=Y[1];var bd=Y[2];y= y[_$_ee21[86]](X,bm+ _$_ee21[100]+ bd+ _$_ee21[32]);R()}}function M(){var Z=/(\b\d+|\(.+?\)|\w+)\^\^(\b\d+|\(.+?\)|\w+)/g;var Y;while(Y= Z[_$_ee21[84]](y)){var X=Y[0];var bd=Y[1];var bc=Y[2];y= y[_$_ee21[86]](X,_$_ee21[101]+ bd+ _$_ee21[102]+ bc+ _$_ee21[103]);M()}}function E(){y= y[_$_ee21[86]](/%e\@/g,_$_ee21[79])[_$_ee21[86]](/%e\$/g,_$_ee21[105])[_$_ee21[86]](/%e\*/g,_$_ee21[104])[_$_ee21[86]](/%e#/g,_$_ee21[6])[_$_ee21[86]](/%c/g,_$_ee21[6])}function O(){var Z=/\b(include)\b\s"((.+?).atom)"/g;var Y;while(Y= Z[_$_ee21[84]](y)){var X=Y[0];var v=Y[2];try{var bf= new ASIO()[_$_ee21[70]](v);y= y[_$_ee21[86]](X,_$_ee21[24]);j[_$_ee21[54]](bf);O()}catch(e){console[_$_ee21[53]](e);console[_$_ee21[45]](_$_ee21[106]+ v+ _$_ee21[107])}}}function P(){var Z=/\b(include)\b\s\<(.+?)\>/g;var Y;while(Y= Z[_$_ee21[84]](y)){var X=Y[0];var bh=Y[2];var bg= new ASIO();switch(bh){case _$_ee21[108]:;case _$_ee21[109]:y= y[_$_ee21[86]](X,_$_ee21[24]);j[_$_ee21[34]](_$_ee21[108],bg);j[_$_ee21[34]](_$_ee21[109],bg);P();break;default:y= y[_$_ee21[86]](X,_$_ee21[24]);console[_$_ee21[45]](_$_ee21[110]+ bh+ _$_ee21[107]);break}}}this[_$_ee21[111]]= function(){return y};String[_$_ee21[112]][_$_ee21[88]]= function(W,U,V){return this[_$_ee21[9]](0,W)+ V+ this[_$_ee21[9]](U)};function Q(o,W){var bl=0;for(var bj=0;bj< W;bj++){var bi=_$_ee21[24]+ o[_$_ee21[113]](bj);var bk=_$_ee21[24];if(bj> 0){bk= _$_ee21[24]+ o[_$_ee21[113]](bj- 1)};if(bi=== _$_ee21[51]&& bk!== _$_ee21[114]){bl= bl+ 1}};if((bl% 2)== 0){return false}else {return true}}function N(g){var be;if(window[_$_ee21[115]]){be=  new XMLHttpRequest()}else {be=  new ActiveXObject()};be[_$_ee21[117]](_$_ee21[116],g,false);be[_$_ee21[118]]();return (be[_$_ee21[119]]!= 404)}};ASParser[_$_ee21[28]]= function(){var bn=_$_ee21[120];var bo=_$_ee21[24];for(var bj=0;bj< 7;bj++){bo+= bn[_$_ee21[113]](Math[_$_ee21[122]](Math[_$_ee21[121]]()* bn[_$_ee21[8]]))};ASParser[_$_ee21[81]]= _$_ee21[123]+ bo;ASParser[_$_ee21[82]]= _$_ee21[124]+ bo;ASParser[_$_ee21[83]]= _$_ee21[125]+ bo};var ASIO=function(){this[_$_ee21[70]]= function(g){return this[_$_ee21[127]](g,_$_ee21[17])[_$_ee21[126]]};this[_$_ee21[127]]= function(g,bp){var bq;if(window[_$_ee21[115]]){bq=  new XMLHttpRequest()}else {bq=  new ActiveXObject()};bq[_$_ee21[117]](_$_ee21[128],g,false);if(bp!= null|| bp!= undefined){bq[_$_ee21[129]](bp)};var br=null;bq[_$_ee21[21]](_$_ee21[0],function(bs){if(bq[_$_ee21[130]]== 4){if(bq[_$_ee21[119]]== 200){br= bq[_$_ee21[131]]}}});bq[_$_ee21[21]](_$_ee21[53],function(bs){console[_$_ee21[53]](bq[_$_ee21[132]]);console[_$_ee21[45]](bq[_$_ee21[132]])});bq[_$_ee21[118]](null);return {text:br,url:bq[_$_ee21[133]],request:bq}};this[_$_ee21[45]]= function(bt){console[_$_ee21[45]](bt);return bt};this[_$_ee21[134]]= function(){var o=_$_ee21[24];for(var bj=0;bj< arguments;bj++){o+= arguments+ _$_ee21[135]};console[_$_ee21[45]](o)};this[_$_ee21[59]]= function(){console[_$_ee21[59]]()}};var Console=function(bz){var bH=document[_$_ee21[137]](_$_ee21[136]);var bw=document[_$_ee21[137]](_$_ee21[136]);var bD=document[_$_ee21[137]](_$_ee21[138]);var bC=[];var bA=_$_ee21[24];var bx=document;var bF=document[_$_ee21[137]](_$_ee21[139]);bF[_$_ee21[141]](document[_$_ee21[140]](_$_ee21[24]));document[_$_ee21[142]][_$_ee21[141]](bF);var bG=bF[_$_ee21[143]];this[_$_ee21[144]]= false;this[_$_ee21[55]]= 100;this[_$_ee21[56]]= 28;var bB=null;function bv(o){if( typeof bz=== _$_ee21[90]){if(bB== null){bz(o)}else {bB(o);bB= null}}}function bE(o){var bS=document[_$_ee21[137]](_$_ee21[145]);bS[_$_ee21[148]](_$_ee21[146],_$_ee21[147]);bS[_$_ee21[139]][_$_ee21[149]]= 0;bS[_$_ee21[139]][_$_ee21[150]]= 0;bS[_$_ee21[16]]= o;bS[_$_ee21[151]]= o;bw[_$_ee21[141]](bS);bw[_$_ee21[152]]= bw[_$_ee21[153]]}this[_$_ee21[60]]= function(bI){bB= bI};this[_$_ee21[45]]= function(o){bE(o)};this[_$_ee21[59]]= function(){bw[_$_ee21[154]]= _$_ee21[24]};this[_$_ee21[20]]= function(){bH[_$_ee21[155]]= _$_ee21[156];bH[_$_ee21[139]][_$_ee21[55]]= this[_$_ee21[55]]+ _$_ee21[157];bH[_$_ee21[139]][_$_ee21[56]]= this[_$_ee21[56]]+ _$_ee21[158];bH[_$_ee21[139]][_$_ee21[159]]= _$_ee21[160];bH[_$_ee21[139]][_$_ee21[161]]= _$_ee21[162];bH[_$_ee21[139]][_$_ee21[163]]= _$_ee21[164];bH[_$_ee21[139]][_$_ee21[165]]= _$_ee21[166];bH[_$_ee21[139]][_$_ee21[149]]= _$_ee21[167];bH[_$_ee21[139]][_$_ee21[168]]= by()+ 1;bH[_$_ee21[139]][_$_ee21[150]]= _$_ee21[169];bH[_$_ee21[21]](_$_ee21[170],function(bs){bD[_$_ee21[171]]()});bx[_$_ee21[172]][_$_ee21[141]](bH);var bK=[_$_ee21[173],_$_ee21[174],_$_ee21[175]];bD[_$_ee21[155]]= _$_ee21[176];bD[_$_ee21[11]]= _$_ee21[126];bD[_$_ee21[139]][_$_ee21[177]]= _$_ee21[178];bD[_$_ee21[139]][_$_ee21[179]]= _$_ee21[178];bD[_$_ee21[139]][_$_ee21[180]]= _$_ee21[178];bD[_$_ee21[139]][_$_ee21[181]]= _$_ee21[178];bD[_$_ee21[139]][_$_ee21[168]]= _$_ee21[178];bD[_$_ee21[139]][_$_ee21[55]]= _$_ee21[182];bD[_$_ee21[139]][_$_ee21[183]]= _$_ee21[169];bD[_$_ee21[139]][_$_ee21[184]]= _$_ee21[169];bD[_$_ee21[139]][_$_ee21[161]]= _$_ee21[162];bD[_$_ee21[139]][_$_ee21[163]]= _$_ee21[164];bD[_$_ee21[139]][_$_ee21[165]]= _$_ee21[167];bD[_$_ee21[139]][_$_ee21[185]]= _$_ee21[167];bD[_$_ee21[139]][_$_ee21[186]]= _$_ee21[187];var bJ=0;bD[_$_ee21[21]](_$_ee21[188],function(bs){switch(bs[_$_ee21[191]]){case 13:bE(bD[_$_ee21[189]]);bv(bD[_$_ee21[189]]);bC[_$_ee21[190]](bD[_$_ee21[189]]);bA= bD[_$_ee21[189]];bJ= bC[_$_ee21[8]];bD[_$_ee21[189]]= _$_ee21[24];break;case 38:if(bJ> 0){bJ--;bD[_$_ee21[189]]= bC[bJ]};break;case 40:if(bJ< bC[_$_ee21[8]]){if(bJ< bC[_$_ee21[8]]- 1){bJ++;bD[_$_ee21[189]]= bC[bJ]}else {bJ= bC[_$_ee21[8]];bD[_$_ee21[189]]= _$_ee21[24]}};break;default:return;break};bs[_$_ee21[192]]()});bH[_$_ee21[141]](bD);bw[_$_ee21[155]]= _$_ee21[193];bw[_$_ee21[139]][_$_ee21[55]]= _$_ee21[182];bw[_$_ee21[139]][_$_ee21[56]]= bH[_$_ee21[194]]- bD[_$_ee21[194]]- 5+ _$_ee21[195];bw[_$_ee21[139]][_$_ee21[161]]= _$_ee21[196];bw[_$_ee21[139]][_$_ee21[197]]= _$_ee21[167];bw[_$_ee21[139]][_$_ee21[165]]= _$_ee21[167];bw[_$_ee21[139]][_$_ee21[163]]= bD[_$_ee21[139]][_$_ee21[197]];bw[_$_ee21[139]][_$_ee21[198]]= _$_ee21[187];bw[_$_ee21[139]][_$_ee21[168]]= _$_ee21[178];bw[_$_ee21[21]](_$_ee21[170],function(bs){bD[_$_ee21[171]]()});bH[_$_ee21[141]](bw);bu(bG,_$_ee21[199],bK[_$_ee21[201]](_$_ee21[200]),0);bu(bG,_$_ee21[202],_$_ee21[203],0)};this[_$_ee21[57]]= function(){this[_$_ee21[144]]= true;bH[_$_ee21[139]][_$_ee21[159]]= _$_ee21[204];bH[_$_ee21[139]][_$_ee21[168]]= by()+ 1;bD[_$_ee21[171]]()};this[_$_ee21[58]]= function(){this[_$_ee21[144]]= false;bH[_$_ee21[139]][_$_ee21[159]]= _$_ee21[160];bH[_$_ee21[139]][_$_ee21[168]]= by()+ 1};this[_$_ee21[205]]= function(){if(!this[_$_ee21[144]]){this[_$_ee21[57]]()}else {this[_$_ee21[58]]()}};this[_$_ee21[206]]= function(){return bH};window[_$_ee21[21]](_$_ee21[207],function(){bw[_$_ee21[139]][_$_ee21[56]]= bH[_$_ee21[194]]- bD[_$_ee21[194]]- 5+ _$_ee21[195]});function by(){var bP=document[_$_ee21[172]][_$_ee21[208]];var bQ=0;for(var bj=0;bj< bP[_$_ee21[8]];bj++){var bR=document[_$_ee21[212]][_$_ee21[211]](bP[bj],null)[_$_ee21[210]](_$_ee21[209]);if((bR> bQ)&& (bR!= _$_ee21[187])){bQ= bR}};return bQ}function bu(bO,bN,bM,bL){if(_$_ee21[213] in  bO){bO[_$_ee21[213]](bN+ _$_ee21[214]+ bM+ _$_ee21[215],bL)}else {if(_$_ee21[216] in  bO){bO[_$_ee21[216]](bN,bM,bL)}}}};function getParameterByName(s,g){if(!g){g= window[_$_ee21[2]][_$_ee21[1]]};g= g[_$_ee21[15]]();s= s[_$_ee21[86]](/[\[\]]/g,_$_ee21[217]);var bT= new RegExp(_$_ee21[218]+ s+ _$_ee21[219]),bU=bT[_$_ee21[84]](g);if(!bU){return null};if(!bU[2]){return _$_ee21[24]};return decodeURIComponent(bU[2][_$_ee21[86]](/\+/g,_$_ee21[135]))}