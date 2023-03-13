package c2.search.netlas.target;

import c2.search.netlas.annotation.Detect;
import c2.search.netlas.annotation.Test;
import c2.search.netlas.annotation.Wire;
import c2.search.netlas.classscanner.ClassScanner;
import c2.search.netlas.scheme.Host;
import c2.search.netlas.scheme.Response;
import c2.search.netlas.scheme.Results;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checker {
  private static final Logger LOGGER = LoggerFactory.getLogger(Checker.class);
  private static final String TARGET_CLASS_NAME = "c2.search.netlas.target";
  private NetlasWrapper netlasWrapper;
  private Host host;
  private ClassScanner classScanner;

  public NetlasWrapper getNetlasWrapper() {
    return netlasWrapper;
  }

  public void setNetlasWrapper(NetlasWrapper netlasWrapper) {
    this.netlasWrapper = netlasWrapper;
  }

  public Host getHost() {
    return host;
  }

  public void setHost(Host host) {
    this.host = host;
  }

  public ClassScanner getClassScanner() {
    return classScanner;
  }

  public void setClassScanner(ClassScanner classScanner) {
    this.classScanner = classScanner;
  }

  public static Logger getLogger() {
    return LOGGER;
  }

  public static String getTargetClassName() {
    return TARGET_CLASS_NAME;
  }

  public Checker(NetlasWrapper netlasWrapper, Host host)
      throws ClassNotFoundException, IOException {
    this.netlasWrapper = netlasWrapper;
    this.host = host;
    this.classScanner = new ClassScanner(TARGET_CLASS_NAME);
  }

  public Results run()
      throws ClassNotFoundException, InstantiationException, IllegalAccessException,
          NoSuchMethodException, InvocationTargetException, IOException {
    List<Class<?>> detectedClasses = classScanner.getClassesWithAnnotation(Detect.class);
    if (detectedClasses.isEmpty()) {
      throw new IllegalStateException(
          "No class with @Detect annotation found in " + TARGET_CLASS_NAME);
    }
    Results results = new Results();
    for (Class<?> clazz : detectedClasses) {
      Object instant = instantiateClass(clazz);
      injectDependencies(instant);
      results.addResponse(getNameOfClass(clazz), invokeTestMethods(instant));
    }
    return results;
  }

  private Object instantiateClass(Class<?> clazz)
      throws IllegalAccessException, InstantiationException, NoSuchMethodException,
          InvocationTargetException {
    LOGGER.info("Instantiating {}", clazz.getName());
    return clazz.getDeclaredConstructor().newInstance();
  }

  private String getNameOfClass(Class<?> clazz) {
    Detect detect = clazz.getAnnotation(Detect.class);
    String nameOfDetect = detect.name();
    if (nameOfDetect == null || nameOfDetect.isEmpty()) {
      nameOfDetect = clazz.getName();
    }
    return nameOfDetect;
  }

  private List<Response> invokeTestMethods(Object instant) {
    LOGGER.info("Invoking test methods of {}", instant.getClass().getName());
    List<Response> responses = new ArrayList<>();
    for (Method method : instant.getClass().getMethods()) {
      if (method.isAnnotationPresent(Test.class)) {
        try {
          Response response = (Response) method.invoke(instant);
          responses.add(response);
        } catch (Exception e) {
          LOGGER.error(
              "Error invoking test method {} on {} - {}",
              method.getName(),
              instant.getClass().getName(),
              e.getMessage());
          responses.add(new Response(false));
        }
      }
    }
    return responses;
  }

  private void injectDependencies(Object instant) throws IllegalAccessException {
    LOGGER.info("Injecting dependencies to {}", instant.getClass().getName());
    for (Field field : instant.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(Wire.class)) {
        field.setAccessible(true);
        Wire wire = field.getAnnotation(Wire.class);
        String nameOfVariable = wire.name();
        if (nameOfVariable == null || nameOfVariable.isEmpty()) {
          nameOfVariable = field.getName();
        }
        switch (nameOfVariable) {
          case "netlasWrapper":
            field.set(instant, this.netlasWrapper);
            break;
          case "host":
            field.set(instant, this.host);
            break;
          default:
            LOGGER.warn(
                "Unrecognized field {} in {}", nameOfVariable, instant.getClass().getName());
            break;
        }
        LOGGER.info(
            "Injected dependency {} to {}", nameOfVariable, instant.getClass().getSimpleName());
      }
    }
  }
}
