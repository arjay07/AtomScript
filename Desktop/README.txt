            _                          _____                 _           _   
    /\     | |                        / ____|               (_)         | |  
   /  \    | |_    ___    _ __ ___   | (___     ___   _ __   _   _ __   | |_ 
  / /\ \   | __|  / _ \  | '_ ` _ \   \___ \   / __| | '__| | | | '_ \  | __|
 / ____ \  | |_  | (_) | | | | | | |  ____) | | (__  | |    | | | |_) | | |_ 
/_/    \_\  \__|  \___/  |_| |_| |_| |_____/   \___| |_|    |_| | .__/   \__|
                                                                | |          
                                                                |_|          

©ZeroSeven Interactive 2016
AtomScript is an interpreted programming language.

INSTALLATION-
At the moment, I am still working on an installer. Follow the instructions below to install AtomScript onto your computer.

1. Make sure you have the latest version of Java installed.
2. Extract the AtomScript Setup Package.
3. Run the AtomScript Setup program.
4. Test if AtomScript works by running one of the AtomScript example programs.

RUNNING-
Running an AtomScript program is easy. Create an AtomScript file by creating a text file with the .atom extension. Write some code in the file and then double click on it to run the program. It's as simple as that!

BASIC SYNTAX-
* Comments:
	- Comments start with a # symbol. Afterwards, the whole line is a comment.
	- # This is a comment
	  # So is this

* Variables:
	- To create a variable, simply start by typing an @ symbol. You can then assign a value to the variable:
	- @variable = "This is a variable";
	  @number = 5;

* Functions:
	- Functions are created with a $ symbol. You can then enter the body code within curly brackets.
	- $foo(){
	      print("Hello world!");
	  }
	  @foo2 = $(){
	      print("Hello world!");
	  }

* Objects:
	- An instance of an object can be defined simply with two curly brackets.
	- @object = {};
	- You can create object contsructors with a function.
	- @Person = $(name, age){
	      this->name = name;
	      this->age = age;
	  }
	- You can then call a constructor by using the * symbol.
	- @Bob = *Person("Bob", 20); 

BASIC PROGRAMS-
You can find some basic programs in the examples folder of this package. Here is a simple hello world program:

	@helloWorld = "Hello world!";

	$main(){

	    print(helloWorld);

	}
