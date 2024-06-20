package com.ifms.arcondicionado.servicos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.springframework.stereotype.Service;

@Service
public class ContentExtractor<E> {
    public String extract(E entidade) {
        Method[] methods = entidade.getClass().getDeclaredMethods();
        String content = "";

        for(int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            int paramCount = method.getParameterCount();

            if(paramCount == 0 && methodName.startsWith("get")) {
                try {
                    content += methodName.substring(3) + " : ";

                    if(method.invoke(entidade) == null) {
                        content += "null";
                    }else {
                        content += method.invoke(entidade).toString();
                    }

                    if(i != methods.length - 1) {
                        content += " - ";
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return content;
    }
}
