package net.njsharpe.docu.reflect;

public class MethodCallInfo {

    private final Class<?> source;
    private final String name;
    private final Class<?>[] parameters;

    private MethodCallInfo(Class<?> source, String name, Class<?>... parameters) {
        this.source = source;
        this.name = name;
        this.parameters = parameters;
    }

    public Class<?> getSource() {
        return this.source;
    }

    public String getName() {
        return this.name;
    }

    public Class<?>[] getParameters() {
        return this.parameters;
    }

    public static MethodCallInfo of(Class<?> source, String name, Class<?>... parameters) {
        return new MethodCallInfo(source, name, parameters);
    }

}
