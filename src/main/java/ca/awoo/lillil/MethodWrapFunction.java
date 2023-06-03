package ca.awoo.lillil;

import java.lang.reflect.Method;

public class MethodWrapFunction implements Function {

    private String methodName;
    private Object object;

    public MethodWrapFunction(String methodName, Object object){
        this.methodName = methodName;
        this.object = object;
    }


    @Override
    public Object apply(Object... args) throws LillilRuntimeException {
        Class[] argTypes = new Class[args.length];
        for(int i = 0; i < args.length; i++){
            argTypes[i] = args[i].getClass();
        }
        try{
            //return object.getClass().getMethod(methodName, argTypes).invoke(object, args);
            for(Method method : object.getClass().getMethods()){
                if(method.getName().equals(methodName)){
                    if(method.getParameterTypes().length == args.length){
                        Class[] paramTypes = method.getParameterTypes();
                        boolean match = true;
                        for(int i = 0; i < paramTypes.length; i++){
                            if(!isAssignableFrom(paramTypes[i], argTypes[i])){
                                match = false;
                                break;
                            }
                        }
                        if(match)
                            return method.invoke(object, args);
                    }
                }
            }
            StringBuilder paramList = new StringBuilder();
            for(Class argType : argTypes){
                paramList.append(argType.getName()).append(", ");
            }
            paramList.delete(paramList.length() - 2, paramList.length());
            Method[] methods = object.getClass().getMethods();
            StringBuilder methodList = new StringBuilder();
            for(Method method : methods){
                if(method.getName().equals(methodName)){
                    methodList.append(method.getName()).append("(");
                    for(Class paramType : method.getParameterTypes()){
                        methodList.append(paramType.getName()).append(", ");
                    }
                    methodList.delete(methodList.length() - 2, methodList.length());
                    methodList.append("), ");
                }
            }
            methodList.delete(methodList.length() - 2, methodList.length());
            throw new LillilRuntimeException("No such method " + methodName + " with parameters " + paramList.toString() + "\nFound: " + methodList.toString());
        } catch (Exception e){
            throw new LillilRuntimeException("Error invoking method: " + methodName, e);
        }

    }

    private boolean isAssignableFrom(Class a, Class b){
        //TODO: More primitive types
        if(a.equals(double.class) && b.equals(Double.class)){
            return true;
        } else {
            return a.isAssignableFrom(b);
        }
    }
    
}
