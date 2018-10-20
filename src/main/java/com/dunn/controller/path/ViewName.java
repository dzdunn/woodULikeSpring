package com.dunn.controller.path;

public class ViewName {

	private ViewName() {
	}

	public final static String TEMPLATES = "/templates";

	public final static String HEADER = TEMPLATES + "/header";

	public final static String HOME = "/";

	public final static String ABOUT = "/about";

	public final static String CONTACT = "/contact";

	public final static String MY_WOOD_PROJECTS = "/myWoodProjects";

	public final static String CREATE_WOOD_PROJECT = MY_WOOD_PROJECTS + "/createWoodProject";

	public final static String MANAGE_WOOD_PROJECTS = MY_WOOD_PROJECTS + "/manageWoodProjects";

	public final static String MY_PROFILE = MY_WOOD_PROJECTS + "/myProfile";

	public final static String USER = "/user";

	public final static String LOGIN = USER + "/login";

	public final static String LOGIN_PROCESS = LOGIN + "process";

	public final static String REGISTER = USER + "/register";

	public final static String REGISTER_PROCESS = REGISTER + "process";


}
