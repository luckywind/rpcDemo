package com.cxf.demo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Provider {

  public static void main(String[] args) {
    ServerSocket server =null;
    ObjectOutputStream out = null;
    try {
      server = new ServerSocket(8080);
      Socket socket = null;

      while (true) {
        System.out.println("开始监听----");
        socket=server.accept();
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        String interfaceName = input.readUTF(); //接口名称
        String methodName = input.readUTF();   //方法名称
        Class<?>[] parameterType =(Class<?>[]) input.readObject(); //参数类型
        Object[] arguments = (Object[]) input.readObject();
        System.out.println("接收到的参数" + Arrays.toString(arguments));

        //根据接口名称获取class
        Class<?> serviceInterfaceClass = Class.forName(interfaceName);
        //根据方法名称和参数类型反射得到方法
        Method method = serviceInterfaceClass.getMethod(methodName, parameterType);
        //服务实例化（这里做简单处理，正常应该根据得到的接口名称serviceInterfaceClass获取对应的service,但本demo只提供一个服务）
        SayHelloServiceImpl service = new SayHelloServiceImpl();
        //反射执行这个方法
        Object result = method.invoke(service, arguments);
        //写回处理结果
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(result);

      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

}
