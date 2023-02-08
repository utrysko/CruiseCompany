package com.cruise.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Util class for getting parameter for pagination
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class PaginationUtil {

    private PaginationUtil(){}

    /**
     * Get limit parameter from request
     *
     * @param req - HttpServletRequest
     * @return limit(int)
     */
    public static int getLimit(HttpServletRequest req){
        String param = req.getParameter("limit");
        if (param == null || param.isEmpty()){
            return 5;
        }
        int limit = Integer.parseInt(param);
        req.setAttribute("limit", limit);
        return limit;
    }

    /**
     * Get page parameter from request
     *
     * @param req - HttpServletRequest
     * @return page(int)
     */
    public static int getPage(HttpServletRequest req){
        String param = req.getParameter("page");
        if (param == null){
            return 0;
        }
        return Integer.parseInt(param);
    }

    /**
     * Get sortBy parameter from request
     *
     * @param req - HttpServletRequest
     * @return sortBy(int)
     */
    public static int getSortBy(HttpServletRequest req){
        String param = req.getParameter("sortBy");
        if (param == null || param.isEmpty()){
            return 1;
        }
        int sortBy = Integer.parseInt(param);
        req.setAttribute("sortBy", sortBy);
        return sortBy;
    }

    /**
     * Get number of pages parameter
     *
     * @param amount - number of records in database
     * @param limit - limit parameter
     * @return limit(int)
     */
    public static int getPages(int amount, int limit) {
        if (amount % limit != 0) {
            return amount / limit + 1;
        }
            return amount / limit;
    }
}
