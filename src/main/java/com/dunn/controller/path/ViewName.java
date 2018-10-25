package com.dunn.controller.path;

public class ViewName {

	private ViewName() {
	}

	public final static String TEMPLATES = "/templates";

	public final static String HEADER = TEMPLATES + "/header";

	public final static String HOME = "/home";

	public final static String HOMEPAGE = HOME + "/home";

	public final static String ABOUT =  HOME + "/about";

	public final static String CONTACT = HOME + "/contact";

	public final static String MY_WOOD_PROJECTS = "/myWoodProjects";

	public final static String CREATE_WOOD_PROJECT = MY_WOOD_PROJECTS + "/createWoodProject";

	public final static String MANAGE_WOOD_PROJECTS = MY_WOOD_PROJECTS + "/manageWoodProjects";

	public final static String MY_PROFILE = MY_WOOD_PROJECTS + "/myProfile";

	public final static String USER = "/user";

	public final static String LOGIN = USER + "/login";

	public final static String LOGIN_PROCESS = LOGIN + "Process";

	public final static String LOGOUT = "/logout";

	public final static String REGISTER = USER + "/register";

	public final static String REGISTER_PROCESS = REGISTER + "Process";

	public final static String RESET_PASSWORD = USER + "/resetPassword";

	public final static String RESET_PASSWORD_PROCESS = RESET_PASSWORD + "Process";

	public final static String CHANGE_PASSWORD = USER + "/changePassword";

	public final static String UPDATE_PASSWORD = USER + "/updatePassword";

	public final static String UPDATE_PASSWORD_PROCESS = UPDATE_PASSWORD + "Process";


}
