package com.dunn.controller.uipaths.views;

public final class ViewName {

	public ViewName() {
	}

	public final static String PROCESS = "Process";

	public final static String TEMPLATES = "/templates";
	public final static ViewNameWrapper TEMPLATES_WRAPPER = new ViewNameWrapper(TEMPLATES);

	public final static String HEADER = TEMPLATES + "/header";
	public final static ViewNameWrapper HEADER_WRAPPER = new ViewNameWrapper(HEADER);

	public final static String HOME = "/home";
	public final static ViewNameWrapper HOME_WRAPPER = new ViewNameWrapper(HOME, true);

	public final static String HOMEPAGE = HOME + "/home";
	public final static ViewNameWrapper HOMEPAGE_WRAPPER = new ViewNameWrapper(HOMEPAGE, true);

	public final static String ABOUT =  HOME + "/about";
	public final static ViewNameWrapper ABOUT_WRAPPER = new ViewNameWrapper(ABOUT, true);

	public final static String CONTACT = HOME + "/contact";
	public final static ViewNameWrapper CONTACT_WRAPPER = new ViewNameWrapper(CONTACT, true);

	public final static String MY_WOOD_PROJECTS = "/myWoodProjects";
	public final static ViewNameWrapper MY_WOOD_PROJECTS_WRAPPER = new ViewNameWrapper(MY_WOOD_PROJECTS);

	public final static String CREATE_WOOD_PROJECT = MY_WOOD_PROJECTS + "/createWoodProject";
	public final static ViewNameWrapper CREATE_WOOD_PROJECT_WRAPPER = new ViewNameWrapper(CREATE_WOOD_PROJECT);

	public final static String EDIT_WOOD_PROJECT = MY_WOOD_PROJECTS + "/editWoodProject";
	public final static ViewNameWrapper EDIT_WOOD_PROJECT_WRAPPER = new ViewNameWrapper(EDIT_WOOD_PROJECT);

	public final static String SAVE_WOOD_PROJECT = MY_WOOD_PROJECTS + "/saveWoodProject";
	public final static ViewNameWrapper SAVE_WOOD_PROJECT_WRAPPER = new ViewNameWrapper(SAVE_WOOD_PROJECT);

	public final static String MANAGE_WOOD_PROJECTS = MY_WOOD_PROJECTS + "/manageWoodProjects";
	public final static ViewNameWrapper MANAGE_WOOD_PROJECTS_WRAPPER = new ViewNameWrapper(MANAGE_WOOD_PROJECTS);

	public final static String MY_PROFILE = MY_WOOD_PROJECTS + "/myProfile";
	public final static ViewNameWrapper MY_PROFILE_WRAPPER = new ViewNameWrapper(MY_PROFILE);

	public final static String USER = "/user";
	public final static ViewNameWrapper USER_WRAPPER = new ViewNameWrapper(USER);

	public final static String LOGIN = USER + "/login";
	public final static ViewNameWrapper LOGIN_WRAPPER = new ViewNameWrapper(LOGIN, true);

	public final static String LOGIN_PROCESS = LOGIN + PROCESS;
	public final static ViewNameWrapper LOGIN_PROCESS_WRAPPER = new ViewNameWrapper(LOGIN_PROCESS, true);

	public final static String LOGIN_FAILURE = LOGIN + "Failure";
	public final static ViewNameWrapper LOGIN_FAILURE_WRAPPER = new ViewNameWrapper(LOGIN_FAILURE, true) ;

	public final static String LOGOUT = "/logout";
	public final static ViewNameWrapper LOGOUT_WRAPPER = new ViewNameWrapper(LOGOUT);

	public final static String REGISTER = USER + "/register";
	public final static ViewNameWrapper REGISTER_WRAPPER = new ViewNameWrapper(REGISTER, true);

	public final static String REGISTER_PROCESS = REGISTER + PROCESS;
	public final static ViewNameWrapper REGISTER_PROCESS_WRAPPER = new ViewNameWrapper(REGISTER_PROCESS, true);

	public final static String RESET_PASSWORD = USER + "/resetPassword";
	public final static ViewNameWrapper RESET_PASSWORD_WRAPPER = new ViewNameWrapper(RESET_PASSWORD, true);

	public final static String RESET_PASSWORD_PROCESS = RESET_PASSWORD + PROCESS;
	public final static ViewNameWrapper RESET_PASSWORD_PROCESS_WRAPPER = new ViewNameWrapper(RESET_PASSWORD_PROCESS,true);

	public final static String RESET_PASSWORD_EMAIL_SENT = USER + "/resetPasswordEmailSent";
	public final static ViewNameWrapper RESET_PASSWORD_EMAIL_SENT_WRAPPER = new ViewNameWrapper(RESET_PASSWORD_EMAIL_SENT, true);

	public final static String RESET_PASSWORD_TOKEN_INVALID = USER + "/resetPasswordTokenInvalid";
	public final static ViewNameWrapper RESET_PASSWORD_TOKEN_INVALID_WRAPPER = new ViewNameWrapper(RESET_PASSWORD_TOKEN_INVALID, true);

	public final static String CHANGE_PASSWORD = USER + "/changePassword";
	public final static ViewNameWrapper CHANGE_PASSWORD_WRAPPER = new ViewNameWrapper(CHANGE_PASSWORD, true);

	public final static String UPDATE_PASSWORD = USER + "/updatePassword";
	public final static ViewNameWrapper UPDATE_PASSWORD_WRAPPER = new ViewNameWrapper(UPDATE_PASSWORD, true);

	public final static String UPDATE_PASSWORD_PROCESS = UPDATE_PASSWORD + PROCESS;
	public final static ViewNameWrapper UPDATE_PASSWORD_PROCESS_WRAPPER = new ViewNameWrapper(UPDATE_PASSWORD_PROCESS);

	public final static String FILE_UPLOAD_PROCESS = "/fileUpload" + PROCESS;
	public final static ViewNameWrapper FILE_UPLOAD_PROCESS_WRAPPER = new ViewNameWrapper(FILE_UPLOAD_PROCESS);

	public final static String WOOD_PROJECTS = "/woodProjects";
	public final static ViewNameWrapper WOOD_PROJECT_WRAPPER = new ViewNameWrapper(WOOD_PROJECTS, true);

	public final static String WOOD_PROJECT = WOOD_PROJECTS + "/woodProject";
	public final static ViewNameWrapper GENERIC_WOOD_PROJECT_WRAPPER = new ViewNameWrapper(WOOD_PROJECT, true, true);

}
