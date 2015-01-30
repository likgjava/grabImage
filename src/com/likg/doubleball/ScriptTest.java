package com.likg.doubleball;

import java.io.FileReader;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
public class ScriptTest {
	public static void main(String[] args) throws Exception {
		
//		 ScriptEngineManager manager = new ScriptEngineManager();
//	        ScriptEngine engine2 = manager.getEngineByName("JavaScript");
//	        engine2.eval("print ('Hello World  ')");

		
		//获得一个JavaScript的执行引擎
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		//建立上下文变量
		Bindings bind = engine.createBindings();
		
		String html = "<div id='d'>hello</div>";
		
		
		bind.put("factor", 1);
		bind.put("html", html);
		//绑定上下文，作用域是当前引擎范围
		engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);
		//参数
		int num1 = 1;
		int num2 = 2;
		//执行js代码
		engine.eval(new FileReader("D:/model.js"));
		//是否可调用方法
		if(engine instanceof Invocable) {
			Invocable in = (Invocable) engine;
			//执行JS中方法
			Double result = (Double) in.invokeFunction("formula", num1, num2);
			System.out.println(result); //3.0
		}
	}
}