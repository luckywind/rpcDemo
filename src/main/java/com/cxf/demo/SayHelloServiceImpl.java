package com.cxf.demo;

public class SayHelloServiceImpl implements SayHelloService{

  public String sayHello(String str) {
    if ("hello".equals(str)) {
      return "hello world!";
    } else {
      return "error!";
    }
  }
}
