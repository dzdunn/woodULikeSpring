package com.dunn.controller.path;

//@Component
public class ViewName {

	private static ViewName viewName = new ViewName();

	public static ViewName getInstance() {
		return viewName;
	}

	private ViewName() {
	}


	public final static String HOME = "woodulike";

	public String getHOME() {
		return HOME;
	}

}
