package com.chen.client;
import javax.swing.*;
import java.awt.*;
import java.awt.Event.*;
import java.util.Vector;
class MyList{
 private JFrame frame = new JFrame("hello world");
 private Container cont = frame.getContentPane();
 private JList list1 = null;
 private JList list2 = null;
 
 public MyList(){
  this.frame.setLayout(new GridLayout(1,3));
  String nation[] ={"china","usa","japan","corea","dlsj","ldskj","ldsk","lsfjls","lsdfk"};
  Vector<String> v = new Vector<String>();//可实现自动增长对象数组
  
  v.add("hi");
  v.add("you");
  v.add("who");
  v.add("are");
  
  this.list1 = new JList(nation);
  this.list2 = new JList(v);
  
  this.list1.setBorder(BorderFactory.createTitledBorder("which country do you want to"));
  this.list2.setBorder(BorderFactory.createTitledBorder("do you love me"));
  
  this.cont.add(list1);
  this.cont.add(list2);
  
  this.cont.add(new JScrollPane(this.list1)); //对list1添加滚动条
  this.cont.add(new JScrollPane(this.list2)); //对list1添加滚动条

  
  this.frame.setSize(400,200);
  this.frame.setVisible(true);
  this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}
public class Test {
 public static void main(String args[]){
  new MyList();
 }
 
}