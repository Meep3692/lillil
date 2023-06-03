# lillil

A Lisp made to be used as a scripting and configuration language for Java applications.

## Usage

To use lillil, create a new instance of the `Lillil` class, optionally (though heavily recommended), call `useCoreModule` to add core functionality, then simply call eval with some lillil code to get the result.
```java
Lillil lillil = new Lillil();
lillil.useCoreModule();
double result = (double)lillil.eval("(+ 1 2)");
System.out.println("1 + 2 = " + result);
```
[Javadoc - ca.awoo.lillil.Lillil](https://tomcat.awoo.ca/lillil/site/apidocs/ca/awoo/lillil/Lillil.html)

## Modules
[Javadoc - ca.awoo.lillil.module](https://tomcat.awoo.ca/lillil/site/apidocs/ca/awoo/lillil/module/package-summary.html)

### Native Modules

Modules allow you to extend the functionality of lillil. To create a module from Java code, extend NativeModuleLoader and add your functions.

```java
class MyNativeModuleLoader extends NativeModuleLoader {
    public MyNativeModuleLoader(Lillil lillil){
        super("my-module");
        this.module.put("my-function", new Function() {
            @Override
            public Object apply(Object... args) {
                System.out.println("Hello " + args[0].toString());
            }
        });
    }
}
```
[Javadoc - ca.awoo.lillil.module.NativeModuleLoader](https://tomcat.awoo.ca/lillil/site/apidocs/ca/awoo/lillil/module/NativeModuleLoader.html)

Then, when you create your lillil instance, add this module loader with `addModuleLoader`.

```java
Lillil lillil = new Lillil();
lillil.useCoreModule();
lillil.addModuleLoader(new MyNativeModuleLoader(lillil));
```

You can then access your module by calling `import` or `use-import` from your lillil code.

```lisp
(use-import "my-module")
(my-function "world")
```

Alternatively, you can call `useNativeModule` to add your module bindings to the base bindings of the lillil instance and call your functions from lillil code without needing to import it.

```java
Lillil lillil = new Lillil();
lillil.useCoreModule();
lillil.useNativeModule(new MyNativeModuleLoader(lillil));
```

```lisp
(my-function "world")
```

### lillil Modules

To load modules written in lillil from a folder, call `addModuleFolder`, which will create a new `DirectoryModuleLoader` and add it to the lillil instance.

```java
Lillil lillil = new Lillil();
lillil.useCoreModule();
lillil.addModuleFolder("/my/module/folder");
```

lillil code can now load modules from that folder using dots to seperate folders.

```lisp
(use-import "ca.awoo.something")
```

The above will attempt to load the module from a file at `/my/module/folder/ca/awoo/something.lil`.

These files must return a map of bindings at the end of the file.

```lisp
(defn add (a b)
          (+ a b))

{:add add}
```

### Convention

There are no hard rules on the format of module names, though the Java-style dot notation is prefered. Technically the following would be valid, if a bit cursed.
```java
class HTTPModuleLoader extends ModuleLoader {
    @Override
    public Map<String, Object> loadModule(String module) {
        URL url = new URL(module);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        ...
    }
}
```
```lisp
(use-import "http://example.com/module.lil")
```