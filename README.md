# DI container

### Description
Minimalistic DI container based on the external JSON configuration

### Features

**Support of cyclic dependencies** \
No more need to use good code practices, now you can discover the power of cursed programming.

**Direct access to values** \
No need to interact with beans. Operate the container seamlessly.
You need a minumum of configuration to bootstrap the work.

**No more Provider to perform prototype injection** \
Use plain classes instead.
Container will automatically create a proxy class and invoke the lookup method when you need a new instance.
You should wrap all used classes with appropriate interfaces for the DI container to work.
It will create a proxy class implementing these interfaces and administrate them. You can't access obtained values' fields directly.
Specify proper getters and setters instead.

**All beans are lazy be default**

### Configuration example and params
Configuration consists of a single array of bean definitions.
It is optional to reference other beans in the config. You can use `javax` `@Inject` and `@Named` annotations instead.
The container will correctly recognize them and perform dependency injection.

By default `Prototype`-scoped beans will inherit the bigger scope when injected into some other bean.
To avoid that and control the behavior of the bean value you may specify some null lookup method within a prototype bean class and annotate it with `@Lookup`.
Each time this method is called the actual prototype instance will be replaced with a new one automatically.


**Bean params** \
`name` (*optional*): Name of the bean as it will appear in the context \
`interfaceClass`: Classpath of the interface, which this bean is implementing \
`implementationClass`: Classpath of the bean value class, which implements `interfaceClass` \
`scope`: Bean scope. Possible options are: `Singleton`, `Thread`, `Prototype` \
`fields`: Array of bean references and values, which needs to be written to the fields of the implementation class

**Field params** \
`name`: Name of the implementation class field exactly as in class definition \
Next params are mutually exclusive, so you should use only one of them
`value`: Values of basic Java types: `Integer`, `String`, `List` \
`refName`: Reference to some other bean from the container. Must be exactly as it appears in the configuration \
`refClass`: Reference to some other bean by its interface. Must be a correct classpath
```
[
  "name": "Context",
  "interfaceClass": "ru.nsu.di_container.Context",
  "implementationClass": "ru.nsu.di_container.ApplicationContext",
  "scope": "Singleton",
  "fields": [
    {
      "name": "packagePath",
      "value": "./"
    },
    {
      "name": "beanFactory",
      "refClass": "ru.nsu.di_container.bean.BeanFactory"
    },
    {
      "name": "configuration",
      "refName": "jsonConfiguration"
    }
  ]
]
```
