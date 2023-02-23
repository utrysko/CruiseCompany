package com.cruise.controller.command;


import com.cruise.controller.command.admin.MakeAdminCommand;
import com.cruise.controller.command.admin.cruise.*;
import com.cruise.controller.command.admin.cruiseShip.*;
import com.cruise.controller.command.admin.route.AddRouteCommand;
import com.cruise.controller.command.admin.route.DeleteRouteCommand;
import com.cruise.controller.command.admin.route.RoutesCommand;
import com.cruise.controller.command.admin.tickets.ManageTicketsCommand;
import com.cruise.controller.command.admin.tickets.ShowDocumentCommand;
import com.cruise.controller.command.admin.tickets.TicketChangeStatusCommand;
import com.cruise.controller.command.client.*;
import com.cruise.controller.command.general.I18NCommand;
import com.cruise.controller.command.general.RegisterCommand;
import com.cruise.controller.command.general.SignInCommand;
import com.cruise.controller.command.general.LogoutCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Command factory for Main Controller.
 *
 * @author Vasyl Utrysko
 */
public class CommandFactory {

    private static CommandFactory factory = new CommandFactory();
    private static Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {}

    /**
     * Singleton.
     */
    public static CommandFactory getInstance() {
        if (factory == null) {
            factory = new CommandFactory();
        }
        return factory;
    }
    static {

        // general commands
        commands.put("sign_in", new SignInCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("change_language", new I18NCommand());
        commands.put("redirect", null);

        // admin commands
        commands.put("cruises", new CruisesCommand());
        commands.put("add_cruise", new AddCruiseCommand());
        commands.put("delete_cruise", new DeleteCruiseCommand());
        commands.put("add_route_to_cruise", new AddRouteToCruiseCommand());
        commands.put("add_ship_to_cruise", new AddShipToCruiseCommand());
        commands.put("change_cruise_status", new ChangeCruiseStatusCommand());
        commands.put("edit_cruise", new EditCruiseCommand());

        commands.put("routes", new RoutesCommand());
        commands.put("add_route", new AddRouteCommand());
        commands.put("delete_route", new DeleteRouteCommand());

        commands.put("cruise_ships", new CruiseShipsCommand());
        commands.put("add_cruise_ship", new AddCruiseShipCommand());
        commands.put("edit_staff", new EditStaffCommand());
        commands.put("add_staff", new AddStaffCommand());
        commands.put("delete_staff", new DeleteStaffCommand());
        commands.put("delete_cruise_ship", new DeleteCruiseShipCommand());
        commands.put("edit_cruise_ship", new EditCruiseShipCommand());
        commands.put("cruise_ship_change_status", new CruiseShipChangeStatusCommand());

        commands.put("manage_tickets", new ManageTicketsCommand());
        commands.put("show_document", new ShowDocumentCommand());
        commands.put("ticket_change_status", new TicketChangeStatusCommand());

        commands.put("make_admin", new MakeAdminCommand());

        // client commands
        commands.put("choose_cruise", new ChooseCruiseCommand());
        commands.put("buy_ticket", new BuyTicketCommand());
        commands.put("personal_cabinet", new PersonalCabinetCommand());
        commands.put("my_tickets", new MyTicketsCommand());
        commands.put("edit_profile", new EditProfileCommand());
        commands.put("change_password", new ChangePasswordCommand());
        commands.put("replenish_balance", new ReplenishBalanceCommand());
        commands.put("download_ticket", new TicketDownloadCommand());

    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
