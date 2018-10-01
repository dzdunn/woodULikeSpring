package com.dunn.controller.path;

//@Component
public class ViewName {

	private static ViewName viewName = new ViewName();

	public static ViewName getInstance() {
		return viewName;
	}

	private ViewName() {
	}


	public final static String HOME = "/woodulike";

	public String getHOME() {
		return HOME;
	}

	public final static String MY_WOOD_PROJECTS = "/myWoodProjects";

	public String getMY_WOOD_PROJECTS(){
		return MY_WOOD_PROJECTS;
	}

	public final static String CREATE_WOOD_PROJECT = MY_WOOD_PROJECTS + "/createWoodProject";

	public String getCREATE_WOOD_PROJECT(){
		return CREATE_WOOD_PROJECT;
	}

}
