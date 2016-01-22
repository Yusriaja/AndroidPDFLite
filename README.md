# KopihaoClearableEditText 

_Simple custom EditText which able to clear input with your own icon.
That's all..._

## Why use this
1. Purely **Android** native
2. Clear icon be customize well 


## How it behave
1. When no text, no clear_icon appear.
2. When there is atleast a character, clear_icon will appear.
3. Tap on clear_icon, input of CET will be cleared. 


## How to use 

In your Android Layout XML:
``` xml
<com.view.plugin.cet.ClearableEditText
            android:id="@+id/cetInput"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentTop="true"
            android:background="@android:color/white"
            android:gravity="center_vertical|start"
            android:hint="@string/txt_content"
            android:padding="5dp"
            android:text="@string/app_name"
            android:textSize="20sp" />
```

In your Android Java:
```java
//Find from layout xml
ClearableEditText cetInput = (ClearableEditText) findViewById(R.id.cetInput);

``` 
_or_  

 ```java 
//Declare component
ClearableEditText cetInput = new ClearableEditText(this); 
```

## How to customize

Clear Icon (Drawable) can be customized via:

* Method
* Extended class

Method : 
```java
cetInput.setClearIcon(android.R.drawable.ic_btn_speak_now); 
```

Extended class : 
```java
public class YetAnotherCET extends ClearableEditText {

	public YetAnotherCET(final Context context) {
		super(context);
	}

	public YetAnotherCET(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public YetAnotherCET(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void initView(Context context) {
		super.initView(context);
		setClearIcon(android.R.drawable.btn_star_big_on);
	}

}
```

## Things to take note
- Clear icon will affect CET height.
- Make sure all icons in various resolution is resized well. 


## Dependancy

_None! Beauty of it? Purely Native._  

## Preview

![Preview1](https://lh3.googleusercontent.com/-1UeKVteCsPw/VhAVEBPnmlI/AAAAAAAACf8/nKTBYBK6I10/s512-Ic42/live_cet.png)
 
[Live Demo](https://appetize.io/app/94jcdbmxawkdxjpw43nnt3j10w?device=nexus5&scale=75&orientation=portrait)

<iframe src="https://appetize.io/embed/94jcdbmxawkdxjpw43nnt3j10w?device=iphone5s&amp;scale=50&amp;autoplay=false&amp;orientation=portrait&amp;deviceColor=black" width="378px" height="800px" frameborder="0" scrolling="no"></iframe>
 

## License  

```
Copyright (C) 2015 Kopihao
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
