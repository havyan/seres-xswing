package com.xswing.framework.view.parser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.log.Logger;
import com.xswing.framework.view.Context;
import com.xswing.framework.view.components.Browser;

@XElement(names = { Const.BROWSER })
public class BrowserParser extends ComponentParser<Browser> {

	@Override
	protected void bind(Context context, String id, Browser browser, Element source) {
		super.bind(context, id, browser, source);
		String url = getString(source, Const.URL);
		if (StringUtils.isNotEmpty(url)) {
			bindSet(context, browser, url, value -> {
				String protocol = "http://";
				String urlValue = value.toString();
				if (!urlValue.trim().substring(0, protocol.length()).equalsIgnoreCase(protocol)) {
					urlValue = protocol + urlValue;
				}
				try {
					browser.setUrl(new URL(urlValue));
				} catch (MalformedURLException e) {
					Logger.error(e);
					JOptionPane.showMessageDialog(browser, e.getMessage());
				}
			});
		} else {
			String file = getString(source, Const.FILE);
			if (StringUtils.isNotEmpty(file)) {
				bindSet(context, browser, file, value -> {
					try {
						browser.setFile(new File(value.toString()));
					} catch (MalformedURLException e) {
						Logger.error(e);
						JOptionPane.showMessageDialog(browser, e.getMessage());
					}
				});
			}
		}
	}

}
