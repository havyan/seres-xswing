/**
 * 
 */
package com.xswing.framework.view;

import java.net.MalformedURLException;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.framework.exception.ExceptionUtils;
import com.framework.log.Logger;
import com.xswing.framework.view.parser.ParserEngine;

/**
 * @author HWYan
 * 
 */
public class XPanelBuilder {

	public static XPanel build(String path) {
		return buildWithData(path, null);
	}

	public static XPanel buildWithData(String path, Object data) {
		String caller = null;
		for (StackTraceElement trace : new Throwable().getStackTrace()) {
			if (!trace.getClassName().equals(XPanelBuilder.class.getName())) {
				caller = trace.getClassName();
				break;
			}
		}
		try {
			String contextPath = Class.forName(caller).getResource("").toString();
			return buildWithData(contextPath, path, data);
		} catch (ClassNotFoundException e) {
			Logger.error(e);
		}
		return null;
	}

	public static XPanel build(String contextPath, String path) {
		return buildWithData(contextPath, path, null);
	}

	public static XPanel buildWithData(String contextPath, String path, Object data) {
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
			return buildWithData(new URL(path), data);
		} catch (MalformedURLException e) {
			ExceptionUtils.logAndShowException(e);
			return null;
		}
	}

	public static XPanel build(URL url) {
		return buildWithData(url, null);
	}

	public static XPanel buildWithData(URL url, Object data) {
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
			context.setData(data);
			context.setPath(path.substring(0, path.lastIndexOf("/") + 1));
			XPanel xpanel = (XPanel) ParserEngine.parse(context, doc.getRootElement());
			context.processRefs();
			return xpanel;
		} else {
			return null;
		}
	}

}
