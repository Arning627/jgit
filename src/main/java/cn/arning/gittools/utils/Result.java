package cn.arning.gittools.utils;

/**
 * @author arning
 */
public class Result {

    private String success;

    private String error;

    private String message;

    public static Result success() {
        Result result = new Result();
        result.success = "成功";
        return result;
    }

    public static Result error(String message) {
        Result result = new Result();
        result.error = "失败";
        result.message = message;
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.error = "失败";
        return result;
    }


}
