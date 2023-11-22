package com.intuit.profileservice.util;

public class Constants {

    //alogrithm used for encryption decryption
    public static final String CIPHER = "AES";
    // key used for encryption decryption
    public static final String KEY = "UnsatisfiedDependency";
    // salt used for encryption
    public static final byte[] SALT = new byte[]{0x5a, 0x3f, 0x19, 0x7e, 0x2d, 0x1a, 0x7c, 0x4b};
    // rescode to indicate bad request
    public static final String RESCODE_BADREQUEST = "BR";
    //validation failure rescode
    public static final String RESCODE_VALIDATIONFAILURE = "FE";
    public static final String RESCODE_VALIDATIONFAILURE_DEFAULT_MSG = "Failure occurred";
    // rescode to indicate ducplicate legal name
    public static final String RESCODE_DC = "DC";
    public static final String RESCODE_DC_DEFAULT_MSG = "Duplicate Legal Name";
    public static final String RESCODE_LCC = "LCC";
    public static final String RESCODE_LCC_DEFAULT_MSG = "Legal Name Cannot be changed";
    // rescode to indicate Rate exceeded
    public static final String RESCODE_RLE = "RLE";
    public static final String RESCODE_RLE_DEFAULT_MSG = "Rate Limit exceeded";
    public static final String RESCODE_NF = "404";
    public static final String RESCODE_NF_DEFAULT_MSG = "Not found";
    public static final String SUCCESS_RESCODE = "00";
    public static final String SUCCESS_RESCODE_DEFAULT_MSG = "SUCESSFULL";
    public static final String FAILURE_RESCODE_DEFAULT_MSG = "FAILURE";
    // API end points start
    public static final String CREATE_PROFILE_ENDPOINT = "/profile";
    public static final String CREATE_CONTROLLER_PATH = "/create";
    public static final String VIEW_CONTROLLER_PATH = "/view";
    public static final String VIEW_ALL_ENDPOINT = "/all";
    public static final String VIEW_PROFILE_ENDPOINT = "/profile";
    public static final String DB_CONTROLLER_PATH = "/get";
    public static final String DB_GET_ERRORCODES = "/errorcodes";
    private Constants() {
    }


}
