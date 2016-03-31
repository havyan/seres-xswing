package com.xswing.framework.view.components;

import static javafx.concurrent.Worker.State.FAILED;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

public class Browser extends JFXPanel {

	private WebEngine engine;

	public Browser() {
		this.initWebView();
	}

	public Browser(URL url) {
		this();
		this.setUrl(url);
	}

	private void initWebView() {
		
		Platform.setImplicitExit(false);

		Platform.runLater(() -> {
			WebView webView = new WebView();
			engine = webView.getEngine();
			engine.titleProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
					SwingUtilities.invokeLater(() -> {
						titleChanged(observable, oldValue, newValue);
					});
				}
			});

			engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
				@Override
				public void handle(final WebEvent<String> event) {
					SwingUtilities.invokeLater(() -> {
						handleStatus(event);
					});
				}
			});

			engine.locationProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> ov, String oldValue, final String newValue) {
					SwingUtilities.invokeLater(() -> {
						locationChanged(ov, oldValue, newValue);
					});
				}
			});

			engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {
					SwingUtilities.invokeLater(() -> {
						loadChanged(observableValue, oldValue, newValue);
					});
				}
			});

			engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {

				public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
					if (engine.getLoadWorker().getState() == FAILED) {
						SwingUtilities.invokeLater(() -> {
							handleError(o, old, value);
						});
					}
				}
			});

			Browser.this.setScene(new Scene(webView));
		});

	}

	public void setUrl(URL url) {
		Platform.runLater(() -> {
			engine.load(url.toExternalForm());
		});
	}

	public void setFile(File file) throws MalformedURLException {
		this.setUrl(file.toURI().toURL());
	}

	public void addTitleChangeListener(ChangeListener<String> l) {
		engine.titleProperty().addListener(l);
	}

	public void setStatusHandler(EventHandler<WebEvent<String>> handler) {
		engine.setOnStatusChanged(handler);
	}

	protected void titleChanged(ObservableValue<? extends String> observable, String oldValue, final String newValue) {

	}

	protected void handleStatus(WebEvent<String> event) {

	}

	protected void locationChanged(ObservableValue<? extends String> ov, String oldValue, final String newValue) {

	}

	protected void loadChanged(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {

	}

	protected void handleError(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {

	}
}
