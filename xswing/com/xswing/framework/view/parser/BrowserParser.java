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
	public Browser parseElement(Context context, Element source) {
		Browser panel = createBean(context, source);
		String url = getString(source, Const.URL);
		if (StringUtils.isNotEmpty(url)) {
			String protocol = "http://";
			if (!url.trim().substring(0, protocol.length()).equalsIgnoreCase(protocol)) {
				url = protocol + url;
			}
			try {
				panel.setUrl(new URL(url));
			} catch (MalformedURLException e) {
				Logger.error(e);
				JOptionPane.showMessageDialog(panel, e.getMessage());
			}
		} else {
			String path = getString(source, Const.PATH);
			if (StringUtils.isNotEmpty(path)) {
				try {
					panel.setFile(new File(path));
				} catch (MalformedURLException e) {
					Logger.error(e);
					JOptionPane.showMessageDialog(panel, e.getMessage());
				}
			}
		}

		return panel;
	}

}
