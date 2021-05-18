// package com.jd.jxpp.scm.opencity.common.response;
//
// import lombok.Data;
//
// import java.io.Serializable;
//
//
// /**
//  * @program: platform-operation
//  * @description:
//  * @author: yangh
//  * @create: 2020-04-07 14:45
//  **/
// @Data
// public class GatewayResult<T> implements Serializable {
//
//     private static final long serialVersionUID = -1658160066631207786L;
//
//     private Boolean success; // true成功 false失败
//
//     /** 结果码 */
//     private Integer code;
//
//     /** 结果描述 */
//     private String message;
//
//     /** 结果数据 **/
//     private GatewayDataResult<T> data;
//
//     public void setData(T data) {
//         this.data = new GatewayDataResult<>();
//         this.data.setData(data);
//     }
//
//     public void setData(GatewayDataResult<T> result) {
//         if (null == result) {
//             this.data = new GatewayDataResult<>();
//         } else {
//             this.data = result;
//         }
//     }
//
//     public GatewayResult buildSuccess(T data){
//         GatewayResult result = new GatewayResult();
//         result.setSuccess(true);
//         result.setCode(200);
//         if(null != data){
//             GatewayDataResult dataResult = new GatewayDataResult<T>();
//             dataResult.setData(data);
//             result.setData(dataResult);
//         }
//         return result;
//     }
//
//     public GatewayResult buildSuccess(){
//         GatewayResult result = new GatewayResult();
//         result.setSuccess(true);
//         result.setCode(200);
//         return result;
//     }
//
//     public GatewayResult buildFail(String message){
//         GatewayResult result = new GatewayResult();
//         result.setSuccess(false);
//         result.setCode(100);
//         result.setMessage(message);
//         return result;
//     }
//
// }
