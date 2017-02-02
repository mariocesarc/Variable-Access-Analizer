# Variable-Access-Analizer
a DiSL profiler for getting information about instance variables usage in Java Applications

## Output Info
- CLASS : name of the class where the instance variable was declared
- VARIABLE : instance variable name
- TYPE : type of the instance variable
- READS : number of total reads for the variable (in all instances)
- WRITES : number ot total writes for the variable (in all instances)
- INSTANCES-USE-VARIABLE : number of different instances that read or write this variable
- INSTANCES-TOTAL : total number of instances for the class where the instance variable was declared

This versión doesn't include inherited instance variables and doesn't count reads or writes made outside the class where the instance variable was declared

## How to run DiSL
You can download a source or binary release of DiSL here:
https://disl.ow2.org

In the bin folder you can find the disl.py script for running the instrumentation. The basic command is:
```
./disl.py -- my-inst-path/my-inst.jar -jar my-application-path/my-app.jar
```

### Recommended options:

You can add an exclussion list:
```
-s_exclusionlist my-exclusion-list
```

Some errors may raise with the bytecode modifications that the instrumentation does.
Run your app with this option to disable bytecode verification:
```
-noverify
```
### Running the example app:
```
./disl.py -s_exclusionlist exclusion-list -- build/inst.jar -jar -noverify build/app.jar
```
