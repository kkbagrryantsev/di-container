package ru.nsu.di_container.scanner;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import ru.nsu.di_container.annotations.Component;

import java.util.Set;
import java.util.stream.Collectors;

public class Scanner {
    private ClassInfoList componentClasses;

    public Set<Class<?>> scan(String... packageNames) {
        if (packageNames.length == 0) {
            throw new IllegalArgumentException("No package names provided");
        }
        ClassGraph graph = new ClassGraph().enableAllInfo().acceptPackages(packageNames);
        try (ScanResult scanResult = graph.scan()) {
            componentClasses = scanResult.getClassesWithAnnotation(Component.class);
            System.out.println(componentClasses);
        }

        return getComponentClasses();
    }

    public Set<Class<?>> getComponentClasses() {
        return componentClasses.stream().map(ClassInfo::getClass).collect(Collectors.toSet());
    }

    public Scanner(String... packageNames) {
        if (packageNames.length == 0) {
            throw new IllegalArgumentException("No package names provided");
        }
        scan(packageNames);
    }
}
