/**
 * 
 */
package com.xswing.framework.view;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.framework.exception.ExceptionUtils;
import com.framework.log.Logger;
import com.xswing.framework.model.AppModel;
import com.xswing.framework.model.DefaultAppModel;
import com.xswing.framework.view.parser.ParserEngine;

/**
 * @author HWYan
 * 
 */
public class XPanelBuilder {

	public static XPanel build(String path) {
		return build(path, (AppModel<?>) null, (View) null);
	}

	public static XPanel build(String path, Object data) {
		if (data != null) {
			if (data instanceof AppModel) {
				return build(path, (AppModel<?>) data, (View) null);
			} else {
				return build(path, new DefaultAppModel(data), (View) null);
			}
		}
		return build(path, (AppModel<?>) null, (View) null);
	}

	public static XPanel build(String path, AppModel<?> model) {
		return build(path, model, (View) null);
	}

	public static XPanel build(String path, AppModel<?> model, View view) {
		return build(path, model, view, (Map<String, Object>) null);
	}

	public static XPanel build(String path, AppModel<?> model, View view, Map<String, Object> prdefinedBeans) {
		String caller = null;
		for (StackTraceElement trace : new Throwable().getStackTrace()) {
			if (!trace.getClassName().equals(XPanelBuilder.class.getName())) {
				caller = trace.getClassName();
				break;
			}
		}
		try {
			String classFilePath = Thread.currentThread().getContextClassLoader().getResource(caller.replace('.', '/') + ".class").toString();
			String contextPath = classFilePath.replace("/" + Class.forName(caller).getSimpleName() + ".class", "");
			return build(contextPath, path, model, view, prdefinedBeans);
		} catch (ClassNotFoundException e) {
			Logger.error(e);
		}
		return null;
	}

	public static XPanel build(String contextPath, String path) {
		return build(contextPath, path, (AppModel<?>) null, (View) null, (Map<String, Object>) null);
	}

	public static XPanel build(String contextPath, String path, AppModel<?> model) {
		return build(contextPath, path, model, (View) null, (Map<String, Object>) null);
	}

	public static XPanel build(String contextPath, String path, AppModel<?> model, View view, Map<String, Object> prdefinedBeans) {
		contextPath = contextPath.trim();
		path = path.trim();
		if (path.startsWith("./")) {
			path = contextPath + (contextPath.endsWith("/") ? "" : "/") + path.substring(2);
		} else if (path.startsWith("../")) {
			int index = path.lastIndexOf("../") + 3;
			String realPath = path.substring(index);
			String relativePath = path.substring(0, index).trim();
			while (relativePath.endsWith("../")) {
				if (contextPath.endsWith("/")) {
					contextPath = contextPath.substring(0, contextPath.length() - 1).trim();
				}
				contextPath = contextPath.substring(0, contextPath.lastIndexOf("/") + 1).trim();
				relativePath = relativePath.substring(0, relativePath.length() - 3).trim();
			}
			path = contextPath + realPath;
		} else {
			path = Thread.currentThread().getContextClassLoader().getResource(path).toString();
		}
		try {
			return build(new URL(path), model, view, prdefinedBeans);
		} catch (MalformedURLException e) {
			ExceptionUtils.logAndShowException(e);
			return null;
		}
	}

	public static XPanel build(URL url) {
		return build(url, (AppModel<?>) null, (View) null, (Map<String, Object>) null);
	}

	public static XPanel build(URL url, AppModel<?> model) {
		return build(url, model, (View) null, (Map<String, Object>) null);
	}

	public static XPanel build(URL url, AppModel<?> model, View view) {
		return build(url, model, view, (Map<String, Object>) null);
	}

	public static XPanel build(URL url, AppModel<?> model, View view, Map<String, Object> prdefinedBeans) {
		String path = url.toString();
		Document doc = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(url);
		} catch (Exception e) {
			ExceptionUtils.logAndShowException(e);
		}
		if (doc != null) {
			Context context = new Context();
			context.setDoc(doc);
			context.setModel(model);
			context.setView(view);
			context.setPath(path.substring(0, path.lastIndexOf("/") + 1));
			if (model != null) {
				context.setBean("model", model);
			}
			if (view != null) {
				context.setBean("view", view);
			}
			if (prdefinedBeans != null) {
				for (Map.Entry<String, Object> entry : prdefinedBeans.entrySet()) {
					context.setBean(entry.getKey(), entry.getValue());
				}
			}
			XPanel xpanel = (XPanel) ParserEngine.parse(context, doc.getRootElement());
			context.processRefs();
			return xpanel;
		} else {
			return null;
		}
	}

}
