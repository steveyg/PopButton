popButton
======

A button like Path APP, when we click the button, it will pop some other buttons.
<br>
PopButton是一个用于实现类似Path点聚式导航的控件，[查看中文介绍](http://blog.csdn.net/steveyg/article/details/54290926)


integration
----------

Download this code, copy *.java and activity.xml to your project.
<br>
declare activity in manifast
```xml
<activity  
            android:name=".activity.PopActivity"  
            android:theme="@android:style/Theme.Translucent" /> 
        
```
create a xml for layout, like:
```xml
<?xml version="1.0" encoding="utf-8"?>  
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    xmlns:tools="http://schemas.android.com/tools"  
    android:id="@+id/activity_main"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    tools:context="com.steveyg.popbutton.activity.MainActivity">  
  
   <com.steveyg.popbutton.view.PopButton  
       android:id="@+id/popbutton"  
       android:layout_centerInParent="true"  
       android:background="@drawable/chooser_button"  
       android:layout_width="50dp"  
       android:layout_height="50dp" />  
</RelativeLayout>  
```
in Activity, set PopButton's property
```java
    mPopButton = (PopButton) findViewById(R.id.popbutton);
    mPopButton.getPopmodel()
            .setRotateOfMainButton(45)
            .setRotateOfMenuButton(360)
            .setDurationTime(400)
            .setRotateTime(170)
            .setRotateDelayTime(125)
            .setBackground(0x55000000)
            .setNumOfButton(3)
            .setRadius(250)
            .setMenuDirection(PopModel.UP);
```
set the icon of buttons
```java
    int[] res = {R.drawable.icon1,R.drawable.icon2,R.drawable.icon3};
    mPopButton.getPopmodel().setButtonImageResource(res);
```
and clicklistener, if you need context, please use "PopActivity.instance"
```java
    ArrayList<View.OnClickListener> clickListenerList = new ArrayList<>();
        for(int i = 0; i < 3 ; i++){
            final int fi = i + 1;
            clickListenerList.add(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PopActivity.instance,"click button" + fi ,Toast.LENGTH_SHORT).show();
                }
            });
        }
        mPopButton.getPopmodel().setButtonClickListener(clickListenerList);
```
![](http://img.blog.csdn.net/20170109185923271)
![](http://img.blog.csdn.net/20170109191657374)
![](http://img.blog.csdn.net/20170109193526854)
![](http://img.blog.csdn.net/20170109194834990)
<br>
enjoy it!
