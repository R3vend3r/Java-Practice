package hotelsystem.dependencies.factory;

import hotelsystem.dependencies.annotation.*;
import hotelsystem.dependencies.config.*;
import hotelsystem.dependencies.config.ConfigLoader;
import hotelsystem.dependencies.configurator.*;
import hotelsystem.dependencies.context.AppContext;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class BeanFactory {
    private final Configuration configuration;
    @Getter
    private final BeanConfigurator beanConfigurator;
    private final AppContext appContext;
    private final ConfigLoader configLoader;

    public BeanFactory(AppContext applicationContext) {
        this.configuration = new JavaConfiguration();
        this.beanConfigurator = new JavaBeanConfigurator(
                configuration.getPackageToScan(),
                configuration.getInterfaceToImplementation()
        );
        this.appContext = applicationContext;
        this.configLoader = new ConfigLoader();
    }

    @SneakyThrows
    public <T> T getBean(Class<T> clazz) {
        Class<? extends T> implementationClass = clazz.isInterface()
                ? beanConfigurator.getImplementationClass(clazz)
                : clazz;

        if (appContext.getBeanMap().containsKey(implementationClass)) {
            return (T) appContext.getBeanMap().get(implementationClass);
        }

        // Создаем экземпляр с обработкой приватных конструкторов
        T bean = createInstance(implementationClass);
        appContext.getBeanMap().put(implementationClass, bean);

        // Внедряем зависимости
        injectDependencies(bean);

        // Обрабатываем конфигурационные свойства
        processConfigProperties(bean);

        // Вызываем PostConstruct
        invokePostConstruct(bean);

        return bean;
    }

    @SneakyThrows
    private <T> T createInstance(Class<T> clazz) {
        try {
            // Пробуем получить конструктор по умолчанию
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true); // Разрешаем доступ к приватному конструктору
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No default constructor found for " + clazz.getName(), e);
        }
    }

    private <T> void injectDependencies(T bean) throws Exception {
        for (Field field : getInjectableFields(bean.getClass())) {
            field.setAccessible(true); // Разрешаем доступ к приватным полям

            Object dependency = resolveDependency(field);
            field.set(bean, dependency);
        }
    }

    private List<Field> getInjectableFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());
    }

    private Object resolveDependency(Field field) {
        Variant variant = field.getAnnotation(Variant.class);
        Class<?> dependencyType = field.getType();

        if (variant != null) {
            Class<?> implClass = beanConfigurator.getImplementationClass(dependencyType, variant.value());
            return appContext.getBean(implClass);
        }

        return dependencyType.isInterface()
                ? appContext.getBean(beanConfigurator.getImplementationClass(dependencyType))
                : appContext.getBean(dependencyType);
    }

    private <T> void processConfigProperties(T bean) throws Exception {
        for (Field field : getConfigPropertyFields(bean.getClass())) {
            field.setAccessible(true); // Разрешаем доступ к приватным полям

            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
            String propertyName = annotation.propertyName().isEmpty()
                    ? bean.getClass().getSimpleName() + "." + field.getName()
                    : annotation.propertyName();

            String value = configLoader.getProperty(propertyName);
            if (value != null) {
                Object convertedValue = convertValue(value, field.getType(), annotation.type());
                field.set(bean, convertedValue);
            }
        }
    }

    private List<Field> getConfigPropertyFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(ConfigProperty.class))
                .collect(Collectors.toList());
    }

    private Object convertValue(String value, Class<?> fieldType, Class<?> targetType) {
        Class<?> conversionType = targetType != String.class ? targetType : fieldType;

        if (conversionType == String.class) return value;
        if (conversionType == Integer.class || conversionType == int.class)
            return Integer.parseInt(value);
        if (conversionType == Boolean.class || conversionType == boolean.class)
            return Boolean.parseBoolean(value);
        if (conversionType == Double.class || conversionType == double.class)
            return Double.parseDouble(value);
        if (conversionType == Long.class || conversionType == long.class)
            return Long.parseLong(value);

        throw new IllegalArgumentException("Unsupported type: " + conversionType);
    }

    @SneakyThrows
    private void invokePostConstruct(Object bean) {
        Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(PostConstruct.class))
                .forEach(method -> {
                    method.setAccessible(true);
                    try {
                        method.invoke(bean);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}