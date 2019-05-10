# ConfigLib
A quick and easy way to access configuration files in json.

### Loading a config
```java
ExampleConfig config = (ExampleConfig) LoadableConfig.getByClass(ExampleConfig.class).load();
```

### Saving a config
```java
config.save();
```

Simple as that.