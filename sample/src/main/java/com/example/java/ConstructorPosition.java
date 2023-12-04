package com.example.java;

public class ConstructorPosition {
  public void log() {
    System.out.println("Hi " + nickname + "!");
  }

  public ConstructorPosition(String name) {
    nickname = name.substring(0, 3);
  }

  private String nickname;
}
