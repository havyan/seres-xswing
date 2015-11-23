package com.xswing.framework.view.parser;

import com.xswing.framework.view.Context;

/**
 * TODO need to add a context,because element is not isolated.
 * @author HWYan
 *
 * @param <T>
 * @param <E>
 */
public interface Parser<T,E> {

	public T parse(Context context, E source);

}
