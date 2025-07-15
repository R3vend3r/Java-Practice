package hotelsystem.dependencies.factory;

import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.dependencies.annotation.Variant;
import hotelsystem.dependencies.config.Configuration;
import hotelsystem.dependencies.config.JavaConfiguration;
import hotelsystem.dependencies.configurator.BeanConfigurator;
import hotelsystem.dependencies.configurator.JavaBeanConfigurator;
import hotelsystem.dependencies.context.AppContext;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanFactory {

    private final Configuration configuration;
    @Getter
    private final BeanConfigurator beanConfigurator;
    private final AppContext appContext;

    public BeanFactory(AppContext applicationContext) {
        this.configuration = new JavaConfiguration();
        this.beanConfigurator = new JavaBeanConfigurator(configuration.getPackageToScan(), configuration.getInterfaceToImplementation());
        this.appContext = applicationContext;
    }

    @SneakyThrows
    public <T> T getBean(Class<T> clazz) {
        Class<? extends T> implementationClass;

        if (clazz.isInterface()) {
            implementationClass = beanConfigurator.getImplementationClass(clazz);
        } else {
            implementationClass = clazz;
        }

        if (appContext.getBeanMap().containsKey(implementationClass)) {
            return (T) appContext.getBeanMap().get(implementationClass);
        }

        T bean = implementationClass.getDeclaredConstructor().newInstance();

        appContext.getBeanMap().put(implementationClass, bean);

        for (Field field : Arrays.stream(implementationClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Inject.class)).toList()) {
            field.setAccessible(true);

            Variant variant = field.getAnnotation(Variant.class);
            Class<?> dependencyType = field.getType();
            Object dependency;

            if (variant != null) {
                Class<?> depImpl = beanConfigurator.getImplementationClass(dependencyType, variant.value());
                dependency = appContext.getBean(depImpl);
            } else {
                if (dependencyType.isInterface()) {
                    Class<?> depImpl = beanConfigurator.getImplementationClass(dependencyType);
                    dependency = appContext.getBean(depImpl);
                } else {
                    dependency = appContext.getBean(dependencyType);
                }
            }

            field.set(bean, dependency);
        }

        return bean;
    }



}
