package com.cxf.demo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

public class Consumer {

  public static void main(String[] args) {
    try {
//接口名称
      String interfaceName = SayHelloService.class.getName();
      //接口方法
      Method method = SayHelloService.class.getMethod("sayHello", java.lang.String.class);
      //参数
      Object[] arguments = {"hello"};

      Socket socket = new Socket("127.0.0.1",8080);

      //发送请求
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      out.writeUTF(interfaceName);
      out.writeUTF(method.getName());
      out.writeObject(method.getParameterTypes());
      out.writeObject(arguments);

      //获取结果
      ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
      Object returnObject = inputStream.readObject();
      System.out.println(returnObject.toString());
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

}
