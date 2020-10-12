package com.moer.day.office.util;


/**
 * @author : hepw
 * @date : 2020/2/25 15:28
 * @func : 定义魔法值
 */
public interface Constants {
    /**
     * 渠道：1 点评 0 问答评论
     */
    String PRAISE_FLAG_CHANNEL_ONE = "点评";
    String PRAISE_FLAG_CHANNEL_ZERO = "问答评论";
    /**
     * 品牌/厂商/车系的特定前缀
     */
    String CAR_BRAND_ID_PREFIX = "carbrand_";
    String CAR_MAKE_ID_PREFIX = "carmake_";
    String CAR_MODEL_ID_PREFIX = "carmodel_";

    String CAR_MAIN_BRAND_ID_STE = "carmainbrand";
    String CAR_BRAND_ID_STE = "carbrand";
    String CAR_MAKE_ID_STE = "carmake";
    String CAR_MODEL_ID_STE = "carmodel";

    String CAR_MAIN_BRAND_ID_FIELD_PREFIX = "car_mainbrand_id";
    String CAR_BRAND_ID_FIELD_PREFIX = "car_brand_id";
    String CAR_MAKE_ID_FIELD_PREFIX = "car_make_id";
    String CAR_MODEL_ID_FIELD_PREFIX = "car_model_id";
    String CAR_MODEL_ID = "serial_id";

    String UNDER_LINE_STR = "_";
    String MIDDLE_LINE_STR = "-";
    String BLANK_SPACE_STR = " ";
    String REPLACE = "replace";

    String MONTH = "month";
    String WEEK = "week";
    String DAY = "day";

    String NATION = "nation";
    String COUNTRY = "country";
    String PROVINCE = "province";
    String PROVINCE_FIELD_PREFIX = "province_id";
    String CITY_FIELD_PREFIX = "city_id";
    String COUNTRYZH = "全国";


    String MODEL_TYPE_NAME = "车型";
    String MAKE_TYPE_NAME = "厂商";
    String BRAND_TYPE_NAME = "品牌";
    String CUSTOMER_TYPE_NAME = "客户主体";
    String MODEL_SALE_PROVINCE = "carmodel";
    String MODEL = "model";
    String SERIAL = "serial";
    String MAKE = "make";
    String BRAND = "brand";
    /**
     * 客户主体
     */
    String CUSTOMER = "customer";
    String MAIN_BRAND = "mainbrand";
    String BRAND_TO_MODEL_MENU = "brandToModelMenu";

    String ZERO = "0";
    String ONE = "1";
    String TWO = "2";
    String THREE = "3";

    String CAR_LEVEL = "level";
    String CAR_LEVEL_ONE = "levelone";
    String CAR_LEVEL_TWO = "leveltwo";
    String CAR_LEVEL_ONE_FIELD_PREFIX = "level_one_id";
    String CAR_LEVEL_TWO_FIELD_PREFIX = "level_two_id";

    String CAR_LEVEL_ONE_PREFIX = "modelLevel_";
    String CAR_LEVEL_TWO_PREFIX = "secondModelLevel_";
    String EMPTY_STRING = "";

    String HTTP_RESULT_STATUS = "status";
    String HTTP_RESULT_DATA = "data";
    String URL_SPILT_MARK = "?";

    String CACHE_ENV_PREFIX = "prod_";

    String NEGATIVE = "negative";
    String POSITIVE = "positive";
    String INDECATORTYPE = "1";
    String PPT_FIELD_PREFIX = ".pptx";

    String DATE_MONTH_FORMAT_STR = "yyyy-MM";
    String DATE_FORMAT_STR = "yyyy-MM-dd";
    String DATETIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    String DATE_YEAR_FORMAT_STR = "yyyy";

    String SALE_LINE_NAME = "乘用车整体";
    String SALE_LINE_NAME_JCEN = "take";
    String SALE_LINE_NAME_JCSUV = "suv";
    String SHAPE_LINE = "line";
    String SHAPE_BAR = "bar";


    String NEWS = "news";
    String VIDEO = "video";

    int FIVE_PAGE = 5;

    int PRE_YEAR = 12;

    String PRAISE_GOOD = "好评声量";
    String PRAISE_BAD = "抱怨声量";
    String PRAISE_TOTAL = "总声量";
    String PRAISE_INDEX = "情感指数";

    String PRAISE_LEVEL_CAR_INFO = "本品好评声量占比,本品抱怨声量占比,级别好评声量占比,级别抱怨声量占比,本品情感指数,级别情感指数";

    Double ZERO_DOUBLE = 0D;
    Integer ZERO_INTEGER = 0;
    Integer TEN_INTEGER = 10;

    Integer ONE_THOUSAND = 1000;
    Integer TWO_THOUSAND = 2000;
    Integer ES_SEARCH_RETURN_SIZE = 2000;

    /**
     * 关注
     */
    String ATTENTION = "attention";
    /**
     * 兴趣
     */
    String INTEREST = "interest";
    /**
     * 意向
     */
    String INTENTION = "intention";
    /**
     * 线索
     */
    String LEADS = "leads";

    String BRAND_TYPE_LUXURY = "豪华品牌";
    String BRAND_TYPE_OTHER = "其他品牌";

}
