/**
 * 
 */
package com.xswing.framework.view.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@SuppressWarnings("unchecked")
public class ParserEngine {

	public static final Map<String, Class<Parser<?, Element>>> PARSERS = new HashMap<String, Class<Parser<?, Element>>>();

	public static void register(Class<Parser<?, Element>> cls) {
		XElement xmlElement = cls.getAnnotation(XElement.class);
		if (xmlElement != null) {
			for (String name : xmlElement.names()) {
				PARSERS.put(name, cls);
			}
		}
	}

	static {
		Set<Class<?>> clses = BaseUtils.getClasses("com.xswing.framework.view.parser");
		for (Class<?> cls : clses) {
			if (Parser.class.isAssignableFrom(cls)) {
				register((Class<Parser<?, Element>>) cls);
			}
		}
	}

	/**
	 * 
	 * @param context
	 * @param e
	 * @return
	 */
	public static Parser<?, Element> getParser(Context context, Element e) {
		Parser<?, Element> parser = null;
		String name = e.getName();
		if (e.isRootElement()) {
			parser = new XPanelParser();
		} else {
			Class<Parser<?, Element>> cls = PARSERS.get(name);
			String[] wrappers = WrapperParser.class.getAnnotation(XElement.class).names();
			if (e.getName().equals(Const.BEAN) && Arrays.asList(wrappers).contains(e.getParentElement().getName())) {
				cls = PARSERS.get(Const.COMPONENTBEAN);
			}
			if (cls != null) {
				parser = (Parser<?, Element>) BaseUtils.newInstance(cls);
			}
		}
		return parser;
	}

	public static Object parse(Context context, Element e) {
		Parser<?, Element> parser = getParser(context, e);
		if (parser != null) {
			return parser.parse(context, e);
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		Set<Class<?>> clss = BaseUtils.getClasses("com.xswing.framework.view.parser");
		for (Class<?> cls : clss) {
			System.out.println(cls.getName());
		}
	}

}
