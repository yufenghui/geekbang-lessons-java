package cn.yufenghui.lession.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 类型转换-迭代器模式
 *
 * @author : yufenghui
 * @date : 2021/3/20 19:08
 * @Description:
 */
public class Converters implements Iterable<Converter> {

    public static final int DEFAULT_PRIORITY = 100;

    private final Map<Class<?>, PriorityQueue<PrioritizedConverter>> typedConverters = new HashMap<>();

    private ClassLoader classLoader;

    private boolean addDiscoveredConverters = false;

    public Converters(ClassLoader classLoader) {
        this.classLoader = classLoader;

        addConverter(new StringConverter());
        addConverter(new IntegerConverter());
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void addDiscoveredConverters() {
        if(addDiscoveredConverters) {
            return;
        }

        addConverters(ServiceLoader.load(Converter.class, classLoader));
        addDiscoveredConverters = true;
    }

    public void addConverters(Iterable<Converter> converters) {
        converters.forEach(this::addConverter);
    }

    public void addConverter(Converter converter) {
        addConverter(converter, DEFAULT_PRIORITY);
    }

    public void addConverter(Converter converter, int priority) {
        Class<?> converterType = resolveConverterType(converter);
        addConverter(converter, priority, converterType);
    }

    public Class<?> resolveConverterType(Converter converter) {
        Class<?> converterType = null;

        for(Type superInterface : converter.getClass().getGenericInterfaces()) {
            converterType = resolveConverterType(superInterface);
            if(converterType != null) {
                break;
            }
        }

        return converterType;
    }

    private Class<?> resolveConverterType(Type type) {
        Class<?> converterType = null;

        if(type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            if(pType.getRawType() instanceof Class) {
                Class<?> rawType = (Class<?>) pType.getRawType();
                if(Converter.class.isAssignableFrom(rawType)) {
                    Type[] arguments = pType.getActualTypeArguments();
                    if(arguments.length == 1 && arguments[0] instanceof Class) {
                        converterType = (Class<?>) arguments[0];
                    }
                }
            }
        }

        return converterType;
    }

    public void addConverter(Converter converter, int priority, Class<?> converterType) {
        PriorityQueue priorityQueue =
                typedConverters.computeIfAbsent(converterType, t -> new PriorityQueue<>());

        priorityQueue.offer(new PrioritizedConverter(converter, priority));
    }

    public void addConverters(Converter... converters) {
        addConverters(Arrays.asList(converters));
    }

    public List<Converter> getConverters(Class<?> converterType) {
        PriorityQueue<PrioritizedConverter> prioritizedConverters = typedConverters.get(converterType);
        if(prioritizedConverters == null || prioritizedConverters.isEmpty()) {
            return Collections.emptyList();
        }

        List<Converter> converters = new LinkedList<>();
        for (PrioritizedConverter prioritizedConverter : prioritizedConverters) {
            converters.add(prioritizedConverter.getConverter());
        }

        return converters;
    }

    protected Class<?> resolveConvertedType(Converter<?> converter) {
        assertConverter(converter);
        Class<?> convertedType = null;
        Class<?> converterClass = converter.getClass();
        while (converterClass != null) {
            convertedType = resolveConverterType(converterClass);
            if (convertedType != null) {
                break;
            }

            Type superType = converterClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType) {
                convertedType = resolveConverterType(superType);
            }

            if (convertedType != null) {
                break;
            }
            // recursively
            converterClass = converterClass.getSuperclass();

        }

        return convertedType;
    }

    private void assertConverter(Converter<?> converter) {
        Class<?> converterClass = converter.getClass();
        if (converterClass.isInterface()) {
            throw new IllegalArgumentException("The implementation class of Converter must not be an interface!");
        }
        if (Modifier.isAbstract(converterClass.getModifiers())) {
            throw new IllegalArgumentException("The implementation class of Converter must not be abstract!");
        }
    }


    @Override
    public Iterator<Converter> iterator() {
        List<Converter> allConverters = new LinkedList<>();
        for(PriorityQueue<PrioritizedConverter> converters : typedConverters.values()) {
            for (Converter converter : converters) {
                allConverters.add(converter);
            }
        }
        return allConverters.iterator();
    }
    
}
