package com.cruise.controller;

/**
 * Class for all jsp-pages and commands.
 *
 * @author  Vasyl Utrysko
 */
public final class AllPath {
    // pages

    //common
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_SING_IN = "view/sign_in.jsp";
    public static final String ERROR_PAGE = "view/error_page.jsp";
    public static final String REGISTER_PAGE = "view/register.jsp";

    //admin
    public static final String ADMIN_MENU = "view/admin/admin_menu.jsp";
    public static final String CRUISES_PAGE = "view/admin/cruises.jsp";
    public static final String ADD_ROUTE_TO_CRUISE_PAGE = "view/admin/add_route_to_cruise.jsp";
    public static final String ADD_SHIP_TO_CRUISE_PAGE = "view/admin/add_ship_to_cruise.jsp";
    public static final String ADD_ROUTE_PAGE = "view/admin/add_route.jsp";
    public static final String ROUTES_PAGE = "view/admin/routes.jsp";
    public static final String EDIT_STAFF_PAGE = "view/admin/edit_staff.jsp";
    public static final String CRUISE_SHIPS_PAGE = "view/admin/cruise_ships.jsp";
    public static final String ADD_CRUISE_SHIP_PAGE = "view/admin/add_cruise_ship.jsp";
    public static final String MANAGE_TICKETS_PAGE = "view/admin/manage_tickets.jsp";

    //client
    public static final String CLIENT_MENU = "view/client/client_menu.jsp";
    public static final String CHOOSE_CRUISE_PAGE = "view/client/choose_cruise.jsp";
    public static final String PERSONAL_CABINET_PAGE = "view/client/personal_cabinet.jsp";
    public static final String MY_TICKETS_PAGE = "view/client/my_tickets.jsp";

    //admin command
    public static final String CRUISES_COMMAND = "controller?action=cruises";
    public static final String EDIT_STAFF_COMMAND = "controller?action=edit_staff";
    public static final String ROUTES_COMMAND = "controller?action=routes";
    public static final String CRUISE_SHIPS_COMMAND = "controller?action=cruise_ships";
    public static final String MANAGE_TICKETS_COMMAND = "controller?action=manage_tickets";

    //client command
    public static final String CHOOSE_CRUISE_COMMAND = "controller?action=choose_cruise";
    public static final String PERSONAL_CABINET_COMMAND = "controller?action=personal_cabinet";
    public static final String MY_TICKETS_COMMAND = "controller?action=my_tickets";

}
