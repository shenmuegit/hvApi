package bbcat.constant;

import lombok.Data;

@Data
public class Result {

    private Integer code;

    private Object data;

    private Long total;

    private Boolean success;

    private Result(Integer code,Object data){
        this.code = code;
        this.data = data;
        this.total = 0L;
        this.success = true;
    }

    private Result(Integer code,Object data,Long total){
        this.code = code;
        this.data = data;
        this.total = total;
        this.success = true;
    }

    private Result(Integer code,Object data,Long total,Boolean success){
        this.code = code;
        this.data = data;
        this.total = total;
        this.success = success;
    }


    private Result(){

    }

    public static Result build(){
        return new Result();
    }

    public static Result build(Integer code,Object data){
        return new Result(code,data);
    }

    public static Result build(Integer code,Object data,Long total){
        return new Result(code,data,total);
    }

    public static Result build(Integer code,Object data,Long total,Boolean success){
        return new Result(code,data,total,success);
    }
    public static Result success(){
        return build(200,null);
    }

    public static Result success(Object data){
        return build(200,data);
    }

    public static Result success(Object data,Long total){
        return build(200,data,total,true);
    }

    public static Result fail(Object data){
        return build(500,data);
    }

    public Result setData(Object data){
        this.data = data;
        return this;
    }

    public Result setCode(Integer code){
        this.code = code;
        return this;
    }
}
