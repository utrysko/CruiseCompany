package com.cruise.controller;

import javax.servlet.http.HttpServletRequest;

public class PaginationUtil {

    public static int getLimit(HttpServletRequest req){
        String param = req.getParameter("limit");
        if (param == null || param.isEmpty()){
            return 5;
        }
        int limit = Integer.parseInt(param);
        req.setAttribute("limit", limit);
        return limit;
    }
    public static int getPage(HttpServletRequest req){
        String param = req.getParameter("page");
        if (param == null){
            return 0;
        }
        return Integer.parseInt(param);
    }

    public static int getSortBy(HttpServletRequest req){
        String param = req.getParameter("sortBy");
        if (param == null || param.isEmpty()){
            return 1;
        }
        int sortBy = Integer.parseInt(param);
        req.setAttribute("sortBy", sortBy);
        return sortBy;
    }
    public static int getPages(int amount, int limit) {
        if (amount % limit != 0) {
            return amount / limit + 1;
        }
            return amount / limit;
    }

    private PaginationUtil(){}
}
